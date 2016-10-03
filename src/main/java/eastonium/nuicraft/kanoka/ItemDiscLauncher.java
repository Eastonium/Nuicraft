package eastonium.nuicraft.kanoka;

import javax.annotation.Nullable;

import eastonium.nuicraft.Bionicle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemDiscLauncher extends Item {
	
	public ItemDiscLauncher(){
		this.maxStackSize = 1;
		this.setMaxDamage(500);
		this.setCreativeTab(Bionicle.bioToolTab);
	}
	
	public Item setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack launcherItem, World world, EntityPlayer playerIn, EnumHand hand){
		ItemStack disc = this.findAmmo(playerIn);
		if (disc == null) return new ActionResult(EnumActionResult.FAIL, launcherItem);
		EntityDisc discEntity;
		if (disc.getItem() == Bionicle.bambooDisc){
			if (!world.isRemote){
				discEntity = new EntityDisc(world, playerIn, null, false);
	            discEntity.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.5F, 0F);
				world.spawnEntityInWorld(discEntity);
			}
		}else{
			NBTTagCompound discNBT = disc.getTagCompound();
			if (discNBT == null) return new ActionResult(EnumActionResult.FAIL, launcherItem);
			if (!world.isRemote){
				discEntity = new EntityDisc(world, playerIn, discNBT, !playerIn.capabilities.isCreativeMode);
	            discEntity.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.5F, 0F);
				world.spawnEntityInWorld(discEntity);				
			}
		}
		world.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		launcherItem.damageItem(1, playerIn);
		disc.stackSize--;
		if(disc.stackSize <= 0) disc = null;
		return new ActionResult(EnumActionResult.SUCCESS, launcherItem);
	}
	
	private ItemStack findAmmo(EntityPlayer player){
        if (this.isDisc(player.getHeldItem(EnumHand.OFF_HAND))){
            return player.getHeldItem(EnumHand.OFF_HAND);
        }else if (this.isDisc(player.getHeldItem(EnumHand.MAIN_HAND))){
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }else{
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i){
                ItemStack itemstack = player.inventory.getStackInSlot(i);
                if (this.isDisc(itemstack)) return itemstack;
            }
            return null;
        }
    }

    protected boolean isDisc(@Nullable ItemStack stack){
        return stack != null && (stack.getItem() == Bionicle.bambooDisc || stack.getItem() == Bionicle.kanokaDisc);
    }
}
