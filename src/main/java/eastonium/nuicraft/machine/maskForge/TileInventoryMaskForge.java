package eastonium.nuicraft.machine.maskForge;

import java.util.Arrays;

import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.machine.maskForge.recipe.IMFRecipe;
import eastonium.nuicraft.machine.maskForge.recipe.MFRecipeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileInventoryMaskForge extends TileEntity implements ITickable, ISidedInventory, IFluidHandler{
	public static final int BUCKET_VOLUME = 1000;
	
	public static final int FUEL_SLOTS_COUNT = 1;
	public static final int INPUT_SLOTS_COUNT = 6;
	public static final int OUTPUT_SLOTS_COUNT = 1;
	public static final int TOTAL_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

	public static final int FUEL_SLOT = 0;
	public static final int FIRST_INPUT_SLOT = FUEL_SLOT + FUEL_SLOTS_COUNT;
	public static final int OUTPUT_SLOT = FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT;

	private NonNullList<ItemStack> forgeItemstacks = NonNullList.<ItemStack>withSize(TOTAL_SLOTS_COUNT, ItemStack.EMPTY);
    protected FluidTank tank = new FluidTank(BUCKET_VOLUME * 4);

	public int cookTime = 0;

	private static final short COOK_TIME_FOR_COMPLETION = 200;

	public double fractionOfFuelRemaining(){
		if(isTankEmpty()) return 0;
		double fraction = tank.getFluidAmount() / (double)tank.getCapacity();
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}

	public double fractionLeftOfCompletion(){
		double fraction = cookTime / (double)COOK_TIME_FOR_COMPLETION;
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}
	
	public boolean isTankFull(){
		return tank.getFluidAmount() == tank.getCapacity();
	}
	
	public boolean isTankEmpty(){
		return tank.getFluidAmount() == 0;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill){
		if(resource.getFluid() == FluidRegistry.LAVA){
	        return tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain){
		if(resource == null || !resource.isFluidEqual(tank.getFluid())){
            return null;
        }
        return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain){
		return tank.drain(maxDrain, doDrain);
	}

	/*
	@Override
	public boolean canFill(Fluid fluid){
		return fluid == FluidRegistry.LAVA;
	}

	@Override
	public boolean canDrain(Fluid fluid){
		return fluid == FluidRegistry.LAVA;
	}*/

	@Override
	public IFluidTankProperties[] getTankProperties(){
		return tank.getTankProperties();
	}
	
	@Override
	public void update(){
		boolean hasLava = !isTankEmpty();
		boolean needsUpdate = false;

		if(!world.isRemote){
			if(!forgeItemstacks.get(FUEL_SLOT).isEmpty()){
				ItemStack item = forgeItemstacks.get(FUEL_SLOT);
				if(item.getItem() == Items.LAVA_BUCKET && tank.getCapacity() - tank.getFluidAmount() >= BUCKET_VOLUME){
					fill(new FluidStack(FluidRegistry.LAVA, BUCKET_VOLUME), true);
					setInventorySlotContents(FUEL_SLOT, item.getItem().getContainerItem(forgeItemstacks.get(FUEL_SLOT)));
					needsUpdate = true;
				}/*else if(item.getItem() instanceof IFluidContainerItem){
					fill(null, ((IFluidContainerItem)item.getItem()).drain(item, tank.getCapacity()-tank.getFluidAmount(), true), true);
				}*///TODO make sure this system works
			}
			if(hasLava && canSmelt()){
				drain(2, true);
				++cookTime;
				if(cookTime == COOK_TIME_FOR_COMPLETION){
					cookTime = 0;
					smeltItem();
					needsUpdate = true;
				}
			}
			if(!canSmelt()){
				cookTime = 0;
			}
			if (world.getBlockState(pos).getValue(BlockMaskForge.LEVEL) != (int)Math.ceil(fractionOfFuelRemaining() * 4)){
				needsUpdate = true;
				BlockMaskForge.updateLavaLevel((int)Math.ceil(fractionOfFuelRemaining() * 4), world, pos);
			}
		}		
		if (needsUpdate){
			markDirty();
		}
	}

	private boolean canSmelt(){
		byte emptySlots = 0;
		for(int i = FIRST_INPUT_SLOT; i < OUTPUT_SLOT; i++){
			if(forgeItemstacks.get(i).isEmpty()){
				emptySlots++;
			}
		}
		if(emptySlots >= 6) return false;
		
		IMFRecipe matchingRecipe = MFRecipeManager.getInstance().getMatchingRecipe(getInputItemStacks());
		if (matchingRecipe == null) return false;
		
		ItemStack outputItemstack = matchingRecipe.getOutput();
		if (forgeItemstacks.get(OUTPUT_SLOT).isEmpty()) return true;
		if (forgeItemstacks.get(OUTPUT_SLOT).getItem() == NuiCraftItems.kanoka_disc) return false;
		if (!forgeItemstacks.get(OUTPUT_SLOT).isItemEqual(outputItemstack)) return false;
		int result = forgeItemstacks.get(OUTPUT_SLOT).getCount() + outputItemstack.getCount();
		return (result <= getInventoryStackLimit() && result <= outputItemstack.getMaxStackSize());
	}

	public boolean smeltItem(){
		if(!canSmelt()) return false;		
		IMFRecipe matchingRecipe = MFRecipeManager.getInstance().getMatchingRecipe(getInputItemStacks());
		if (matchingRecipe == null) return false;
		
		ItemStack outputItemstack = matchingRecipe.getOutput();
		NonNullList<ItemStack> newInputstacks = matchingRecipe.getRemainingItems();

		if(forgeItemstacks.get(OUTPUT_SLOT).isEmpty()){
			setInventorySlotContents(OUTPUT_SLOT, outputItemstack.copy());
		}else if(forgeItemstacks.get(OUTPUT_SLOT).isItemEqual(outputItemstack)){
			forgeItemstacks.get(OUTPUT_SLOT).grow(outputItemstack.getCount());
		}
		for(int i = FIRST_INPUT_SLOT; i < OUTPUT_SLOT; i++){
			setInventorySlotContents(i, newInputstacks.get(i-FUEL_SLOTS_COUNT));
		}
		return true;
	}
		
	public NonNullList<ItemStack> getInputItemStacks(){
		NonNullList<ItemStack> itemstacks = NonNullList.<ItemStack>withSize(INPUT_SLOTS_COUNT, ItemStack.EMPTY);
		for(int i = FIRST_INPUT_SLOT; i < OUTPUT_SLOT; i++){
			itemstacks.set(i-FUEL_SLOTS_COUNT, forgeItemstacks.get(i));
		}
		return itemstacks;
	}

	@Override
	public int getSizeInventory(){
		return forgeItemstacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot){
		return forgeItemstacks.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int count){
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if(itemStackInSlot.isEmpty()) return ItemStack.EMPTY;

		ItemStack itemStackRemoved;
		if(itemStackInSlot.getCount() <= count){
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, ItemStack.EMPTY);
		}else{
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.getCount() == 0){
				setInventorySlotContents(slotIndex, ItemStack.EMPTY);
			}
		}
		markDirty();
		return itemStackRemoved;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (!itemStack.isEmpty()) setInventorySlotContents(slotIndex, ItemStack.EMPTY);
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack){
		forgeItemstacks.set(slotIndex, itemstack);
		if (!itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit()) {
			itemstack.setCount(getInventoryStackLimit());
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player){
		if (world.getTileEntity(pos) != this) return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack){
		if (itemstack == null) {
			return false;
		}else if(i == OUTPUT_SLOT){
			return false;
		}else if(i == FUEL_SLOT){
			return isItemValidForFuelSlot(itemstack);
		}else return isItemValidForInputSlot(itemstack);
	}
	
	static public boolean isItemValidForFuelSlot(ItemStack itemStack){
		return (itemStack != null && (itemStack.getItem() == Items.LAVA_BUCKET || itemStack.getItem() instanceof IFluidHandlerItem));
	}
	
	static public boolean isItemValidForInputSlot(ItemStack itemstack){
		/*Item item = itemstack.getItem();
		if(item instanceof ItemMask || item instanceof ItemKanoka || item instanceof ItemMataMask || item instanceof ItemModeledMask || item instanceof ItemMaskIgnika) return true;
		if(item == Bionicle.ingotProtodermis) return true;
		return false;
		*/
		return itemstack != null;
	}	
	
	private static final byte COOK_FIELD_ID = 0;
	private static final byte FUEL_AMOUNT_FIELD_ID = 1;
	private static final byte NUMBER_OF_FIELDS = 2;

	@Override
	public int getField(int id){
		if(id == COOK_FIELD_ID) return cookTime;
		if(id == FUEL_AMOUNT_FIELD_ID) return tank.getFluidAmount();
		System.err.println("Invalid field ID in TileInventoryMaskForge.getField:" + id);
		return 0;
	}

	@Override
	public void setField(int id, int value){
		if(id == COOK_FIELD_ID){
			cookTime = (short)value;
		}else if(id == FUEL_AMOUNT_FIELD_ID){
			FluidStack flStack = new FluidStack(FluidRegistry.LAVA, Math.abs(tank.getFluidAmount() - value));
			if(value > tank.getFluidAmount()){
				fill(flStack, true);
			}else if(value < tank.getFluidAmount()){
				drain(flStack, true);
			}
		}else{
			System.err.println("Invalid field ID in TileInventoryMaskForge.setField:" + id);
		}
	}
	
	@Override
	public int getFieldCount() {
		return NUMBER_OF_FIELDS;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < forgeItemstacks.size(); ++i){
			if (!forgeItemstacks.get(i).isEmpty()){
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte)i);
				forgeItemstacks.get(i).writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		tagCompound.setTag("Items", dataForAllSlots);
		
		tagCompound.setShort("CookTime", (short)cookTime);
		tagCompound.setShort("fuelAmount", (short)tank.getFluidAmount());
		return tagCompound;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound){
		super.readFromNBT(nbtTagCompound);
		final byte NBT_TYPE_COMPOUND = 10;  // See NBTBase.createNewByType() for a listing
		NBTTagList dataForAllSlots = nbtTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);
		
		clear();
		for(int i = 0; i < dataForAllSlots.tagCount(); ++i){
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			byte slotNumber = dataForOneSlot.getByte("Slot");
			if (slotNumber >= 0 && slotNumber < forgeItemstacks.size()){
				setInventorySlotContents(slotNumber, new ItemStack(dataForOneSlot));
			}
		}
		cookTime = nbtTagCompound.getShort("CookTime");
		fill(new FluidStack(FluidRegistry.LAVA, nbtTagCompound.getShort("fuelAmount")), true);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		final int METADATA = 0;
		return new SPacketUpdateTileEntity(pos, METADATA, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
	
	@Override
	public void clear() {
		forgeItemstacks = NonNullList.<ItemStack>withSize(TOTAL_SLOTS_COUNT, ItemStack.EMPTY);
	}

	@Override
	public String getName(){
		return "Mask Forge";
	}

	@Override
	public boolean hasCustomName(){
		return false;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isEmpty() {
		return isTankEmpty();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int[] allSlots = new int[TOTAL_SLOTS_COUNT];
		for (int i = 0; i < TOTAL_SLOTS_COUNT; i++) {
			allSlots[i] = i;
		}
		return allSlots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		if (index == FUEL_SLOT) {
			return isItemValidForFuelSlot(itemStackIn);
		} else if (index >= FIRST_INPUT_SLOT && index < FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT) {
			return isItemValidForInputSlot(itemStackIn);
		} else return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == FUEL_SLOT || index == OUTPUT_SLOT;
	}
}
