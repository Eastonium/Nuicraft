package eastonium.nuicraft.mobs.kofo_jaga;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderKofoJaga extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation(NuiCraft.MODID + ":textures/entity/kofo_jaga.png");
	protected ModelKofoJaga model;
	
	public RenderKofoJaga(RenderManager rm, ModelBase mb, float f)
	{
		super(rm, mb, f);
		model = ((ModelKofoJaga)mainModel);
	}
	
	@Override
	public void doRender(EntityLiving entity, double d0, double d1, double d2, float f, float f1) 
	{
		super.doRender((EntityKofoJaga)entity, d0, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation  getEntityTexture(Entity entity) {
		return texture;
	}

}
