package eastonium.nuicraft.machine.purifier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPurifier extends Container {
	private TileInventoryPurifier purifierInv;
	private int [] cachedFields;

	// must assign a slot index to each of the slots used by the GUI.
	// For this container, we can see the furnace fuel, input, and output slots as well as the player inventory slots and the hotbar.
	// Each time we add a Slot to the container using addSlotToContainer(), it automatically increases the slotIndex, which means
	//  0  - 8  = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
	//  9  - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
	//  36      = fuel slots (tileEntity 0)
	//  37 - 42 = input slots (tileEntity 1 - 6)
	//  43      = output slots (tileEntity 7)

	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

	public final int ITEM_SLOTS_COUNT = 5;
	public final int FURNACE_SLOTS_COUNT = 3 + ITEM_SLOTS_COUNT;
	
	private final int VANILLA_FIRST_SLOT_INDEX = 0;
	private final int INPUT_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private final int FIRST_ITEM_SLOT_INDEX = INPUT_SLOT_INDEX + 1;
	private final int FILTER_SLOT_INDEX = FIRST_ITEM_SLOT_INDEX + ITEM_SLOTS_COUNT;
	private final int OUTPUT_SLOT_INDEX = FILTER_SLOT_INDEX + 1;

	private final int INPUT_SLOT_NUMBER = 0;
	private final int FIRST_ITEM_SLOT_NUMBER = 1;
	private final int FILTER_SLOT_NUMBER = FIRST_ITEM_SLOT_NUMBER + ITEM_SLOTS_COUNT;
	private final int OUTPUT_SLOT_NUMBER = FILTER_SLOT_NUMBER + 1;

	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;

	public ContainerPurifier(InventoryPlayer playerInv, TileInventoryPurifier purifierInv){
		this.purifierInv = purifierInv;

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

		final int INPUT_SLOT_XPOS = 74;
		final int INPUT_SLOT_YPOS = 8;
		addSlotToContainer(new SlotInput(purifierInv, INPUT_SLOT_NUMBER, INPUT_SLOT_XPOS, INPUT_SLOT_YPOS));

		final int ITEM_SLOTS_XPOS = 74;
		final int ITEM_SLOTS_YPOS = 30;
		for (int y = 0; y < 2; y++) {
			for(int x = 0; x < 3; x++){
				int slotNumber = y * 3 + x + FIRST_ITEM_SLOT_NUMBER;
				if (y == 1 && x == 1) x++;
				addSlotToContainer(new SlotItem(purifierInv, slotNumber, ITEM_SLOTS_XPOS + 20 * x, ITEM_SLOTS_YPOS + 20 * y));
			}
		}
		final int FILTER_SLOT_XPOS = 94;
		final int FILTER_SLOT_YPOS = 55;
		addSlotToContainer(new SlotFilter(purifierInv, FILTER_SLOT_NUMBER, FILTER_SLOT_XPOS, FILTER_SLOT_YPOS));
		
		final int OUTPUT_SLOTS_XPOS = 154;
		final int OUTPUT_SLOTS_YPOS = 55;
		addSlotToContainer(new SlotOutput(purifierInv, OUTPUT_SLOT_NUMBER, OUTPUT_SLOTS_XPOS, OUTPUT_SLOTS_YPOS));
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer){
		return purifierInv.isUseableByPlayer(par1EntityPlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return null;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if(sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT){
			if(TileInventoryPurifier.isItemValidForInputSlot(sourceStack)){
				if(!mergeItemStack(sourceStack, INPUT_SLOT_INDEX, INPUT_SLOT_INDEX + 1, false)){
					return null;
				}
			}else if (TileInventoryPurifier.isItemValidForOutputSlot(sourceStack)){
				if(!mergeItemStack(sourceStack, OUTPUT_SLOT_INDEX, OUTPUT_SLOT_INDEX + 1, false)){
					return null;
				}
			}else if (TileInventoryPurifier.isItemValidForFilterSlot(sourceStack)){
				if(!mergeItemStack(sourceStack, FILTER_SLOT_INDEX, FILTER_SLOT_INDEX + 1, false)){
					return null;
				}
			}else return null;
		}else if(sourceSlotIndex >= INPUT_SLOT_INDEX && sourceSlotIndex < INPUT_SLOT_INDEX + FURNACE_SLOTS_COUNT){
			if(!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)){
				return null;
			}
		}else{
			System.err.print("Invalid slotIndex:" + sourceSlotIndex);
			return null;
		}

		if(sourceStack.stackSize == 0){
			sourceSlot.putStack(null);
		}else{
			sourceSlot.onSlotChanged();
		}

		sourceSlot.onPickupFromSlot(player, sourceStack);
		return copyOfSourceStack;
	}
	
	//Changed to be sensitive to Slot stack limits
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
        boolean flag = false;
        int i = startIndex;
        if (reverseDirection) i = endIndex - 1;
        
        if (stack.isStackable()){
            while (stack.stackSize > 0 && (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex)){
            	Slot slot = (Slot)this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();
                int maxLimit = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                
                if (itemstack != null && areItemStacksEqual(stack, itemstack)){
                    int j = itemstack.stackSize + stack.stackSize;
                    if (j <= maxLimit){
                        stack.stackSize = 0;
                        itemstack.stackSize = j;
                        slot.onSlotChanged();
                        flag = true;
                        
                    }else if (itemstack.stackSize < maxLimit){
                        stack.stackSize -= maxLimit - itemstack.stackSize;
                        itemstack.stackSize = maxLimit;
                        slot.onSlotChanged();
                        flag = true;
                    }
                }
                if (reverseDirection){ 
                	--i;
                }else ++i;
            }
        }
        if (stack.stackSize > 0){
            if (reverseDirection){
                i = endIndex - 1;
            }else i = startIndex;

            while (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex){
                Slot slot1 = (Slot)this.inventorySlots.get(i);
                ItemStack itemstack1 = slot1.getStack();

                if (itemstack1 == null && slot1.isItemValid(stack)){ // Forge: Make sure to respect isItemValid in the slot.
                	if(stack.stackSize <= slot1.getSlotStackLimit()){
                		slot1.putStack(stack.copy());
                        slot1.onSlotChanged();
                        stack.stackSize = 0;
                        flag = true;
                        break;
                	}else{
                		itemstack1 = stack.copy();
                		stack.stackSize -= slot1.getSlotStackLimit();
                        itemstack1.stackSize = slot1.getSlotStackLimit();
                        slot1.putStack(itemstack1);
                        slot1.onSlotChanged();
                        flag = true;
                	}                    
                }
                if (reverseDirection){
                    --i;
                }else ++i;
            }
        }
        return flag;
    }
	
	private static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB){
        return stackB.getItem() == stackA.getItem() && (!stackA.getHasSubtypes() || stackA.getMetadata() == stackB.getMetadata()) && ItemStack.areItemStackTagsEqual(stackA, stackB);
    }
	
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();

		boolean allFieldsHaveChanged = false;
		boolean fieldHasChanged [] = new boolean[purifierInv.getFieldCount()];
		if (cachedFields == null) {
			cachedFields = new int[purifierInv.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		for (int i = 0; i < cachedFields.length; ++i) {
			if (allFieldsHaveChanged || cachedFields[i] != purifierInv.getField(i)) {
				cachedFields[i] = purifierInv.getField(i);
				fieldHasChanged[i] = true;
			}
		}

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
			for (int fieldID = 0; fieldID < purifierInv.getFieldCount(); ++fieldID) {
				if (fieldHasChanged[fieldID]) {
					icontainerlistener.sendProgressBarUpdate(this, fieldID, cachedFields[fieldID]);
				}
			}
		}
	}	

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		this.purifierInv.setField(id, data);
	}
	
	public class SlotInput extends Slot {
		public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition){
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileInventoryPurifier.isItemValidForInputSlot(stack);
		}
	}
	public class SlotFilter extends Slot {
		public SlotFilter(IInventory inventoryIn, int index, int xPosition, int yPosition){
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileInventoryPurifier.isItemValidForFilterSlot(stack);
		}
		@Override
		public int getSlotStackLimit(){ return 1; }
	}
	public class SlotItem extends Slot {
		public SlotItem(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			return false;
		}
	}
	public class SlotOutput extends Slot {
		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileInventoryPurifier.isItemValidForOutputSlot(stack);
		}
		@Override
		public int getSlotStackLimit(){ return 1; }
	}
}
