package eastonium.nuicraft.machine.reservior;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockReservior extends BlockContainer {

	public BlockReservior() {
		super(Material.IRON);
		setCreativeTab(NuiCraft.nuicraftTab);
		setUnlocalizedName(NuiCraft.MODID + ".reservior");
		setRegistryName("reservior");
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		if(worldIn.isRemote) return true;
//		ItemStack itemstack = playerIn.inventory.getCurrentItem();
//		if (!itemstack.isEmpty() && itemstack.getItem() == Items.LAVA_BUCKET){
//			int i = ((Integer)state.getValue(LEVEL)).intValue();
//			if (i < 4){				
//				TileEntity tileEntity = worldIn.getTileEntity(pos);
//				if(tileEntity != null && tileEntity instanceof TileInventoryMaskForge){
//					if (!playerIn.capabilities.isCreativeMode){
//						playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.BUCKET));
//					}
//					((TileInventoryMaskForge)tileEntity).fill(new FluidStack(FluidRegistry.LAVA, 1000/*Bucket volume*/), true);
//				}
//			}
//			return true;
//		}
		if(playerIn.isSneaking()) return true;
		playerIn.openGui(NuiCraft.modInstance, GuiHandlerReservior.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	//Dependent on fluid light level?
//	@Override
//	public int getLightValue(IBlockState state){
//		if(state.getValue(LEVEL).intValue() > 0) return 15;
//		return 0;
//	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileEntity);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileInventoryReservior();
	}
}
