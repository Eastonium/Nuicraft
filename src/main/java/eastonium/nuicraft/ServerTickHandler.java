package eastonium.nuicraft;

import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ServerTickHandler
{
	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event)
	{
		ArrayList<EntityPlayerMP> players = (ArrayList<EntityPlayerMP>)FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList();
		for (EntityPlayerMP player : players){
			if (player.ticksExisted % 20 == 0 && player.inventory.armorItemInSlot(3) != null/* && PacketValues.get(player).GetMaskActivated()*/){    
				World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(player.dimension);
				ItemStack mask = player.inventory.armorItemInSlot(3);
				if (removeFallDistance(mask)){
					player.fallDistance = 0.0F;
				}
				//MATA MASKS
				if(mask.getItem() == Bionicle.maskMataGold){
					player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30, 1, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 30, 0, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 30, 1, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 30, 0, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 30, 1, false, false));
					
				}else if(mask.getItem() == Bionicle.maskMataPakari){
					player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30, 0, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 30, 0, false, false));

				}else if(mask.getItem() == Bionicle.maskMataKaukau){
					player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 30, 0, false, false));

				}else if(mask.getItem() == Bionicle.maskMataHau){
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 30, 1, false, false));

				}else if(mask.getItem() == Bionicle.maskMataKakama){
					player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 30, 1, false, false));
				}
				//NUVA MASKS
				else if(mask.getItem() == Bionicle.maskNuvaPakari){
					player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30, 1, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 30, 1, false, false));

				}else if(mask.getItem() == Bionicle.maskNuvaKaukau){
					player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 30, 0, false, false));

				}else if(mask.getItem() == Bionicle.maskNuvaHau){
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 30, 3, false, false));

				}else if(mask.getItem() == Bionicle.maskNuvaKakama){
					player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 30, 3, false, false));
				}
				//LEGENDARY MASKS
				else if(mask.getItem() == Bionicle.maskIgnika){
					List listIgnika = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(5.0D, 5.0D, 5.0D));
					for (int i = 0; i < listIgnika.size(); ++i){
						Entity entity1 = (Entity)listIgnika.get(i);
						if (player.ticksExisted % 30 == 0 && (entity1 instanceof EntityMob || entity1 instanceof EntityAmbientCreature || entity1 instanceof EntitySquid)){
							entity1.attackEntityFrom(DamageSource.wither, 1);
							player.heal(1);
						}
					}
				}else if (mask.getItem() == Bionicle.maskVahi){
					player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30, 1, false, false));
					if(player.ticksExisted % 2 == 0){
						world.getWorldInfo().setWorldTime(world.getWorldInfo().getWorldTime() - 1); //Slow Down Time
					}
					List list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(10.0D, 10.0D, 10.0D));
					for (int i = 0; i < list.size(); ++i){
						Entity entity1 = (Entity)list.get(i);
						if (entity1 instanceof EntityLiving){
							((EntityLiving) entity1).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30, 3, false, false));
						}
					}
				}
			}
		}
	}

	private boolean removeFallDistance(ItemStack mask){
		return (mask.getItem() == Bionicle.maskMataMiru || mask.getItem() == Bionicle.maskNuvaMiru || mask.getItem() == Bionicle.maskMataGold);
	}
}