package eastonium.nuicraft.kanoka;

import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.item.ItemGenericMeta.EnumGenericItem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RenderKanoka<T extends EntityKanoka> extends RenderSnowball<T> {

    private final RenderItem itemRenderer;
	
	public RenderKanoka(RenderManager renderManager, RenderItem itemRenderer) {
		super(renderManager, NuiCraftItems.generic_item, itemRenderer);
		this.itemRenderer = itemRenderer;
	}

	@Override
	public ItemStack getStackToRender(T entityIn) {
        return entityIn.isBamboo() ? EnumGenericItem.KANOKA_FLYING_BLACK.getStack(1) : EnumGenericItem.KANOKA_FLIYNG_WHITE.getStack(1);
    }
}
