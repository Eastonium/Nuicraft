package eastonium.nuicraft.blocks;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBionicleStone extends BlockHorizontal
{
	public BlockBionicleStone(String name){
		super(Material.ROCK);
		setSoundType(SoundType.STONE);
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setCreativeTab(NuiCraft.bio_block_tab);
		setHarvestLevel("pickaxe", 0);
		setUnlocalizedName(NuiCraft.MODID + "." + name);
		setRegistryName(name);
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta){
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }
	
	@Override
	public int getMetaFromState(IBlockState state){
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

	@Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
}
