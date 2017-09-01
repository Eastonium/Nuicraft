package eastonium.nuicraft.mobs.nui_jaga;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderNuiJaga extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation(NuiCraft.MODID + ":textures/entity/nui_jaga.png");
	protected ModelNuiJaga model;

	// TODO Adding color variants
	
	public RenderNuiJaga(RenderManager rm, ModelBase mb, float f)
	{
		super(rm, mb, f);
		model = ((ModelNuiJaga)mainModel);
	}
	
	@Override
	public void doRender(EntityLiving entity, double d0, double d1, double d2, float f, float f1) 
	{
		super.doRender((EntityNuiJaga)entity, d0, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation  getEntityTexture(Entity entity) {
		return texture;
	}

}
