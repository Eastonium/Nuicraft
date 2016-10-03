package eastonium.nuicraft.blocks;

import java.util.Random;

import eastonium.nuicraft.Bionicle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBamboo extends BlockReed implements IPlantable
{
	public BlockBamboo(){
		super();
		this.setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		float var3 = 0.375F;
		//this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
		this.setTickRandomly(true);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(Bionicle.bioMaterialTab);
	}
	
	public Block setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.REEDS || this.checkForDrop(worldIn, pos, state))
        {
            if (worldIn.isAirBlock(pos.up()))
            {
                int i;

                for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i)
                {
                    ;
                }

                if (i < 6)
                {
                    int j = ((Integer)state.getValue(AGE)).intValue();

                    if (j == 10)
                    {
                        worldIn.setBlockState(pos.up(), this.getDefaultState());
                        worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(0)), 4);
                    }
                    else
                    {
                        worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(j + 1)), 4);
                    }
                }
            }
        }
        if (rand.nextInt(10) == 0){
            /*int i = 5;
            for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-2, -1, -2), pos.add(2, 1, 2))){
                if (worldIn.getBlockState(blockpos).getBlock() == this){
                    --i;
                    if (i <= 0) return;
                }
            }*/
            BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, -rand.nextInt(2), rand.nextInt(3) - 1);
            for (int k = 0; k < 2; ++k){
                if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1)){
                    pos = blockpos1;
                }
                blockpos1 = pos.add(rand.nextInt(3) - 1, -rand.nextInt(2), rand.nextInt(3) - 1);
            }
            if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1)){
                worldIn.setBlockState(blockpos1, this.getDefaultState(), 2);
            }
        }
    }

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        if (block != this && block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.SAND)
        {
            return false;
        }else{
        	return true;
        }
    }
    
    @Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
    	return Item.getItemFromBlock(this);
	}   
    
    @SideOnly(Side.CLIENT)
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
		return new ItemStack(Item.getItemFromBlock(this));
	}
	
	@SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
    {
		return 16777215;
    }
}
