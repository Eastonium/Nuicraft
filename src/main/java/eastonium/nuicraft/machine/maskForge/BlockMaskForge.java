package eastonium.nuicraft.machine.maskForge;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import eastonium.nuicraft.Bionicle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMaskForge extends BlockContainer {
	
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 4);
    protected static float f = 0.125F;
    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
	
	public BlockMaskForge(){
		super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(0)));
		this.setCreativeTab(Bionicle.bioBlockTab);
	}
	
	public Block setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
	
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity){
		addCollisionBoxToList(pos, mask, list, AABB_LEGS);
        addCollisionBoxToList(pos, mask, list, AABB_WALL_WEST);
        addCollisionBoxToList(pos, mask, list, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, mask, list, AABB_WALL_EAST);
        addCollisionBoxToList(pos, mask, list, AABB_WALL_SOUTH);
    }
	
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
        int i = ((Integer)state.getValue(LEVEL)).intValue();
        float f = (float)pos.getY() + (float)(i + 13) / 16.0F;

        if (i > 0 && entityIn.getEntityBoundingBox().minY <= (double)f && !entityIn.isImmuneToFire()){
        	entityIn.attackEntityFrom(DamageSource.lava, 2.0F);
            entityIn.setFire(7);
        }
    }
	
	/*
	public void setBlockBoundsForItemRender(){
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    */

	public static void updateLavaLevel(int level, World worldIn, BlockPos pos){
		TileEntity tileentity = worldIn.getTileEntity(pos);
		worldIn.setBlockState(pos, Bionicle.MaskForge.getDefaultState().withProperty(LEVEL, level));
		if (tileentity != null){
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
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
	public TileEntity createNewTileEntity(World world, int meta){
		return new TileInventoryMaskForge();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote) return true;
		ItemStack itemstack = playerIn.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == Items.LAVA_BUCKET){
			int i = ((Integer)state.getValue(LEVEL)).intValue();
			if (i < 4){				
				TileEntity tileEntity = worldIn.getTileEntity(pos);
				if(tileEntity != null && tileEntity instanceof TileInventoryMaskForge){
					if (!playerIn.capabilities.isCreativeMode){
						playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.BUCKET));
					}
					((TileInventoryMaskForge)tileEntity).fill(new FluidStack(FluidRegistry.LAVA, 1000/*Bucket volume*/), true);
				}
			}
			return true;
		}
		if(playerIn.isSneaking()) return true;
		playerIn.openGui(Bionicle.modInstance, GuiHandlerMaskForge.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return ((Integer)state.getValue(LEVEL)).intValue();
	}

	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] {LEVEL});
	}

	@Override
	public int getLightValue(IBlockState state){
		if(state.getValue(LEVEL).intValue() > 0) return 15;
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand){
		if(world.getBlockState(pos).getValue(LEVEL).intValue() > 0){
			if (rand.nextInt(20) == 0){
				double d0 = (double)((float)pos.getX() + rand.nextFloat());
				double d1 = (double)pos.getY() + 0.6D;//(double)pos.getY() + this.maxY;
				double d2 = (double)((float)pos.getZ() + rand.nextFloat());
				world.spawnParticle(EnumParticleTypes.LAVA, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
				world.playSound(d0, d1, d2,  SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileEntity);
		}
		super.breakBlock(worldIn, pos, state);
	}
}
