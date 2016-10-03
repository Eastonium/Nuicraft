package eastonium.nuicraft;

import java.util.HashSet;
import java.util.Set;

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
import eastonium.nuicraft.items.ItemHeatstoneLighter;
import eastonium.nuicraft.items.ItemNormal;
import eastonium.nuicraft.items.ItemSluice;
import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanohi.ItemGoldMataMask;
import eastonium.nuicraft.kanohi.ItemMask;
import eastonium.nuicraft.kanohi.ItemMaskMeta;
import eastonium.nuicraft.kanoka.ItemBambooDisc;
import eastonium.nuicraft.kanoka.ItemDiscLauncher;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import eastonium.nuicraft.machine.maskForge.BlockMaskForge;
import eastonium.nuicraft.machine.purifier.BlockPurifier;
import eastonium.nuicraft.machine.purifier.ItemPurifier;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Bionicle.MODID, name = "Bionicle", version = "V0.8.1.1 MC1.10.2")
public class Bionicle 
{
	public static final String MODID = "nuicraft";
	//public static final String CHANNEL = "Masks";
	
	@Instance(Bionicle.MODID)
	public static Bionicle modInstance = new Bionicle();
	public static ItemMeshDefinition itemMeshDef = new BionicleItemMeshDef();
	//public static final PacketPipeline packetPipeline = new PacketPipeline();
	
	public static CreativeTabs bioBlockTab;
	public static CreativeTabs bioMaterialTab;
	public static CreativeTabs bioToolTab;
	public static CreativeTabs bioMaskTab;

	public static ToolMaterial PROTODERMIS = EnumHelper.addToolMaterial("Protodermis", 2, 500, 5.0F, 2.0F, 7);
	public static ToolMaterial PROTOSTEEL = EnumHelper.addToolMaterial("Protosteel", 7, 4620, 11.0F, 5.0F, 15);
//Fluids
	public static Fluid protodermis;
		
//Blocks
	public static Block MaskForge;
	public static Block Purifier;
	public static Item purifierItem;
	
	public static Block StoneKoroBlock;
	public static Block LitKoroBlock;
	public static Block IceKoroBlock;
	public static Block LeafyKoroBlock;
	
	public static Block NuvaCube;
	//public static Block CrystalizedProtodermis;
	public static Block MataNuiStone;
	public static Block MakutaStone;
	public static Block LightstoneOre;	
	public static Block Lightstone;
	public static Block HeatstoneOre;
	public static Block ProtodermisOre;
	
	public static BlockBamboo Bamboo;
	
	public static Block BlockProtodermis;
	public static Block BlockProtosteel;
//Items
	public static Item ingotProtodermis;
	public static Item ingotProtosteel;
	public static Item nuggetProtodermis;
	public static Item nuggetProtosteel;
	public static Item nuggetIron;
	public static Item rawProtodermis;
	public static Item rawHeatstone;
	public static Item bambooStick;
	
	//public static Item kolhiiBall;	
//Kanoka
	public static Item kanokaFlying;
	public static Item bambooDisc;
	public static Item kanokaDisc;
	public static Item kanokaTime;
	
//Weapons
	public static Item discLauncher;	
	public static Item swordProtodermis;
//Tools
	public static Item pickProtodermis;
	public static Item hatchetProtodermis;
	public static Item shovelProtodermis;
	public static Item sytheProtodermis;
	public static Item heatstoneLighter;
	public static Item sluice;
//Masks	
	public static Item maskMataGold;	
	public static Item maskMataKakama;
	public static Item maskMataPakari;
	public static Item maskMataKaukau;
	public static Item maskMataMiru;
	public static Item maskMataHau;
	public static Item maskMataAkaku;
	
	public static Item maskNuvaKakama;
	public static Item maskNuvaPakari;
	public static Item maskNuvaKaukau;
	public static Item maskNuvaMiru;
	public static Item maskNuvaHau;
	public static Item maskNuvaAkaku;

	public static Item maskIgnika;
	public static Item maskVahi;

	@SidedProxy(clientSide = "eastonium.nuicraft.ClientProxyBionicle", serverSide = "eastonium.nuicraft.CommonProxyBionicle")
	public static CommonProxyBionicle proxy;
	
