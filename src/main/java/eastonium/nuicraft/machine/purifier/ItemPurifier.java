package eastonium.nuicraft.machine.purifier;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemPurifier extends Item {
	
    public ItemPurifier(){
		this.setCreativeTab(NuiCraft.nuicraftTab);
		this.setUnlocalizedName(NuiCraft.MODID + ".purifier_item");
		this.setRegistryName("purifier_item");
    }
    
    public Item setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(NuiCraft.MODID, name);
        return this;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	ItemStack stack = player.getHeldItem(hand);
        if (worldIn.isRemote){
            return EnumActionResult.SUCCESS;
        }else if (facing != EnumFacing.UP){
            return EnumActionResult.FAIL;
        }else{
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            boolean flag = block.isReplaceable(worldIn, pos);
            if (!flag) pos = pos.up();

            int i = MathHelper.floor((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EnumFacing enumfacing = EnumFacing.getHorizontal(i);
            BlockPos blockpos = pos.offset(enumfacing);

            if (player.canPlayerEdit(pos, facing, stack) && player.canPlayerEdit(blockpos, facing, stack)){
                boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
                boolean flag2 = flag || worldIn.isAirBlock(pos);
                boolean flag3 = flag1 || worldIn.isAirBlock(blockpos);

                if (flag2 && flag3 && worldIn.getBlockState(pos.down()).isTopSolid() && worldIn.getBlockState(blockpos.down()).isTopSolid()){
                    IBlockState iblockstate1 = NuiCraftBlocks.purifier.getDefaultState().withProperty(BlockPurifier.FACING, enumfacing).withProperty(BlockPurifier.PART, BlockBed.EnumPartType.FOOT);

                    if (worldIn.setBlockState(pos, iblockstate1, 11)){
                        IBlockState iblockstate2 = iblockstate1.withProperty(BlockPurifier.PART, BlockBed.EnumPartType.HEAD);
                        worldIn.setBlockState(blockpos, iblockstate2, 11);
                    }
                    SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
                    worldIn.playSound((EntityPlayer)null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    stack.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
                else return EnumActionResult.FAIL;
            }else return EnumActionResult.FAIL;
        }
    }
}