package eastonium.nuicraft.items;

import eastonium.nuicraft.Bionicle;
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
		this.maxStackSize = 1;
		this.setMaxDamage(128);
		this.setCreativeTab(Bionicle.bioToolTab);
	}
	
	public Item setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		pos = pos.offset(side);
		if(!playerIn.canPlayerEdit(pos, side, stack)){
			return EnumActionResult.FAIL;
		}else{
			if(stack.getItemDamage() < this.getMaxDamage()){
				if(worldIn.isAirBlock(pos)){
	                worldIn.playSound(playerIn, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
					worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
				}
				stack.damageItem(1, playerIn);
	            return EnumActionResult.SUCCESS;
			}else return EnumActionResult.FAIL;
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
		if(world.getBiomeGenForCoords(entity.getPosition()) == Biomes.HELL){
			if(world.rand.nextInt(60) == 0){
				stack.damageItem(-1, (EntityLivingBase)entity);
				//Auto-recharging doesn't work while in creative mode
			}
		}
	}
}
