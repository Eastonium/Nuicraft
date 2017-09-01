package eastonium.nuicraft.mobs.kofo_jaga;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelKofoJaga extends ModelBase
{
  //fields
    ModelRenderer LegL1;
    ModelRenderer LegL2;
    ModelRenderer LegL3;
    ModelRenderer LegR1;
    ModelRenderer LegR2;
    ModelRenderer LegR3;
    ModelRenderer Tail1;
    ModelRenderer Tail2;
    ModelRenderer Sting1;
    ModelRenderer Sting2;
    ModelRenderer Sting3;
    ModelRenderer Sting4;
    ModelRenderer Sting5;
    ModelRenderer Sting6;
    ModelRenderer Body1;
    ModelRenderer Body2;
    ModelRenderer Arm1;
    ModelRenderer Arm2;
    ModelRenderer Body;
    ModelRenderer Head;
  
  public ModelKofoJaga()
  {
    textureWidth = 64;
    textureHeight = 32;
    setTextureOffset("Arm1.ArmCentre1", 32, 16);
    setTextureOffset("Arm1.Hand1", 36, 0);
    setTextureOffset("Arm2.ArmCentre2", 32, 16);
    setTextureOffset("Arm2.Hand2", 36, 0);
    setTextureOffset("Head.Head1", 0, 16);
    setTextureOffset("Head.Head2", 0, 23);
    setTextureOffset("Head.Eye1", 24, 16);
    setTextureOffset("Head.Eye2", 24, 16);
    
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
      Tail1 = new ModelRenderer(this, 36, 23);
      Tail1.addBox(0F, 0F, 0F, 1, 1, 6);
      Tail1.setRotationPoint(-0.5F, 20F, 9F);
      Tail1.setTextureSize(64, 32);
      Tail1.mirror = true;
      setRotation(Tail1, 0.7853982F, 0F, 0F);
      Tail2 = new ModelRenderer(this, 22, 20);
      Tail2.addBox(0F, 0F, 0F, 2, 2, 10);
      Tail2.setRotationPoint(-1F, 9F, 8F);
      Tail2.setTextureSize(64, 32);
      Tail2.mirror = true;
      setRotation(Tail2, -0.8726646F, 0F, 0F);
      Sting1 = new ModelRenderer(this, 36, 8);
      Sting1.addBox(0F, 0F, 0F, 2, 2, 6);
      Sting1.setRotationPoint(-1F, 9F, 4F);
      Sting1.setTextureSize(64, 32);
      Sting1.mirror = true;
      setRotation(Sting1, 0F, 0F, 0F);
      Sting2 = new ModelRenderer(this, 0, 6);
      Sting2.addBox(0F, 0F, 0F, 2, 2, 4);
      Sting2.setRotationPoint(-1F, 11F, 4F);
      Sting2.setTextureSize(64, 32);
      Sting2.mirror = true;
      setRotation(Sting2, 2.268928F, 0F, 0F);
      Sting3 = new ModelRenderer(this, 20, 4);
      Sting3.addBox(0F, 0F, 0F, 2, 1, 7);
      Sting3.setRotationPoint(-1F, 8.5F, -3F);
      Sting3.setTextureSize(64, 32);
      Sting3.mirror = true;
      setRotation(Sting3, 0.3490659F, 0F, 0F);
      Sting4 = new ModelRenderer(this, 52, 9);
      Sting4.addBox(0F, 0F, 0F, 2, 5, 2);
      Sting4.setRotationPoint(-1F, 6F, -3F);
      Sting4.setTextureSize(64, 32);
      Sting4.mirror = true;
      setRotation(Sting4, -0.1047198F, 0F, 0F);
      Sting5 = new ModelRenderer(this, 52, 9);
      Sting5.addBox(0F, 0F, 0F, 2, 4, 2);
      Sting5.setRotationPoint(-1F, 11F, -3.5F);
      Sting5.setTextureSize(64, 32);
      Sting5.mirror = true;
      setRotation(Sting5, 0.7853982F, 0F, 0F);
      Sting6 = new ModelRenderer(this, 46, 24);
      Sting6.addBox(0F, 0F, 0F, 1, 1, 7);
      Sting6.setRotationPoint(-0.5F, 7F, -2F);
      Sting6.setTextureSize(64, 32);
      Sting6.mirror = true;
      setRotation(Sting6, -0.2617994F, 0F, 0F);
      Body1 = new ModelRenderer(this, 0, 0);
      Body1.addBox(2F, 3F, -5F, 4, 4, 12);
      Body1.setRotationPoint(-4F, 16F, 0F);
      Body1.setTextureSize(64, 32);
      Body1.mirror = true;
      setRotation(Body1, 0F, 0F, 0F);
      Body2 = new ModelRenderer(this, 0, 23);
      Body2.addBox(0F, 0F, 0F, 4, 2, 5);
      Body2.setRotationPoint(-1F, 19F, 10F);
      Body2.setTextureSize(64, 32);
      Body2.mirror = true;
      setRotation(Body2, 3.141593F, 0F, 1.570796F);
    Arm1 = new ModelRenderer(this, "Arm1");
    Arm1.setRotationPoint(0F, 20F, -4F);
    setRotation(Arm1, 0F, 0.2617994F, 0F);
    Arm1.mirror = true;
      Arm1.addBox("ArmCentre1", 0F, 0F, 0F, 14, 2, 2);
      Arm1.addBox("Hand1", 10F, 0F, -7F, 4, 1, 7);
    Arm2 = new ModelRenderer(this, "Arm2");
    Arm2.setRotationPoint(0F, 20F, -4F);
    setRotation(Arm2, 0F, -0.2617994F, 0F);
    Arm2.mirror = false;
      Arm2.addBox("ArmCentre2", -14F, 0F, 0F, 14, 2, 2);
      Arm2.addBox("Hand2", -14F, 0F, -7F, 4, 1, 7);
    Body = new ModelRenderer(this, "Body");
    Body.setRotationPoint(-4F, 16F, 0F);
    setRotation(Body, 0F, 0F, 0F);
    Body.mirror = true;
    Head = new ModelRenderer(this, "Head");
    Head.setRotationPoint(0F, 21F, -5F);
    setRotation(Head, 0F, 0F, 0F);
    Head.mirror = true;
      Head.addBox("Head1", -5F, -2F, -3F, 10, 2, 2);
      Head.addBox("Head2", -2F, 0F, -3F, 4, 2, 5);
      Head.addBox("Eye1", -5F, -2F, -5F, 2, 2, 2);
      Head.addBox("Eye2", 3F, -2F, -5F, 2, 2, 2);
  }
 

  public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
  {
	 this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
    LegL1.render(par7);
    LegL2.render(par7);
    LegL3.render(par7);
    LegR1.render(par7);
    LegR2.render(par7);
    LegR3.render(par7);
    Tail1.render(par7);
    Tail2.render(par7);
    Sting1.render(par7);
    Sting2.render(par7);
    Sting3.render(par7);
    Sting4.render(par7);
    Sting5.render(par7);
    Sting6.render(par7);
    Body1.render(par7);
    Body2.render(par7);
    Arm1.render(par7);
    Arm2.render(par7);
    Body.render(par7);
    Head.render(par7);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity)
  {
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
