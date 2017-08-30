package eastonium.nuicraft;

import org.apache.logging.log4j.Logger;

import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.proxy.CommonProxyBionicle;
import eastonium.nuicraft.util.NuiCraftItemMeshDef;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = NuiCraft.MODID, name = "NuiCraft", version = "1.12.1-0.8.1.2")
public class NuiCraft 
{
	public static final String MODID = "nuicraft";
	
	@Instance(NuiCraft.MODID)
	public static NuiCraft modInstance = new NuiCraft();
	
	public static Logger logger;
	
	public static ItemMeshDefinition itemMeshDef = new NuiCraftItemMeshDef();
	
	public static CreativeTabs nuicraftTab;
	public static CreativeTabs nuicraftMaskTab;

	public static ToolMaterial PROTODERMIS = EnumHelper.addToolMaterial("Protodermis", 2, 500, 5.0F, 2.0F, 7);
	public static ToolMaterial PROTOSTEEL = EnumHelper.addToolMaterial("Protosteel", 7, 4620, 11.0F, 5.0F, 15);
//Fluids
	public static Fluid protodermis;

	@SidedProxy(clientSide = "eastonium.nuicraft.proxy.ClientProxyBionicle", serverSide = "eastonium.nuicraft.proxy.CommonProxyBionicle")
	public static CommonProxyBionicle proxy;
	
	public NuiCraft(){
		FluidRegistry.enableUniversalBucket();
	}
	
	public static ItemStack getRedHau() {
		ItemColoredMask i = (ItemColoredMask)NuiCraftItems.mask_mata_hau;
		ItemStack iS = new ItemStack(i);
		i.setColor(iS, 16711680);
		return iS;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		logger = event.getModLog();
		
		nuicraftTab = new CreativeTabs("nuicraftTab"){public ItemStack getTabIconItem(){return new ItemStack(NuiCraftBlocks.nuva_cube);}};
		nuicraftMaskTab = new CreativeTabs("nuicraftMaskTab"){public ItemStack getTabIconItem(){ return getRedHau(); }};

		proxy.preInit();
		MinecraftForge.EVENT_BUS.register(new ServerTickHandler());
		GameRegistry.registerWorldGenerator(new NuiCraftWorldGenerator(), 2);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		PROTODERMIS = PROTODERMIS.setRepairItem(NuiCraftItems.getGIIS("ingot_protodermis", 1));
		PROTOSTEEL = PROTOSTEEL.setRepairItem(NuiCraftItems.getGIIS("ingot_protosteel", 1));

		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		proxy.postInit();
	}
}
