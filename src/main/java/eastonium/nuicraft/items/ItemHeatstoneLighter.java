package eastonium.nuicraft.items;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHeatstoneLighter extends ItemFlintAndSteel {	
	public ItemHeatstoneLighter(){
		maxStackSize = 1;
		setMaxDamage(128);
		setCreativeTab(NuiCraft.bio_tool_tab);
		setUnlocalizedName(NuiCraft.MODID + ".heatstone_lighter");
		setRegistryName("heatstone_lighter");
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
    	pos = pos.offset(facing);
		if(!player.canPlayerEdit(pos, facing, stack)){
			return EnumActionResult.FAIL;
		}else{
			if(stack.getItemDamage() < this.getMaxDamage()){
				if(worldIn.isAirBlock(pos)){
	                worldIn.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
					worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
				}
				stack.damageItem(1, player);
	            return EnumActionResult.SUCCESS;
			}else return EnumActionResult.FAIL;
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
		if(world.getBiome(entity.getPosition()) == Biomes.HELL){
			if(world.rand.nextInt(60) == 0){
				stack.damageItem(-1, (EntityLivingBase)entity);
				//Auto-recharging doesn't work while in creative mode
			}
		}
	}
}
