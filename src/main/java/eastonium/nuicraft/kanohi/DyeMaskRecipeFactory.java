package eastonium.nuicraft.kanohi;

import com.google.gson.JsonObject;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;


public class DyeMaskRecipeFactory implements IRecipeFactory {
    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
        return new DyeMaskRecipe(new ResourceLocation(NuiCraft.MODID, "dye_mask"), recipe.getIngredients());
    }
    
	public static class DyeMaskRecipe extends ShapelessOreRecipe {
		public DyeMaskRecipe(ResourceLocation group, NonNullList<Ingredient> input) {
			super(group, input, NuiCraft.getRedHau());
	    }
	
		@Override
		public boolean matches(InventoryCrafting inv, World worldIn) {
			boolean hasDye = false;
			boolean hasMask = false;
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack.isEmpty()) continue;
				else if (DyeUtils.isDye(stack)) {
					hasDye = true;
					continue;
				}
				else if (stack.getItem() instanceof ItemColoredMask) {
					ItemColoredMask mask = (ItemColoredMask)stack.getItem();
					if (hasMask || !mask.hasColor(stack)) return false;
					hasMask = true;
					continue;
				} else return false;
			}
			return hasDye && hasMask;
		}
	
	
		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv){
			ItemStack resultStack = ItemStack.EMPTY;
			int[] RGB = new int[]{0, 0, 0};
			int i = 0;
			int colorCount = 0;
			ItemColoredMask itemMask = null;
	
			for (int k = 0; k < inv.getSizeInventory(); ++k){
				ItemStack stack = inv.getStackInSlot(k);
				if (!stack.isEmpty()){
					if (stack.getItem() instanceof ItemColoredMask){
						itemMask = (ItemColoredMask)stack.getItem();
						if(!resultStack.isEmpty()) return ItemStack.EMPTY;
						resultStack = stack.copy();
						resultStack.setCount(1);
	
						if (itemMask.hasColor(stack) && itemMask.getColor(stack) != ItemColoredMask.DEFAULT_COLOR) {
							int maskColor = itemMask.getColor(resultStack);
							int maskR = maskColor >> 16 & 255;
							int maskG = maskColor >> 8 & 255;
							int maskB = maskColor & 255;
							i += Math.max(maskR, Math.max(maskG, maskB));
							RGB[0] += maskR;
							RGB[0] += maskR;
							RGB[0] += maskR;
							++colorCount;
						}
					}else{
						if (!DyeUtils.isDye(stack)) return ItemStack.EMPTY;
						int[] dyeRGB = getRGB(DyeUtils.rawMetaFromStack(stack));
						int dyeR = dyeRGB[0];
						int dyeG = dyeRGB[1];
						int dyeB = dyeRGB[2];
						i += Math.max(dyeR, Math.max(dyeG, dyeB));
						RGB[0] += dyeR;
						RGB[1] += dyeG;
						RGB[2] += dyeB;
						++colorCount;
					}
				}
			}
	
			if (resultStack.isEmpty()) return ItemStack.EMPTY;
			
			int finalR = RGB[0] / colorCount;
			int finalG = RGB[1] / colorCount;
			int finalB = RGB[2] / colorCount;
			float f = (float)i / (float)colorCount;
			float f1 = (float)Math.max(finalR, Math.max(finalG, finalB));
			finalR = (int)((float)finalR * f / f1);
			finalG = (int)((float)finalG * f / f1);
			finalB = (int)((float)finalB * f / f1);
			int finalColor = (finalR << 8) + finalG;
			finalColor = (finalColor << 8) + finalB;
			itemMask.setColor(resultStack, finalColor);
			return resultStack;
		}
	
		public static int[] getRGB(int dyeMeta){
			switch(dyeMeta){
				case 0 : return new int[]{255, 255, 255}; //White
				case 1 : return new int[]{255, 127, 0  }; //Orange
				case 2 : return new int[]{255, 0  , 255}; //Magenta
				case 3 : return new int[]{0  , 255, 255}; //Light Blue
				case 4 : return new int[]{255, 255, 0  }; //Yellow
				case 5 : return new int[]{0  , 255, 0  }; //Lime
				case 6 : return new int[]{255, 170, 170}; //Pink
				case 7 : return new int[]{85 , 85 , 85 }; //Grey
				case 8 : return new int[]{170, 170, 170}; //Light Grey
				case 9 : return new int[]{0  , 170, 170}; //Cyan
				case 10: return new int[]{128, 0  , 255}; //Purple
				case 11: return new int[]{0  , 0  , 255}; //Blue
				case 12: return new int[]{97 , 56 , 29 }; //Brown
				case 13: return new int[]{0  , 127, 0  }; //Green
				case 14: return new int[]{255, 0  , 0  }; //Red
				case 15: return new int[]{0  , 0  , 0  }; //Black
			}
			return null;
		}
		
	//	public static class DyeIngredient extends Ingredient {
	//		private NonNullList<ItemStack> dyes;
	//	    private IntList itemIds = null;
	//	    private ItemStack[] array = null;
	//
	//	    public DyeIngredient() {
	//	        super(0);
	//	        for (EnumDyeColor color : EnumDyeColor.values()) {
	//	        	dyes.addAll(OreDictionary.getOres("dye" + 
	//	        		color.getUnlocalizedName().substring(0, 1).toUpperCase() + 
	//	        		color.getUnlocalizedName().substring(1)
	//	        	));
	//	        }
	//	    }
	//
	//	    @Override
	//	    @Nonnull
	//	    public ItemStack[] getMatchingStacks() {
	//	        if (array == null || this.array.length != dyes.size()) {
	//	            NonNullList<ItemStack> lst = NonNullList.create();
	//	            for (ItemStack itemstack : this.dyes) {
	//	                if (itemstack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
	//	                    itemstack.getItem().getSubItems(CreativeTabs.SEARCH, lst);
	//	                } else lst.add(itemstack);
	//	            }
	//	            this.array = lst.toArray(new ItemStack[lst.size()]);
	//	        }
	//	        return this.array;
	//	    }
	//
	//
	//	    @Override
	//	    @Nonnull
	//	    @SideOnly(Side.CLIENT)
	//	    public IntList getValidItemStacksPacked() {
	//	        if (this.itemIds == null || this.itemIds.size() != dyes.size()) {
	//	            this.itemIds = new IntArrayList(this.dyes.size());
	//	            for (ItemStack itemstack : this.dyes) {
	//	                if (itemstack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
	//	                    NonNullList<ItemStack> lst = NonNullList.create();
	//	                    itemstack.getItem().getSubItems(CreativeTabs.SEARCH, lst);
	//	                    for (ItemStack item : lst)
	//	                        this.itemIds.add(RecipeItemHelper.pack(item));
	//	                } else {
	//	                    this.itemIds.add(RecipeItemHelper.pack(itemstack));
	//	                }
	//	            }
	//	            this.itemIds.sort(IntComparators.NATURAL_COMPARATOR);
	//	        }
	//	        return this.itemIds;
	//	    }
	//
	//
	//	    @Override
	//	    public boolean apply(@Nullable ItemStack input) {
	//	        if (input == null) return false;
	//	        for (ItemStack target : this.dyes) {
	//	            if (OreDictionary.itemMatches(target, input, false)) return true;
	//	        }
	//	        return false;
	//	    }
	//
	//	    @Override
	//	    protected void invalidate() {
	//	        this.itemIds = null;
	//	    }
	//	}
	}
}