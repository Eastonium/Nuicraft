 package eastonium.nuicraft.machine.reservior;

import org.apache.logging.log4j.Level;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.machine.maskForge.TileInventoryMaskForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerReservior extends Container {
	private TileInventoryReservior tileEntity;
	private int [] cachedFields;

	private static final int INV_START = 4, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;
	public static final int INV_SIZE = 36;

	// must assign a slot index to each of the slots used by the GUI.
	// For this container, we can see the furnace fuel, input, and output slots as well as the player inventory slots and the hotbar.
	// Each time we add a Slot to the container using addSlotToContainer(), it automatically increases the slotIndex, which means
	//  0  - 8  = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
	//  9  - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
	//  36      = fuel slots (tileEntity 0)
	//  37 - 42 = input slots (tileEntity 1 - 7)
	//  43      = output slots (tileEntity 8)

	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

	public static final int RESERVIOR_SLOTS_COUNT = 4;

	// slot index is the unique index for all slots in this container i.e. 0 - 35 for invPlayer then 36 - 49 for tileInventory
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int INPUT_TO_EMPTY_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private static final int OUTPUT_EMPTIED_SLOT_INDEX = INPUT_TO_EMPTY_SLOT_INDEX + 1;
	private static final int INPUT_TO_FILL_SLOT_INDEX = OUTPUT_EMPTIED_SLOT_INDEX + 1;
	private static final int OUTPUT_FILLED_SLOT_INDEX = INPUT_TO_FILL_SLOT_INDEX + 1;

	// slot number is the slot number within each component; i.e. invPlayer slots 0 - 35, and tileInventory slots 0 - 14
	public static final int INPUT_TO_EMPTY_SLOT_NUMBER = 0;
	public static final int OUTPUT_EMPTIED_SLOT_NUMBER = INPUT_TO_EMPTY_SLOT_NUMBER + 1;
	public static final int INPUT_TO_FILL_SLOT_NUMBER = OUTPUT_EMPTIED_SLOT_NUMBER + 1;
	public static final int OUTPUT_FILLED_SLOT_NUMBER = INPUT_TO_FILL_SLOT_NUMBER + 1;

	public ContainerReservior(InventoryPlayer playerInv, TileInventoryReservior tileEntity) {
		this.tileEntity = tileEntity;

		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 142;
		for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
			int slotNumber = x;
			addSlotToContainer(new Slot(playerInv, slotNumber, HOTBAR_XPOS + 18 * x, HOTBAR_YPOS));
		}
		final int PLAYER_INVENTORY_XPOS = 8;
		final int PLAYER_INVENTORY_YPOS = 84;
		for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
			for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
				int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
				int xpos = PLAYER_INVENTORY_XPOS + x * 18;
				int ypos = PLAYER_INVENTORY_YPOS + y * 18;
				addSlotToContainer(new Slot(playerInv, slotNumber,  xpos, ypos));
			}
		}

		final int SLOT_XPOS1 = 50;
		final int SLOT_YPOS1 = 18;
		final int SLOT_XPOS2 = 110;
		final int SLOT_YPOS2 = 45;
		addSlotToContainer(new SlotInputToEmpty(tileEntity, INPUT_TO_EMPTY_SLOT_NUMBER, SLOT_XPOS1, SLOT_YPOS1));
		addSlotToContainer(new SlotInputToEmpty(tileEntity, OUTPUT_EMPTIED_SLOT_NUMBER, SLOT_XPOS1, SLOT_YPOS2));
		addSlotToContainer(new SlotInputToEmpty(tileEntity, INPUT_TO_FILL_SLOT_NUMBER, SLOT_XPOS2, SLOT_YPOS1));
		addSlotToContainer(new SlotInputToEmpty(tileEntity, OUTPUT_FILLED_SLOT_NUMBER, SLOT_XPOS2, SLOT_YPOS2));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUsableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex) {
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			if (TileInventoryReservior.isItemValidForEmptyingSlot(sourceStack)) {
				if (!mergeItemStack(sourceStack, INPUT_TO_EMPTY_SLOT_INDEX, INPUT_TO_EMPTY_SLOT_INDEX + 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (TileInventoryReservior.isItemValidForFillingSlot(sourceStack)) {
				if (!mergeItemStack(sourceStack, INPUT_TO_FILL_SLOT_INDEX, INPUT_TO_FILL_SLOT_INDEX + 1, false)) {
					return ItemStack.EMPTY;
				}
			} else return ItemStack.EMPTY;
		} else if (sourceSlotIndex >= INPUT_TO_EMPTY_SLOT_INDEX && sourceSlotIndex < INPUT_TO_EMPTY_SLOT_INDEX + RESERVIOR_SLOTS_COUNT) {
			if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			NuiCraft.logger.log(Level.ERROR, "Invalid slotIndex:" + sourceSlotIndex);
			return ItemStack.EMPTY;
		}

		sourceSlot.onTake(player, sourceStack);
		return copyOfSourceStack;
	}
	
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();

		boolean allFieldsHaveChanged = false;
		boolean fieldHasChanged [] = new boolean[tileEntity.getFieldCount()];
		if (cachedFields == null) {
			cachedFields = new int[tileEntity.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		for (int i = 0; i < cachedFields.length; ++i) {
			if (allFieldsHaveChanged || cachedFields[i] != tileEntity.getField(i)) {
				cachedFields[i] = tileEntity.getField(i);
				fieldHasChanged[i] = true;
			}
		}

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);		
			for (int fieldID = 0; fieldID < tileEntity.getFieldCount(); ++fieldID) {
				if (fieldHasChanged[fieldID]) {
					icontainerlistener.sendWindowProperty(this, fieldID, cachedFields[fieldID]);
				}
			}
		}
	}
	

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		this.tileEntity.setField(id, data);
	}
	
	public class SlotInputToFill extends Slot {
		public SlotInputToFill(IInventory inventoryIn, int index, int xPosition, int yPosition){
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileInventoryReservior.isItemValidForFillingSlot(stack);
		}
	}
	public class SlotInputToEmpty extends Slot {
		public SlotInputToEmpty(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileInventoryReservior.isItemValidForEmptyingSlot(stack);
		}
	}
	public class SlotOutput extends Slot {
		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			return false;
		}
	}
}
