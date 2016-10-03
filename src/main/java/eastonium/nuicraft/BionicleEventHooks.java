package eastonium.nuicraft;


public class BionicleEventHooks 
{	
	/*@SubscribeEvent
	public void renderShadow(RenderPlayerEvent.Pre event)
	{
		if(event.entity.isInvisible())
		{	
			EntityPlayer entity = event.entityPlayer;
			entity.setInvisible(false);
			event.renderer.doRenderShadowAndFire(entity, entity.posX, entity.posY, entity.posZ, 1.0F, event.partialRenderTick); 
			entity.setInvisible(true);
		}
	}*/

	/*private ModelBase bipedModel = new ModelBiped(1.0F);
	private static final ResourceLocation hauShieldTexture = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

	@SubscribeEvent
	public void renderOnPlayerArmor(RenderPlayerEvent.SetArmorModel event) 
	{
		float f1 = (float)event.entityPlayer.ticksExisted + par3;
		//event.renderer.bindTexture(hauShieldTexture);
		//ForgeHooksClient.bindTexture(hauShieldTexture);
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		float f2 = f1 * 0.01F;
		float f3 = f1 * 0.01F;
		GL11.glTranslatef(f2, f3, 0.0F);
		event.renderer.setRenderPassModel(this.bipedModel);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_BLEND);
		float f4 = 0.5F;
		GL11.glColor4f(f4, f4, f4, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
	}*/
}