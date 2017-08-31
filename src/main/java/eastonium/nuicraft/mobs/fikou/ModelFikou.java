package eastonium.nuicraft.mobs.fikou;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelFikou extends ModelBase
{
  //fields
    ModelRenderer Body;
    ModelRenderer Head;
    ModelRenderer LegL1;
    ModelRenderer LegL2;
    ModelRenderer LegL3;
    ModelRenderer LegR1;
    ModelRenderer LegR2;
    ModelRenderer LegR3;
  
    public ModelFikou()
    {
      textureWidth = 32;
      textureHeight = 32;
      setTextureOffset("Body.Body2", 0, 23);
      setTextureOffset("Body.Body1", 0, 13);
      setTextureOffset("Body.Mask", 0, 0);
      setTextureOffset("Head.Head", 0, 23);
      setTextureOffset("Head.Eye1", 24, 13);
      setTextureOffset("Head.Eye2", 24, 13);
      
        LegL1 = new ModelRenderer(this, 18, 23);
        LegL1.addBox(0F, 0F, 0F, 6, 1, 1);
        LegL1.setRotationPoint(1F, 21F, -2F);
        LegL1.setTextureSize(32, 32);
        LegL1.mirror = true;
        setRotation(LegL1, 0F, 0.2617994F, 0.4363323F);
        LegL2 = new ModelRenderer(this, 18, 23);
        LegL2.addBox(0F, 0F, 0F, 6, 1, 1);
        LegL2.setRotationPoint(1F, 21F, 0F);
        LegL2.setTextureSize(32, 32);
        LegL2.mirror = true;
        setRotation(LegL2, 0F, 0F, 0.4363323F);
        LegL3 = new ModelRenderer(this, 18, 23);
        LegL3.addBox(0F, 0F, 0F, 6, 1, 1);
        LegL3.setRotationPoint(1F, 21F, 2F);
        LegL3.setTextureSize(32, 32);
        LegL3.mirror = true;
        setRotation(LegL3, 0F, -0.2617994F, 0.4363323F);
        LegR1 = new ModelRenderer(this, 18, 23);
        LegR1.addBox(-6F, 0F, 0F, 6, 1, 1);
        LegR1.setRotationPoint(-1F, 21F, -2F);
        LegR1.setTextureSize(32, 32);
        LegR1.mirror = true;
        setRotation(LegR1, 0F, -0.2617994F, -0.4363323F);
        LegR1.mirror = false;
        LegR2 = new ModelRenderer(this, 18, 23);
        LegR2.addBox(-6F, 0F, 0F, 6, 1, 1);
        LegR2.setRotationPoint(-1F, 21F, 0F);
        LegR2.setTextureSize(32, 32);
        LegR2.mirror = true;
        setRotation(LegR2, 0F, 0F, -0.4363323F);
        LegR2.mirror = false;
        LegR3 = new ModelRenderer(this, 18, 23);
        LegR3.addBox(-6F, 0F, 0F, 6, 1, 1);
        LegR3.setRotationPoint(-1F, 21F, 2F);
        LegR3.setTextureSize(32, 32);
        LegR3.mirror = true;
        setRotation(LegR3, 0F, 0.2617994F, -0.4363323F);
        LegR3.mirror = false;
      Body = new ModelRenderer(this, "Body");
      Body.setRotationPoint(-4F, 16F, 0F);
      setRotation(Body, 0F, 0F, 0F);
      Body.mirror = true;
        Body.addBox("Body2", 2F, 4F, -2F, 4, 2, 5);
        Body.addBox("Body1", 1F, 2F, 1F, 6, 4, 6);
        Body.addBox("Mask", 0F, 1F, 0F, 8, 5, 8);
      Head = new ModelRenderer(this, "Head");
      Head.setRotationPoint(-2F, 19F, -6F);
      setRotation(Head, 0F, 0F, 0F);
      Head.mirror = true;
        Head.addBox("Head", 0F, 0F, 0F, 4, 2, 5);
        Head.addBox("Eye1", -2F, 0F, 2F, 2, 2, 2);
        Head.addBox("Eye2", 4F, 0F, 2F, 2, 2, 2);
    }
    
    public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
  	 this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
    Body.render(par7);
    Head.render(par7);
    LegL1.render(par7);
    LegL2.render(par7);
    LegL3.render(par7);
    LegR1.render(par7);
    LegR2.render(par7);
    LegR3.render(par7);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity)
  {
	  //this.Head.rotateAngleY = par4 / (180F / (float)Math.PI);
      //this.Head.rotateAngleX = par5 / (180F / (float)Math.PI);
	  
      float f6 = -((float)Math.PI / 4F);
      this.LegL1.rotateAngleZ = -f6 * 0.75F;
      this.LegR1.rotateAngleZ = f6 * 0.75F;
      this.LegL2.rotateAngleZ = -f6 * 0.6F;
      this.LegR2.rotateAngleZ = f6 * 0.6F;
      this.LegL3.rotateAngleZ = -f6 * 0.75F;
      this.LegR3.rotateAngleZ = f6 * 0.75F;
      float f7 = -0.0F;
      float f8 = 0.3926991F;
      this.LegL1.rotateAngleY = f8 * 1.0F + f7;
      this.LegR1.rotateAngleY = -f8 * 1.0F - f7;
      this.LegL2.rotateAngleY = f7;
      this.LegR2.rotateAngleY = - f7;
      this.LegL3.rotateAngleY = -f8 * 1.0F + f7;
      this.LegR3.rotateAngleY = f8 * 1.0F - f7;
      float f9 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
      float f10 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * par2;
      float f11 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * par2;
      float f12 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + ((float)Math.PI * 3F / 2F)) * 0.4F) * par2;
      float f13 = Math.abs(MathHelper.sin(par1 * 0.6662F + 0.0F) * 0.4F) * par2;
      float f14 = Math.abs(MathHelper.sin(par1 * 0.6662F + (float)Math.PI) * 0.4F) * par2;
      float f15 = Math.abs(MathHelper.sin(par1 * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * par2;
      float f16 = Math.abs(MathHelper.sin(par1 * 0.6662F + ((float)Math.PI * 3F / 2F)) * 0.4F) * par2;
      this.LegL1.rotateAngleY += f9;
      this.LegR1.rotateAngleY += -f9;
      this.LegL2.rotateAngleY += ((f10+f11)/2);
      this.LegR2.rotateAngleY += -((f10+f11)/2);
      this.LegL3.rotateAngleY += f12;
      this.LegR3.rotateAngleY += -f12;
      this.LegL1.rotateAngleZ += f13;
      this.LegR1.rotateAngleZ += -f13;
      this.LegL2.rotateAngleZ += ((f14+f15)/2);
      this.LegR2.rotateAngleZ += -((f14+f15)/2);
      this.LegL3.rotateAngleZ += f16;
      this.LegR3.rotateAngleZ += -f16;
  }

}