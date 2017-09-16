package eastonium.nuicraft.machine.reservior;

import java.util.Map;

import org.apache.logging.log4j.Level;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileInventoryReservior extends TileEntity implements ITickable, ISidedInventory, IFluidHandler {
	public static final int TOTAL_SLOTS_COUNT = ContainerReservior.RESERVIOR_SLOTS_COUNT;
	private static final int INPUT_TO_EMPTY_SLOT = ContainerReservior.INPUT_TO_EMPTY_SLOT_NUMBER;
	private static final int OUTPUT_EMPTIED_SLOT = ContainerReservior.OUTPUT_EMPTIED_SLOT_NUMBER;
	private static final int INPUT_TO_FILL_SLOT = ContainerReservior.INPUT_TO_FILL_SLOT_NUMBER;
	private static final int OUTPUT_FILLED_SLOT = ContainerReservior.OUTPUT_FILLED_SLOT_NUMBER;

	private NonNullList<ItemStack> slotStacks = NonNullList.<ItemStack>withSize(ContainerReservior.RESERVIOR_SLOTS_COUNT, ItemStack.EMPTY);
    protected FluidTank tank = new FluidTank(Fluid.BUCKET_VOLUME * 16);

    public double fractionOfFluid(){
		if(isTankEmpty()) return 0;
		double fraction = tank.getFluidAmount() / (double)tank.getCapacity();
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
		if(this.isTankEmpty() || resource.isFluidEqual(tank.getFluid())){
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
		if (tank.getFluid() != null) NuiCraft.logger.log(Level.INFO, tank.getFluid().getFluid().getName() + " " + tank.getFluidAmount());
		if(!world.isRemote){
			if(!slotStacks.get(INPUT_TO_EMPTY_SLOT).isEmpty()) {
				if (this.isTankEmpty() || FluidUtil.getFluidContained(slotStacks.get(INPUT_TO_EMPTY_SLOT)).isFluidEqual(tank.getFluid())) {
					FluidUtil.tryEmptyContainer(slotStacks.get(INPUT_TO_EMPTY_SLOT), this, 1000, null, true);
				}
			}
			if(!slotStacks.get(INPUT_TO_FILL_SLOT).isEmpty()) {
				
			}
//			if(!slotStacks.get(FUEL_SLOT).isEmpty()){
//				ItemStack item = slotStacks.get(FUEL_SLOT);
//				if(item.getItem() == Items.LAVA_BUCKET && tank.getCapacity() - tank.getFluidAmount() >= BUCKET_VOLUME){
//					fill(new FluidStack(FluidRegistry.LAVA, BUCKET_VOLUME), true);
//					setInventorySlotContents(FUEL_SLOT, item.getItem().getContainerItem(slotStacks.get(FUEL_SLOT)));
//					needsUpdate = true;
//				}
//			}
//			if(!isTankEmpty() && currentRecipe != null){
//				drain(2, true);
//				++cookTime;
//				if(cookTime >= currentRecipe.getRecipeDuration()){
//					cookTime = currentRecipe.getRecipeDuration(); //prevent over-cooking
//					if (smeltItem()) {
//						updateCurrentRecipe();
//						cookTime -= currentRecipe.getRecipeDuration();
//					}
//				}
//				needsUpdate = true;
//			}
//			if(currentRecipe == null){
//				cookTime = 0;
//			}
//			if (world.getBlockState(pos).getValue(BlockMaskForge.LEVEL) != (int)Math.ceil(fractionOfFuelRemaining() * 4)){
//				needsUpdate = true;
//				BlockMaskForge.updateLavaLevel((int)Math.ceil(fractionOfFuelRemaining() * 4), world, pos);
//			}
		}		
		if (needsUpdate){
			markDirty();
		}
	}
	
	@Override
	public int getSizeInventory(){
		return slotStacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot){
		return slotStacks.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if (itemStackInSlot.isEmpty()) return ItemStack.EMPTY;

		ItemStack itemStackRemoved;
		if (itemStackInSlot.getCount() <= count) {
			itemStackRemoved = itemStackInSlot;
			slotStacks.set(slotIndex, ItemStack.EMPTY);
		} else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.getCount() == 0) {
				slotStacks.set(slotIndex, ItemStack.EMPTY);
			}
		}
		markDirty();
		return itemStackRemoved;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (!itemStack.isEmpty()) slotStacks.set(slotIndex, ItemStack.EMPTY);
		markDirty();
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack){
		slotStacks.set(slotIndex, itemstack);
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
		if (itemstack.isEmpty()) {
			return false;
		} else if (i == INPUT_TO_EMPTY_SLOT) {
			return isItemValidForEmptyingSlot(itemstack);
		} else if (i == INPUT_TO_FILL_SLOT) {
			return isItemValidForFillingSlot(itemstack);
		} else return false;
	}
	
	static public boolean isItemValidForEmptyingSlot(ItemStack itemstack){
		if (itemstack.isEmpty()) return false;
		FluidStack fluidStack = FluidUtil.getFluidContained(itemstack);
		return fluidStack != null && fluidStack.amount > 0;
	}
	
	static public boolean isItemValidForFillingSlot(ItemStack itemstack){
		if (itemstack.isEmpty()) return false;
		return FluidUtil.getFluidHandler(itemstack) != null;
	}	
	
	private static final byte FLUID_AMOUNT_FIELD_ID = 0;
	private static final byte FLUID_ID_FIELD_ID = 1;
	private static final byte NUMBER_OF_FIELDS = 0;//TODO put back if new stuff doesnt work during testing

	@Override
	public int getField(int id){
		if (id == FLUID_AMOUNT_FIELD_ID) return tank.getFluidAmount();
		if (id == FLUID_ID_FIELD_ID) {
			if (tank.getFluid() == null) return 0;
			return FluidRegistry.getRegisteredFluidIDs().get(tank.getFluid().getFluid());
		}
		System.err.println("Invalid field ID in TileInventoryReservior.getField:" + id);
		return 0;
	}

	@Override
	public void setField(int id, int value){
		if (id == FLUID_AMOUNT_FIELD_ID) {
			if (tank.getFluid() != null) tank.getFluid().amount = value;
		} else if (id == FLUID_ID_FIELD_ID) {
			for (Map.Entry<Fluid, Integer> entry : FluidRegistry.getRegisteredFluidIDs().entrySet()) {
				if (entry.getValue() == value) {
					tank.setFluid(new FluidStack(entry.getKey(), tank.getFluidAmount()));
					break;
				}
			}
			
		} else {
			System.err.println("Invalid field ID in TileInventoryReservior.setField:" + id);
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
		for (int i = 0; i < slotStacks.size(); ++i){
			if (!slotStacks.get(i).isEmpty()){
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte)i);
				slotStacks.get(i).writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		tagCompound.setTag("Items", dataForAllSlots);		
		
		tagCompound.setTag("Fluid", tank.writeToNBT(new NBTTagCompound()));
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
			if (slotNumber >= 0 && slotNumber < slotStacks.size()){
				slotStacks.set(slotNumber, new ItemStack(dataForOneSlot));
			}
		}
		
		tank.readFromNBT(nbtTagCompound.getCompoundTag("Fluid"));
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		NuiCraft.logger.log(Level.INFO, nbtTagCompound);
		return new SPacketUpdateTileEntity(pos, 0, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NuiCraft.logger.log(Level.INFO, pkt.getNbtCompound());
		readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }
	
	@Override
	public void clear() {
		slotStacks = NonNullList.<ItemStack>withSize(TOTAL_SLOTS_COUNT, ItemStack.EMPTY);
	}

	@Override
	public String getName(){
		return "Reservior";
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
		if (index == INPUT_TO_EMPTY_SLOT) {
			return isItemValidForEmptyingSlot(itemStackIn);
		} else if (index == INPUT_TO_FILL_SLOT) {
			return isItemValidForFillingSlot(itemStackIn);
		} else return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == OUTPUT_EMPTIED_SLOT || index == OUTPUT_FILLED_SLOT;
	}
}
