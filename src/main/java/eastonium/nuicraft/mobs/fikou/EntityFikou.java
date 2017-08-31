package eastonium.nuicraft.mobs.fikou;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;

public class EntityFikou extends EntityAnimal{

    public static final ResourceLocation ENTITIES_FIKOU = LootTableList.register(new ResourceLocation(NuiCraft.MODID, "entities/fikou"));

	public EntityFikou(World par1World) {
		super(par1World);
		this.setSize(0.5F, 0.5F);
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.6D));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAIPanic(this, 0.8D));
	}
    protected boolean canDespawn()
    {
        return false;
    }

	/*@Override
	protected boolean isAIEnabled(){
		return true;
	}*/

    @Nullable
    protected ResourceLocation getLootTable(){
        return ENTITIES_FIKOU;
    }

	@Override
	public EntityAgeable createChild(EntityAgeable var1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