	public Bionicle(){
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		bioBlockTab = new CreativeTabs("bioBlockTab"){public Item getTabIconItem(){return Item.getItemFromBlock(NuvaCube);}};
		bioMaterialTab = new CreativeTabs("bioMaterialTab"){public Item getTabIconItem(){return Item.getItemFromBlock(Lightstone);}};
		bioToolTab = new CreativeTabs("bioWeaponTab"){public Item getTabIconItem(){return discLauncher;}};
		bioMaskTab = new CreativeTabs("bioMaskTab"){public Item getTabIconItem(){return maskVahi;}};
 
		PROTODERMIS = PROTODERMIS.setRepairItem(new ItemStack(Bionicle.ingotProtodermis));
		PROTOSTEEL = PROTOSTEEL.setRepairItem(new ItemStack(Bionicle.ingotProtosteel));
//Fluids
		protodermis = BionicleFluidUtil.createFluid("protodermis", MODID + ":blocks/protodermis", true,
				fluid -> fluid.setRarity(EnumRarity.UNCOMMON),
				fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.LIGHT_BLUE)));
		
		protodermis = BionicleFluidUtil.createFluid("protodermisMolten", MODID + ":blocks/protodermisMolten", true,
				fluid -> fluid.setRarity(EnumRarity.UNCOMMON).setLuminosity(15).setDensity(3500).setViscosity(5500).setTemperature(2800),
				fluid -> new BlockFluidClassic(fluid, Material.LAVA));
//Blocks
		MaskForge = new BlockMaskForge().setName("MaskForge").setHardness(3.5F);
		Purifier = new BlockPurifier().setName("Purifier").setHardness(3.5F);
	    purifierItem = new ItemPurifier().setName("purifierItem");

		StoneKoroBlock = new BlockKoro(Material.ROCK, MapColor.OBSIDIAN).setName("StoneKoroBlock").setHardness(1.5F).setResistance(5F);
		StoneKoroBlock.setHarvestLevel("pickaxe", 0);		
		LitKoroBlock = new BlockKoro(Material.REDSTONE_LIGHT, MapColor.OBSIDIAN).setName("LitKoroBlock").setLightLevel(1.0F).setHardness(1.5F).setResistance(5F);
		LitKoroBlock.setHarvestLevel("pickaxe", 0);
		IceKoroBlock = new BlockKoro(Material.PACKED_ICE, MapColor.ICE).setName("IceKoroBlock").setHardness(1.5F).setResistance(3F);
		IceKoroBlock.setHarvestLevel("pickaxe", 0);		
		LeafyKoroBlock = new BlockKoro(Material.LEAVES, MapColor.FOLIAGE).setName("LeafyKoroBlock").setHardness(1.5F).setResistance(3F);
		LeafyKoroBlock.setHarvestLevel("axe", 0);
		
		NuvaCube = new BlockNuvaCube().setName("NuvaCube").setLightLevel(0.625F).setHardness(-1.0F).setResistance(6000000.0F);
		
		//CrystalizedProtodermis = new BlockCrystalizedProtodermis().setBlockUnbreakable().setLightOpacity(3).setResistance(6000000.0F).setStepSound(Block.soundTypeGlass).setCreativeTab(bioBlockTab);
		
		MataNuiStone = new BlockBionicleStone().setName("MataNuiStone").setHardness(1.5F).setResistance(10.0F);
		MakutaStone = new BlockBionicleStone().setName("MakutaStone").setHardness(1.5F).setResistance(10.0F);
	
		Lightstone = new BlockLightstone().setName("Lightstone").setHardness(0.0F).setLightLevel(1.0F);
		
		LightstoneOre = new BlockOre().setName("LightstoneOre").setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setTickRandomly(true);
		LightstoneOre.setHarvestLevel("pickaxe", 2);
		
		HeatstoneOre = new BlockOre().setName("HeatstoneOre").setHardness(3.0F).setResistance(5.0F);
		HeatstoneOre.setHarvestLevel("pickaxe", 2);
		
		ProtodermisOre = new BlockProtodermisDeposit().setName("ProtodermisOre").setHardness(3.0F).setResistance(5.0F);
		ProtodermisOre.setHarvestLevel("pickaxe", 1);
		
		Bamboo = (BlockBamboo) new BlockBamboo().setName("Bamboo").setHardness(0.2F);
		
		BlockProtodermis = new BlockMetal(Material.IRON).setName("BlockProtodermis").setHardness(5.0F).setResistance(10.0F);
		BlockProtosteel = new BlockMetal(Material.IRON).setName("BlockProtosteel").setHardness(5.0F).setResistance(10.0F);
