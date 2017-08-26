package eastonium.nuicraft;

import eastonium.nuicraft.blocks.BlockBamboo;
import eastonium.nuicraft.blocks.BlockBionicleStone;
import eastonium.nuicraft.blocks.BlockKoro;
import eastonium.nuicraft.blocks.BlockLightstone;
import eastonium.nuicraft.blocks.BlockMetal;
import eastonium.nuicraft.blocks.BlockNuvaCube;
import eastonium.nuicraft.blocks.BlockOre;
import eastonium.nuicraft.blocks.BlockProtodermisDeposit;
import eastonium.nuicraft.fluid.BionicleFluidUtil;
import eastonium.nuicraft.items.ItemBionicleAxe;
import eastonium.nuicraft.items.ItemBionicleHoe;
import eastonium.nuicraft.items.ItemBioniclePick;
import eastonium.nuicraft.items.ItemBionicleShovel;
import eastonium.nuicraft.items.ItemBionicleSword;
import eastonium.nuicraft.items.ItemBlockKoro;
import eastonium.nuicraft.items.ItemGenericMeta;
import eastonium.nuicraft.items.ItemHeatstoneLighter;
import eastonium.nuicraft.items.ItemSluice;
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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
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
	    
	    EntityRegistry.registerModEntity(new ResourceLocation(NuiCraft.MODID, "entityKanokaDisk"), EntityDisc.class, "Kanoka_Disc", 1, NuiCraft.modInstance, 64, 10, true);
	    //TODO Bamboo disc here
	    
	    EntityRegistry.registerModEntity(new ResourceLocation(NuiCraft.MODID, "entityMahi"), EntityMahi.class, "Mahi", 3, NuiCraft.modInstance, 64, 1, true, 0xE6C381, 0xD1322B);
		EntityRegistry.addSpawn(EntityMahi.class, 15, 2, 5, EnumCreatureType.CREATURE, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.PLAINS, Biomes.MUTATED_PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
		
		registerFluidModels();
	}
	
	private void registerFluidModels() {
		BionicleFluidUtil.modFluidBlocks.forEach(this::registerFluidModel);
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
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
			new BlockMaskForge().setHardness(3.5F),
			new BlockPurifier().setHardness(3.5F),

			new BlockKoro("stone_koro_block", Material.ROCK, MapColor.OBSIDIAN).setHardness(1.5F).setResistance(5F),//.setHarvestLevel("pickaxe", 0),		
			new BlockKoro("lit_koro_block", Material.REDSTONE_LIGHT, MapColor.OBSIDIAN).setLightLevel(1.0F).setHardness(1.5F).setResistance(5F),//.setHarvestLevel("pickaxe", 0),
			new BlockKoro("icy_koro_block", Material.PACKED_ICE, MapColor.ICE).setHardness(1.5F).setResistance(3F),//.setHarvestLevel("pickaxe", 0),		
			new BlockKoro("leafy_koro_block", Material.LEAVES, MapColor.FOLIAGE).setHardness(1.5F).setResistance(3F),//.setHarvestLevel("axe", 0),
			
			new BlockNuvaCube().setLightLevel(0.625F).setHardness(-1.0F).setResistance(6000000.0F),
			
			//new BlockCrystalizedProtodermis().setBlockUnbreakable().setLightOpacity(3).setResistance(6000000.0F).setStepSound(Block.soundTypeGlass).setCreativeTab(bioBlockTab),
			
			new BlockBionicleStone("matanui_stone").setHardness(1.5F).setResistance(10.0F),
			new BlockBionicleStone("makuta_stone").setHardness(1.5F).setResistance(10.0F),
			new BlockLightstone().setHardness(0.0F).setLightLevel(1.0F),
			
			new BlockOre("lightstone_ore").setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setTickRandomly(true),//.setHarvestLevel("pickaxe", 2),
			new BlockOre("heatstone_ore").setHardness(3.0F).setResistance(5.0F),//.setHarvestLevel("pickaxe", 2),
			new BlockProtodermisDeposit().setHardness(3.0F).setResistance(5.0F),//.setHarvestLevel("pickaxe", 1),
			
			new BlockBamboo().setHardness(0.2F),
			
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
		ItemGenericMeta.addGenericItem("kanoka_flying", null);
		ItemGenericMeta.addGenericItem("kanoka_time", NuiCraft.bio_tool_tab, true);
		ItemGenericMeta.addGenericItem("dye_mask", null);
		
		event.getRegistry().registerAll(
			new ItemBlock(NuiCraftBlocks.mask_forge).setRegistryName(NuiCraftBlocks.mask_forge.getRegistryName()),
			//new ItemBlock(NuiCraftBlocks.purifier).setRegistryName(NuiCraftBlocks.purifier.getRegistryName()),
			
			new ItemBlockKoro(NuiCraftBlocks.stone_koro_block),
			new ItemBlockKoro(NuiCraftBlocks.lit_koro_block),
			new ItemBlockKoro(NuiCraftBlocks.icy_koro_block),
			new ItemBlockKoro(NuiCraftBlocks.leafy_koro_block),

			new ItemBlock(NuiCraftBlocks.nuva_cube).setRegistryName(NuiCraftBlocks.nuva_cube.getRegistryName()),
			
			new ItemBlock(NuiCraftBlocks.matanui_stone).setRegistryName(NuiCraftBlocks.matanui_stone.getRegistryName()),
			new ItemBlock(NuiCraftBlocks.makuta_stone).setRegistryName(NuiCraftBlocks.makuta_stone.getRegistryName()),
			new ItemBlock(NuiCraftBlocks.lightstone).setRegistryName(NuiCraftBlocks.lightstone.getRegistryName()),

			new ItemBlock(NuiCraftBlocks.lightstone_ore).setRegistryName(NuiCraftBlocks.lightstone_ore.getRegistryName()),
			new ItemBlock(NuiCraftBlocks.heatstone_ore).setRegistryName(NuiCraftBlocks.heatstone_ore.getRegistryName()),
			new ItemBlock(NuiCraftBlocks.protodermis_ore).setRegistryName(NuiCraftBlocks.protodermis_ore.getRegistryName()),
			
			new ItemBlock(NuiCraftBlocks.bamboo).setRegistryName(NuiCraftBlocks.bamboo.getRegistryName()),
			new ItemBlock(NuiCraftBlocks.block_protodermis).setRegistryName(NuiCraftBlocks.block_protodermis.getRegistryName()),
			new ItemBlock(NuiCraftBlocks.block_protosteel).setRegistryName(NuiCraftBlocks.block_protosteel.getRegistryName()),
							
			new ItemPurifier(),			
		    	    
		    /*EntityRegistry.registerModEntity(EntityKolhiiBall.class, "Kolhii_Ball", 10, this, 64, 10, true),
		    new ItemKolhiiBall().setName("KolhiiBall"),
		    GameRegistry.registerItem(kolhiiBall, "kolhii_ball"),
		    */
			
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
