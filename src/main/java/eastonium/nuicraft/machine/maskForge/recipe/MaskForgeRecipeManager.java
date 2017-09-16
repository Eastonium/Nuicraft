package eastonium.nuicraft.machine.maskForge.recipe;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftBlocks;
import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.item.ItemGenericMeta.EnumGenericItem;
import eastonium.nuicraft.util.CountedIngredient;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreIngredient;

public class MaskForgeRecipeManager {
	private static MaskForgeRecipeManager maskForgeRecipes = new MaskForgeRecipeManager();
	private List<IMFRecipe> recipeList = Lists.<IMFRecipe>newArrayList();

	public static MaskForgeRecipeManager getInstance(){
		return maskForgeRecipes;
	}

	private MaskForgeRecipeManager(){
		recipeList.add(new RandomKanokaRecipe());
		recipeList.add(new KanokaTimeRecipe());
		/*
		 * 1 - Reconstitution at Random
		 * 2 - Freezing
		 * 3 - Weakening
		 * 4 - Remove Poison
		 * 5 - Enlarging
		 * 6 - Shrinking
		 * 7 - Regeneration
		 * 8 - Teleportation 
		*/
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_kakama), 1, 8));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_pakari), 4, 5, 7));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_kaukau), 4, 6, 7));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_miru),   3, 8));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_hau),    5, 8));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_akaku),  7, 8));
		
		recipeList.add(new MaskForgeRecipe(1000, new ItemStack(NuiCraftItems.mask_vahi),
				new CountedIngredient(EnumGenericItem.KANOKA_TIME.getStack(1))));
		
		recipeList.add(new MaskForgeRecipe(400, EnumGenericItem.PROTOSTEEL_INGOT.getStack(1),
				new CountedIngredient(new OreIngredient("ingotProtodermis"), 2),
				new CountedIngredient(new OreIngredient("ingotIron"), 2),
				new CountedIngredient(Items.COAL, 2)));
		
		recipeList.add(new MaskForgeRecipe(100, new ItemStack(NuiCraftBlocks.lightstone, 4),
				new CountedIngredient(Items.GLOWSTONE_DUST, 1),
				new CountedIngredient(new OreIngredient("ingotProtodermis"), 1)));
	}

	public List<IMFRecipe> getRecipeList(){
		return recipeList;
	}
	
	public IMFRecipe getMatchingRecipe(NonNullList<ItemStack> inputItemStacks){
		if (inputItemStacks.isEmpty()) return null;
		NonNullList<ItemStack> input = NonNullList.create();
		Iterator<ItemStack> stacks;
		for (IMFRecipe recipe : recipeList){
			stacks = inputItemStacks.iterator();
			while (stacks.hasNext()) {
				input.add(stacks.next().copy());
			}
			if (recipe.matches(input)) return recipe;
			input.clear();
		}
		return null;
	}
}