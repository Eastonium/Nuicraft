package eastonium.nuicraft.kanohi;

import eastonium.nuicraft.Bionicle;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMask extends ItemArmor {
	public static ArmorMaterial KANOHI = EnumHelper.addArmorMaterial("Kanohi", Bionicle.MODID + ":textures/models/masks/blank.png", 1, new int[]{0, 0, 0, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	private boolean isShiny = false;

	public ItemMask(boolean isShiny){
		super(KANOHI, 0, EntityEquipmentSlot.HEAD);
		this.isShiny = isShiny;
		this.setMaxDamage(0);
		this.setCreativeTab(Bionicle.bioMaskTab);
	}
	
	public Item setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer){
		return Bionicle.MODID + ":textures/models/masks/" + this.getUnlocalizedName().substring(9) + ".png";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot slot, ModelBiped _default){
		ModelBiped armorModel = null;
		if(stack != null){
			if(stack.getItem() == Bionicle.maskMataAkaku || 
					(stack.getItem() == Bionicle.maskMataGold && stack.getItemDamage() == 5)){
				armorModel = Bionicle.proxy.getArmorModel(0);//Akaku Model
			}else if(stack.getItem() == Bionicle.maskIgnika && stack.getItemDamage() == 0){
				armorModel = Bionicle.proxy.getArmorModel(1);//VNOG Ignika Model
			}else armorModel = Bionicle.proxy.getArmorModel(-1);//Generic Model
		}
		if(armorModel != null){
			armorModel.isSneak = entityLiving.isSneaking();
			//armorModel.isChild = entityLiving.isChild();
			return armorModel;
		}
		return null;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack){
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack itemstack){
		if(isShiny){
			itemstack.setTagInfo("ench", new NBTTagList());
			return true;
		}else return false;
	}
	
	//TODO On creation for ench/aquaAffinity?

	/*public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		if(par3Entity instanceof EntityPlayer && (par1ItemStack.getItem() == Bionicle.maskMataKaukau || par1ItemStack.getItemDamage() == 0)){
			if(ClientTickHandler.maskActivated){
				if(par1ItemStack.getTagCompound() == null) par1ItemStack.addEnchantment(Enchantment.aquaAffinity, 1);
			}else par1ItemStack.setTagCompound(null);
		}
	}*/
}
