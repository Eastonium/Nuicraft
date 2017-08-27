package eastonium.nuicraft.block;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumBlockRenderType;

public class BlockNuvaCube extends Block {	
    public BlockNuvaCube() {
        super(Material.ROCK);
        setSoundType(SoundType.STONE);
        setCreativeTab(NuiCraft.nuicraftTab);
        setUnlocalizedName(NuiCraft.MODID + ".nuva_cube");
        setRegistryName("nuva_cube");
    }

    /*public static boolean isEnderEyeInserted(int var0){
        return (var0 & 4) != 0;
    }

    public int idDropped(int var1, Random var2, int var3)
    {
        return 0;
    }

    
    @Override
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        int var6 = ((MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6, var6);
    }*/
}
