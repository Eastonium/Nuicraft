package eastonium.nuicraft.particle;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class LighstoneFX extends Particle {
	private final ResourceLocation particleRL = new ResourceLocation(NuiCraft.MODID + ":particle/lightstone_particle");

	/**
	 * Construct a new LightstoneFX at the given [x,y,z] position with the given initial velocity.
	 */
	public LighstoneFX(World world, double x, double y, double z/*, double velocityX, double velocityY, double velocityZ*/){
		super(world, x, y, z, 0, 0, 0/* velocityX, velocityY, velocityZ*/);
		particleGravity = Blocks.FIRE.blockParticleGravity;
		particleMaxAge = 100;

		//the vanilla EntityFX constructor added random variation to our starting velocity.  Undo it!
		/*motionX = 0;
		motionY = 0;
		motionZ = 0;*/
		motionX = (rand.nextFloat() - 0.5F) * 0.05F;
		motionY = (rand.nextFloat() - 0.5F) * 0.05F;
		motionZ = (rand.nextFloat() - 0.5F) * 0.05F;
		
        particleMaxAge = 50;//(int)(20.0D / (Math.random() * 0.8D + 0.2D));
        particleScale *= rand.nextFloat() * 0.35F + 0.3F;

		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(particleRL.toString());
		setParticleTexture(sprite);
	}

	@Override
	public int getFXLayer(){
		return 1;
	}

	/**
	 * call once per tick to update the EntityFX position, calculate collisions, remove when max lifetime is reached, etc
	 */
	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		move(motionX, motionY, motionZ);
		
		motionX *= 0.95D;
		motionY *= 0.95D;
		motionZ *= 0.95D;
		this.particleScale *= 0.99F;

//		if(isCollided){
//			this.setExpired();
//		}

		if(particleMaxAge-- <= 0){
			setExpired();
		}
	}
}