package eastonium.nuicraft.block;

import java.util.List;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockKoro extends Block
{    
	public BlockKoro(String name, Material material, MapColor mapColor) {
		super(material, mapColor);
        setDefaultState(blockState.getBaseState().withProperty(getMETA(), Integer.valueOf(0)));
        setHarvestLevel(material == Material.LEAVES ? "axe" : "pickaxe", 0);
		setCreativeTab(NuiCraft.nuicraftTab);
		setUnlocalizedName(NuiCraft.MODID + ".koro_block");
		setRegistryName(name);
	}
	
	private PropertyInteger getMETA() {
		if(blockMaterial == Material.ROCK) {
			return PropertyInteger.create("meta", 0, 8);
		}else if(blockMaterial == Material.REDSTONE_LIGHT || blockMaterial == Material.PACKED_ICE) {
			return PropertyInteger.create("meta", 0, 1);
		}else return PropertyInteger.create("meta", 0, 2);		
	}
	
	@Override
	public int damageDropped(IBlockState state) {
        return state.getValue(getMETA());
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (int i : getMETA().getAllowedValues()){
            items.add(new ItemStack(this, 1, i));
        }
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(this.getMETA(), Integer.valueOf(meta));
    }

    /*@Override
    public MapColor getMapColor(IBlockState state)
    {
        return ((BlockKoro.EnumType)state.getValue(VARIANT)).getMapColor();
    }*/
    
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(getMETA());
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[]{getMETA()});
    }
}
