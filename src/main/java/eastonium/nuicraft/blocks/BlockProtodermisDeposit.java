package eastonium.nuicraft.blocks;

import java.util.Random;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftBlocks;
import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.items.ItemGenericMeta;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockProtodermisDeposit extends BlockOre {

	public static final PropertyInteger DROPS = PropertyInteger.create("drops", 0, 4);

	public BlockProtodermisDeposit(){
		super("protodermis_ore");
		setDefaultState(blockState.getBaseState().withProperty(DROPS, 0));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return NuiCraftItems.generic_item;
	}
	@Override
	public int damageDropped(IBlockState state){
		return ItemGenericMeta.getMetaFromName("raw_protodermis");
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random){
		return quantityDropped(random) + random.nextInt(fortune + 1);
	}

	@Override
	public int quantityDropped(Random random){
		return 2;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if(heldItem != null && heldItem.getItem() == NuiCraftItems.sluice){
			if(worldIn.isRemote){
				for (int i = 0; i < 5; ++i){
					worldIn.spawnParticle(EnumParticleTypes.ITEM_CRACK, hitX + pos.getX(), hitY + pos.getY(), hitZ + pos.getZ(), 
							((double)worldIn.rand.nextFloat() - 0.5D) * 0.2D, 
	    					((double)worldIn.rand.nextFloat()) * 0.2D, 
	    					((double)worldIn.rand.nextFloat() - 0.5D) * 0.2D,
							new int[] {Item.getIdFromItem(NuiCraftItems.generic_item)});
							//TODO: How to make metadata sensitive???? new int[] {Item.getIdFromItem(NuiCraftItems.raw_protodermis)});

				}
			}else{
				int drops = state.getValue(DROPS);
				//TODO make fortune work in 33% chance increments do drop another
				double f = 1.5;
				double posX = (hitX < 1.0F && hitX > 0F) ? hitX : ((hitX - 0.5) * f) + 0.5;
				double posY = (hitY < 1.0F && hitY > 0F) ? hitY : ((hitY - 0.5) * f) + 0.5;
				double posZ = (hitZ < 1.0F && hitZ > 0F) ? hitZ : ((hitZ - 0.5) * f) + 0.5;
				EntityItem entityitem = new EntityItem(worldIn, posX + pos.getX(), posY + pos.getY(), posZ + pos.getZ(), NuiCraftItems.getGIIS("raw_protodermis", 1));
				float f3 = 0.06F;
				entityitem.motionX = worldIn.rand.nextGaussian() * (double)f3;
				entityitem.motionY = worldIn.rand.nextGaussian() * (double)f3 + 0.20000000298023224D;
				entityitem.motionZ = worldIn.rand.nextGaussian() * (double)f3;
				worldIn.spawnEntity(entityitem);

				if(drops <= 0){
					worldIn.setBlockState(pos, Blocks.STONE.getDefaultState());
				}else{
					worldIn.setBlockState(pos, state.withProperty(DROPS, drops - 1));
				}
			}
			return true;
		}
		return false;
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		if(state.getValue(DROPS) == 0){
			worldIn.setBlockState(pos, getDefaultState().withProperty(DROPS, 1 + worldIn.rand.nextInt(3)));
		}
	}

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
		if(worldIn.isRemote) return getDefaultState();
		if(placer instanceof EntityPlayer && ((EntityPlayer)placer).capabilities.isCreativeMode){
			return getDefaultState().withProperty(DROPS, 1 + worldIn.rand.nextInt(3));
		}
		return getDefaultState().withProperty(DROPS, 1);
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return getDefaultState().withProperty(DROPS, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(DROPS);
	}

	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] {DROPS});
	}
}
