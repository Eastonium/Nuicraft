package eastonium.nuicraft.mobs.mahi;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelMahi extends ModelBase
{
  //fields
    ModelRenderer EyeL;
    ModelRenderer EyeR;
    ModelRenderer HeadBase1;
    ModelRenderer HeadBase2;
    ModelRenderer HeadBase3;
    ModelRenderer HeadBaseMain;
    ModelRenderer HeadNose1;
    ModelRenderer HeadNose2;
    ModelRenderer HeadNose3;
    ModelRenderer HornL1;
    ModelRenderer HornL2;
    ModelRenderer HornL3;
    ModelRenderer HornL4;
    ModelRenderer HornL5;
    ModelRenderer HornL6;
    ModelRenderer HornR1;
    ModelRenderer HornR2;
    ModelRenderer HornR3;
    ModelRenderer HornR4;
    ModelRenderer HornR5;
    ModelRenderer HornR6;
    ModelRenderer Neck;
    ModelRenderer BodyBase1;
    ModelRenderer BodyAngleRF;
    ModelRenderer BodyAngleLF;
    ModelRenderer BodyAngleRB;
    ModelRenderer BodyAngleLB;
    ModelRenderer BodyFlancR;
    ModelRenderer BodyFlancL;
    ModelRenderer BodyTail;
    ModelRenderer LegRF;
    ModelRenderer LegRB;
    ModelRenderer LegLF;
    ModelRenderer LegLB;
  
  public ModelMahi()
  {
    textureWidth = 64;
    textureHeight = 32;
    setTextureOffset("LegRF.LegRF1", 56, 4);
    setTextureOffset("LegRF.LegRF2", 44, 0);
    setTextureOffset("LegRF.LegRF3", 48, 0);
    setTextureOffset("LegRB.LegRB1", 56, 4);
    setTextureOffset("LegRB.LegRB2", 44, 0);
    setTextureOffset("LegRB.LegRB3", 48, 0);
    setTextureOffset("LegLF.LegLF1", 56, 4);
    setTextureOffset("LegLF.LegLF2", 44, 0);
    setTextureOffset("LegLF.LegLF3", 48, 0);
    setTextureOffset("LegLB.LegLB1", 56, 4);
    setTextureOffset("LegLB.LegLB2", 44, 0);
    setTextureOffset("LegLB.LegLB3", 48, 0);
    setTextureOffset("HeadBaseMain.base", 23, 3);
    setTextureOffset("EyeL.eye", 56, 0);
    setTextureOffset("EyeR.eye", 56, 0);
    setTextureOffset("HeadBase1.HeadBase1", 32, 7);
    setTextureOffset("HeadBase2.HeadBase2", 0, 0);
    setTextureOffset("HeadBase3.HeadBase3", 42, 18);
    
    
      HeadBaseMain = new ModelRenderer(this, "HeadBaseMain");
      HeadBaseMain.addBox("base",-2F, 0F, -3F, 4, 4, 4);
      HeadBaseMain.setRotationPoint(0F, 7F, -6F);
      setRotation(HeadBaseMain, 0.4363323F, 0F, 0F);
    	
      EyeL = new ModelRenderer(this, "EyeL");
      EyeL.addBox("eye", 2F, -2F, -3F, 2, 2, 2);
      HeadBaseMain.addChild(EyeL);
      
      EyeR = new ModelRenderer(this, "EyeR");
      EyeR.addBox("eye", -4F, -2F, -3F, 2, 2, 2);
      HeadBaseMain.addChild(EyeR);
      
      HeadBase1 = new ModelRenderer(this, "HeadBase1");
      HeadBase1.addBox("HeadBase1", -2F, -3.2F, -5.8F, 4, 3, 7);
      HeadBase1.setRotationPoint(0F, 2F, 0F);
      HeadBase1.mirror = true;
      setRotation(HeadBase1, 0.4363323F, 0F, 0F);
      HeadBaseMain.addChild(HeadBase1);
      
      HeadBase2 = new ModelRenderer(this, "HeadBase2");
      HeadBase2.addBox("HeadBase2", -2.5F, -1.5F, -8.1F, 5, 3, 5);
      HeadBase2.mirror = true;
      setRotation(HeadBase2, 0.4363323F, 0F, 0F);
      HeadBaseMain.addChild(HeadBase2);
      
      HeadBase3 = new ModelRenderer(this, "HeadBase3");
      HeadBase3.addBox("HeadBase3", -2.5F, 2F, -8F, 5, 3, 6);
      HeadBase3.mirror = true;
      setRotation(HeadBase3, 0F, 0F, 0F);
      HeadBaseMain.addChild(HeadBase3);
      
      HeadNose1 = new ModelRenderer(this, 54, 12);
      HeadNose1.addBox(-1F, -0.9F, -8F, 2, 3, 3);//MODEL: Needs an offset fix
      HeadNose1.mirror = true;
      setRotation(HeadNose1, 0.2617994F, 0F, 0F);
      HeadBaseMain.addChild(this.HeadNose1);
      
      HeadNose2 = new ModelRenderer(this, 35, 0);
      HeadNose2.addBox(-1F, -2F, -3.5F, 2, 2, 5);//MODEL: Needs an offset fix
      HeadNose2.mirror = true;
      setRotation(HeadNose2, -0.0698132F, 0F, 0F);
      HeadBaseMain.addChild(this.HeadNose2);
      
      HeadNose3 = new ModelRenderer(this, 35, 0);
      HeadNose3.addBox(-1F, -3.2F, -7.3F, 2, 2, 5);
      HeadNose3.mirror = true;
      setRotation(HeadNose3, 0.4886922F, 0F, 0F);
      HeadBaseMain.addChild(this.HeadNose3);
      
      HornL1 = new ModelRenderer(this, 30, 23);
      HornL1.addBox(0.7F, -4.4F, -1F, 4, 2, 2);
      HornL1.mirror = false;
      setRotation(HornL1, 0F, 0F, 0.9599311F);
      
      HornL2 = new ModelRenderer(this, 34, 27);
      HornL2.addBox(2.5F, 4F, -1F, 7, 1, 2);
      HornL2.mirror = false;
      setRotation(HornL2, 0F, 0F, -0.5235988F);
      
      HornL3 = new ModelRenderer(this, 40, 17);
      HornL3.addBox(8.7F, -3.9F, -1F, 2, 5, 2);
      HornL3.mirror = false;
      setRotation(HornL3, 0F, 0F, 0.1047198F);
      
      HornL4 = new ModelRenderer(this, 30, 23);
      HornL4.addBox(-0.2F, -10.7F, -1F, 4, 2, 2);
      HornL4.mirror = false;
      setRotation(HornL4, 0F, 0F, 0.9948377F);
      
      HornL5 = new ModelRenderer(this, 34, 30);
      HornL5.addBox(2.4F, -1.6F, -0.5F, 8, 1, 1);
      HornL5.mirror = false;
      setRotation(HornL5, 0F, 0F, 0.2617994F);
      
      HornL6 = new ModelRenderer(this, 30, 19);
      HornL6.addBox(1F, -2F, -1F, 3, 2, 2);
      HornL6.mirror = false;
      setRotation(HornL6, 0F, 0F, 0F);
      
      HeadBaseMain.addChild(this.HornL1);
      HeadBaseMain.addChild(this.HornL2);
      HeadBaseMain.addChild(this.HornL3);
      HeadBaseMain.addChild(this.HornL4);
      HeadBaseMain.addChild(this.HornL5);
      HeadBaseMain.addChild(this.HornL6);
      
      HornR1 = new ModelRenderer(this, 30, 23);
      HornR1.addBox(-4.7F, -4.4F, -1F, 4, 2, 2);
      setRotation(HornR1, 0F, 0F, -0.9599311F);
      HornR1.mirror = true;
      
      HornR2 = new ModelRenderer(this, 34, 27);
      HornR2.addBox(-9.5F, 4F, -1F, 7, 1, 2);
      setRotation(HornR2, 0F, 0F, 0.5235988F);
      HornR2.mirror = true;
      
      HornR3 = new ModelRenderer(this, 40, 17);
      HornR3.addBox(-10.7F, -3.9F, -1F, 2, 5, 2);
      setRotation(HornR3, 0F, 0F, -0.1047198F);
      HornR3.mirror = true;
      
      HornR4 = new ModelRenderer(this, 30, 23);
      HornR4.addBox(-3.7F, -10.7F, -1F, 4, 2, 2);
      setRotation(HornR4, 0F, 0F, -0.9948377F);
      HornR4.mirror = true;
      
      HornR5 = new ModelRenderer(this, 34, 30);
      HornR5.addBox(-10.5F, -1.6F, -0.5F, 8, 1, 1);
      setRotation(HornR5, 0F, 0F, -0.2617994F);
      HornR5.mirror = true;
      
      HornR6 = new ModelRenderer(this, 30, 19);
      HornR6.addBox(-4F, -2F, -1F, 3, 2, 2);
      setRotation(HornR6, 0F, 0F, 0F);
      HornR6.mirror = true;
      
      HeadBaseMain.addChild(this.HornR1);
      HeadBaseMain.addChild(this.HornR2);
      HeadBaseMain.addChild(this.HornR3);
      HeadBaseMain.addChild(this.HornR4);
      HeadBaseMain.addChild(this.HornR5);
      HeadBaseMain.addChild(this.HornR6);
      
      Neck = new ModelRenderer(this, 0, 25);
      Neck.addBox(0F, 0F, 0F, 2, 5, 2);
      Neck.setRotationPoint(-1F, 9F, -7F);
      Neck.mirror = true;
      setRotation(Neck, 0.4363323F, 0F, 0F);
      
      BodyBase1 = new ModelRenderer(this, 2, 9);
      BodyBase1.addBox(0F, 0F, 0F, 4, 4, 10);
      BodyBase1.setRotationPoint(-2F, 12F, -5F);
      BodyBase1.mirror = true;
      setRotation(BodyBase1, 0F, 0F, 0F);
      
      BodyAngleRF = new ModelRenderer(this, 22, 23);
      BodyAngleRF.addBox(0F, 0F, 0F, 2, 5, 4);
      BodyAngleRF.setRotationPoint(-4F, 12F, -6F);
      BodyAngleRF.mirror = true;
      setRotation(BodyAngleRF, 0F, 0F, 0F);
      
      BodyAngleLF = new ModelRenderer(this, 22, 23);
      BodyAngleLF.addBox(0F, 0F, 0F, 2, 5, 4);
      BodyAngleLF.setRotationPoint(2F, 12F, -6F);
      BodyAngleLF.mirror = true;
      setRotation(BodyAngleLF, 0F, 0F, 0F);
      
      BodyAngleRB = new ModelRenderer(this, 22, 23);
      BodyAngleRB.addBox(0F, 0F, 0F, 2, 5, 4);
      BodyAngleRB.setRotationPoint(-4F, 12F, 2F);
      BodyAngleRB.mirror = true;
      setRotation(BodyAngleRB, 0F, 0F, 0F);
      
      BodyAngleLB = new ModelRenderer(this, 22, 23);
      BodyAngleLB.addBox(0F, 0F, 0F, 2, 5, 4);
      BodyAngleLB.setRotationPoint(2F, 12F, 2F);
      BodyAngleLB.mirror = true;
      setRotation(BodyAngleLB, 0F, 0F, 0F);
      
      BodyFlancR = new ModelRenderer(this, 12, 26);
      BodyFlancR.addBox(0F, 0F, 0F, 1, 2, 4);
      BodyFlancR.setRotationPoint(2F, 12F, -2F);
      BodyFlancR.mirror = true;
      setRotation(BodyFlancR, 0F, 0F, 0F);
      BodyFlancR.mirror = false;
      
      BodyFlancL = new ModelRenderer(this, 12, 26);
      BodyFlancL.addBox(0F, 0F, 0F, 1, 2, 4);
      BodyFlancL.setRotationPoint(-3F, 12F, -2F);
      BodyFlancL.mirror = true;
      setRotation(BodyFlancL, 0F, 0F, 0F);
      BodyFlancL.mirror = false;
      
      BodyTail = new ModelRenderer(this, 22, 23);
      BodyTail.addBox(0F, 0F, 0F, 2, 5, 4);
      BodyTail.setRotationPoint(-1F, 8F, 7F);
      BodyTail.mirror = true;
      setRotation(BodyTail, -0.8726646F, 0F, 0F);
      
    LegRF = new ModelRenderer(this, "LegRF");
    LegRF.setRotationPoint(-5F, 15F, -4F);
    setRotation(LegRF, 0F, 0F, 0F);
    LegRF.mirror = true;
      LegRF.addBox("LegRF1", 0F, -1F, -1F, 2, 6, 2);
      LegRF.addBox("LegRF2", 0.5F, 5F, -0.5F, 1, 3, 1);
      LegRF.addBox("LegRF3", 0F, 7F, -1F, 2, 2, 2);
      
    LegRB = new ModelRenderer(this, "LegRB");
    LegRB.setRotationPoint(-5F, 15F, 4F);
    setRotation(LegRB, 0F, 0F, 0F);
    LegRB.mirror = true;
      LegRB.addBox("LegRB1", 0F, -1F, -1F, 2, 6, 2);
      LegRB.addBox("LegRB2", 0.5F, 5F, -0.5F, 1, 3, 1);
      LegRB.addBox("LegRB3", 0F, 7F, -1F, 2, 2, 2);
      
    LegLF = new ModelRenderer(this, "LegLF");
    LegLF.setRotationPoint(3F, 15F, -4F);
    setRotation(LegLF, 0F, 0F, 0F);
    LegLF.mirror = true;
      LegLF.addBox("LegLF1", 0F, -1F, -1F, 2, 6, 2);
      LegLF.addBox("LegLF2", 0.5F, 5F, -0.5F, 1, 3, 1);
      LegLF.addBox("LegLF3", 0F, 7F, -1F, 2, 2, 2);
      
    LegLB = new ModelRenderer(this, "LegLB");
    LegLB.setRotationPoint(3F, 15F, 4F);
    setRotation(LegLB, 0F, 0F, 0F);
    LegLB.mirror = true;
      LegLB.addBox("LegLB1", 0F, -1F, -1F, 2, 6, 2);
      LegLB.addBox("LegLB2", 0.5F, 5F, -0.5F, 1, 3, 1);
      LegLB.addBox("LegLB3", 0F, 7F, -1F, 2, 2, 2);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);

    HeadBaseMain.render(f5);

    Neck.render(f5);
    BodyBase1.render(f5);
    BodyAngleRF.render(f5);
    BodyAngleLF.render(f5);
    BodyAngleRB.render(f5);
    BodyAngleLB.render(f5);
    BodyFlancR.render(f5);
    BodyFlancL.render(f5);
    BodyTail.render(f5);
    LegRF.render(f5);
    LegRB.render(f5);
    LegLF.render(f5);
    LegLB.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
	 //Head move
	  
	  this.HeadBaseMain.rotateAngleY = f3 / (180F / (float)Math.PI);
      this.HeadBaseMain.rotateAngleX = f4 / (180F / (float)Math.PI);
	  
	  
	 //Legs move
	  float f6 = (180F / (float)Math.PI);
      this.LegLF.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
      this.LegRF.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
      this.LegLB.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
      this.LegRB.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
      this.LegLF.rotateAngleY = 0.0F;
      this.LegRF.rotateAngleY = 0.0F;
      this.LegLB.rotateAngleY = 0.0F;
      this.LegRB.rotateAngleY = 0.0F;
  }

}
