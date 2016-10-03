package eastonium.nuicraft.blocks;

import java.util.Random;

import eastonium.nuicraft.Bionicle;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockOre extends Block {	
	public BlockOre(){
		super(Material.ROCK);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(Bionicle.bioBlockTab);
	}
	
	public Block setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
	
	@Override
	protected boolean canSilkHarvest(){
        return true;
    }

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		if(this == Bionicle.LightstoneOre) return Item.getItemFromBlock(Bionicle.Lightstone);
		if(this == Bionicle.HeatstoneOre) return Bionicle.rawHeatstone;
		if(this == Bionicle.ProtodermisOre) return Bionicle.rawProtodermis;
		return null;
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random){
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }
	
	@Override
	public int quantityDropped(Random random){
        if(this == Bionicle.HeatstoneOre){
        	return 1 + random.nextInt(2);
        }else return 3 + random.nextInt(3);
    }
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune){
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }
	
	@Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune){
        if (this.getItemDropped(world.getBlockState(pos), RANDOM, fortune) != Item.getItemFromBlock(this)){
        	return 3 + RANDOM.nextInt(5);
        }
        return 0;
    }
}