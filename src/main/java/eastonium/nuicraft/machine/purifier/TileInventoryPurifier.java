package eastonium.nuicraft.machine.purifier;

import java.util.Arrays;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftFluids;
import eastonium.nuicraft.machine.maskForge.recipe.IMFRecipe;
import eastonium.nuicraft.machine.maskForge.recipe.MaskForgeRecipeManager;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileInventoryPurifier extends TileEntity implements ITickable, IInventory, IFluidHandler {
	public static final int BUCKET_VOLUME = 1000;

	public static final int ITEM_SLOTS_COUNT = 5;
	public static final int TOTAL_SLOTS_COUNT = 3 + ITEM_SLOTS_COUNT;

	public static final int INPUT_SLOT_NUMBER = 0;
	public static final int FIRST_ITEM_SLOT_NUMBER = 1;
	public static final int FILTER_SLOT_NUMBER = FIRST_ITEM_SLOT_NUMBER + ITEM_SLOTS_COUNT;
	public static final int OUTPUT_SLOT_NUMBER = FILTER_SLOT_NUMBER + 1;

	private NonNullList<ItemStack> pureItemstacks = NonNullList.<ItemStack>withSize(TOTAL_SLOTS_COUNT, ItemStack.EMPTY);
	protected FluidTank rawTank = new FluidTank(BUCKET_VOLUME * 16);
	protected FluidTank pureTank = new FluidTank(BUCKET_VOLUME * 4);

	public int rawProgress = 0, pureProgress = 0;
	private static final short PROGRESS_SIZE = 50;

	public TileInventoryPurifier() {
		rawTank.setCanDrain(false);
		pureTank.setCanFill(false);
	}

	public double fractionOfTankRemaining(FluidTank tank) {
		if (isTankEmpty(tank))
			return 0;
		double fraction = tank.getFluidAmount() / (double) tank.getCapacity();
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}

	/*
	 * public double fractionLeftOfCompletion(){ double fraction = this.cookTime
	 * / (double)COOK_TIME_FOR_COMPLETION; return
	 * MathHelper.clamp_double(fraction, 0.0, 1.0); }
	 */

	public boolean isTankFull(FluidTank tank) {
		return tank.getFluidAmount() == tank.getCapacity();
	}

	public boolean isTankEmpty(FluidTank tank) {
		return tank.getFluidAmount() == 0;
	}

	/*
	 * public ItemStack[] getInputItemStacks(){ ItemStack[] itemstacks = new
	 * ItemStack[INPUT_SLOTS_COUNT]; for(int i = FIRST_INPUT_SLOT; i <
	 * FIRST_OUTPUT_SLOT; i++){ itemstacks[i-FUEL_SLOTS_COUNT] =
	 * pureItemstacks[i]; } return itemstacks; }
	 */

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource.getFluid() == NuiCraftFluids.protodermis) {
			return rawTank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource == null || resource.getFluid() != NuiCraftFluids.protodermis_pure) {
			return null;
		}
		return pureTank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return pureTank.drain(maxDrain, doDrain);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { rawTank.getTankProperties()[0], pureTank.getTankProperties()[0] };
	}

	@Override
	public void update() {
		boolean needsUpdate = false;

		if (!world.isRemote) {
			ItemStack inStack = pureItemstacks.get(INPUT_SLOT_NUMBER);
			ItemStack outStack = pureItemstacks.get(OUTPUT_SLOT_NUMBER);
			if (!inStack.isEmpty()) {
				if (ItemStack.areItemStacksEqual(inStack, FluidUtil.getFilledBucket(new FluidStack(NuiCraftFluids.protodermis, BUCKET_VOLUME)))
						&& rawTank.getCapacity() - rawTank.getFluidAmount() >= BUCKET_VOLUME) {
					fill(new FluidStack(NuiCraftFluids.protodermis, BUCKET_VOLUME), true);
					pureItemstacks.set(INPUT_SLOT_NUMBER, new ItemStack(Items.BUCKET, 1));
					needsUpdate = true;
				}
				if (inStack.getItem() == Items.BUCKET) {
					if (outStack.isEmpty()) {
						pureItemstacks.set(OUTPUT_SLOT_NUMBER, new ItemStack(Items.BUCKET, 1));
						inStack.shrink(1);
						needsUpdate = true;
					}/* else if (outStack.getItem() == Items.BUCKET) { // Don't use this because of how filling buckets works
						int moveSize = Math.min(inStack.getCount(), outStack.getMaxStackSize() - outStack.getCount());
						inStack.shrink(moveSize);
						outStack.grow(moveSize);
					}*/
				}
			}
			if (outStack.getItem() == Items.BUCKET && outStack.getCount() == 1 && pureTank.getFluidAmount() >= BUCKET_VOLUME) {
				pureItemstacks.set(OUTPUT_SLOT_NUMBER, FluidUtil.getFilledBucket(new FluidStack(NuiCraftFluids.protodermis_pure, BUCKET_VOLUME)));
				pureTank.drainInternal(BUCKET_VOLUME, true);
				needsUpdate = true;
			}
			if (!pureItemstacks.get(FILTER_SLOT_NUMBER).isEmpty() && !isTankEmpty(rawTank) && !isTankFull(pureTank)) {
				rawTank.drainInternal(2, true);
				pureTank.fillInternal(new FluidStack(NuiCraftFluids.protodermis_pure, 2), true);
				needsUpdate = true;
			}
		}
		if (needsUpdate) {
			markDirty();
		}
	}

	@Override
	public int getSizeInventory() {
		return pureItemstacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return pureItemstacks.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack currentStack = getStackInSlot(slotIndex);
		if (currentStack.isEmpty())
			return ItemStack.EMPTY;

		ItemStack newStack = ItemStack.EMPTY;
		if (currentStack.getCount() <= count) {
			newStack = currentStack;
			setInventorySlotContents(slotIndex, ItemStack.EMPTY);
		} else {
			newStack = currentStack.splitStack(count);
			if (currentStack.getCount() == 0) {
				setInventorySlotContents(slotIndex, ItemStack.EMPTY);
			}
		}
		markDirty();
		return newStack;
	}

	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack stack = getStackInSlot(slotIndex);
		if (!stack.isEmpty())
			setInventorySlotContents(slotIndex, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack stack) {
		pureItemstacks.set(slotIndex, stack);
		if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this)
			return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET,
				pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == INPUT_SLOT_NUMBER) {
			return isItemValidForInputSlot(stack);
		} else if (slot == FILTER_SLOT_NUMBER) {
			return isItemValidForFilterSlot(stack);
		} else if (slot == OUTPUT_SLOT_NUMBER) {
			return isItemValidForOutputSlot(stack);
		} else
			return false;
	}

	static public boolean isItemValidForInputSlot(ItemStack stack) {
		return ItemStack.areItemStacksEqual(stack, FluidUtil.getFilledBucket(new FluidStack(NuiCraftFluids.protodermis, BUCKET_VOLUME)));
	}

	static public boolean isItemValidForFilterSlot(ItemStack itemstack) {
		return true;// TODO choose things for filters
	}

	static public boolean isItemValidForOutputSlot(ItemStack stack) {
		return (stack.getItem() == Items.BUCKET);
	}

	private static final byte RAWPRO_FIELD_ID = 0;
	private static final byte PUREPRO_FIELD_ID = 1;
	private static final byte RAW_AMOUNT_FIELD_ID = 2;
	private static final byte PURE_AMOUNT_FIELD_ID = 3;
	private static final byte NUMBER_OF_FIELDS = 4;

	@Override
	public int getField(int id) {
		if (id == RAWPRO_FIELD_ID)
			return rawProgress;
		if (id == PUREPRO_FIELD_ID)
			return pureProgress;
		if (id == RAW_AMOUNT_FIELD_ID)
			return rawTank.getFluidAmount();
		if (id == PURE_AMOUNT_FIELD_ID)
			return pureTank.getFluidAmount();
		System.err.println("Invalid field ID in TileInventoryPurifier.getField:" + id);
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if (id == RAWPRO_FIELD_ID) {
			rawProgress = (short) value;
		} else if (id == PUREPRO_FIELD_ID) {
			pureProgress = (short) value;
		} else if (id == RAW_AMOUNT_FIELD_ID) {
			FluidStack flStack = new FluidStack(NuiCraftFluids.protodermis, Math.abs(rawTank.getFluidAmount() - value));
			if (value > rawTank.getFluidAmount()) {
				rawTank.fillInternal(flStack, true);
			} else if (value < rawTank.getFluidAmount()) {
				rawTank.drainInternal(flStack, true);
			}
		} else if (id == PURE_AMOUNT_FIELD_ID) {
			FluidStack flStack = new FluidStack(NuiCraftFluids.protodermis_pure,
					Math.abs(pureTank.getFluidAmount() - value));
			if (value > pureTank.getFluidAmount()) {
				pureTank.fillInternal(flStack, true);
			} else if (value < pureTank.getFluidAmount()) {
				pureTank.drainInternal(flStack, true);
			}
		} else {
			System.err.println("Invalid field ID in TileInventoryPurifier.setField:" + id);
		}
	}

	@Override
	public int getFieldCount() {
		return NUMBER_OF_FIELDS;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < pureItemstacks.size(); ++i) {
			if (!pureItemstacks.get(i).isEmpty()) {
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				pureItemstacks.get(i).writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		tagCompound.setTag("Items", dataForAllSlots);
		tagCompound.setShort("rawPro", (short) rawProgress);
		tagCompound.setShort("purePro", (short) pureProgress);
		tagCompound.setShort("rawAmount", (short) rawTank.getFluidAmount());
		tagCompound.setShort("pureAmount", (short) pureTank.getFluidAmount());
		return tagCompound;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		final byte NBT_TYPE_COMPOUND = 10; // See NBTBase.createNewByType() for a listing
		NBTTagList dataForAllSlots = nbtTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);
		clear();
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			byte slotNumber = dataForOneSlot.getByte("Slot");
			if (slotNumber >= 0 && slotNumber < pureItemstacks.size()) {
				setInventorySlotContents(slotNumber, new ItemStack(dataForOneSlot));
			}
		}
		rawProgress = nbtTagCompound.getShort("rawPro");
		pureProgress = nbtTagCompound.getShort("purePro");
		rawTank.fillInternal(new FluidStack(NuiCraftFluids.protodermis, nbtTagCompound.getShort("rawAmount")), true);
		pureTank.fillInternal(new FluidStack(NuiCraftFluids.protodermis_pure, nbtTagCompound.getShort("pureAmount")), true);
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
		pureItemstacks = NonNullList.<ItemStack>withSize(TOTAL_SLOTS_COUNT, ItemStack.EMPTY);
	}

	@Override
	public String getName() {
		return "Purifier";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isEmpty() {
		return false;// TODO do something with this?
	}
}
