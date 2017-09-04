package eastonium.nuicraft.machine.maskForge;

import org.apache.logging.log4j.Level;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMaskForge extends Container {
	private TileInventoryMaskForge maskForgeInv;
	private int [] cachedFields;

	private static final int INV_START = 8, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;
	/** Defining your inventory size this way is handy */
	public static final int INV_SIZE = 36;

	// must assign a slot index to each of the slots used by the GUI.
	// For this container, we can see the furnace fuel, input, and output slots as well as the player inventory slots and the hotbar.
	// Each time we add a Slot to the container using addSlotToContainer(), it automatically increases the slotIndex, which means
	//  0  - 8  = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
	//  9  - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
	//  36      = fuel slots (tileEntity 0)
	//  37 - 42 = input slots (tileEntity 1 - 7)
	//  43      = output slots (tileEntity 8)

	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

	public final int FUEL_SLOTS_COUNT = 1;
	public final int INPUT_SLOTS_COUNT = 6;
	public final int OUTPUT_SLOTS_COUNT = 1;
	public final int FURNACE_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

	// slot index is the unique index for all slots in this container i.e. 0 - 35 for invPlayer then 36 - 49 for tileInventoryFurnace
	private final int VANILLA_FIRST_SLOT_INDEX = 0;
	private final int FIRST_FUEL_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private final int FIRST_INPUT_SLOT_INDEX = FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT;
	private final int FIRST_OUTPUT_SLOT_INDEX = FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT;

	// slot number is the slot number within each component; i.e. invPlayer slots 0 - 35, and tileInventoryFurnace slots 0 - 14
	private final int FIRST_FUEL_SLOT_NUMBER = 0;
	private final int FIRST_INPUT_SLOT_NUMBER = FIRST_FUEL_SLOT_NUMBER + FUEL_SLOTS_COUNT;
	private final int FIRST_OUTPUT_SLOT_NUMBER = FIRST_INPUT_SLOT_NUMBER + INPUT_SLOTS_COUNT;

	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;

	public ContainerMaskForge(InventoryPlayer playerInv, TileInventoryMaskForge maskForgeInv) {
		this.maskForgeInv = maskForgeInv;

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

		final int FUEL_SLOTS_XPOS = 27;
		final int FUEL_SLOTS_YPOS = 55;
		addSlotToContainer(new SlotFuel(maskForgeInv, FIRST_FUEL_SLOT_NUMBER, FUEL_SLOTS_XPOS, FUEL_SLOTS_YPOS));

		final int INPUT_SLOTS_XPOS = 61;
		final int INPUT_SLOTS_YPOS = 11;
		for (int y = 0; y < 3; y++) {
			for(int x = 0; x < 2; x++) {
				int slotNumber = y * 2 + x + FIRST_INPUT_SLOT_NUMBER;
				addSlotToContainer(new SlotInput(maskForgeInv, slotNumber, INPUT_SLOTS_XPOS + 20 * x, INPUT_SLOTS_YPOS + 20 * y));
			}
		}
		final int OUTPUT_SLOTS_XPOS = 145;
		final int OUTPUT_SLOTS_YPOS = 31 ;
		addSlotToContainer(new SlotOutput(maskForgeInv, FIRST_OUTPUT_SLOT_NUMBER, OUTPUT_SLOTS_XPOS, OUTPUT_SLOTS_YPOS));
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return maskForgeInv.isUsableByPlayer(par1EntityPlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex) {
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			if (TileInventoryMaskForge.isItemValidForFuelSlot(sourceStack)) {
				if (!mergeItemStack(sourceStack, FIRST_FUEL_SLOT_INDEX, FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT, false)) {
					return ItemStack.EMPTY;
				}
			} else if (TileInventoryMaskForge.isItemValidForInputSlot(sourceStack)) {
				if (!mergeItemStack(sourceStack, FIRST_INPUT_SLOT_INDEX, FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT, false)) {
					return ItemStack.EMPTY;
				}
			} else return ItemStack.EMPTY;
		} else if (sourceSlotIndex >= FIRST_FUEL_SLOT_INDEX && sourceSlotIndex < FIRST_FUEL_SLOT_INDEX + FURNACE_SLOTS_COUNT) {
			if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			System.err.print("Invalid slotIndex:" + sourceSlotIndex);
			return ItemStack.EMPTY;
		}

//		if (sourceStack.getCount() == 0) {
//			sourceSlot.putStack(ItemStack.EMPTY);
//		} else {
//			sourceSlot.onSlotChanged();
//		}
		sourceSlot.onTake(player, sourceStack);
		return copyOfSourceStack;
	}
	
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();

		boolean allFieldsHaveChanged = false;
		boolean fieldHasChanged [] = new boolean[maskForgeInv.getFieldCount()];
		if (cachedFields == null) {
			cachedFields = new int[maskForgeInv.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		for (int i = 0; i < cachedFields.length; ++i) {
			if (allFieldsHaveChanged || cachedFields[i] != maskForgeInv.getField(i)) {
				cachedFields[i] = maskForgeInv.getField(i);
				fieldHasChanged[i] = true;
			}
		}

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
			for (int fieldID = 0; fieldID < maskForgeInv.getFieldCount(); ++fieldID) {
				if (fieldHasChanged[fieldID]) {
					icontainerlistener.sendWindowProperty(this, fieldID, cachedFields[fieldID]);
				}
			}
		}
	}
	

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		this.maskForgeInv.setField(id, data);
	}
	
	public class SlotFuel extends Slot {
		public SlotFuel(IInventory inventoryIn, int index, int xPosition, int yPosition){
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileInventoryMaskForge.isItemValidForFuelSlot(stack);
		}
	}
	public class SlotInput extends Slot {
		public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileInventoryMaskForge.isItemValidForInputSlot(stack);
		}
		@Override
		public void onSlotChanged() {
			super.onSlotChanged();
			((TileInventoryMaskForge)this.inventory).updateCurrentRecipe();
		}
	}
	public class SlotOutput extends Slot {
		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			//return TileInventoryMaskForge.isItemValidForOutputSlot(stack);
			return false;
		}
	}
}
