package eastonium.nuicraft.machine.maskForge.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import eastonium.nuicraft.Bionicle;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class MFRecipeManager {
	private static MFRecipeManager maskForgeRecipes = new MFRecipeManager();
	private List<IMFRecipe> recipeList = Lists.<IMFRecipe>newArrayList();

	public static MFRecipeManager getInstance(){
		return maskForgeRecipes;
	}

	private MFRecipeManager(){
		this.recipeList.add(new ColoredMetalMaskMFRecipe());
		this.recipeList.add(new KanokaMFRecipe());
		this.recipeList.add(new KanokaTimeMFRecipe());
		// 1 - Reconstitution at Random
		// 2 - Freezing
		// 3 - Weakening
		// 4 - Remove Poison
		// 5 - Enlarging
		// 6 - Shrinking
		// 7 - Regeneration
		// 8 - Teleportation
		this.recipeList.add(new MaskMFRecipe(new ItemStack(Bionicle.maskMataKakama), new int[]{1, 8}));
		this.recipeList.add(new MaskMFRecipe(new ItemStack(Bionicle.maskMataPakari), new int[]{4, 5, 7}));
		this.recipeList.add(new MaskMFRecipe(new ItemStack(Bionicle.maskMataKaukau), new int[]{4, 6, 7}));
		this.recipeList.add(new MaskMFRecipe(new ItemStack(Bionicle.maskMataMiru), new int[]{3, 8}));
		this.recipeList.add(new MaskMFRecipe(new ItemStack(Bionicle.maskMataHau), new int[]{5, 8}));
		this.recipeList.add(new MaskMFRecipe(new ItemStack(Bionicle.maskMataAkaku), new int[]{7, 8}));

		/*
		int[] kanokaloc = new int[6];
		if (this.forgeItemstacks[0] != null && this.forgeItemstacks[1] != null && this.forgeItemstacks[2] != null 
				&& this.forgeItemstacks[3] != null && this.forgeItemstacks[4] != null && this.forgeItemstacks[5] != null)
		{
			if(		this.forgeItemstacks[0].getItem() instanceof ItemKanoka && this.forgeItemstacks[1].getItem() instanceof ItemKanoka &&
					this.forgeItemstacks[2].getItem() instanceof ItemKanoka && this.forgeItemstacks[3].getItem() instanceof ItemKanoka &&
					this.forgeItemstacks[4].getItem() instanceof ItemKanoka && this.forgeItemstacks[5].getItem() instanceof ItemKanoka &&
					this.forgeItemstacks[0].getItemDamage() == 9 && this.forgeItemstacks[1].getItemDamage() == 9 &&
					this.forgeItemstacks[2].getItemDamage() == 9 && this.forgeItemstacks[3].getItemDamage() == 9 &&
					this.forgeItemstacks[4].getItemDamage() == 9 && this.forgeItemstacks[5].getItemDamage() == 9){
				kanokaloc[0] = (int)this.forgeItemstacks[0].getTagCompound().getByte("DiscLoc");
				kanokaloc[1] = (int)this.forgeItemstacks[1].getTagCompound().getByte("DiscLoc");
				kanokaloc[2] = (int)this.forgeItemstacks[2].getTagCompound().getByte("DiscLoc");
				kanokaloc[3] = (int)this.forgeItemstacks[3].getTagCompound().getByte("DiscLoc");
				kanokaloc[4] = (int)this.forgeItemstacks[4].getTagCompound().getByte("DiscLoc");
				kanokaloc[5] = (int)this.forgeItemstacks[5].getTagCompound().getByte("DiscLoc");
				if(!TileInventoryMaskForge.hasDuplicates(kanokaloc)){
					return true;
				}
			}
		}
		*/
		
		this.addRecipe(new ItemStack(Bionicle.maskVahi), new ItemStack(Bionicle.kanokaTime));
		
		if(Loader.isModLoaded("tconstruct")){
			this.addOreDictRecipe(new ItemStack(Bionicle.ingotProtosteel), new ItemStack(Bionicle.ingotProtodermis, 8), "04ingotSteel", "01ingotBedrockium");
		}else{
			this.addOreDictRecipe(new ItemStack(Bionicle.ingotProtosteel), new ItemStack(Bionicle.ingotProtodermis, 8), "08ingotIron", "08obsidian");
		}
		this.addRecipe(new ItemStack(Bionicle.Lightstone, 4), Items.GLOWSTONE_DUST, Bionicle.ingotProtodermis);
	}

	public List<IMFRecipe> getRecipeList(){
		return recipeList;
	}

	public void addRecipe(ItemStack output, Object... inputs){
		Object[] input = new Object[inputs.length];
		byte i = 0;
		for(Object object : inputs){
			if (object instanceof ItemStack){
				input[i] = ((ItemStack)object).copy();
				i++;
			}else if(object instanceof Item){
				input[i] = new ItemStack((Item)object);
				i++;
			}else if(object instanceof Block){
				input[i] = new ItemStack((Block)object);
				i++;            
			}else{
				/*if (!(object instanceof String)){
					throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
				}*/
				input[i] = (String)object;
				i++;
			}
		}
		this.recipeList.add(new GenericMFRecipe(output, input));
	}	

	public void addOreDictRecipe(Object output, Object... inputs){
		//output is formatted something like this: 05ingotProtodermis <2digitStackSize><oredictName>
		for(Object input : inputs){
			if(input instanceof String && !OreDictionary.doesOreNameExist(((String)input).substring(2))){
				return;
			}
		}
		if(output instanceof String){
			List<ItemStack> outputVariations = OreDictionary.getOres(((String)output).substring(2));
			if(!outputVariations.isEmpty()){
				ItemStack output1 = outputVariations.get(0);
				output1.stackSize = Integer.parseInt(((String)output).substring(0, 2));
				System.out.println(output1);
				addRecipe(output1, inputs);
			}
		}else if(output instanceof ItemStack){
			addRecipe((ItemStack)output, inputs);
		}else return;
	}

	public IMFRecipe getMatchingRecipe(ItemStack[] inputItemstacks){
		ItemStack[] input = new ItemStack[inputItemstacks.length];
		for (IMFRecipe recipe : this.recipeList){
			for(int i = 0; i < inputItemstacks.length; i++){
				if(inputItemstacks[i] == null){
					input[i] = null;
				}else input[i] = inputItemstacks[i].copy();
			}
			if (recipe.matches(input)){
				return recipe;
			}
		}
		return null;
	}
}