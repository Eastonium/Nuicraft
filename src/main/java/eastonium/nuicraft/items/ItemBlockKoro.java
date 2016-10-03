package eastonium.nuicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockKoro extends ItemBlock
{
	public static final String[] stoneBlockNames = new String[] {"Onu_1", "Onu_2", "Po_1", "Po_2", "Ta_1", "Ta_2", "Ta_3", "Trdx_1", "Trdx_2"};
	public static final String[] litBlockNames = new String[] {"Onu_0", "Ta_0"};
	public static final String[] iceBlockNames = new String[] {"Ko_1", "Ko_2"};
	public static final String[] leafyBlockNames = new String[] {"Ga_1", "Ga_2", "Ga_3"};

	public ItemBlockKoro(Block block){
		super(block);
		this.setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack){
		Material material = Block.getBlockFromItem(itemstack.getItem()).getDefaultState().getMaterial();
		int i = itemstack.getItemDamage();
		if(material == null){
			return null;
		}else if(material == Material.ROCK){
			if(i < 0 || i >= stoneBlockNames.length) i = 0;		
			return super.getUnlocalizedName() + "_" + stoneBlockNames[i];
		}else if(material == Material.REDSTONE_LIGHT){
			if(i < 0 || i >= litBlockNames.length) i = 0;		
			return super.getUnlocalizedName() + "_" + litBlockNames[i];
		}else if(material == Material.PACKED_ICE){
			if(i < 0 || i >= iceBlockNames.length) i = 0;		
			return super.getUnlocalizedName() + "_" + iceBlockNames[i];
		}else if(material == Material.LEAVES){
			if(i < 0 || i >= leafyBlockNames.length) i = 0;		
			return super.getUnlocalizedName() + "_" + leafyBlockNames[i];
		}else return null;
	}

	@Override
	public int getMetadata(int metadata){
		return metadata;
	}
}
