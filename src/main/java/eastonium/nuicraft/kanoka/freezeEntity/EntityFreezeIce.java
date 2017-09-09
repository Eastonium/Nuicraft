package eastonium.nuicraft.kanoka.freezeEntity;

import javax.annotation.Nullable;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFreezeIce extends Entity {
    private static final DataParameter<Float> HEALTH = EntityDataManager.<Float>createKey(EntityFreezeIce.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> BASE_WIDTH = EntityDataManager.<Float>createKey(EntityFreezeIce.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> BASE_HEIGHT = EntityDataManager.<Float>createKey(EntityFreezeIce.class, DataSerializers.FLOAT);

	public EntityFreezeIce(World worldIn) {
		super(worldIn);
		this.setSize(1.0F, 1.0F);
	}
	
	public EntityFreezeIce(World worldIn, Entity entityIn) {
		this(worldIn);
		this.setPosition(entityIn.posX, entityIn.posY, entityIn.posZ);
		if (entityIn instanceof EntityLivingBase) {
			EntityLivingBase entityliving = (EntityLivingBase)entityIn;
			this.setRotation(entityliving.rotationYawHead, entityliving.cameraPitch);
		} else {
			this.setRotation(entityIn.rotationYaw, entityIn.rotationPitch);
		}
//		this.addVelocity(entityIn.motionX, entityIn.motionY, entityIn.motionZ); //TODO maintain velocity from frozen entity
		this.setHealth(100.0F);
		if (entityIn.startRiding(this, true)) {
			this.setBaseWidth(entityIn.width * 1.4F);
			this.setBaseHeight(entityIn.height * 1.3F);
		}
	}

	@Override
	protected void entityInit() {
        this.dataManager.register(HEALTH, Float.valueOf(0.0F));
        this.dataManager.register(BASE_WIDTH, Float.valueOf(0.0F));		
        this.dataManager.register(BASE_HEIGHT, Float.valueOf(0.0F));		
	}
	
    public void setHealth(float health) {
        this.dataManager.set(HEALTH, Float.valueOf(health));
    }
    
    public float getHealth() {
        return ((Float)this.dataManager.get(HEALTH)).floatValue();
    }
    
    private void setBaseWidth(float health) {
        this.dataManager.set(BASE_WIDTH, Float.valueOf(health));
    }
    
    public float getBaseWidth() {
        return ((Float)this.dataManager.get(BASE_WIDTH)).floatValue();
    }
    
    private void setBaseHeight(float health) {
        this.dataManager.set(BASE_HEIGHT, Float.valueOf(health));
    }
    
    public float getBaseHeight() {
        return ((Float)this.dataManager.get(BASE_HEIGHT)).floatValue();
    }
	
	@Nullable
	@Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getEntityBoundingBox();
    }

    @Override
    public boolean canBePushed() {
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
	public boolean canRiderInteract() {
        return !this.isDead;
    }

    @Override
    public double getMountedYOffset() {
    	return 0.0D;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        
        this.extinguish();
//        if (this.isBeingRidden()) { //TODO cancel damage from fire for passengers
//        	for (Entity passenger : this.getPassengers()) {
//        		passenger.extinguish();
//        	}
//        }
        
        this.setSize(this.getBaseWidth(), this.getBaseHeight() * this.getHealth() / 100);
        this.setHealth(this.getHealth() - 0.25F); //TODO Biome + power level dependent melt rate
        //TODO melt faster if mob/player has heatstone in hand
        if (this.getHealth() <= 0.0F) this.setDead();
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        double startMotionX = this.motionX;
        double startMotionY = this.motionY;
        double startMotionZ = this.motionZ;

        if (!this.hasNoGravity()) this.motionY -= 0.03999999910593033D;

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

        float f = 0.98F;
        if (this.onGround) {
            BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
            net.minecraft.block.state.IBlockState underState = this.world.getBlockState(underPos);
            f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
        }
        this.motionX *= (double)f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)f;

        if (this.onGround) {
            this.motionY *= -0.5D;
        }
        this.handleWaterMovement();//TODO make ice float (on lava too)

        if (!this.world.isRemote) {
            double deltaMotionX = this.motionX - startMotionX;
            double deltaMotionY = this.motionY - startMotionY;
            double deltaMotionZ = this.motionZ - startMotionZ;
            double deltaMotion = deltaMotionX * deltaMotionX + deltaMotionY * deltaMotionY + deltaMotionZ * deltaMotionZ;

            if (deltaMotion > 0.01D) {
                this.isAirBorne = true;
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.world.isRemote && !this.isDead) {
            if (this.isEntityInvulnerable(source)) {
                return false;
            } else if (source == DamageSource.ANVIL && amount >= 16) {
            	this.shatter();
            	return true;
            } else if (source == DamageSource.IN_FIRE || source == DamageSource.LIGHTNING_BOLT ||
            		source == DamageSource.ON_FIRE || source == DamageSource.LAVA || source == DamageSource.HOT_FLOOR) {
                //this.setBeenAttacked();
                this.setHealth(this.getHealth() - amount);
                if (this.getHealth() <= 0.0F) this.setDead();
                return true;
            } else if (source.getTrueSource() instanceof EntityPlayer && 
            		((EntityPlayer)source.getTrueSource()).capabilities.isCreativeMode) {
            	this.setDead();
            	return true;
            } else return false;
        } else {
            return true;
        }
    }
    
    private void shatter() {
    	//TODO shattering particles
    	this.playSound(SoundType.GLASS.getBreakSound(), SoundType.GLASS.getVolume() * 1.5F, SoundType.GLASS.getPitch());
    	for (Entity passenger : this.getPassengers()) {
    		if (passenger instanceof EntityLivingBase) {
    			passenger.fallDistance = 0.0F;
    			((EntityLivingBase)passenger).attackEntityFrom(NuiCraft.SHATTER, 30.0F);
    		}
    	}
    	this.setDead();
    }
    
//    @SideOnly(Side.CLIENT)
//    @Override
//    public void performHurtAnimation() {
//    	//TODO particles
//    }
    
    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
        	passenger.setPosition(this.posX, this.posY, this.posZ);
            this.applyYawToEntity(passenger);
        }
    }
    
    protected void applyYawToEntity(Entity entity) {
    	float f = 5.0f;
    	float minYaw = this.rotationYaw - f;
    	float maxYaw = this.rotationYaw + f;
    	float minPitch = this.rotationPitch - f;
    	float maxPitch = this.rotationPitch + f;
        entity.prevRotationYaw = MathHelper.clamp(entity.prevRotationYaw, minYaw, maxYaw);
        entity.rotationYaw = MathHelper.clamp(entity.rotationYaw, minYaw, maxYaw);
        entity.prevRotationPitch = MathHelper.clamp(entity.prevRotationPitch, minPitch, maxPitch);
        entity.rotationPitch = MathHelper.clamp(entity.rotationPitch, minPitch, maxPitch);
        if (entity instanceof EntityLivingBase) {
        	EntityLivingBase entityliving = (EntityLivingBase)entity;
        	entityliving.rotationYawHead = MathHelper.clamp(entityliving.rotationYawHead, minYaw, maxYaw);
        	entityliving.cameraPitch = MathHelper.clamp(entityliving.cameraPitch, minPitch, maxPitch);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void applyOrientationToEntity(Entity entityToUpdate) {
        this.applyYawToEntity(entityToUpdate);
    }
    
    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
        if (!this.isRiding()) {
            if (onGroundIn) {
                if (this.fallDistance > 15.0F) {
                    this.fall(this.fallDistance, 1.0F);                    
                    if (!this.world.isRemote) {
                    	this.shatter();
                    }
                }
                this.fallDistance = 0.0F;
                
            } else if (this.world.getBlockState((new BlockPos(this)).down()).getMaterial() != Material.WATER && y < 0.0D) {
                this.fallDistance = (float)((double)this.fallDistance - y);
            }
        }
    }
    
    @Override
    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, damageMultiplier);
        int i = MathHelper.ceil((distance - 1.50F) * damageMultiplier);
        if (i > 0) {
            int j = MathHelper.floor(this.posX);
            int k = MathHelper.floor(this.posY - 0.20000000298023224D);
            int l = MathHelper.floor(this.posZ);
            IBlockState iblockstate = this.world.getBlockState(new BlockPos(j, k, l));
            if (iblockstate.getMaterial() != Material.AIR) {
                SoundType soundtype = iblockstate.getBlock().getSoundType(iblockstate, world, new BlockPos(j, k, l), this);
                this.playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
            }
        }
    }
    
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.setHealth(compound.getFloat("health"));
		this.setBaseWidth(compound.getFloat("baseWidth"));
		this.setBaseHeight(compound.getFloat("baseHeight"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("health", this.getHealth());
		compound.setFloat("baseWidth", this.getBaseWidth());
		compound.setFloat("baseHeight", this.getBaseHeight());
	}
}
