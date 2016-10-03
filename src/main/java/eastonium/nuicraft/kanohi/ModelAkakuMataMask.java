package eastonium.nuicraft.kanohi;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelAkakuMataMask extends ModelBiped {
	ModelRenderer Mask;
	ModelRenderer EyeBase1;
	ModelRenderer EyeBase2;
	ModelRenderer Eye1;
	ModelRenderer Eye2;
	ModelRenderer Eye3;

	public ModelAkakuMataMask(){
		this.textureWidth = 64;
		this.textureHeight = 32;

		Mask = new ModelRenderer(this, 0, 0);
		Mask.addBox(-4F, -7.5F, -4F, 8, 13, 13);
		Mask.setRotationPoint(0F, 0F, 0F);
		Mask.setTextureSize(64, 32);
		Mask.mirror = true;
		setRotation(Mask, 0F, 0F, 0F);

		EyeBase1 = new ModelRenderer(this, 29, 0);
		EyeBase1.addBox(-3F, -5F, 0F, 3, 5, 5);
		EyeBase1.setRotationPoint(-0.5F, -3.9F, -4.5F);
		EyeBase1.setTextureSize(64, 32);
		EyeBase1.mirror = true;
		setRotation(EyeBase1, 0F, 0F, -0.4363323F);
		EyeBase2 = new ModelRenderer(this, 45, 0);
		EyeBase2.addBox(-4F, -3F, 0F, 4, 3, 5);
		EyeBase2.setRotationPoint(-0.5F, -3.9F, -4.55F);
		EyeBase2.setTextureSize(64, 32);
		EyeBase2.mirror = true;
		setRotation(EyeBase2, 0F, 0F, -0.7853982F);
		Eye1 = new ModelRenderer(this, 0, 0);
		Eye1.addBox(-2.5F, -4.5F, -2F, 2, 2, 2);
		Eye1.setRotationPoint(-0.5F, -3.9F, -4.5F);
		Eye1.setTextureSize(64, 32);
		Eye1.mirror = true;
		setRotation(Eye1, 0F, 0F, -0.4363323F);
		Eye2 = new ModelRenderer(this, 0, 4);
		Eye2.addBox(-3.5F, -2.5F, -1F, 2, 2, 1);
		Eye2.setRotationPoint(-0.5F, -3.9F, -4.5F);
		Eye2.setTextureSize(64, 32);
		Eye2.mirror = true;
		setRotation(Eye2, 0F, 0F, -0.7853982F);
		Eye3 = new ModelRenderer(this, 0, 7);
		Eye3.addBox(0F, 0F, 0F, 1, 1, 1);
		Eye3.setRotationPoint(-2F, -4.8F, -5.0F);
		Eye3.setTextureSize(64, 32);
		Eye3.mirror = true;
		setRotation(Eye3, 0F, 0F, 0F);

		//this.Mask.addChild(Mask);
		this.Mask.addChild(EyeBase1);
		this.Mask.addChild(EyeBase2);
		this.Mask.addChild(Eye1);
		this.Mask.addChild(Eye2);
		this.Mask.addChild(Eye3);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale){
		this.setRotationAngles(f, f1, f2, f3, f4, scale, entity);
		GlStateManager.pushMatrix();
		if (entity.isSneaking()) GlStateManager.translate(0.0F, 0.25F, 0.0F);
		this.Mask.render(scale * 1.2F);
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
