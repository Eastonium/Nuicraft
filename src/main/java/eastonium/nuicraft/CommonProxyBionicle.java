package eastonium.nuicraft;

import eastonium.nuicraft.block.BlockBamboo;
import eastonium.nuicraft.block.BlockBionicleStone;
import eastonium.nuicraft.block.BlockKoro;
import eastonium.nuicraft.block.BlockLightstone;
import eastonium.nuicraft.block.BlockMetal;
import eastonium.nuicraft.block.BlockNuvaCube;
import eastonium.nuicraft.block.BlockOre;
import eastonium.nuicraft.block.BlockProtodermisDeposit;
import eastonium.nuicraft.fluid.BlockNuiCraftFluid;
import eastonium.nuicraft.item.ItemBionicleAxe;
import eastonium.nuicraft.item.ItemBionicleHoe;
import eastonium.nuicraft.item.ItemBioniclePick;
import eastonium.nuicraft.item.ItemBionicleShovel;
import eastonium.nuicraft.item.ItemBionicleSword;
import eastonium.nuicraft.item.ItemBlockGeneric;
import eastonium.nuicraft.item.ItemBlockKoro;
import eastonium.nuicraft.item.ItemGenericMeta;
import eastonium.nuicraft.item.ItemHeatstoneLighter;
import eastonium.nuicraft.item.ItemSluice;
import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanohi.ItemGoldMataMask;
import eastonium.nuicraft.kanohi.ItemMask;
import eastonium.nuicraft.kanohi.ItemMaskMeta;
import eastonium.nuicraft.kanoka.EntityDisc;
import eastonium.nuicraft.kanoka.ItemBambooDisc;
import eastonium.nuicraft.kanoka.ItemDiscLauncher;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import eastonium.nuicraft.machine.maskForge.BlockMaskForge;
import eastonium.nuicraft.machine.maskForge.GuiHandlerMaskForge;
import eastonium.nuicraft.machine.maskForge.TileInventoryMaskForge;
import eastonium.nuicraft.machine.purifier.BlockPurifier;
import eastonium.nuicraft.machine.purifier.GuiHandlerPurifier;
import eastonium.nuicraft.machine.purifier.ItemPurifier;
import eastonium.nuicraft.machine.purifier.TileInventoryPurifier;
import eastonium.nuicraft.mobs.mahi.EntityMahi;
import eastonium.nuicraft.util.MeshDefinitionFix;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class CommonProxyBionicle 
{
	public void preInit(){
		NetworkRegistry.INSTANCE.registerGuiHandler(NuiCraft.modInstance, GuiHandler.getInstance());
		
		GameRegistry.registerTileEntity(TileInventoryMaskForge.class, "tileEntityMaskForge");
		GuiHandler.getInstance().registerGuiHandler(new GuiHandlerMaskForge(), GuiHandlerMaskForge.getGuiID());
		
		GameRegistry.registerTileEntity(TileInventoryPurifier.class, "tileEntityPurifier");
		GuiHandler.getInstance().registerGuiHandler(new GuiHandlerPurifier(), GuiHandlerPurifier.getGuiID());

		//Technical entities ID 0-100

	    EntityRegistry.registerModEntity(new ResourceLocation(NuiCraft.MODID, "entityKanokaDisk"), EntityDisc.class, "nuicraft.disc", 1, NuiCraft.modInstance, 64, 10, true);
	    
	    /*EntityRegistry.registerModEntity(EntityKolhiiBall.class, "Kolhii_Ball", 10, this, 64, 10, true),
	    new ItemKolhiiBall().setName("KolhiiBall"),
	    GameRegistry.registerItem(kolhiiBall, "kolhii_ball"),
	    */
	    
		//Mobs ID 101-200

	    EntityRegistry.registerModEntity(new ResourceLocation(NuiCraft.MODID, "entityMahi"), EntityMahi.class, "nuicraft.mahi", 101, NuiCraft.modInstance, 64, 1, true, 0xE6C381, 0xD1322B);
		EntityRegistry.addSpawn(EntityMahi.class, 15, 2, 5, EnumCreatureType.CREATURE, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.PLAINS, Biomes.MUTATED_PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
		
		registerFluids();
	}
	
	public void init(){
	//Smelting Recipes
	    GameRegistry.addSmelting(NuiCraftBlocks.protodermis_ore, NuiCraftItems.getGIIS("raw_protodermis", 2), 0.7F);
	    GameRegistry.addSmelting(NuiCraftItems.getGIIS("raw_protodermis", 1), NuiCraftItems.getGIIS("ingot_protodermis", 1), 0.7F);
	    
	    GameRegistry.addSmelting(new ItemStack(NuiCraftBlocks.stone_koro_block, 1, 1), new ItemStack(NuiCraftBlocks.stone_koro_block, 1, 0), 0.2F);
	    GameRegistry.addSmelting(Blocks.SANDSTONE, new ItemStack(NuiCraftBlocks.stone_koro_block, 1, 3), 0.2F);
	    GameRegistry.addSmelting(new ItemStack(NuiCraftBlocks.stone_koro_block, 1, 3), new ItemStack(NuiCraftBlocks.stone_koro_block, 1, 2), 0.2F);
	    GameRegistry.addSmelting(new ItemStack(NuiCraftBlocks.stone_koro_block, 1, 5), new ItemStack(NuiCraftBlocks.stone_koro_block, 1, 4), 0.2F);

    //Ore Dictionary registries
        //TODO Find where these are supposed to actually go
		OreDictionary.registerOre("gemLightstone", NuiCraftBlocks.lightstone);
		OreDictionary.registerOre("oreLightstone", NuiCraftBlocks.lightstone_ore);
		OreDictionary.registerOre("oreHeatstone", NuiCraftBlocks.heatstone_ore);
		OreDictionary.registerOre("oreProtodermis", NuiCraftBlocks.protodermis_ore);
		
	    OreDictionary.registerOre("cropBamboo", NuiCraftBlocks.bamboo);
		
		OreDictionary.registerOre("blockProtodermis", NuiCraftBlocks.block_protodermis);
		OreDictionary.registerOre("blockProtosteel", NuiCraftBlocks.block_protosteel);		
		OreDictionary.registerOre("ingotProtodermis", NuiCraftItems.getGIIS("ingot_protodermis", 1));
	    OreDictionary.registerOre("ingotProtosteel", NuiCraftItems.getGIIS("ingot_protosteel", 1));
	    OreDictionary.registerOre("nuggetProtodermis", NuiCraftItems.getGIIS("nugget_protodermis", 1));
	    OreDictionary.registerOre("nuggetProtosteel", NuiCraftItems.getGIIS("nugget_protosteel", 1));
	    
		OreDictionary.registerOre("gemHeatstone", NuiCraftItems.getGIIS("raw_heatstone", 1));
	    OreDictionary.registerOre("stickBamboo", NuiCraftItems.getGIIS("bamboo_stick", 1));
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
			new BlockNuiCraftFluid(NuiCraftFluids.protodermis, Material.WATER),
			new BlockNuiCraftFluid(NuiCraftFluids.protodermis_pure, Material.WATER),
			new BlockNuiCraftFluid(NuiCraftFluids.protodermis_molten, Material.LAVA),
			
			new BlockMaskForge().setHardness(3.5F),
			new BlockPurifier().setHardness(3.5F),

			new BlockKoro("stone_koro_block", Material.ROCK, MapColor.OBSIDIAN).setHardness(1.5F).setResistance(5F),
			new BlockKoro("lit_koro_block", Material.REDSTONE_LIGHT, MapColor.OBSIDIAN).setLightLevel(1.0F).setHardness(1.5F).setResistance(5F),
			new BlockKoro("icy_koro_block", Material.PACKED_ICE, MapColor.ICE).setHardness(1.5F).setResistance(3F),
			new BlockKoro("leafy_koro_block", Material.LEAVES, MapColor.FOLIAGE).setHardness(1.5F).setResistance(3F),
			
			new BlockNuvaCube().setLightLevel(0.625F).setHardness(-1.0F).setResistance(6000000.0F),
			//new BlockCrystalizedProtodermis().setBlockUnbreakable().setLightOpacity(3).setResistance(6000000.0F),
			new BlockBionicleStone("matanui_stone").setHardness(1.5F).setResistance(10.0F),
			new BlockBionicleStone("makuta_stone").setHardness(1.5F).setResistance(10.0F),
			new BlockLightstone().setHardness(0.0F).setLightLevel(1.0F),
			
			new BlockOre("lightstone_ore", 2).setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setTickRandomly(true),
			new BlockOre("heatstone_ore", 2).setHardness(3.0F).setResistance(5.0F),
			new BlockProtodermisDeposit().setHardness(3.0F).setResistance(5.0F),
			
			new BlockBamboo().setHardness(1.0F).setResistance(9001.0F), //bleggaman - "bamboo is freaking tough!"
			
			new BlockMetal("block_protodermis", Material.IRON).setHardness(5.0F).setResistance(10.0F),
			new BlockMetal("block_protosteel", Material.IRON).setHardness(5.0F).setResistance(10.0F)
		);
	}
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
		ItemGenericMeta.addGenericItem("ingot_protodermis");
		ItemGenericMeta.addGenericItem("ingot_protosteel");
		ItemGenericMeta.addGenericItem("nugget_protodermis");
		ItemGenericMeta.addGenericItem("nugget_protosteel");
		ItemGenericMeta.addGenericItem("raw_protodermis");
		ItemGenericMeta.addGenericItem("raw_heatstone");    
		ItemGenericMeta.addGenericItem("bamboo_stick");
		ItemGenericMeta.addGenericItem("kanoka_flying", false, true);
		ItemGenericMeta.addGenericItem("kanoka_time", true);
		ItemGenericMeta.addGenericItem("dye_mask", false, true);
		
		event.getRegistry().registerAll(
			new ItemBlockGeneric(NuiCraftBlocks.fluid_protodermis),
			new ItemBlockGeneric(NuiCraftBlocks.fluid_protodermis_pure),
			new ItemBlockGeneric(NuiCraftBlocks.fluid_protodermis_molten),				
				
			new ItemBlockGeneric(NuiCraftBlocks.mask_forge),
			new ItemPurifier(),
			//new ItemBlockGeneric(NuiCraftBlocks.purifier),
			
			new ItemBlockKoro(NuiCraftBlocks.stone_koro_block),
			new ItemBlockKoro(NuiCraftBlocks.lit_koro_block),
			new ItemBlockKoro(NuiCraftBlocks.icy_koro_block),
			new ItemBlockKoro(NuiCraftBlocks.leafy_koro_block),

			new ItemBlockGeneric(NuiCraftBlocks.nuva_cube),
			
			new ItemBlockGeneric(NuiCraftBlocks.matanui_stone),
			new ItemBlockGeneric(NuiCraftBlocks.makuta_stone),
			new ItemBlockGeneric(NuiCraftBlocks.lightstone),

			new ItemBlockGeneric(NuiCraftBlocks.lightstone_ore),
			new ItemBlockGeneric(NuiCraftBlocks.heatstone_ore),
			new ItemBlockGeneric(NuiCraftBlocks.protodermis_ore),
			
			new ItemBlockGeneric(NuiCraftBlocks.bamboo),
			new ItemBlockGeneric(NuiCraftBlocks.block_protodermis),
			new ItemBlockGeneric(NuiCraftBlocks.block_protosteel),
			
			//Generic Items
			new ItemGenericMeta(),

			//Kanoka
			new ItemBambooDisc(),
			new ItemKanokaDisc(),
			
			//Weapons	    
			new ItemDiscLauncher(),	    
			new ItemBionicleSword("protodermis_sword", NuiCraft.PROTODERMIS),
			
			//Tools	    
			new ItemBioniclePick("protodermis_pick", NuiCraft.PROTODERMIS),
			new ItemBionicleAxe("protodermis_axe", NuiCraft.PROTODERMIS, 2.0F, -2.9F),
			new ItemBionicleShovel("protodermis_shovel", NuiCraft.PROTODERMIS),
			new ItemBionicleHoe("protodermis_sythe", NuiCraft.PROTODERMIS),
		    
			new ItemHeatstoneLighter(),
			new ItemSluice(),
			
			//Mata Masks
			new ItemGoldMataMask(),
			new ItemColoredMask("mask_mata_kakama"),
			new ItemColoredMask("mask_mata_pakari"),
			new ItemColoredMask("mask_mata_kaukau"),
			new ItemColoredMask("mask_mata_miru"),
			new ItemColoredMask("mask_mata_hau"),
			new ItemColoredMask("mask_mata_akaku"),
			
			//Nuva Masks
			new ItemMask("mask_nuva_kakama"),
			new ItemMask("mask_nuva_pakari"),
			new ItemMask("mask_nuva_kaukau"),
			new ItemMask("mask_nuva_miru"),
			new ItemMask("mask_nuva_hau"),
			new ItemMask("mask_nuva_akaku"),
			
			//Legendary Masks    
			new ItemMaskMeta("mask_ignika", 3, true),
			new ItemMaskMeta("mask_vahi", 2, true)
		);
	}
	
	public static void registerFluids() {
		FluidRegistry.addBucketForFluid(NuiCraftFluids.protodermis);
		FluidRegistry.addBucketForFluid(NuiCraftFluids.protodermis_pure);
		FluidRegistry.addBucketForFluid(NuiCraftFluids.protodermis_molten);
	}


	private void registerFluidModel(IFluidBlock fluidBlock) {
		Item item = Item.REGISTRY.getObject(((Block)fluidBlock).getRegistryName());
		ModelBakery.registerItemVariants(item);
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(NuiCraft.MODID + ":fluid", fluidBlock.getFluid().getName());
		ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));
		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modelResourceLocation;
			}
		});
	}

	public ModelBiped getArmorModel(int id){
		return null;
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
