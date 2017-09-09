package eastonium.nuicraft.kanoka;

import java.util.Set;

import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.kanoka.freezeEntity.EntityFreezeIce;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityKanoka extends EntityThrowable {
	
    private static final DataParameter<Boolean> BAMBOO = EntityDataManager.<Boolean>createKey(EntityKanoka.class, DataSerializers.BOOLEAN);
	private boolean dropOnImpact;
	private NBTTagCompound itemNBT;
    
	public EntityKanoka(World world){
		super(world);
	}
	public EntityKanoka(World world, EntityLivingBase entity, NBTTagCompound itemNBT, boolean dropOnImpact){
		super(world, entity);
		this.itemNBT = itemNBT == null ? new NBTTagCompound() : itemNBT;
		this.dropOnImpact = dropOnImpact;
	}
	public EntityKanoka(World world, EntityLivingBase entity) {
		this(world, entity, null, false);
		this.dataManager.set(BAMBOO, Boolean.valueOf(true));
	}
	
	@Override
	protected void entityInit() {
		this.dataManager.register(BAMBOO, Boolean.valueOf(false));
	}
	
	public boolean isBamboo() {
		return ((Boolean)this.getDataManager().get(BAMBOO)).booleanValue();
	}
	
    @SideOnly(Side.CLIENT)
    @Override
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
		if (!world.isRemote){
			if (itemNBT == null) {
				this.setDead();
				return;
			}
			boolean forceNoDrop = dropOnImpact == false;
			switch(itemNBT.getByte("powerType")){
			case 1:
				dropOnImpact = onImpactAtRandom(result);
				break;
			case 2:
				dropOnImpact = onImpactFreeze(result);
				break;
			case 3:
				dropOnImpact = onImpactWeaken(result);
				break;
			case 4:
				dropOnImpact = onImpactRemovePoison(result);
				break;
			case 5:
				dropOnImpact = onImpactEnlarge(result);
				break;
			case 6:
				dropOnImpact = onImpactShrink(result);
				break;
			case 7:
				dropOnImpact = onImpactRegeneration(result);
				break;
			case 8:
				dropOnImpact = onImpactTeleport(result);
				break;
			default: 
				dropOnImpact = onImpactBamboo(result);
				break;
			}
			if (dropOnImpact && !forceNoDrop) {
				if (itemNBT.hasKey("metru")){
					ItemStack discDrop = new ItemStack(NuiCraftItems.kanoka_disc, 1);
					discDrop.setTagCompound(itemNBT);
					world.spawnEntity(new EntityItem(world, posX, posY, posZ, discDrop));
				} else {
					world.spawnEntity(new EntityItem(world, posX, posY, posZ, new ItemStack(NuiCraftItems.kanoka_bamboo, 1)));
				}
			} else if (this.isBamboo()) {
				this.world.setEntityState(this, (byte)3);
			}
			this.setDead();
		}
	}

	private boolean onImpactBamboo(RayTraceResult result){
		if (result.entityHit != null){
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 5);
			return false;
		}
		return true;
	}

	private boolean onImpactAtRandom(RayTraceResult result){
		if (result.entityHit != null) {
			if (result.entityHit instanceof EntityPlayerMP){
				result.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, getThrower()), 6F);
				((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30, 1));
				return false;
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
				newEntity.readFromNBT(entityHit.writeToNBT(new NBTTagCompound()));
				newEntity.setUniqueId(MathHelper.getRandomUUID(this.rand));
				if (newEntity instanceof EntityLivingBase) {
					float newHealth = entityHit.getHealth() / entityHit.getMaxHealth() * ((EntityLivingBase)newEntity).getMaxHealth();
					((EntityLivingBase)newEntity).setHealth(newHealth);
				}
				if (world.spawnEntity(newEntity)) {
					entityHit.setDead();
					return false;
				} else return true;
			}
		}
		return true;
	}
	
	private boolean isEntityValidForAtRandom(Entity entity) {
		return entity != null && (entity instanceof EntityLivingBase || entity instanceof EntityBoat || entity instanceof EntityMinecart) && entity.isNonBoss();
	}

	private boolean onImpactEnlarge(RayTraceResult result){
		return true;
	}

	private boolean onImpactShrink(RayTraceResult result){
		return true;
	}

	private boolean onImpactFreeze(RayTraceResult result){
		if (result.entityHit != null && !(result.entityHit instanceof EntityFreezeIce)) {
			if (result.entityHit.isRiding() && result.entityHit.getRidingEntity() instanceof EntityFreezeIce) {
				return true; //TODO reset freeze timer
			}
			EntityFreezeIce ice = new EntityFreezeIce(world, result.entityHit);
			if (ice.getBaseHeight() == 0.0F) return true;
			return !world.spawnEntity(ice);
		}
		return true;
//		if (result.entityHit != null && result.entityHit instanceof EntityLivingBase) {
//			EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
//			boolean isPlayer = entityHit instanceof EntityPlayerMP;
//			
//			entityHit.extinguish();
//			entityHit.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, isPlayer ? 150 : 500, 3));
//			entityHit.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, isPlayer ? 150 : 500, 3));
//
//			if (entityHit instanceof EntityBlaze){
//				entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 10F);
//			}
//			for (int i = 0; i < 24; ++i){
//				world.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//				world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX, posY, posZ, 0.0D, 0.0D, 0.0D, new int[]{Block.getStateId(Blocks.PACKED_ICE.getDefaultState())});
//			}
//		}
	}

	private boolean onImpactRegeneration(RayTraceResult result){
		if (result.entityHit != null && result.entityHit instanceof EntityLivingBase){
			EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
			entityHit.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 150, 1));
			return false;
		}
		return true;
	}

	private boolean onImpactRemovePoison(RayTraceResult result){
		if (result.entityHit != null && result.entityHit instanceof EntityLivingBase){
			EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
			boolean removedAnEffect = false;
			if (entityHit.isPotionActive(MobEffects.POISON)) {
				entityHit.removePotionEffect(MobEffects.POISON);
				removedAnEffect = true;
			}
			if (entityHit.isPotionActive(MobEffects.WITHER))  {
				entityHit.removePotionEffect(MobEffects.WITHER);
				removedAnEffect = true;
			}
			if (entityHit.isPotionActive(MobEffects.NAUSEA)) {
				entityHit.removePotionEffect(MobEffects.NAUSEA);
				removedAnEffect = true;
			}
			if (entityHit.isPotionActive(MobEffects.HUNGER)) {
				entityHit.removePotionEffect(MobEffects.HUNGER);
				removedAnEffect = true;
			}
			return !removedAnEffect;
		}
		return true;
	}

	private boolean onImpactWeaken(RayTraceResult result){
		if (result.entityHit != null && result.entityHit instanceof EntityLivingBase){
			EntityLivingBase entityHit = (EntityLivingBase)result.entityHit;
			entityHit.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
			return false;
		}
		return true;
	}

	private boolean onImpactTeleport(RayTraceResult result){
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
			if(result.entityHit instanceof EntityLivingBase){
				EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)result.entityHit, randX, randY, randZ, 0);
				result.entityHit.posX = event.getTargetX();
				result.entityHit.posY = event.getTargetY();
				result.entityHit.posZ = event.getTargetZ();
				if (event.getResult().equals(Result.DENY)) return true;
			}
			world.playSound((EntityPlayer)null, result.entityHit.lastTickPosX, result.entityHit.lastTickPosY, result.entityHit.lastTickPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			result.entityHit.setPositionAndUpdate(randX, randY, randZ);
			result.entityHit.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
			result.entityHit.fallDistance = 0;
			return false;
		}
		return true;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.itemNBT = compound.getCompoundTag("itemNBT");
		this.dropOnImpact = compound.getBoolean("drop");
		this.dataManager.set(BAMBOO, Boolean.valueOf(compound.getBoolean("isBamboo")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setTag("itemNBT", this.itemNBT);
		compound.setBoolean("drop", this.dropOnImpact);
		compound.setBoolean("isBamboo", this.isBamboo());
	}
}