//Items
		ingotProtodermis = new ItemNormal(false).setName("ingotProtodermis");		
	    ingotProtosteel = new ItemNormal(false).setName("ingotProtosteel");
	    nuggetProtodermis = new ItemNormal(false).setName("nuggetProtodermis");
	    nuggetProtosteel = new ItemNormal(false).setName("nuggetProtosteel");
	    nuggetIron = new ItemNormal(false).setUnlocalizedName(MODID + ".nuggetIron").setRegistryName(MODID, "nuggetIron");
	    rawProtodermis = new ItemNormal(false).setName("rawProtodermis");
		rawHeatstone = new ItemNormal(false).setName("heatstone");	    
	    bambooStick = new ItemNormal(false).setName("bambooStick");
	    	    
	    /*EntityRegistry.registerModEntity(EntityKolhiiBall.class, "Kolhii_Ball", 10, this, 64, 10, true);
	    kolhiiBall = new ItemKolhiiBall().setName("KolhiiBall");
	    GameRegistry.registerItem(kolhiiBall, "kolhii_ball");
	    */

//Kanoka
	    kanokaFlying = new ItemNormal(false).setName("discFlying").setCreativeTab(null);
	    bambooDisc = new ItemBambooDisc().setName("kanokaBamboo");
		kanokaDisc = new ItemKanokaDisc().setName("kanokaDisc");
		kanokaTime = new ItemNormal(true).setName("kanokaTime").setCreativeTab(bioToolTab);
		
//Weapons	    
	    discLauncher = new ItemDiscLauncher().setName("discLauncher");	    
	    swordProtodermis = new ItemBionicleSword(PROTODERMIS).setName("protoSword");	    
//Tools	    
	    pickProtodermis = new ItemBioniclePick(PROTODERMIS).setName("protoPick");
	    hatchetProtodermis = new ItemBionicleAxe(PROTODERMIS, 2.0F, -2.9F).setName("protoAxe");
	    shovelProtodermis = new ItemBionicleShovel(PROTODERMIS).setName("protoShovel");
	    sytheProtodermis = new ItemBionicleHoe(PROTODERMIS).setName("protoHoe");
	    
	    heatstoneLighter = new ItemHeatstoneLighter().setName("heatstoneLighter");	 
	    sluice = new ItemSluice().setName("sluice");
//Mata Masks
	    maskMataGold = new ItemGoldMataMask().setName("maskMataGold");	    
	    maskMataKakama = new ItemColoredMask(false).setName("maskMataKakama");
	    maskMataPakari = new ItemColoredMask(false).setName("maskMataPakari");
	    maskMataKaukau = new ItemColoredMask(false).setName("maskMataKaukau");
	    maskMataMiru = new ItemColoredMask(false).setName("maskMataMiru");
	    maskMataHau = new ItemColoredMask(false).setName("maskMataHau");
	    maskMataAkaku = new ItemColoredMask(false).setName("maskMataAkaku");
//Nuva Masks
	    maskNuvaKakama = new ItemMask(false).setName("maskNuvaKakama");
	    maskNuvaPakari = new ItemMask(false).setName("maskNuvaPakari");
	    maskNuvaKaukau = new ItemMask(false).setName("maskNuvaKaukau");
	    maskNuvaMiru = new ItemMask(false).setName("maskNuvaMiru");
	    maskNuvaHau = new ItemMask(false).setName("maskNuvaHau");
	    maskNuvaAkaku = new ItemMask(false).setName("maskNuvaAkaku");
//Legendary Masks    
	    maskIgnika = new ItemMaskMeta(true, 3).setName("maskIgnika");
	    maskVahi = new ItemMaskMeta(true, 2).setName("maskVahi");
//Loadings
		proxy.preInit();
		MinecraftForge.EVENT_BUS.register(new ServerTickHandler());
		//MinecraftForge.EVENT_BUS.register(new JoinWorld());
		//MinecraftForge.EVENT_BUS.register(new EntityConstructingEvent());
		//MinecraftForge.EVENT_BUS.register(new BionicleEventHooks());
		GameRegistry.registerWorldGenerator(new WorldGeneratorBionicle(), 2);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		proxy.init();
		//packetPipeline.initialise();
	}
	/*
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		packetPipeline.postInitialise();
	}*/
}
