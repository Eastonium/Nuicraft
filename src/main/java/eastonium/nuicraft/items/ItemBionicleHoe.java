package eastonium.nuicraft.items;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBionicleHoe extends ItemHoe {
	public ItemBionicleHoe(String name, ToolMaterial par1){
		super(par1);
		setCreativeTab(NuiCraft.bio_tool_tab);
		setUnlocalizedName(NuiCraft.MODID + "." + name);
		setRegistryName(name);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase playerIn) {
        Block block = state.getBlock();
        if (state.getMaterial() != Material.LEAVES && block != Blocks.WEB && block != Blocks.TALLGRASS && block != Blocks.VINE && block != Blocks.TRIPWIRE && block != Blocks.WOOL && !(block instanceof net.minecraftforge.common.IShearable)){
            return super.onBlockDestroyed(stack, worldIn, state, pos, playerIn);
        }else{
            return true;
        }
    }

	@Override
	public boolean canHarvestBlock(IBlockState blockIn){
        return blockIn == Blocks.WEB || blockIn == Blocks.REDSTONE_WIRE || blockIn == Blocks.TRIPWIRE;
    }

	@Override
    public float getStrVsBlock(ItemStack stack, IBlockState block){
        return block != Blocks.WEB && block.getMaterial() != Material.LEAVES ? (block == Blocks.WOOL ? 5.0F : super.getStrVsBlock(stack, block)) : 15.0F;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand){
        if (entity.world.isRemote){
            return false;
        }
        if (entity instanceof net.minecraftforge.common.IShearable){
            net.minecraftforge.common.IShearable target = (net.minecraftforge.common.IShearable)entity;
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
            if (target.isShearable(itemstack, entity.world, pos)){
                java.util.List<ItemStack> drops = target.onSheared(itemstack, entity.world, pos,
                        net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, itemstack));
                java.util.Random rand = new java.util.Random();
                for(ItemStack stack : drops){
                    net.minecraft.entity.item.EntityItem ent = entity.entityDropItem(stack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
                itemstack.damageItem(1, entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, net.minecraft.entity.player.EntityPlayer player){
        if (player.world.isRemote || player.capabilities.isCreativeMode){
            return false;
        }
        Block block = player.world.getBlockState(pos).getBlock();
        if (block instanceof net.minecraftforge.common.IShearable){
            net.minecraftforge.common.IShearable target = (net.minecraftforge.common.IShearable)block;
            if (target.isShearable(itemstack, player.world, pos)){
                java.util.List<ItemStack> drops = target.onSheared(itemstack, player.world, pos,
                        net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, itemstack));
                java.util.Random rand = new java.util.Random();

                for(ItemStack stack : drops){
                    float f = 0.7F;
                    double d  = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d1 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d2 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    net.minecraft.entity.item.EntityItem entityitem = new net.minecraft.entity.item.EntityItem(player.world, (double)pos.getX() + d, (double)pos.getY() + d1, (double)pos.getZ() + d2, stack);
                    entityitem.setDefaultPickupDelay();
                    player.world.spawnEntity(entityitem);
                }
                itemstack.damageItem(1, player);
                player.addStat(net.minecraft.stats.StatList.getBlockStats(block));
            }
        }
        return false;
    }
}
