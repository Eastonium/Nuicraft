package eastonium.nuicraft.blocks;

import java.util.Random;

import eastonium.nuicraft.Bionicle;
import eastonium.nuicraft.particle.LighstoneFX;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLightstone extends BlockTorch {
	public BlockLightstone(){
		super();
        this.setCreativeTab(Bionicle.bioMaterialTab);
	}
	
	public Block setName(String name){
        this.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand){
		if(!worldIn.isRemote) return;
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.35D;
        double d2 = (double)pos.getZ() + 0.5D;
        double d3 = 0.3D;
        double d4 = 0.35D;

        if (enumfacing.getAxis().isHorizontal()){
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            Minecraft.getMinecraft().effectRenderer.addEffect(new LighstoneFX(worldIn, d0 + d4 * (double)enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double)enumfacing1.getFrontOffsetZ()));
        }else{ 
        	Minecraft.getMinecraft().effectRenderer.addEffect(new LighstoneFX(worldIn, d0, d1, d2));
        }
    }
}