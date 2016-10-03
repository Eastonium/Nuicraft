package eastonium.nuicraft.blocks;

import java.util.List;

import eastonium.nuicraft.Bionicle;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockKoro extends Block
{    
	public BlockKoro(Material material, MapColor mapColor/*, int metadataAmount*/){
		super(material, mapColor);
		this.setUnlocalizedName("KoroBlock");
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getMETA(), Integer.valueOf(0)));
		this.setCreativeTab(Bionicle.bioBlockTab);
	}
	
	public Block setName(String name){
		//Unlocalized name is always KoroBlock
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
	
	private PropertyInteger getMETA(){
		if(this.blockMaterial == Material.ROCK){
			return PropertyInteger.create("meta", 0, 8);
		}else if(this.blockMaterial == Material.REDSTONE_LIGHT || this.blockMaterial == Material.PACKED_ICE){
			return PropertyInteger.create("meta", 0, 1);
		}else return PropertyInteger.create("meta", 0, 2);		
	}
	
	@Override
	public int damageDropped(IBlockState state){
        return state.getValue(this.getMETA());
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list){
        for (int i : this.getMETA().getAllowedValues()){
            list.add(new ItemStack(itemIn, 1, i));
        }
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(this.getMETA(), Integer.valueOf(meta));
    }

    /*@Override
    public MapColor getMapColor(IBlockState state)
    {
        return ((BlockKoro.EnumType)state.getValue(VARIANT)).getMapColor();
    }*/
    
    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(this.getMETA());
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[]{this.getMETA()});
    }
}
