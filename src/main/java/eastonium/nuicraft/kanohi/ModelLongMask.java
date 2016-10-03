package eastonium.nuicraft.kanohi;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelLongMask extends ModelBiped {
	ModelRenderer Mask;
	
	public ModelLongMask(){
		this.textureWidth = 64;
        this.textureHeight = 32;

        this.Mask = new ModelRenderer(this, 0, 0);
		this.Mask.addBox(-4F, -7.25F, -4F, 8, 13, 13);
		this.Mask.setRotationPoint(0F, 0F, 0F);
		this.Mask.setTextureSize(64, 32);
		this.Mask.mirror = true;
		setRotation(this.Mask, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale){
		this.setRotationAngles(f, f1, f2, f3, f4, scale, entity);
		GlStateManager.pushMatrix();
		if (entity.isSneaking()) GlStateManager.translate(0.0F, 0.25F, 0.0F);
		Mask.render(scale * 1.2F);
		GlStateManager.popMatrix();
	}

	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn){
		this.Mask.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
		this.Mask.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);

		if(this.isSneak){
			Mask.rotationPointY = 1.0F;
		}else{
			Mask.rotationPointY = 0.0F;
		}
	}


	private void setRotation(ModelRenderer model, float x, float y, float z){
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
