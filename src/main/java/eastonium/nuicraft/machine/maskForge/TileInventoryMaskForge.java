package eastonium.nuicraft.machine.maskForge;

import java.util.Iterator;

import org.apache.logging.log4j.Level;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.machine.maskForge.recipe.IMFRecipe;
import eastonium.nuicraft.machine.maskForge.recipe.MaskForgeRecipeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileInventoryMaskForge extends TileEntity implements ITickable, ISidedInventory, IFluidHandler {
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
    private IMFRecipe currentRecipe = null;
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

	@Override
	public IFluidTankProperties[] getTankProperties(){
		return tank.getTankProperties();
	}
	
	@Override
	public void update(){
		boolean needsUpdate = false;

		if(!world.isRemote){
			if(!forgeItemstacks.get(FUEL_SLOT).isEmpty()){
				ItemStack item = forgeItemstacks.get(FUEL_SLOT);
				if(item.getItem() == Items.LAVA_BUCKET && tank.getCapacity() - tank.getFluidAmount() >= BUCKET_VOLUME){
					fill(new FluidStack(FluidRegistry.LAVA, BUCKET_VOLUME), true);
					setInventorySlotContents(FUEL_SLOT, item.getItem().getContainerItem(forgeItemstacks.get(FUEL_SLOT)));
					needsUpdate = true;
				}
			}
			if(!isTankEmpty() && currentRecipe != null){
				drain(2, true);
				++cookTime;
				if(cookTime >= COOK_TIME_FOR_COMPLETION){
					if (smeltItem()) {
						updateCurrentRecipe();
						cookTime = 0;
					}
				}
				needsUpdate = true;
			}
			if(currentRecipe == null){
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
	
	public void updateCurrentRecipe() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) return;
		currentRecipe = MaskForgeRecipeManager.getInstance().getMatchingRecipe(getInputItemStacks());
	}

	private boolean hasOutputRoom(){		
		ItemStack outputStack = currentRecipe.getOutput();
		if (forgeItemstacks.get(OUTPUT_SLOT).isEmpty()) return true;
		if (forgeItemstacks.get(OUTPUT_SLOT).getItem() == NuiCraftItems.kanoka_disc) return false;
		if (!forgeItemstacks.get(OUTPUT_SLOT).isItemEqual(outputStack)) return false;
		int result = forgeItemstacks.get(OUTPUT_SLOT).getCount() + outputStack.getCount();
		return (result <= getInventoryStackLimit() && result <= outputStack.getMaxStackSize());
	}

	public boolean smeltItem() {
		// this function does not use setInventorySlotContents to not update recipe
		if (!currentRecipe.matches(getInputItemStacks())) { // sanity check, and updates output/returnstacks since it is shared across all mask forges
			NuiCraft.logger.log(Level.ERROR, "The maskforge has somehow managed to not update its recipe. Please contact the author of NuiCraft.");
			return false;
		}
		if(!hasOutputRoom()) return false;
		
		ItemStack outputStack = currentRecipe.getOutput();

		if (forgeItemstacks.get(OUTPUT_SLOT).isEmpty()) {
			forgeItemstacks.set(OUTPUT_SLOT, outputStack.copy());
		} else /*if (forgeItemstacks.get(OUTPUT_SLOT).isItemEqual(outputStack)) */{
			forgeItemstacks.get(OUTPUT_SLOT).grow(outputStack.getCount());
		}
		
		Iterator<ItemStack> stacks = currentRecipe.getRemainingItems().iterator();
		int i = FIRST_INPUT_SLOT;
		while (stacks.hasNext()) { // will shift all items to the top, which is fine
//			ItemStack stack = stacks.next();
//			if (stack.isEmpty()) continue;
			forgeItemstacks.set(i, stacks.next());
			i++;
		}
		while (i < OUTPUT_SLOT) {
			forgeItemstacks.set(i, ItemStack.EMPTY);
			i++;
		}
		return true;
	}
		
	private NonNullList<ItemStack> getInputItemStacks() {
		NonNullList<ItemStack> itemstacks = NonNullList.create();
		for(int i = FIRST_INPUT_SLOT; i < OUTPUT_SLOT; i++) {
			if (!forgeItemstacks.get(i).isEmpty()) {
				itemstacks.add(forgeItemstacks.get(i));
			}
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
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if (itemStackInSlot.isEmpty()) return ItemStack.EMPTY;

		ItemStack itemStackRemoved;
		if (itemStackInSlot.getCount() <= count) {
			itemStackRemoved = itemStackInSlot;
			forgeItemstacks.set(slotIndex, ItemStack.EMPTY);
		} else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.getCount() == 0) {
				forgeItemstacks.set(slotIndex, ItemStack.EMPTY);
			}
		}
		markDirty();
		return itemStackRemoved;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (!itemStack.isEmpty()) forgeItemstacks.set(slotIndex, ItemStack.EMPTY);
		markDirty();
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
		if (itemstack.isEmpty() || i == OUTPUT_SLOT) {
			return false;
		} else if(i == FUEL_SLOT) {
			return isItemValidForFuelSlot(itemstack);
		} else {
			return isItemValidForInputSlot(itemstack);
		}
	}
	
	static public boolean isItemValidForFuelSlot(ItemStack itemStack){
		return !itemStack.isEmpty() && itemStack.getItem() == Items.LAVA_BUCKET;
	}
	
	static public boolean isItemValidForInputSlot(ItemStack itemstack){
		return true;
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
				forgeItemstacks.set(slotNumber, new ItemStack(dataForOneSlot));
			}
		}
		cookTime = nbtTagCompound.getShort("CookTime");
		fill(new FluidStack(FluidRegistry.LAVA, nbtTagCompound.getShort("fuelAmount")), true);
		
		updateCurrentRecipe();
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
