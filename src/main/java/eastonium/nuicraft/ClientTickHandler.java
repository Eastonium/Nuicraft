package eastonium.nuicraft;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickHandler
{
	//public static boolean maskActivated = false;

	@SubscribeEvent
	public void tickStart(TickEvent.ClientTickEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if (player == null)
		{
			return;
		}
		ItemStack mask = player.inventory.armorItemInSlot(3);
		if (mask != null)
		{
			Minecraft minecraft = Minecraft.getMinecraft();
			//World world = minecraft.theWorld;
			//if(maskActivated){
				if(mask.getItem() == Bionicle.maskMataGold){
					//KAKAMA
					/*if(!minecraft.entityRenderer.isShaderActive()){
						ShaderHelper.chooseShader("phosphor");
					}*/
					//MIRU
					if ((minecraft.currentScreen == null) && (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode())))
					{
						if (player.motionY < 0.15D)
						{
							player.motionY += 0.05D;
						}
					}else// if ((minecraft.currentScreen == null) && !(Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.getKeyCode())))
					{
						if (player.motionY < 0.0D)
						{
							player.motionY /= 1.2D;
						}
					}
					player.motionX = 0;
					player.motionZ = 0;
					
				}else if(mask.getItem() == Bionicle.maskMataKakama || mask.getItem() == Bionicle.maskNuvaKakama)
				{
					/*if(!minecraft.entityRenderer.isShaderActive()){
						ShaderHelper.chooseShader("phosphor");
					}*/
					/*if(player.getFoodStats().getFoodLevel() < player.getFoodStats().getPrevFoodLevel()){
						player.getFoodStats().setFoodLevel(player.getFoodStats().getPrevFoodLevel());
					}*/
				}else if(mask.getItem() == Bionicle.maskMataMiru)
				{
					if ((minecraft.currentScreen == null) && (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode())))
					{
						if (player.motionY < 0.15D)
						{
							player.motionY += 0.05D;
						}
					}else// if ((minecraft.currentScreen == null) && !(Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.getKeyCode())))
					{
						if (player.motionY < 0.0D)
						{
							player.motionY /= 1.2D;
						}
					}
					player.motionX = 0;
					player.motionZ = 0;
				}else if(mask.getItem() == Bionicle.maskNuvaMiru)
				{
					if ((minecraft.currentScreen == null) && (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode())))
					{
						if (player.motionY < 0.30D)
						{
							player.motionY += 0.05D;
						}
						player.motionX = 0;
						player.motionZ = 0;
					}else if ((minecraft.currentScreen == null) && !(Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.getKeyCode())))
					{
						if (player.motionY < 0.0D)
						{
							player.motionY /= 1.2D;
						}
						player.motionX = 0;
						player.motionZ = 0;
					}
				}
				else if(mask.getItem() == Bionicle.maskVahi){
					/*if(!minecraft.entityRenderer.isShaderActive()){
						ShaderHelper.chooseShader("desaturate");
					}*/
				}
			//}//else Minecraft.getMinecraft().entityRenderer.stopUseShader();;
		}
	}

	/*private void handleHelmet(Item item)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayer player = minecraft.thePlayer;
		World world = minecraft.theWorld;



		if (item == SpecialArmorMod.slimeHelmet)
		{
			int l = MathHelper.floor_double(player.posX);
			int i1 = MathHelper.floor_double(player.posY + 1.0D);
			int j1 = MathHelper.floor_double(player.posZ);
			if (world.getBlock(l, i1, j1).getMaterial().isSolid())
			{
				if (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode()))
				{
					player.motionY = 0.1D;
				}
				else
					player.motionY *= 0.6D;
			}
		}
	}

	private void handleBoots(Item item)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayer player = minecraft.thePlayer;
		World world = minecraft.theWorld;

		if (item == SpecialArmorMod.heavyBoots)
		{
			if (player.onGround)
			{
				if (!player.isInWater())
				{
					player.motionX /= 1.950000047683716D;
					player.motionZ /= 1.950000047683716D;
				}
				else {
					player.motionX /= 1.549999952316284D;
					player.motionZ /= 1.549999952316284D;
				}
			}
			if (!player.isInWater())
			{
				if (player.motionY > 0.0D)
				{
					player.motionY /= 1.100000023841858D;
				}
				else
					player.motionY *= 1.100000023841858D;
			}
			else
			{
				if (player.motionY > 0.0D)
				{
					player.motionY /= 50.0D;
				}
				else {
					player.motionY *= 1.200000047683716D;
				}
				if (!player.onGround)
				{
					player.motionX /= 1.200000047683716D;
					player.motionZ /= 1.200000047683716D;
				}
			}
		} else if (item == SpecialArmorMod.jumpBoots)
		{
			if (!hasJetpack)
			{
				if (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode()))
				{
					if (player.motionY > 0.0D)
					{
						player.motionY += 0.03D;
					}
				}
			}
		} else if (item == SpecialArmorMod.hoverBoots)
		{
			int l = MathHelper.floor_double(player.posX);
			int i1 = MathHelper.floor_double(player.posY - 2.0D);
			int i2 = MathHelper.floor_double(player.posY - 1.0D);
			int j1 = MathHelper.floor_double(player.posZ);
			Block l1 = world.getBlock(l, i1, j1);
			Block l2 = world.getBlock(l, i2, j1);
			if ((l2 != Blocks.water) || (l2 != Blocks.flowing_water) || (l2 != Blocks.lava) || (l2 != Blocks.flowing_lava))
			{
				if ((l1 == Blocks.water) || (l1 == Blocks.flowing_water) || (l1 == Blocks.lava) || (l1 == Blocks.flowing_lava))
				{
					player.motionY = 0.0D;
					if (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode()))
					{
						player.motionY += 0.01D;
					}
				}
				if ((Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode())) && (player.motionY <= 0.0D))
				{
					if (this.hoverTime > 0)
					{
						player.motionY = 0.0D;
						this.hoverTime -= 1;
					}
				}
			}
			else {
				player.motionY = 0.051D;
			}
			if (player.onGround)
			{
				this.hoverTime = 20;
			}

		}
		else if (item == SpecialArmorMod.skates)
		{
			int l = MathHelper.floor_double(player.posX);
			int i1 = MathHelper.floor_double(player.posY - 2.0D);
			int j1 = MathHelper.floor_double(player.posZ);
			if (player.onGround)
			{
				if (world.getBlock(l, i1, j1) == Blocks.ice)
				{
					Blocks.ice.slipperiness = 0.6F;
					player.motionX *= 1.100000023841858D;
					player.motionZ *= 1.100000023841858D;
				}
				else {
					player.motionX *= 0.699999988079071D;
					player.motionZ *= 0.699999988079071D;
				}
			}
		}
		else {
			Blocks.ice.slipperiness = 0.98F;
		}

		if (item == SpecialArmorMod.doubleJumpBoots)
		{
			if (!player.onGround)
			{
				if ((Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode())) && (!jumpKeyDown) && (this.canJump == 1))
				{
					player.motionY = 0.4199999868869782D;
					this.canJump = 2;
				}
				if ((Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode())) && (this.canJump == 0))
				{
					this.canJump = 1;
				}
			}
			else {
				this.canJump = 0;
				if (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode()))
				{
					this.canJump = 1;
				}
			}
		}
	}

	private void handleLegs(Item item)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayer player = minecraft.thePlayer;
		World world = minecraft.theWorld;

		if (mask.getItem() == Bionicle.maskMataMiru || (mask.getUnlocalizedName().startsWith("Mata") && mask.getItemDamage() == 0))
		{
			int l = MathHelper.floor_double(player.posX);
			int i1 = MathHelper.floor_double(player.posY - 2.0D);
			int j1 = MathHelper.floor_double(player.posZ);
			if ((Keyboard.isKeyDown(minecraft.gameSettings.keyBindForward.getKeyCode())) || (Keyboard.isKeyDown(minecraft.gameSettings.keyBindBack.getKeyCode())) || (Keyboard.isKeyDown(minecraft.gameSettings.keyBindLeft.getKeyCode())) || (Keyboard.isKeyDown(minecraft.gameSettings.keyBindRight.getKeyCode())))
			{
				if (world.getBlock(l, i1, j1) != Blocks.ice)
				{
					if (player.onGround)
					{
						if (!player.isInWater())
						{
							player.motionX *= 1.149999976158142D;
							player.motionZ *= 1.149999976158142D;
						}
					}
				}
			}
		}
	}

	private void handlePlate(Item item)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayer player = minecraft.thePlayer;
		World world = minecraft.theWorld;

		if (item == SpecialArmorMod.cape)
		{
			if (player.motionY < 0.0D)
			{
				player.motionY /= 1.600000023841858D;
			}
		} else if (item == SpecialArmorMod.firePlate)
		{
			if ((minecraft.currentScreen == null) && (Keyboard.isKeyDown(fireKey)) && (!fireKeyDown))
			{
				this.fireTimer = 1;
			}
			if (this.fireTimer > 0)
			{
				this.fireTimer += 1;
				if (this.fireTimer > 100)
				{
					this.fireTimer = 100;
				}
			}
			if ((minecraft.currentScreen == null) && (!Keyboard.isKeyDown(fireKey)) && (fireKeyDown))
			{
				int x = MathHelper.floor_double(player.posX);
				int y = MathHelper.floor_double(player.posY - 1.0D);
				int z = MathHelper.floor_double(player.posZ);
				sendFire(x, y, z, this.fireTimer);
				this.fireTimer = 0;
			}
		} else if (item == SpecialArmorMod.tntPlate)
		{
			if ((minecraft.currentScreen == null) && (!Keyboard.isKeyDown(tntKey)) && (tntKeyDown))
			{
				sendExplosion((int)player.posX, (int)player.posY - 1, (int)player.posZ);
			}
		} else if (item == SpecialArmorMod.jetpack)
		{
			if ((minecraft.currentScreen == null) && (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode())))
			{
				if (player.motionY < 1.0D)
				{
					player.motionY += 0.05D;
				}
				world.spawnParticle("smoke", player.posX, player.posY - 1.0D, player.posZ, 0.0D, 0.0D, 0.0D);
			}
			if (player.motionY < 0.0D)
			{
				player.motionY /= 1.049999952316284D;
			}
			hasJetpack = true;
			System.out.println(player.motionY);
		}
		else {
			hasJetpack = false;
		}
	}

	private void sendExplosion(int x, int y, int z)
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		int[] dataInt = { 1, x, y, z };
		try
		{
			for (int i = 0; i < dataInt.length; i++)
			{
				data.writeInt(dataInt[i]);
			}
		}
		catch (IOException e)
		{
		}

		FMLProxyPacket pkt = new FMLProxyPacket(Unpooled.wrappedBuffer(bytes.toByteArray()), SpecialArmorMod.channelName);
		SpecialArmorMod.channel.sendToServer(pkt);
	}

	private void sendFire(int x, int y, int z, int fireTimer)
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		int[] dataInt = { 0, x, y, z, fireTimer };
		try
		{
			for (int i = 0; i < dataInt.length; i++)
			{
				data.writeInt(dataInt[i]);
			}s
		}
		catch (IOException e)
		{
		}

		FMLProxyPacket pkt = new FMLProxyPacket(Unpooled.wrappedBuffer(bytes.toByteArray()), SpecialArmorMod.channelName);
		SpecialArmorMod.channel.sendToServer(pkt);
	}*/
}