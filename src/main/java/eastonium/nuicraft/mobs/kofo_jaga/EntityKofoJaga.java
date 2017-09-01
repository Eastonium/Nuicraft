package eastonium.nuicraft.mobs.kofo_jaga;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class EntityKofoJaga extends EntityMob{

	public EntityKofoJaga(World par1World) {
		super(par1World);
		this.setSize(1.3F, 1.0F);

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.6D));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.2D, true));
	}
    protected boolean canDespawn()
    {
        return false;
    }
	protected boolean isAIEnabled()
	{
		return true;
	}
}
