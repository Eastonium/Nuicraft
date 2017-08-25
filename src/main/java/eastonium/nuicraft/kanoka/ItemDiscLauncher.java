package eastonium.nuicraft.kanoka;

import javax.annotation.Nullable;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
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
		this.setCreativeTab(NuiCraft.bio_tool_tab);
		this.setUnlocalizedName(NuiCraft.MODID + ".disc_launcher");
		this.setRegistryName("disc_launcher");
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack launcherItem = playerIn.getHeldItem(handIn);
		ItemStack disc = findAmmo(playerIn);
		if (disc.isEmpty()) return new ActionResult(EnumActionResult.FAIL, launcherItem);
		EntityDisc discEntity;
		if (disc.getItem() == NuiCraftItems.kanoka_bamboo){
			if (!worldIn.isRemote){
				discEntity = new EntityDisc(worldIn, playerIn, null, false);
	            discEntity.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.5F, 0F);
				worldIn.spawnEntity(discEntity);
			}
		}else{
			NBTTagCompound discNBT = disc.getTagCompound();
			if (discNBT == null) return new ActionResult(EnumActionResult.FAIL, launcherItem);
			if (!worldIn.isRemote){
				discEntity = new EntityDisc(worldIn, playerIn, discNBT, !playerIn.capabilities.isCreativeMode);
	            discEntity.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.5F, 0F);
				worldIn.spawnEntity(discEntity);				
			}
		}
		worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		launcherItem.damageItem(1, playerIn);
		disc.setCount(disc.getCount()-1);;
		if(disc.getCount() <= 0) disc = ItemStack.EMPTY;
		return new ActionResult(EnumActionResult.SUCCESS, launcherItem);
	}
	
	private ItemStack findAmmo(EntityPlayer player){
        if (isDisc(player.getHeldItem(EnumHand.OFF_HAND))){
            return player.getHeldItem(EnumHand.OFF_HAND);
        }else if (isDisc(player.getHeldItem(EnumHand.MAIN_HAND))){
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }else{
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i){
                ItemStack itemstack = player.inventory.getStackInSlot(i);
                if (isDisc(itemstack)) return itemstack;
            }
            return ItemStack.EMPTY;
        }
    }

    protected boolean isDisc(ItemStack stack){
        return !stack.isEmpty() && (stack.getItem() == NuiCraftItems.kanoka_bamboo || stack.getItem() == NuiCraftItems.kanoka_disc);
    }
}
