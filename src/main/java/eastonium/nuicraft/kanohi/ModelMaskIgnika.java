package eastonium.nuicraft.kanohi;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelMaskIgnika extends ModelBiped {
	ModelRenderer Mask;
	ModelRenderer RightArch;
	ModelRenderer LeftArch;

	public ModelMaskIgnika(){
		textureWidth = 64;
		textureHeight = 32;

		Mask = new ModelRenderer(this, 0, 2);
		Mask.addBox(-4F, -7.25F, -4F, 8, 8, 8);
		Mask.setRotationPoint(0F, 0F, 0F);
		Mask.setTextureSize(64, 32);
		Mask.mirror = true;
		setRotation(Mask, 0F, 0F, 0F);
		RightArch = new ModelRenderer(this, 1, -3);
		RightArch.addBox(0F, -9.25F, -2F, 0, 10, 3);
		RightArch.setRotationPoint(-4F, -8F, 2F);
		RightArch.setTextureSize(64, 32);
		RightArch.mirror = true;
		setRotation(RightArch, 0F, 0F, 0.0872665F);
		LeftArch = new ModelRenderer(this, 1, -3);
		LeftArch.addBox(0F, -9.25F, -2F, 0, 10, 3);
		LeftArch.setRotationPoint(4F, -8F, 2F);
		LeftArch.setTextureSize(64, 32);
		LeftArch.mirror = true;
		setRotation(LeftArch, 0F, 0F, -0.0872665F);

		Mask.addChild(RightArch);
		Mask.addChild(LeftArch);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale){
		setRotationAngles(f, f1, f2, f3, f4, scale, entity);
		GlStateManager.pushMatrix();
		if (entity.isSneaking()) GlStateManager.translate(0.0F, 0.25F, 0.0F);
		Mask.render(scale * 1.2F);
		GlStateManager.popMatrix();
	}

	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn){
		Mask.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
		Mask.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);

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
