package eastonium.nuicraft.kanoka;

import eastonium.nuicraft.NuiCraftItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EntityDisc extends EntityThrowable {
	protected NBTTagCompound nbtData;//write these to NBT
	protected boolean dropOnImpact;

	public EntityDisc(World world){
		super(world);
	}

	public EntityDisc(World par1World, EntityLivingBase par2EntityLivingBase, NBTTagCompound nbtData, boolean dropOnImpact){
		super(par1World, par2EntityLivingBase);
		this.nbtData = nbtData;
		this.dropOnImpact = dropOnImpact;
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		if(nbtData != null && nbtData.getByte("metru") == 5){
			this.motionX *= 2;
			this.motionY *= 1.4;
			this.motionZ *= 2;
		}else if(nbtData != null && nbtData.getByte("metru") == 5){
			this.motionX *= 1.4;
			this.motionY *= 1.2;
			this.motionZ *= 1.4;
		}
	}

	@Override
	protected void onImpact(RayTraceResult result){
		if(nbtData != null){
			if (!world.isRemote){
				ItemStack discDrop = new ItemStack(NuiCraftItems.kanoka_disc, 1);
				discDrop.setTagCompound(nbtData);
				if(dropOnImpact && world.getGameRules().getBoolean("doTileDrops") && result.entityHit == null){
					this.world.spawnEntity(new EntityItem(world, posX, posY, posZ, discDrop));
				}
				this.setDead();
			}
			if(result.entityHit != null){
				switch(nbtData.getByte("powerType")){
				case 1:
					this.onImpactAtRandom(result);
					break;
				case 2:
					this.onImpactFreeze(result);
					break;
				case 3:
					this.onImpactWeaken(result);
					break;
				case 4:
					this.onImpactRemovePoison(result);
					break;
				case 5:
					this.onImpactEnlarge(result);
					break;
				case 6:
					this.onImpactShrink(result);
					break;
				case 7:
					this.onImpactRegeneration(result);
					break;
				case 8:
					this.onImpactTeleport(result);
					break;
				}
			}
		}else/* if(nbtData == null)*/{
			onImpactBamboo(result);
		}
	}

	private void onImpactBamboo(RayTraceResult result){
		if (result.entityHit != null){
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 5);            
		}
		for (int i = 0; i < 5; ++i){
			this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, posX, posY, posZ, 
					((double)rand.nextFloat() - 0.5D) * 0.08D, 
					((double)rand.nextFloat() - 0.5D) * 0.08D, 
					((double)rand.nextFloat() - 0.5D) * 0.08D, 
					new int[] {Item.getIdFromItem(NuiCraftItems.kanoka_bamboo)});
		}
		if (!world.isRemote){
			setDead();
		}
	}

	private void onImpactAtRandom(RayTraceResult result){
		if (result.entityHit != null && !(result.entityHit instanceof EntityPlayerMP)){
			//this.setDead();
			result.entityHit.setDead();
			int entityID = rand.nextInt(26) + 50;
			if(entityID == 63) entityID = 41; 
			if(entityID == 64) entityID = 42;
			if(entityID > 66) entityID += 24;
			if(entityID > 100) entityID = 120;//TODO redo this by having array of possible ids and randomizing from that
			Entity entity = EntityList.createEntityByID(entityID, world);
			entity.setLocationAndAngles((double)result.entityHit.lastTickPosX, (double)result.entityHit.lastTickPosY, (double)result.entityHit.lastTickPosZ, result.entityHit.rotationYaw, result.entityHit.rotationPitch);
			world.spawnEntity(entity);
		}
		if(result.entityHit instanceof EntityPlayerMP){
			result.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, getThrower()), 6F);
			//TODO Nausea
		}
	}

	private void onImpactEnlarge(RayTraceResult result){

	}

	private void onImpactShrink(RayTraceResult result){

	}

	private void onImpactFreeze(RayTraceResult result){
		EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
		if (entityHit != null && !(entityHit instanceof EntityPlayerMP)){
			//this.setDead();
			entityHit.extinguish();
			entityHit.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 500, 3));
			entityHit.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 500, 3));

			if (entityHit instanceof EntityBlaze){
				entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 10F);
			}
			for (int i = 0; i < 24; ++i){
				world.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX, posY, posZ, 0.0D, 0.0D, 0.0D, new int[]{Block.getStateId(Blocks.PACKED_ICE.getDefaultState())});
			}
		}
		if(entityHit instanceof EntityPlayerMP)
		{
			entityHit.extinguish();
			entityHit.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 150, 1));
			entityHit.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 150, 1));
			for (int i = 0; i < 20; ++i){
				world.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX, posY, posZ, 0.0D, 0.0D, 0.0D, new int[]{Block.getStateId(Blocks.PACKED_ICE.getDefaultState())});
			}
		}
	}

	private void onImpactRegeneration(RayTraceResult result){
		EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
		if (entityHit != null){
			//this.setDead();
			entityHit.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 150, 1));
		}
	}

	private void onImpactRemovePoison(RayTraceResult result){
		EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
		if (entityHit != null){
			//this.setDead();
			entityHit.clearActivePotions();
		}
	}

	private void onImpactWeaken(RayTraceResult result){
		EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
		if (entityHit != null){
			//this.setDead();
			entityHit.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
		}
	}

	private void onImpactTeleport(RayTraceResult result){
		Entity entityHit = result.entityHit;
		if (entityHit != null){
			//this.setDead();
			double randX = 0;
			double randY = 0;
			double randZ = 0;
			for(int i = 0; i < 10; i++){
				randX = entityHit.posX + (this.rand.nextDouble() - 0.5D) * 32.0D;
				randY = entityHit.posY + (double)(this.rand.nextInt(16) - 9);
				randZ = entityHit.posZ + (this.rand.nextDouble() - 0.5D) * 32.0D;
				BlockPos blockpos = new BlockPos(randX, randY + entityHit.getEyeHeight(), randZ);
				Block block = world.getBlockState(blockpos).getBlock();
				if(block.isPassable(world, blockpos)){
					break;
				}
			}
			world.playSound((EntityPlayer)null, entityHit.posX, entityHit.posY, entityHit.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			if(entityHit instanceof EntityLivingBase){
				EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)entityHit, randX, randY, randZ, 0);
				entityHit.posX = event.getTargetX();
				entityHit.posY = event.getTargetY();
				entityHit.posZ = event.getTargetZ();
			}
			entityHit.setPositionAndUpdate(randX, randY, randZ);
			entityHit.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
			entityHit.fallDistance = 0;
		}
	}
}
