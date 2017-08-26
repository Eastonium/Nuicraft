package eastonium.nuicraft.blocks;

import java.util.Random;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftBlocks;
import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.items.ItemGenericMeta;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockOre extends Block {	
	public BlockOre(String name) {
		super(Material.ROCK);
		setSoundType(SoundType.STONE);
		setCreativeTab(NuiCraft.bio_block_tab);
		setUnlocalizedName(NuiCraft.MODID + "." + name);
        setRegistryName(name);
	}
	
	@Override
	protected boolean canSilkHarvest() {
        return true;
    }

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(state.getBlock() == NuiCraftBlocks.lightstone_ore) return Item.getItemFromBlock(NuiCraftBlocks.lightstone);
		if(state.getBlock() == NuiCraftBlocks.heatstone_ore) return NuiCraftItems.generic_item;// return NuiCraftItems.raw_heatstone;
		//if(state.getBlock() == NuiCraftBlocks.protodermis_ore) return NuiCraftItems.generic_item;// return NuiCraftItems.raw_protodermis;
		return null;
	}
	
	@Override
	public int damageDropped(IBlockState state){
		if (state.getBlock() == NuiCraftBlocks.heatstone_ore) return ItemGenericMeta.getMetaFromName("raw_heatstone");
		//if (state.getBlock() == NuiCraftBlocks.protodermis_ore) return ItemGenericMeta.getMetaFromName("raw_protodermis");
		return 0;
    }
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
        return quantityDropped(random) + random.nextInt(fortune + 1);
    }
	
	@Override
	public int quantityDropped(Random random) {
        if (this == NuiCraftBlocks.heatstone_ore) {
        	return 1 + random.nextInt(2);
        } else return 3 + random.nextInt(3);
    }
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }
	
	@Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        if (getItemDropped(world.getBlockState(pos), RANDOM, fortune) != Item.getItemFromBlock(this)) {
        	return 3 + RANDOM.nextInt(5);
        }
        return 0;
    }
}