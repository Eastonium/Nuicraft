package eastonium.nuicraft.mobs.mahi;

import javax.annotation.Nullable;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityMahi extends EntityAnimal {
	
	public static final ResourceLocation ENTITIES_MAHI = LootTableList.register(new ResourceLocation(NuiCraft.MODID, "entities/mahi"));

	public EntityMahi(World world) {
		super(world);
		setSize(1.0F, 1.1F);
		setPathPriority(PathNodeType.WATER, -1.0F);
	}
	
    protected void initEntityAI(){
    	tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.6D));
		tasks.addTask(2, new EntityAIWander(this, 1.2D));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(6, new EntityAILookIdle(this));


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
