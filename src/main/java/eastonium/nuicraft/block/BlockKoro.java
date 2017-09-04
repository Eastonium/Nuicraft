package eastonium.nuicraft.block;

import java.util.List;

import javax.annotation.Nullable;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockKoro extends Block {    
	public BlockKoro() {
		super(Material.ROCK);
		setHardness(1.5F);
		setResistance(5F);
        setDefaultState(blockState.getBaseState().withProperty(getMETA(), Integer.valueOf(0)));
		setCreativeTab(NuiCraft.nuicraftTab);
		setUnlocalizedName(NuiCraft.MODID + ".koro_block");
		setRegistryName("koro_block");
	}
	
	private PropertyInteger getMETA() {
		return PropertyInteger.create("meta", 0, EnumKoroBlock.values().length - 1);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
        return state.getValue(getMETA());
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (int i : getMETA().getAllowedValues()){
            items.add(new ItemStack(this, 1, i));
        }
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(getMETA(), Integer.valueOf(meta));
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(getMETA());
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[]{getMETA()});
    }
    
    @Override
    public Material getMaterial(IBlockState state) {
    	return EnumKoroBlock.byMetadata(getMetaFromState(state)).getMaterial();
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	return EnumKoroBlock.byMetadata(getMetaFromState(state)).getMapColor();
    }
    
    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
    	return EnumKoroBlock.byMetadata(getMetaFromState(state)).getSoundType();
    }
    
    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    	return EnumKoroBlock.byMetadata(getMetaFromState(state)).getLightValue();
    }
    
    @Override
    @Nullable public String getHarvestTool(IBlockState state) {
    	return EnumKoroBlock.byMetadata(getMetaFromState(state)).getHarvestTool();
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
    	return EnumKoroBlock.byMetadata(getMetaFromState(state)).getHarvestLevel();
    }
	
    public enum EnumKoroBlock implements IStringSerializable {
//		public static final String[] stoneBlockNames = new String[] {"onu_1", "onu_2", "po_1", "po_2", "ta_1", "ta_2", "ta_3", "trdx_1", "trdx_2"};
//		public static final String[] litBlockNames = new String[] {"onu_0", "ta_0"};
//		public static final String[] iceBlockNames = new String[] {"ko_1", "ko_2"};
//		public static final String[] leafyBlockNames = new String[] {"ga_1", "ga_2", "ga_3"};
    	
		PROTO(0, "proto", Material.ROCK, MapColor.GRAY, SoundType.STONE, 0, "pickaxe", 0),
    	PROTO_SMOOTH(1, "proto_smooth", Material.ROCK, MapColor.GRAY, SoundType.STONE, 0, "pickaxe", 0),
    	PROTO_LIGHT(2, "proto_light", Material.ROCK, MapColor.YELLOW, SoundType.STONE, 15, "pickaxe", 0),
    	SANDY_PROTO(3, "proto_sandy", Material.ROCK, MapColor.YELLOW_STAINED_HARDENED_CLAY, SoundType.STONE, 0, "pickaxe", 0),
    	SANDY_PROTO_SMOOTH(4, "proto_sandy_smooth", Material.ROCK, MapColor.YELLOW_STAINED_HARDENED_CLAY, SoundType.STONE, 0, "pickaxe", 0),
    	DARK_PROTO(5, "proto_dark", Material.ROCK, MapColor.BLACK, SoundType.STONE, 0, "pickaxe", 0),
    	DARK_PROTO_SMOOTH(6, "proto_dark_smooth", Material.ROCK, MapColor.BLACK, SoundType.STONE, 0, "pickaxe", 0),
    	DARK_PROTO_BRICK(7, "proto_dark_brick", Material.ROCK, MapColor.BLACK, SoundType.STONE, 0, "pickaxe", 0),
    	DARK_PROTO_LIGHT(8, "proto_dark_light", Material.ROCK, MapColor.GOLD, SoundType.STONE, 15, "pickaxe", 0),
    	FROZEN_PROTO(9, "proto_frozen", Material.PACKED_ICE, MapColor.ICE, SoundType.GLASS, 0, "pickaxe", 0),
    	FROZEN_PROTO_BRICK(10, "proto_frozen_brick", Material.PACKED_ICE, MapColor.ICE, SoundType.GLASS, 0, "pickaxe", 0),
    	SEAWEED_ROUGH(11, "seaweed_rough", Material.LEAVES, MapColor.GRASS, SoundType.PLANT, 0, "axe", 0),
    	SEAWEED_LUMPY(12, "seaweed_lumpy", Material.CLAY, MapColor.GRASS, SoundType.PLANT, 0, "axe", 0),
    	SOAWEED_SMOOTH(13, "seaweed_smooth", Material.CLAY, MapColor.GRASS, SoundType.SLIME, 0, "axe", 0),
    	INFECTED_BRICK(14, "infected_brick", Material.ROCK, MapColor.BLACK, SoundType.STONE, 0, "pickaxe", 0),
    	INFECTED_BRICK_LARGE(15, "infected_brick_large", Material.ROCK, MapColor.BLACK, SoundType.STONE, 0, "pickaxe", 0);
// RIP metal blocks -- metadata only allows for 16 variants
//    	PROTO_METAL(16, "proto_metal", "protoMetal", Material.IRON, MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY, SoundType.METAL, 0, "pickaxe", 0),
//    	PROTOSTEEL(17, "protosteel", "protosteel", Material.IRON, MapColor.CYAN_STAINED_HARDENED_CLAY, SoundType.METAL, 0, "pickaxe", 1);

        private static final EnumKoroBlock[] META_LOOKUP = new EnumKoroBlock[values().length];
    	private final int meta;
    	private final String name;
    	private final Material material;
    	private final MapColor mapColor;
    	private final SoundType sound;
    	private final int lightValue;
    	private final String harvestTool;
    	private final int harvestLevel;  

    	private EnumKoroBlock(int meta, String name, Material material, MapColor mapColor, 
    			SoundType sound, int lightValue, String harvestTool, int harvestLevel) {
    		this.meta = meta;
    		this.name = name;
    		this.material = material;
    		this.mapColor = mapColor;
    		this.sound = sound;
    		this.lightValue = lightValue;
    		this.harvestTool = harvestTool;
    		this.harvestLevel = harvestLevel;
    	}
    	
    	public ItemStack getStack(int count) {
    		return new ItemStack(NuiCraftBlocks.koro_block, count, this.getMetadata());
    	}
    	
    	public int getMetadata() {
    		return this.meta;
    	}
    	
    	@Override
		public String getName() {
			return this.name;
		}
    	
    	public Material getMaterial() {
    		return this.material;
    	}
    	
    	public MapColor getMapColor() {
    		return this.mapColor;
    	}
    	
    	public SoundType getSoundType() {
    		return this.sound;
    	}
    	
    	public int getLightValue() {
    		return this.lightValue;
    	}
    	
    	public String getHarvestTool() {
    		return this.harvestTool;
    	}
    	
    	public int getHarvestLevel() {
    		return this.harvestLevel;
    	}

    	@Override
    	public String toString() {
            return this.name;
        }
    	
    	public static EnumKoroBlock byMetadata(int meta) {
            return META_LOOKUP[meta % META_LOOKUP.length];
        }

		static {
	        for (EnumKoroBlock enumGenericBlock : values()) {
	            META_LOOKUP[enumGenericBlock.getMetadata()] = enumGenericBlock;
	        }
	    }		
    }
}
