package eastonium.nuicraft.mobs.mahi;

import javax.annotation.Nullable;

import eastonium.nuicraft.Bionicle;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityMahi extends EntityAnimal {
	
	public static final ResourceLocation ENTITIES_MAHI = LootTableList.register(new ResourceLocation(Bionicle.MODID, "entities/mahi"));

	public EntityMahi(World world) {
		super(world);
		this.setSize(1.0F, 1.1F);
		this.setPathPriority(PathNodeType.WATER, -1.0F);
	}
	
    protected void initEntityAI(){
    	this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIWander(this, 0.4D));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAIPanic(this, 0.6D));
    }
	
	protected boolean canDespawn(){
        return false;
    }
	
	/*@Override
	protected boolean isAIEnabled(){
		return true;
	}*/
	
	@Nullable
    protected ResourceLocation getLootTable(){
        return ENTITIES_MAHI;
	}
	
	
	@Override
	public EntityAgeable createChild(EntityAgeable var1) {
		return null;
	}
}
