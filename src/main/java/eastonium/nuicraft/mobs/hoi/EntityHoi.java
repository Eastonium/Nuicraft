package eastonium.nuicraft.mobs.hoi;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;

public class EntityHoi extends EntityAnimal{

	public static final ResourceLocation ENTITIES_HOI = LootTableList.register(new ResourceLocation(NuiCraft.MODID, "entities/hoi"));

	public EntityHoi (World par1World) {
		super(par1World);
		this.setSize(0.5F, 0.5F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.4D));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAIPanic(this, 0.6D));
	}

	/*
    private EntityAIBase field_110178_bs = new EntityAIMoveTowardsRestriction(this, 1.0D);
	private boolean field_110180_bt;
	protected boolean canDespawn()
    {
        return false;
    }
	protected boolean isAIEnabled()
	{
		return true;
	}
	*/

	@Nullable
	protected ResourceLocation getLootTable(){
		return ENTITIES_HOI;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}