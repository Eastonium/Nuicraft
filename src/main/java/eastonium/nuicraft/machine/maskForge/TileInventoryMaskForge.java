package eastonium.nuicraft.machine.maskForge;

import java.util.Arrays;

import eastonium.nuicraft.Bionicle;
import eastonium.nuicraft.machine.maskForge.recipe.IMFRecipe;
import eastonium.nuicraft.machine.maskForge.recipe.MFRecipeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileInventoryMaskForge extends TileEntity implements ITickable, IInventory, IFluidHandler{
	public static final int BUCKET_VOLUME = 1000;
	
	public static final int FUEL_SLOTS_COUNT = 1;
	public static final int INPUT_SLOTS_COUNT = 6;
	public static final int OUTPUT_SLOTS_COUNT = 1;
	public static final int TOTAL_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

	public static final int FIRST_FUEL_SLOT = 0;
	public static final int FIRST_INPUT_SLOT = FIRST_FUEL_SLOT + FUEL_SLOTS_COUNT;
	public static final int FIRST_OUTPUT_SLOT = FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT;

	private ItemStack[] forgeItemstacks = new ItemStack[TOTAL_SLOTS_COUNT];
    protected FluidTank tank = new FluidTank(BUCKET_VOLUME * 4);

	public int cookTime = 0;

	private static final short COOK_TIME_FOR_COMPLETION = 200;

	public double fractionOfFuelRemaining(){
		if(isTankEmpty()) return 0;
		double fraction = this.tank.getFluidAmount() / (double)this.tank.getCapacity();
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}

	public double fractionLeftOfCompletion(){
		double fraction = this.cookTime / (double)COOK_TIME_FOR_COMPLETION;
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}
	
	public boolean isTankFull(){
		return this.tank.getFluidAmount() == this.tank.getCapacity();
	}
	
	public boolean isTankEmpty(){
		return this.tank.getFluidAmount() == 0;
	}
	
	public ItemStack[] getInputItemStacks(){
		ItemStack[] itemstacks = new ItemStack[INPUT_SLOTS_COUNT];
		for(int i = FIRST_INPUT_SLOT; i < FIRST_OUTPUT_SLOT; i++){
			itemstacks[i-FUEL_SLOTS_COUNT] = forgeItemstacks[i];
		}
		return itemstacks;
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
		boolean hasLava = !this.isTankEmpty();
		boolean needsUpdate = false;

		if(!this.worldObj.isRemote){
			if(this.forgeItemstacks[FIRST_FUEL_SLOT] != null){
				ItemStack item = this.forgeItemstacks[FIRST_FUEL_SLOT];
				if(item.getItem() == Items.LAVA_BUCKET && this.tank.getCapacity() - this.tank.getFluidAmount() >= BUCKET_VOLUME){
					this.fill(new FluidStack(FluidRegistry.LAVA, BUCKET_VOLUME), true);
					this.forgeItemstacks[FIRST_FUEL_SLOT] = item.getItem().getContainerItem(forgeItemstacks[FIRST_FUEL_SLOT]);
					needsUpdate = true;
				}/*else if(item.getItem() instanceof IFluidContainerItem){
					this.fill(null, ((IFluidContainerItem)item.getItem()).drain(item, this.tank.getCapacity()-this.tank.getFluidAmount(), true), true);
				}*///TODO make sure this system works
			}
			if(hasLava && this.canSmelt()){
				this.drain(2, true);
				++this.cookTime;
				if(this.cookTime == COOK_TIME_FOR_COMPLETION){
					this.cookTime = 0;
					this.smeltItem();
					needsUpdate = true;
				}
			}
			if(!this.canSmelt()){
				this.cookTime = 0;
			}
			if (this.worldObj.getBlockState(pos).getValue(BlockMaskForge.LEVEL) != (int)Math.ceil(this.fractionOfFuelRemaining() * 4)){
				needsUpdate = true;
				BlockMaskForge.updateLavaLevel((int)Math.ceil(this.fractionOfFuelRemaining() * 4), this.worldObj, this.pos);
			}
		}		
		if (needsUpdate){
			this.markDirty();
		}
	}

	private boolean canSmelt(){
		byte emptySlots = 0;
		for(int i = FIRST_INPUT_SLOT; i < FIRST_OUTPUT_SLOT; i++){
			if(this.forgeItemstacks[i] == null){
				emptySlots++;
			}
		}
		if(emptySlots >= 6) return false;
		
		IMFRecipe matchingRecipe = MFRecipeManager.getInstance().getMatchingRecipe(getInputItemStacks());
		if (matchingRecipe == null) return false;
		
		ItemStack outputItemstack = matchingRecipe.getOutput();
		if (this.forgeItemstacks[FIRST_OUTPUT_SLOT] == null) return true;
		if (this.forgeItemstacks[FIRST_OUTPUT_SLOT].getItem() == Bionicle.kanokaDisc) return false;
		if (!this.forgeItemstacks[FIRST_OUTPUT_SLOT].isItemEqual(outputItemstack)) return false;
		int result = forgeItemstacks[FIRST_OUTPUT_SLOT].stackSize + outputItemstack.stackSize;
		return (result <= getInventoryStackLimit() && result <= outputItemstack.getMaxStackSize());
	}

	public boolean smeltItem(){
		if(!this.canSmelt()) return false;		
		IMFRecipe matchingRecipe = MFRecipeManager.getInstance().getMatchingRecipe(getInputItemStacks());
		if (matchingRecipe == null) return false;
		
		ItemStack outputItemstack = matchingRecipe.getOutput();
		ItemStack[] newInputstacks = matchingRecipe.getRemainingItems();

		if(this.forgeItemstacks[FIRST_OUTPUT_SLOT] == null){
			this.forgeItemstacks[FIRST_OUTPUT_SLOT] = outputItemstack.copy();
		}else if(this.forgeItemstacks[FIRST_OUTPUT_SLOT].isItemEqual(outputItemstack)){
			forgeItemstacks[FIRST_OUTPUT_SLOT].stackSize += outputItemstack.stackSize;
		}
		for(int i = FIRST_INPUT_SLOT; i < FIRST_OUTPUT_SLOT; i++){
			forgeItemstacks[i] = newInputstacks[i-FUEL_SLOTS_COUNT];
		}
		return true;
	}
	

	@Override
	public int getSizeInventory(){
		return this.forgeItemstacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1){
		return this.forgeItemstacks[par1];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int count){
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if(itemStackInSlot == null) return null;

		ItemStack itemStackRemoved;
		if(itemStackInSlot.stackSize <= count){
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, null);
		}else{
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.stackSize == 0){
				setInventorySlotContents(slotIndex, null);
			}
		}
		markDirty();
		return itemStackRemoved;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null) setInventorySlotContents(slotIndex, null);
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack){
		forgeItemstacks[slotIndex] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player){
		if (this.worldObj.getTileEntity(this.pos) != this) return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack){
		if(i == FIRST_OUTPUT_SLOT){
			return false;
		}else if(i == FIRST_FUEL_SLOT){
			return isItemValidForFuelSlot(itemstack);
		}else return this.isItemValidForInputSlot(itemstack);
	}
	
	static public boolean isItemValidForFuelSlot(ItemStack itemStack){
		return (itemStack.getItem() == Items.LAVA_BUCKET || itemStack.getItem() instanceof IFluidContainerItem);
	}
	
	static public boolean isItemValidForInputSlot(ItemStack itemstack){
		/*Item item = itemstack.getItem();
		if(item instanceof ItemMask || item instanceof ItemKanoka || item instanceof ItemMataMask || item instanceof ItemModeledMask || item instanceof ItemMaskIgnika) return true;
		if(item == Bionicle.ingotProtodermis) return true;
		return false;
		*/
		return true;
	}	
	
	private static final byte COOK_FIELD_ID = 0;
	private static final byte FUEL_AMOUNT_FIELD_ID = 1;
	private static final byte NUMBER_OF_FIELDS = 2;

	@Override
	public int getField(int id){
		if(id == COOK_FIELD_ID) return cookTime;
		if(id == FUEL_AMOUNT_FIELD_ID) return this.tank.getFluidAmount();
		System.err.println("Invalid field ID in TileInventoryMaskForge.getField:" + id);
		return 0;
	}

	@Override
	public void setField(int id, int value){
		if(id == COOK_FIELD_ID){
			cookTime = (short)value;
		}else if(id == FUEL_AMOUNT_FIELD_ID){
			FluidStack flStack = new FluidStack(FluidRegistry.LAVA, Math.abs(this.tank.getFluidAmount() - value));
			if(value > this.tank.getFluidAmount()){
				this.fill(flStack, true);
			}else if(value < this.tank.getFluidAmount()){
				this.drain(flStack, true);
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
		for (int i = 0; i < this.forgeItemstacks.length; ++i){
			if (this.forgeItemstacks[i] != null){
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte)i);
				this.forgeItemstacks[i].writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		tagCompound.setTag("Items", dataForAllSlots);
		
		tagCompound.setShort("CookTime", (short)this.cookTime);
		tagCompound.setShort("fuelAmount", (short)this.tank.getFluidAmount());
		return tagCompound;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound){
		super.readFromNBT(nbtTagCompound);
		final byte NBT_TYPE_COMPOUND = 10;  // See NBTBase.createNewByType() for a listing
		NBTTagList dataForAllSlots = nbtTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);
		
		this.clear();
		for(int i = 0; i < dataForAllSlots.tagCount(); ++i){
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			byte slotNumber = dataForOneSlot.getByte("Slot");
			if (slotNumber >= 0 && slotNumber < this.forgeItemstacks.length){
				this.forgeItemstacks[slotNumber] = ItemStack.loadItemStackFromNBT(dataForOneSlot);
			}
		}
		this.cookTime = nbtTagCompound.getShort("CookTime");
		this.fill(new FluidStack(FluidRegistry.LAVA, nbtTagCompound.getShort("fuelAmount")), true);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		final int METADATA = 0;
		return new SPacketUpdateTileEntity(this.pos, METADATA, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
	
	@Override
	public void clear() {
		Arrays.fill(forgeItemstacks, null);
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
		return new TextComponentString(this.getName());
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

}
