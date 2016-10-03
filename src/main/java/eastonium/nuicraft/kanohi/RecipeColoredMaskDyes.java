package eastonium.nuicraft.kanohi;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeColoredMaskDyes implements IRecipe {
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inv, World worldIn){
		ItemStack itemstack = null;
		List<ItemStack> list = Lists.<ItemStack>newArrayList();
		for (int i = 0; i < inv.getSizeInventory(); ++i){
			ItemStack itemstack1 = inv.getStackInSlot(i);
			if (itemstack1 != null){
				if (itemstack1.getItem() instanceof ItemColoredMask){
					ItemColoredMask itemMask = (ItemColoredMask)itemstack1.getItem();

					if (itemstack != null || !itemMask.hasColor(itemstack1)){
						return false;
					}
					itemstack = itemstack1;
				}else{
					if (itemstack1.getItem() != Items.DYE){
						return false;
					}
					list.add(itemstack1);
				}
			}
		}

		return itemstack != null && !list.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inv){
		ItemStack itemstack = null;
		int[] RGB = new int[]{0, 0, 0};
		int i = 0;
		int numColorsToAvg = 0;
		ItemColoredMask itemMask = null;

		for (int k = 0; k < inv.getSizeInventory(); ++k){
			ItemStack itemstack1 = inv.getStackInSlot(k);
			if (itemstack1 != null){
				if (itemstack1.getItem() instanceof ItemColoredMask){
					itemMask = (ItemColoredMask)itemstack1.getItem();
					if(itemstack != null) return null;//?
					itemstack = itemstack1.copy();
					itemstack.stackSize = 1;

					if (itemMask.hasColor(itemstack1) && itemMask.getColor(itemstack1) != ItemColoredMask.DEFAULT_COLOR)
					{
						int l = itemMask.getColor(itemstack);
						float f = (float)(l >> 16 & 255) / 255.0F;
						float f1 = (float)(l >> 8 & 255) / 255.0F;
						float f2 = (float)(l & 255) / 255.0F;
						i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
						RGB[0] = (int)((float)RGB[0] + f * 255.0F);
						RGB[1] = (int)((float)RGB[1] + f1 * 255.0F);
						RGB[2] = (int)((float)RGB[2] + f2 * 255.0F);
						++numColorsToAvg;
					}
				}else{
					if (itemstack1.getItem() != Items.DYE) return null;
					int[] dyeRGB = getRGB(itemstack1.getMetadata());
					int l1 = dyeRGB[0];
					int i2 = dyeRGB[1];
					int j2 = dyeRGB[2];
					i += Math.max(l1, Math.max(i2, j2));
					RGB[0] += l1;
					RGB[1] += i2;
					RGB[2] += j2;
					++numColorsToAvg;
				}
			}
		}

		if (itemMask == null){
			return null;
		}else{
			int i1 = RGB[0] / numColorsToAvg;
			int j1 = RGB[1] / numColorsToAvg;
			int k1 = RGB[2] / numColorsToAvg;
			float f3 = (float)i / (float)numColorsToAvg;
			float f4 = (float)Math.max(i1, Math.max(j1, k1));
			i1 = (int)((float)i1 * f3 / f4);
			j1 = (int)((float)j1 * f3 / f4);
			k1 = (int)((float)k1 * f3 / f4);
			int lvt_12_3_ = (i1 << 8) + j1;
			lvt_12_3_ = (lvt_12_3_ << 8) + k1;
			itemMask.setColor(itemstack, lvt_12_3_);
			return itemstack;
		}
	}

	public static int[] getRGB(int damage){
		switch(damage){
		case 0: return new int[]{0, 0, 0}; //Black
		case 1:	return new int[]{255, 0, 0}; //Red
		case 2: return new int[]{0, 127, 0}; //Green
		case 3: return new int[]{97, 56, 29}; //Brown
		case 4: return new int[]{0, 0, 255}; //Blue
		case 5: return new int[]{128, 0, 255}; //Purple
		case 6: return new int[]{0, 170, 170}; //Cyan
		case 7: return new int[]{170, 170, 170}; //Light Grey
		case 8: return new int[]{85, 85, 85}; //Grey
		case 9: return new int[]{255, 170, 170}; //Pink
		case 10: return new int[]{0, 255, 0};//Lime
		case 11: return new int[]{255, 255 ,0};//Yellow
		case 12: return new int[]{0, 255, 255};//Light Blue
		case 13: return new int[]{255, 0, 255};//Magenta
		case 14: return new int[]{255, 127, 0};//Orange
		case 15: return new int[]{255, 255, 255};//White
		}
		return null;
	}

	public int getRecipeSize(){
		return 10;
	}

	public ItemStack getRecipeOutput(){
		return null;
	}

	public ItemStack[] getRemainingItems(InventoryCrafting inv){
		ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
		for (int i = 0; i < aitemstack.length; ++i){
			ItemStack itemstack = inv.getStackInSlot(i);
			aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
		}
		return aitemstack;
	}
}