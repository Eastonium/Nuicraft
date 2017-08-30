package eastonium.nuicraft.kanoka;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.client.util.ITooltipFlag;
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

public class ItemKanokaDisc extends Item {
	public static final String[] METRU_NAMES = new String[]{"Ta-Koro", "Ga-Koro", "Po-Koro", "Ko-Koro", "Le-Koro", "Onu-Koro"};
	public static final String[] POWER_NAMES = new String[]{"Reconstitution at Random", "Freezing", "Weakening", "Remove Poison", "Enlarging", "Shrinking", "Regeneration", "Teleportation"};
	
	public ItemKanokaDisc(){
		super();
		maxStackSize = 8;
		setCreativeTab(NuiCraft.nuicraftTab);
		setUnlocalizedName(NuiCraft.MODID + ".kanoka_disc");
		setRegistryName("kanoka_disc");
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack){
        byte[] discNumbers = getKanokaNumber(stack);
        if(discNumbers == null) return "Creative Kanoka : Right-Click to Create";
        return "Kanoka of " + POWER_NAMES[discNumbers[1]-1] + " : Power " + discNumbers[2];
    }

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getTagCompound() != null){
			byte[] discNumbers = getKanokaNumber(stack);
			tooltip.add("Metru Made: " + METRU_NAMES[discNumbers[0]-1]);
			tooltip.add("Power Type: " + POWER_NAMES[discNumbers[1]-1]);
			tooltip.add("Power Level: " + discNumbers[2]);
		}
	}

	public static boolean setKanokaNumber(ItemStack stack, int metru, int powerType, int powerLevel){
		if(stack.isEmpty() || !(stack.getItem() instanceof ItemKanokaDisc)) return false;
		//if(metru == 0 && powerType == 0 && powerLevel == 0) return false;
		if(stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound stackTagCompound = stack.getTagCompound();
		if(metru != 0){
			stackTagCompound.setByte("metru", (byte)metru);
		}else stackTagCompound.setByte("metru", (byte)(itemRand.nextInt(6)+ 1));
		if(powerType != 0){
			stackTagCompound.setByte("powerType", (byte)powerType);
		}else stackTagCompound.setByte("powerType", (byte)(itemRand.nextInt(8)+ 1));
		if(powerLevel != 0){
			stackTagCompound.setByte("powerLevel", (byte)powerLevel);
		}else stackTagCompound.setByte("powerLevel", (byte)(itemRand.nextInt(9)+ 1));
		stack.setTagCompound(stackTagCompound);
		return true;
	}

	public static byte[] getKanokaNumber(ItemStack stack){
		if(stack.isEmpty() || !(stack.getItem() instanceof ItemKanokaDisc)) return null;
		NBTTagCompound stackTagCompound = stack.getTagCompound();
		if(stackTagCompound == null) return null;
		return new byte[]{stackTagCompound.getByte("metru"), stackTagCompound.getByte("powerType"), stackTagCompound.getByte("powerLevel")};
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		if(itemStackIn.getTagCompound() == null){//TODO Disc Selector GUI?
			setKanokaNumber(itemStackIn, 0, 0, 0);
			return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
		}		
		if (!playerIn.capabilities.isCreativeMode) itemStackIn.shrink(1);		
		worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote){
			EntityKanoka disc = new EntityKanoka(worldIn, playerIn, itemStackIn.getTagCompound(), !playerIn.capabilities.isCreativeMode);
			disc.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 0.2F);
			worldIn.spawnEntity(disc);
		}
		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}
}
