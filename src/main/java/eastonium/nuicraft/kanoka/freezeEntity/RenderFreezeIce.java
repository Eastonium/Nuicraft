package eastonium.nuicraft.kanoka.freezeEntity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderFreezeIce extends Render<EntityFreezeIce> {
	protected final ItemStack stack;
    private final RenderItem itemRenderer;

    public RenderFreezeIce(RenderManager renderManagerIn, ItemStack stack, RenderItem itemRendererIn) {
        super(renderManagerIn);
        this.stack = stack;
        this.itemRenderer = itemRendererIn;
    }
    public RenderFreezeIce(RenderManager renderManagerIn, Block block, RenderItem itemRendererIn) {
    	this(renderManagerIn, new ItemStack(block), itemRendererIn);
    }
    public RenderFreezeIce(RenderManager renderManagerIn, Item item, RenderItem itemRendererIn) {
    	this(renderManagerIn, new ItemStack(item), itemRendererIn);
    }    

    @Override
    public void doRender(EntityFreezeIce entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y - (0.25F * entity.height) + 0.001F, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(4 * entity.width, 4 * entity.height, 4 * entity.width);
        GlStateManager.rotate(360 - entityYaw, 0, 1, 0);
        GlStateManager.disableCull();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.itemRenderer.renderItem(this.stack, ItemCameraTransforms.TransformType.GROUND);

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        
        GlStateManager.enableCull();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFreezeIce entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
