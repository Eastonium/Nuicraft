package eastonium.nuicraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockKoro extends ItemBlock
{
	public static final String[] stoneBlockNames = new String[] {"onu_1", "onu_2", "po_1", "po_2", "ta_1", "ta_2", "ta_3", "trdx_1", "trdx_2"};
	public static final String[] litBlockNames = new String[] {"onu_0", "ta_0"};
	public static final String[] iceBlockNames = new String[] {"ko_1", "ko_2"};
	public static final String[] leafyBlockNames = new String[] {"ga_1", "ga_2", "ga_3"};

	public ItemBlockKoro(Block block){
		super(block);
		setHasSubtypes(true);
		setRegistryName(block.getRegistryName());
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
