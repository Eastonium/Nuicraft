package eastonium.nuicraft.kanohi;

import java.util.List;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemColoredMask extends ItemMask {
	public static final int DEFAULT_COLOR = 11324652;
	public static final int WHITE_COLOR = 16777215;

	public ItemColoredMask(String name, boolean isShiny){
		super(name, isShiny);
	}
	
	public ItemColoredMask(String name) {
		super(name);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer){
		if(!hasColor(stack)){
			return NuiCraft.MODID + ":textures/models/masks/" + getRegistryName().getResourcePath() + "_" + getMetal(stack) + ".png";
		}
		if(layer == null){
			return NuiCraft.MODID + ":textures/models/masks/" + getRegistryName().getResourcePath() + ".png";
		}
		return NuiCraft.MODID + ":textures/models/masks/blank.png";
	}

	@Override
	public boolean hasOverlay(ItemStack stack){
        return getColor(stack) >= 0;
    }
	
	@Override
	public boolean hasColor(ItemStack stack){
		if(!stack.hasTagCompound()){
			return false;
		}
		if(!stack.getTagCompound().hasKey("display", 10)){
			return false;
		}
		return stack.getTagCompound().getCompoundTag("display").hasKey("color", 3);
	}

	@Override
	public int getColor(ItemStack stack){
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		if(nbttagcompound != null){
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
			if(nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3)){
				return nbttagcompound1.getInteger("color");
			}
		}
		return DEFAULT_COLOR;
	}

	@Override
	public void removeColor(ItemStack stack){
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		if(nbttagcompound != null){
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
			if(nbttagcompound1.hasKey("color")){
				nbttagcompound1.removeTag("color");
			}
		}
	}

	@Override
	public void setColor(ItemStack stack, int color){
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		if(nbttagcompound == null){
			nbttagcompound = new NBTTagCompound();
			stack.setTagCompound(nbttagcompound);
		}
		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
		if(!nbttagcompound.hasKey("display", 10)){
			nbttagcompound.setTag("display", nbttagcompound1);
		}
		nbttagcompound1.setInteger("color", color);
	}
	
	public byte getMetal(ItemStack stack){
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		if(nbttagcompound != null){
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
			if(nbttagcompound1 != null && nbttagcompound1.hasKey("metal", 1)){
				return nbttagcompound1.getByte("metal");
			}
		}
		return (byte)0;
		//return stack.getTagCompound().getCompoundTag("display").getByte("metal");
	}
	
	public void removeMetal(ItemStack stack){
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		if(nbttagcompound != null){
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
			if(nbttagcompound1.hasKey("metal")){
				nbttagcompound1.removeTag("metal");
			}
		}
	}
	
	public void setMetal(ItemStack stack, byte metal){
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		if(nbttagcompound == null){
			nbttagcompound = new NBTTagCompound();
			stack.setTagCompound(nbttagcompound);
		}
		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
		if(!nbttagcompound.hasKey("display", 10)){
			nbttagcompound.setTag("display", nbttagcompound1);
		}
		nbttagcompound1.setByte("metal", metal);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
		if (tab != getCreativeTab()) return;

		ItemStack itemstack = new ItemStack(this);
		this.setMetal(itemstack, (byte)1);//Gold
		items.add(itemstack);
		
		itemstack = itemstack.copy();//Silver
		this.setMetal(itemstack, (byte)2);
		items.add(itemstack);
		
		itemstack = itemstack.copy();//Bronze
		this.setMetal(itemstack, (byte)3);
		items.add(itemstack);
		
		itemstack = itemstack.copy();//Copper
		this.setMetal(itemstack, (byte)4);
		items.add(itemstack);
		
		itemstack = itemstack.copy();//Colorable
		this.removeMetal(itemstack);
		this.setColor(itemstack, DEFAULT_COLOR);
		items.add(itemstack);
	}
}
