package eastonium.nuicraft.mobs.nui_jaga;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class EntityNuiJaga extends EntityMob{

	public EntityNuiJaga(World par1World) {
		super(par1World);
		this.setSize(2.0F, 1.3F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.6D));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAIPanic(this, 0.8D));
		this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
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

/*TODO Hostile behaviour
    - Moving sideways, looking at targeted player
    - Staying in min. 3-4 block range from the player
    - Shooting poisonous balls when in ranged position
        - Medium damage
        - High chance for Poison effect
        - Low chance for Blindness effect
        - Can be deflected by using shield
    - Striking with sting tail when in melee range
        - Very high damage
        - Low chance for poison effect
        - Slower than players melee strike
        - Locks onto current player position for attack, not onto player thyself (Can be dodged by walking sideways)

    Nui-Jaga's behaviour should force the players to develop unique fighting tactics for particular Rahi.
 */