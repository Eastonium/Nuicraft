package eastonium.nuicraft.mobs.hoi;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelHoi extends ModelBase
{
	ModelRenderer Tail;
    ModelRenderer LegR1;
    ModelRenderer LegR2;
    ModelRenderer LegL1;
    ModelRenderer LegL2;
    ModelRenderer Body;
    ModelRenderer Head;
  
  public ModelHoi()
  {
	    textureWidth = 32;
	    textureHeight = 32;
	    setTextureOffset("Body.BodyBack", 6, 19);
	    setTextureOffset("Body.BodysideR", 10, 22);
	    setTextureOffset("Body.Body2", 0, 23);
	    setTextureOffset("Body.Body1", 0, 13);
	    setTextureOffset("Body.Mask", 0, 0);
	    setTextureOffset("Body.BodysideL", 10, 22);
	    setTextureOffset("Head.Head", 13, 25);
	    setTextureOffset("Head.EyeR", 0, 0);
	    setTextureOffset("Head.EyeL", 0, 0);
	    
	      Tail = new ModelRenderer(this, 18, 13);
	      Tail.addBox(0F, 0F, -2F, 2, 2, 4);
	      Tail.setRotationPoint(-1F, 19F, 6F);
	      Tail.setTextureSize(32, 32);
	      Tail.mirror = true;
	      setRotation(Tail, 0.5235988F, 0F, 0F);
	      LegR1 = new ModelRenderer(this, 24, 0);
	      LegR1.addBox(0F, 0F, 0F, 2, 4, 2);
	      LegR1.setRotationPoint(-5F, 20F, -2F);
	      LegR1.setTextureSize(32, 32);
	      LegR1.mirror = true;
	      setRotation(LegR1, 0F, 0F, 0F);
	      LegR2 = new ModelRenderer(this, 24, 0);
	      LegR2.addBox(0F, 0F, 0F, 2, 4, 2);
	      LegR2.setRotationPoint(-5F, 20F, 4F);
	      LegR2.setTextureSize(32, 32);
	      LegR2.mirror = true;
	      setRotation(LegR2, 0F, 0F, 0F);
	      LegL1 = new ModelRenderer(this, 24, 0);
	      LegL1.addBox(0F, 0F, 0F, 2, 4, 2);
	      LegL1.setRotationPoint(3F, 20F, -2F);
	      LegL1.setTextureSize(32, 32);
	      LegL1.mirror = true;
	      setRotation(LegL1, 0F, 0F, 0F);
	      LegL2 = new ModelRenderer(this, 24, 0);
	      LegL2.addBox(0F, 0F, 0F, 2, 4, 2);
	      LegL2.setRotationPoint(3F, 20F, 4F);
	      LegL2.setTextureSize(32, 32);
	      LegL2.mirror = true;
	      setRotation(LegL2, 0F, 0F, 0F);
	    Body = new ModelRenderer(this, "Body");
	    Body.setRotationPoint(-4F, 16F, -2F);
	    setRotation(Body, 0F, 0F, 0F);
	    Body.mirror = true;
	      Body.addBox("BodyBack", 2F, 4F, 6F, 4, 2, 2);
	      Body.addBox("BodysideR", 0F, 4F, 0F, 2, 2, 8);
	      Body.addBox("Body2", 2F, 4F, -2F, 4, 2, 5);
	      Body.addBox("Body1", 1F, 0F, 1F, 6, 4, 6);
	      Body.addBox("Mask", 0F, -1F, 0F, 8, 5, 8);
	      Body.addBox("BodysideL", 6F, 4F, 0F, 2, 2, 8);
	    Head = new ModelRenderer(this, "Head");
	    Head.setRotationPoint(-1F, 20F, -3F);
	    setRotation(Head, -0.5235988F, 0F, 0F);
	    Head.mirror = true;
	      Head.addBox("Head", 0F, 0F, -5F, 2, 2, 5);
	      Head.addBox("EyeR", -1F, 0F, -5F, 1, 2, 2);
	      Head.addBox("EyeL", 2F, 0F, -5F, 1, 2, 2);
	      }
  
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Tail.render(f5);
    LegR1.render(f5);
    LegR2.render(f5);
    LegL1.render(f5);
    LegL2.render(f5);
    Body.render(f5);
    Head.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
{
	float f6 = (180F / (float)Math.PI);
    this.LegL1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.LegR1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
    this.LegL2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
    this.LegR2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.LegL1.rotateAngleY = 0.0F;
    this.LegR1.rotateAngleY = 0.0F;
    this.LegL2.rotateAngleY = 0.0F;
    this.LegR2.rotateAngleY = 0.0F;
}

}