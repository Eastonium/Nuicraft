package eastonium.nuicraft.mobs.mahi;

import eastonium.nuicraft.Bionicle;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMahi extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation(Bionicle.MODID + ":textures/entity/Mahi.png");
	protected ModelMahi model = new ModelMahi();
	
	public RenderMahi(RenderManager rm, ModelBase mb, float f)
	{
		super(rm, mb, f);
		model = ((ModelMahi)mainModel);
	}

	@Override
	public void doRender(EntityLiving entity, double d0, double d1, double d2, float f, float f1) 
	{
		super.doRender((EntityMahi)entity, d0, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}