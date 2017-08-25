package eastonium.nuicraft;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NuiCraftEventHooks 
{	
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public void onFillBucket(FillBucketEvent event){

        // not for us to handle
        ItemStack emptyBucket = event.getEmptyBucket();
        if (emptyBucket == null || emptyBucket.getItem() != Items.BUCKET/* || 
        	(isNbtSensitive() && ItemStack.areItemStackTagsEqual(emptyBucket, getEmpty()))*/){
            return;
        }

        // needs to target a block
        RayTraceResult target = event.getTarget();
        if (target == null || target.typeOfHit != RayTraceResult.Type.BLOCK){
            return;
        }
        World world = event.getWorld();
        BlockPos pos = target.getBlockPos();

        ItemStack singleBucket = emptyBucket.copy();
        singleBucket.setCount(1);;

        FluidActionResult filledBucket = FluidUtil.tryPickUpFluid(singleBucket, event.getEntityPlayer(), world, pos, target.sideHit);
        if (filledBucket != null)
        {
            event.setResult(Event.Result.ALLOW);
            event.setFilledBucket(filledBucket.getResult());
        }
        else
        {
            // cancel event, otherwise the vanilla minecraft ItemBucket would
            // convert it into a water/lava bucket depending on the blocks material
            event.setCanceled(true);
        }
    }
}