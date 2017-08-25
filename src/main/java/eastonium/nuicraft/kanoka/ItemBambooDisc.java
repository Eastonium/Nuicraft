package eastonium.nuicraft.kanoka;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBambooDisc extends Item
{
	public ItemBambooDisc(){
		super();
		setCreativeTab(NuiCraft.bio_tool_tab);
		setUnlocalizedName(NuiCraft.MODID + ".kanoka_bamboo");
		setRegistryName("kanoka_bamboo");
	}
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!playerIn.capabilities.isCreativeMode) stack.shrink(1);	
		worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote){
			EntityDisc disc = new EntityDisc(worldIn, playerIn, null, false);
            disc.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 0.2F);
			worldIn.spawnEntity(disc);
        }
		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
}
