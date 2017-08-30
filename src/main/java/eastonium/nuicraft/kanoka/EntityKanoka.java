package eastonium.nuicraft.kanoka;

import java.util.Set;

import org.apache.logging.log4j.Level;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityKanoka extends EntityThrowable {
	
    private static final DataParameter<Boolean> BAMBOO = EntityDataManager.<Boolean>createKey(EntityKanoka.class, DataSerializers.BOOLEAN);
	
	public EntityKanoka(World world){
		super(world);
	}
	public EntityKanoka(World world, EntityLivingBase entity, NBTTagCompound nbtData, boolean dropOnImpact){
		super(world, entity);
		this.getEntityData().merge(nbtData);
		this.getEntityData().setBoolean("drop", dropOnImpact);
	}
	public EntityKanoka(World world, EntityLivingBase entity) {
		this(world, entity, new NBTTagCompound(), false);
		this.getEntityData().setBoolean("drop", false);
		this.dataManager.set(BAMBOO, Boolean.valueOf(true));
	}
	
	protected void entityInit() {
		this.dataManager.register(BAMBOO, Boolean.valueOf(false));
	}
	
	public boolean getIsBamboo() {
        return ((Boolean)this.dataManager.get(BAMBOO)).booleanValue();
    }
	
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
        	for (int i = 0; i < 5; ++i){
				world.spawnParticle(EnumParticleTypes.ITEM_CRACK, posX, posY, posZ, 
					((double)rand.nextFloat() - 0.5D) * 0.08D, 
					((double)rand.nextFloat() - 0.5D) * 0.08D, 
					((double)rand.nextFloat() - 0.5D) * 0.08D, 
					new int[] {Item.getIdFromItem(NuiCraftItems.kanoka_bamboo)});
			}
        }
    }

	@Override
	protected void onImpact(RayTraceResult result){
		switch(getEntityData().getByte("powerType")){
		case 1:
			onImpactAtRandom(result);
			break;
		case 2:
			onImpactFreeze(result);
			break;
		case 3:
			onImpactWeaken(result);
			break;
		case 4:
			onImpactRemovePoison(result);
			break;
		case 5:
			onImpactEnlarge(result);
			break;
		case 6:
			onImpactShrink(result);
			break;
		case 7:
			onImpactRegeneration(result);
			break;
		case 8:
			onImpactTeleport(result);
			break;
		default: 
			onImpactBamboo(result);
			break;
		}
		if (!world.isRemote){
			if (getEntityData().hasKey("metru")){
				if (getEntityData().getBoolean("drop") && result.entityHit == null) {
					getEntityData().removeTag("drop");
					ItemStack discDrop = new ItemStack(NuiCraftItems.kanoka_disc, 1);
					discDrop.setTagCompound(getEntityData());
					world.spawnEntity(new EntityItem(world, posX, posY, posZ, discDrop));
				}
			} else {
				this.world.setEntityState(this, (byte)3);
			}
			setDead();
		}
	}

	private void onImpactBamboo(RayTraceResult result){
		if (result.entityHit != null){
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 5);            
		}
	}

	private void onImpactAtRandom(RayTraceResult result){
		if (result.entityHit != null) {
			if (result.entityHit instanceof EntityPlayerMP){
				result.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, getThrower()), 6F);
				((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30, 1));
				
			} else if (result.entityHit instanceof EntityLivingBase) {
				EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
				Entity newEntity = null;
				Set<ResourceLocation> allEntitiesSet = EntityList.getEntityNameList();
				ResourceLocation[] allEntities = allEntitiesSet.toArray(new ResourceLocation[allEntitiesSet.size()]);
				while (!isEntityValidForAtRandom(newEntity)) {
					newEntity = EntityList.createEntityByIDFromName(
						allEntities[world.rand.nextInt(allEntities.length - 1) + 1], 
						world);
				}
				if (newEntity instanceof EntityLivingBase) {
					float newHealth = entityHit.getHealth() / entityHit.getMaxHealth() * ((EntityLivingBase)newEntity).getMaxHealth();
					((EntityLivingBase)newEntity).setHealth(newHealth);
				}
				newEntity.getEntityData().merge(entityHit.getEntityData());
				newEntity.setLocationAndAngles((double)entityHit.lastTickPosX, (double)entityHit.lastTickPosY, (double)entityHit.lastTickPosZ, entityHit.rotationYaw, entityHit.rotationPitch);
				entityHit.setDead();
				world.spawnEntity(newEntity);
			}
		}
	}
	
	private boolean isEntityValidForAtRandom(Entity entity) {
		return entity != null && (entity instanceof EntityLivingBase || entity instanceof EntityBoat || entity instanceof EntityMinecart) && entity.isNonBoss();
	}

	private void onImpactEnlarge(RayTraceResult result){

	}

	private void onImpactShrink(RayTraceResult result){

	}

	private void onImpactFreeze(RayTraceResult result){
		if (result.entityHit != null && result.entityHit instanceof EntityLivingBase) {
			EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
			boolean isPlayer = entityHit instanceof EntityPlayerMP;
			
			entityHit.extinguish();
			entityHit.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, isPlayer ? 150 : 500, 3));
			entityHit.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, isPlayer ? 150 : 500, 3));

			if (entityHit instanceof EntityBlaze){
				entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 10F);
			}
			for (int i = 0; i < 24; ++i){
				world.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX, posY, posZ, 0.0D, 0.0D, 0.0D, new int[]{Block.getStateId(Blocks.PACKED_ICE.getDefaultState())});
			}
		}
	}

	private void onImpactRegeneration(RayTraceResult result){
		if (result.entityHit != null && result.entityHit instanceof EntityLivingBase){
			EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
			entityHit.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 150, 1));
		}
	}

	private void onImpactRemovePoison(RayTraceResult result){
		if (result.entityHit != null && result.entityHit instanceof EntityLivingBase){
			EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
			if (entityHit.isPotionActive(MobEffects.POISON)) entityHit.removePotionEffect(MobEffects.POISON);
			if (entityHit.isPotionActive(MobEffects.WITHER)) entityHit.removePotionEffect(MobEffects.WITHER);
			if (entityHit.isPotionActive(MobEffects.NAUSEA)) entityHit.removePotionEffect(MobEffects.NAUSEA);
			if (entityHit.isPotionActive(MobEffects.HUNGER)) entityHit.removePotionEffect(MobEffects.HUNGER);
}
	}

	private void onImpactWeaken(RayTraceResult result){
		if (result.entityHit != null && result.entityHit instanceof EntityLivingBase){
			EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
			entityHit.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
		}
	}

	private void onImpactTeleport(RayTraceResult result){
		if (result.entityHit != null){
			double randX = 0;
			double randY = 0;
			double randZ = 0;
			for(int i = 0; i < 10; i++){
				randX = result.entityHit.posX + (this.rand.nextDouble() - 0.5D) * 32.0D;
				randY = result.entityHit.posY + (double)(this.rand.nextInt(16) - 9);
				randZ = result.entityHit.posZ + (this.rand.nextDouble() - 0.5D) * 32.0D;
				BlockPos blockpos = new BlockPos(randX, randY + result.entityHit.getEyeHeight(), randZ);
				Block block = world.getBlockState(blockpos).getBlock();
				if(block.isPassable(world, blockpos)) break;
			}
			world.playSound((EntityPlayer)null, result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			if(result.entityHit instanceof EntityLivingBase){
				EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)result.entityHit, randX, randY, randZ, 0);
				result.entityHit.posX = event.getTargetX();
				result.entityHit.posY = event.getTargetY();
				result.entityHit.posZ = event.getTargetZ();
			}
			result.entityHit.setPositionAndUpdate(randX, randY, randZ);
			result.entityHit.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
			result.entityHit.fallDistance = 0;
		}
	}
}
