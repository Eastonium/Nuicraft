package eastonium.nuicraft.machine.purifier;

import java.util.Random;

import javax.annotation.Nullable;

import eastonium.nuicraft.Bionicle;
import eastonium.nuicraft.machine.maskForge.GuiHandlerMaskForge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPurifier extends BlockContainer {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyEnum<BlockBed.EnumPartType> PART = PropertyEnum.<BlockBed.EnumPartType>create("part", BlockBed.EnumPartType.class);
    protected static final AxisAlignedBB[] boundBox = {
    		new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.525D, 0.6875D), new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.8125D, 0.525D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.525D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.6875D, 0.525D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, -0.125D, 1.0D, 0.525D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.125D, 0.525D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.525D, 1.125D), new AxisAlignedBB(-0.125D, 0.0D, 0.0D, 1.0D, 0.525D, 1.0D)
    };

    
	public BlockPurifier(){
		super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PART, BlockBed.EnumPartType.HEAD));
	}
	
	public Block setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		return boundBox[getMetaFromState(state)];
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state){ return false; }
	@Override
	public boolean isFullCube(IBlockState state){ return false; }
	
	@SideOnly(Side.CLIENT)
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD){
        	return EnumBlockRenderType.MODEL;
        }
        return EnumBlockRenderType.INVISIBLE;
	}
	@SideOnly(Side.CLIENT)
	@Override
    public BlockRenderLayer getBlockLayer(){ return BlockRenderLayer.CUTOUT; }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(this.getStateFromMeta(meta).getValue(PART) == BlockBed.EnumPartType.HEAD){
			return new TileInventoryPurifier();
		}
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(playerIn.isSneaking()) return true;
		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT){
			pos = pos.offset((EnumFacing)state.getValue(FACING));
		}
		playerIn.openGui(Bionicle.modInstance, GuiHandlerPurifier.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn){
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD){
            if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this){
                worldIn.setBlockToAir(pos);
                if (!worldIn.isRemote) this.dropBlockAsItem(worldIn, pos, state, 0);
            }
        }else if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this){
            worldIn.setBlockToAir(pos);
        }
    }

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
        return new ItemStack(Bionicle.purifierItem);
    }
	
	@Nullable
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return state.getValue(PART) == BlockBed.EnumPartType.FOOT ? null : Bionicle.purifierItem;
    }
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune){
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD){
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
        }
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof IInventory){
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileEntity);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player){
        if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockBed.EnumPartType.FOOT){
        	BlockPos blockpos = pos.offset(((EnumFacing)state.getValue(FACING)));
        	if (worldIn.getBlockState(blockpos).getBlock() == this){
                worldIn.setBlockToAir(blockpos);
            }        
        }
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta){
        EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
        return (meta & 4) > 0 ? this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.HEAD).withProperty(FACING, enumfacing) : this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(FACING, enumfacing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state){
	    int i = ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD) i |= 4;
        return i;
    }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        if (state.getValue(PART) == BlockBed.EnumPartType.FOOT){
            IBlockState iblockstate = worldIn.getBlockState(pos.offset((EnumFacing)state.getValue(FACING)));
        }
        return state;
    }
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
	
	@Override
	protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {FACING, PART});
    }
}
