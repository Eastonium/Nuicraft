package eastonium.nuicraft;

import eastonium.nuicraft.fluid.BionicleFluidUtil;
import eastonium.nuicraft.items.ItemBlockKoro;
import eastonium.nuicraft.kanohi.RecipeColoredMaskDyes;
import eastonium.nuicraft.kanoka.EntityDisc;
import eastonium.nuicraft.machine.maskForge.GuiHandlerMaskForge;
import eastonium.nuicraft.machine.maskForge.TileInventoryMaskForge;
import eastonium.nuicraft.machine.purifier.GuiHandlerPurifier;
import eastonium.nuicraft.machine.purifier.TileInventoryPurifier;
import eastonium.nuicraft.mobs.mahi.EntityMahi;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CommonProxyBionicle 
{
	public void preInit(){
		NetworkRegistry.INSTANCE.registerGuiHandler(Bionicle.modInstance, GuiHandler.getInstance());
		
		registerFluidModels();

		registerBlock(Bionicle.MaskForge);
		GameRegistry.registerTileEntity(TileInventoryMaskForge.class, "tileEntityMaskForge");
		GuiHandler.getInstance().registerGuiHandler(new GuiHandlerMaskForge(), GuiHandlerMaskForge.getGuiID());
		
		registerBlock(Bionicle.Purifier);
		GameRegistry.registerTileEntity(TileInventoryPurifier.class, "tileEntityPurifier");
		GuiHandler.getInstance().registerGuiHandler(new GuiHandlerPurifier(), GuiHandlerPurifier.getGuiID());
		registerItem(Bionicle.purifierItem);
		
		GameRegistry.register(Bionicle.StoneKoroBlock);
		GameRegistry.register(new ItemBlockKoro(Bionicle.StoneKoroBlock), Bionicle.StoneKoroBlock.getRegistryName());
		GameRegistry.register(Bionicle.LitKoroBlock);
		GameRegistry.register(new ItemBlockKoro(Bionicle.LitKoroBlock), Bionicle.LitKoroBlock.getRegistryName());
		GameRegistry.register(Bionicle.IceKoroBlock);
		GameRegistry.register(new ItemBlockKoro(Bionicle.IceKoroBlock), Bionicle.IceKoroBlock.getRegistryName());
		GameRegistry.register(Bionicle.LeafyKoroBlock);
		GameRegistry.register(new ItemBlockKoro(Bionicle.LeafyKoroBlock), Bionicle.LeafyKoroBlock.getRegistryName());
				
		registerBlock(Bionicle.NuvaCube);
		//registerBlock(Bionicle.Seal);//Crystalized protodermis
		registerBlock(Bionicle.MataNuiStone);
		registerBlock(Bionicle.MakutaStone);
		registerBlock(Bionicle.Lightstone);
		registerBlock(Bionicle.LightstoneOre);
		registerBlock(Bionicle.HeatstoneOre);
		registerBlock(Bionicle.ProtodermisOre);
		
		registerBlock(Bionicle.Bamboo);
		
		registerBlock(Bionicle.BlockProtodermis);
		registerBlock(Bionicle.BlockProtosteel);
		
		registerItem(Bionicle.ingotProtodermis);
		registerItem(Bionicle.ingotProtosteel);
		registerItem(Bionicle.nuggetProtodermis);
		registerItem(Bionicle.nuggetProtosteel);
		registerItem(Bionicle.nuggetIron);
		//registerItem(Bionicle.rodProtodermis);
		//registerItem(Bionicle.rodProtosteel);
		registerItem(Bionicle.rawProtodermis);
		registerItem(Bionicle.rawHeatstone);
		registerItem(Bionicle.bambooStick);		
		
	    registerItem(Bionicle.discLauncher);
	    registerItem(Bionicle.swordProtodermis);
	    
	    registerItem(Bionicle.pickProtodermis);	    
	    registerItem(Bionicle.hatchetProtodermis);	    
	    registerItem(Bionicle.shovelProtodermis);	    
	    registerItem(Bionicle.sytheProtodermis);
		registerItem(Bionicle.heatstoneLighter);
		registerItem(Bionicle.sluice);
		
		registerItem(Bionicle.kanokaFlying);
		registerItem(Bionicle.kanokaDisc);
		registerItem(Bionicle.bambooDisc);
		registerItem(Bionicle.kanokaTime);		

		registerItem(Bionicle.maskMataGold);
		
		registerItem(Bionicle.maskMataKakama);
	    registerItem(Bionicle.maskMataPakari);	    
	    registerItem(Bionicle.maskMataKaukau);
	    registerItem(Bionicle.maskMataMiru);	    
	    registerItem(Bionicle.maskMataHau);	    
	    registerItem(Bionicle.maskMataAkaku);
	    
	    registerItem(Bionicle.maskNuvaKakama);	    
	    registerItem(Bionicle.maskNuvaPakari);
	    registerItem(Bionicle.maskNuvaKaukau);
	    registerItem(Bionicle.maskNuvaMiru);
	    registerItem(Bionicle.maskNuvaHau);
	    registerItem(Bionicle.maskNuvaAkaku);	    
	    
	    registerItem(Bionicle.maskIgnika);	    
	    registerItem(Bionicle.maskVahi);
		
		OreDictionary.registerOre("gemLightstone", Bionicle.Lightstone);
		OreDictionary.registerOre("oreLightstone", Bionicle.LightstoneOre);
		OreDictionary.registerOre("oreHeatstone", Bionicle.HeatstoneOre);
		OreDictionary.registerOre("oreProtodermis", Bionicle.ProtodermisOre);
		
	    OreDictionary.registerOre("cropBamboo", Bionicle.Bamboo);
		
		OreDictionary.registerOre("blockProtodermis", Bionicle.BlockProtodermis);
		OreDictionary.registerOre("blockProtosteel", Bionicle.BlockProtosteel);		
		OreDictionary.registerOre("ingotProtodermis", Bionicle.ingotProtodermis);
	    OreDictionary.registerOre("ingotProtosteel", Bionicle.ingotProtosteel);
	    OreDictionary.registerOre("nuggetProtodermis", Bionicle.nuggetProtodermis);
	    OreDictionary.registerOre("nuggetProtosteel", Bionicle.nuggetProtosteel);
	    OreDictionary.registerOre("nuggetIron", Bionicle.nuggetIron);
	    //OreDictionary.registerOre("stickProtodermis", Bionicle.rodProtodermis);
	    //OreDictionary.registerOre("stickProtosteel", Bionicle.rodProtosteel);
	    
		OreDictionary.registerOre("gemHeatstone", Bionicle.rawHeatstone);
	    OreDictionary.registerOre("stickBamboo", Bionicle.bambooStick);
	    
	    EntityRegistry.registerModEntity(EntityDisc.class, "Kanoka_Disc", 1, Bionicle.modInstance, 64, 10, true);
	    //TODO Bamboo disc here
	    EntityRegistry.registerModEntity(EntityMahi.class, "Mahi", 3, Bionicle.modInstance, 64, 1, true, 0xE6C381, 0xD1322B);

		EntityRegistry.addSpawn(EntityMahi.class, 15, 2, 5, EnumCreatureType.CREATURE, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.PLAINS, Biomes.MUTATED_PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
	    
	    CraftingManager.getInstance().addRecipe(new RecipeColoredMaskDyes());

//Smelting Recipes
	    GameRegistry.addSmelting(Bionicle.ProtodermisOre, new ItemStack(Bionicle.rawProtodermis, 2), 0.7F);
	    GameRegistry.addSmelting(Bionicle.rawProtodermis, new ItemStack(Bionicle.ingotProtodermis, 1), 0.7F);
	    //GameRegistry.addSmelting(Bionicle.heatstoneLighter, new ItemStack(Bionicle.heatstoneLighter), 0.3F);
	    
	    GameRegistry.addSmelting(new ItemStack(Bionicle.StoneKoroBlock, 1, 1), new ItemStack(Bionicle.StoneKoroBlock, 1, 0), 0.2F);
	    GameRegistry.addSmelting(Blocks.SANDSTONE, new ItemStack(Bionicle.StoneKoroBlock, 1, 3), 0.2F);
	    GameRegistry.addSmelting(new ItemStack(Bionicle.StoneKoroBlock, 1, 3), new ItemStack(Bionicle.StoneKoroBlock, 1, 2), 0.2F);
	    GameRegistry.addSmelting(new ItemStack(Bionicle.StoneKoroBlock, 1, 5), new ItemStack(Bionicle.StoneKoroBlock, 1, 4), 0.2F);
//Shaped Crafting Recipes
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.MaskForge, 1), new Object[] {"#X#", "#W#", "# #", '#', Bionicle.ingotProtodermis, 'X', Items.CAULDRON, 'W', Blocks.FURNACE});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.purifierItem, 1), new Object[] {"BIC", "III", 'B', Items.BUCKET, 'I', Bionicle.ingotProtodermis, 'C', Items.CAULDRON});
	    
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.StoneKoroBlock, 4, 1), new Object[]{"X#", "#X", '#', Blocks.COBBLESTONE, 'X', Blocks.STAINED_HARDENED_CLAY});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.StoneKoroBlock, 4, 1), new Object[]{"#X", "X#", '#', Blocks.COBBLESTONE, 'X', Blocks.STAINED_HARDENED_CLAY});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.LitKoroBlock, 1), new Object[]{ "X", "#", 'X', new ItemStack(Bionicle.StoneKoroBlock, 1, 1), '#', Bionicle.Lightstone});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.LitKoroBlock, 1), new Object[]{ "#", "X", 'X', new ItemStack(Bionicle.StoneKoroBlock, 1, 1), '#', Bionicle.Lightstone});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.StoneKoroBlock, 4, 5), new Object[]{ " X ", "X#X", " X ", 'X', Blocks.COBBLESTONE, '#', Items.COAL});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.StoneKoroBlock, 4, 6), new Object[]{ "XX", "XX", 'X', new ItemStack(Bionicle.StoneKoroBlock, 1, 5)});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.LitKoroBlock, 4, 1), new Object[]{ " X ", "X#X", " X ", 'X', new ItemStack(Bionicle.StoneKoroBlock, 1, 5), '#', Items.LAVA_BUCKET});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.StoneKoroBlock, 4, 8), new Object[]{ " X ", "X#X", " X ", 'X', Blocks.NETHER_BRICK, '#', Items.ROTTEN_FLESH});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.StoneKoroBlock, 1, 7), new Object[]{"X", 'X', new ItemStack(Bionicle.StoneKoroBlock, 1, 8)});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.StoneKoroBlock, 1, 8), new Object[]{"X", 'X', new ItemStack(Bionicle.StoneKoroBlock, 1, 7)});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.IceKoroBlock, 4, 0), new Object[]{ "XX", "XX", 'X', Blocks.ICE});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.IceKoroBlock, 4, 0), new Object[]{ "XX", "XX", 'X', Blocks.PACKED_ICE});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.IceKoroBlock, 4, 1), new Object[]{ "XX", "XX", 'X', new ItemStack(Bionicle.IceKoroBlock, 1, 0)});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.LeafyKoroBlock, 2, 0), new Object[]{ "XX", "XX", 'X', Blocks.LEAVES});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.LeafyKoroBlock, 4, 1), new Object[]{ " X ", "X#X", " X ", 'X', new ItemStack(Bionicle.LeafyKoroBlock, 1, 0), '#', Items.WATER_BUCKET});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.LeafyKoroBlock, 2, 2), new Object[]{ "XX", "XX", 'X', Blocks.VINE});
	    
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.MataNuiStone, 4), new Object[] { "X#", "#X", 'X', Blocks.STONE, '#', Blocks.SANDSTONE});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.MataNuiStone, 4), new Object[] { "#X", "X#", 'X', Blocks.STONE, '#', Blocks.SANDSTONE});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.MakutaStone, 4), new Object[] { "X#", "#X", 'X', Blocks.OBSIDIAN, '#', Blocks.NETHER_BRICK});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.MakutaStone, 4), new Object[] { "#X", "X#", 'X', Blocks.OBSIDIAN, '#', Blocks.NETHER_BRICK});
	    	    
	    GameRegistry.addShapelessRecipe(new ItemStack(Bionicle.ingotProtodermis, 9), Bionicle.BlockProtodermis);
	    GameRegistry.addShapelessRecipe(new ItemStack(Bionicle.ingotProtosteel, 9), Bionicle.BlockProtosteel);
	    GameRegistry.addShapelessRecipe(new ItemStack(Bionicle.nuggetProtodermis, 9), Bionicle.ingotProtodermis);
	    GameRegistry.addShapelessRecipe(new ItemStack(Bionicle.nuggetProtosteel, 9), Bionicle.ingotProtosteel);
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.BlockProtodermis, 1), new Object[] { "XXX", "XXX", "XXX", 'X', Bionicle.ingotProtodermis});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.BlockProtosteel, 1), new Object[] { "XXX", "XXX", "XXX", 'X', Bionicle.ingotProtosteel});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.ingotProtodermis, 1), new Object[] { "XXX", "XXX", "XXX", 'X', Bionicle.nuggetProtodermis});
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.ingotProtosteel, 1), new Object[] { "XXX", "XXX", "XXX", 'X', Bionicle.nuggetProtosteel});

	    GameRegistry.addRecipe(new ShapedOreRecipe(Items.IRON_INGOT, new Object[] { "XXX", "XXX", "XXX", 'X', "nuggetIron"}));
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Bionicle.nuggetIron, 9), new Object[] { "X", 'X', "ingotIron"}));
	    
	    GameRegistry.addShapedRecipe(new ItemStack(Bionicle.bambooStick, 1), new Object[] {"#", "#", "#", '#', Bionicle.Bamboo});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.bambooDisc, 2), new Object[] {"###", '#', Bionicle.Bamboo});
        
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.heatstoneLighter, 1, 128), new Object[] { "X#X", " X ", 'X', Items.IRON_INGOT, '#', Bionicle.rawHeatstone});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.discLauncher, 1), new Object[] {"# #", " # ", " # ", '#', Items.IRON_INGOT});
        GameRegistry.addRecipe(new ShapedOreRecipe(Bionicle.sluice, new Object[] {"I", "N", "N", 'I', "ingotIron", 'N', "nuggetIron"}));
        
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.swordProtodermis, 1), new Object[] {"#", "#", "X", '#', Bionicle.ingotProtodermis, 'X', Bionicle.bambooStick});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.pickProtodermis, 1), new Object[] {"###", " X ", " X ", '#', Bionicle.ingotProtodermis, 'X', Bionicle.bambooStick});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.hatchetProtodermis, 1), new Object[] {"##", "#X", " X", '#', Bionicle.ingotProtodermis, 'X', Bionicle.bambooStick});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.hatchetProtodermis, 1), new Object[] {"##", "X#", "X ", '#', Bionicle.ingotProtodermis, 'X', Bionicle.bambooStick});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.shovelProtodermis, 1), new Object[] {"#", "X", "X", '#', Bionicle.ingotProtodermis, 'X', Bionicle.bambooStick});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.sytheProtodermis, 1), new Object[] {" ##", "#  ", " X ", '#', Bionicle.ingotProtodermis, 'X', Bionicle.bambooStick});
        GameRegistry.addShapedRecipe(new ItemStack(Bionicle.sytheProtodermis, 1), new Object[] {"## ", "  #", " X ", '#', Bionicle.ingotProtodermis, 'X', Bionicle.bambooStick});
	}
	
	private void registerFluidModels() {
		BionicleFluidUtil.modFluidBlocks.forEach(this::registerFluidModel);
	}

	private void registerFluidModel(IFluidBlock fluidBlock) {
		Item item = Item.REGISTRY.getObject(((Block)fluidBlock).getRegistryName());
		ModelBakery.registerItemVariants(item);
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Bionicle.MODID + ":fluid", fluidBlock.getFluid().getName());
		ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));
		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modelResourceLocation;
			}
		});
	}
	
	private static void registerBlock(Block block){
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block), block.getRegistryName());
	}
	
	private static void registerItem(Item item){
		GameRegistry.register(item);
	}

	public ModelBiped getArmorModel(int id){
		return null;
	}
	
	public void init(){
		
	}

	/*public void sendRequestEventPacket(byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data) {

	}

	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
	public static void storeEntityData(String name, NBTTagCompound compound){
		extendedEntityData.put(name, compound);
	}
	public static NBTTagCompound getEntityData(String name){
		return extendedEntityData.remove(name);
	}
	private static final String getSaveKey(EntityPlayer player) {
		return player.getCommandSenderEntity() + ":" + PacketValues.EXT_PROP_NAME;
	}
	public static void saveProxyData(EntityPlayer player) {
		PacketValues playerData = PacketValues.get(player);
		NBTTagCompound savedData = new NBTTagCompound();

		playerData.saveNBTData(savedData);
		CommonProxyBionicle.storeEntityData(getSaveKey(player), savedData);
	}
	public static final void loadProxyData(EntityPlayer player){
		PacketValues playerData = PacketValues.get(player);
		NBTTagCompound savedData = CommonProxyBionicle.getEntityData(getSaveKey(player));
		if (savedData != null) { playerData.loadNBTData(savedData); 
		}
		Bionicle.packetPipeline.sendTo(new SyncPlayerPropsPacketMaskPower(player), (EntityPlayerMP) player);
	}*/
}
