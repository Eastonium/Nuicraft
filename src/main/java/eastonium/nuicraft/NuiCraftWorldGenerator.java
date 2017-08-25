package eastonium.nuicraft;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class NuiCraftWorldGenerator implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,net.minecraft.world.gen.IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
		Biome biome = world.getBiome(new BlockPos(chunkX, 0, chunkZ));
		
		//if(world.provider.getDimensionId() == -1) generateEnd(world, random, chunkX * 16, chunkZ * 16);
		//if(world.provider.getDimensionId() == 1) generateNether(world, random, chunkX * 16, chunkZ * 16);
		if(biome != Biomes.HELL && biome != Biomes.SKY) generateOres(world, random, chunkX * 16, chunkZ * 16);
		if(biome.getBiomeName().startsWith("Jungle") || biome == Biomes.SWAMPLAND)	generateReeds(world, random, new BlockPos(chunkX, world.getSeaLevel(), chunkZ));
	}

	//private void generateNether(World world, Random random, int chunkX, int chunkZ){}
	//private void generateEnd(World world, Random random, int chunkX, int chunkZ){}

	private void generateOres(World world, Random random, int chunkX, int chunkZ){
		for(int i = 0; i < 15; i++)
		{
			int xCoord = chunkX + random.nextInt(16);
			int yCoord = random.nextInt(120);
			int zCoord = chunkZ + random.nextInt(16);
			(new WorldGenMinable(NuiCraftBlocks.protodermis_ore.getDefaultState(), 4)).generate(world, random, new BlockPos(xCoord, yCoord, zCoord));
		}
		for(int j = 0; j < 8; j++)
		{
			int xCoord = chunkX + random.nextInt(16);
			int yCoord = random.nextInt(50);
			int zCoord = chunkZ + random.nextInt(16);
			(new WorldGenMinable(NuiCraftBlocks.lightstone_ore.getDefaultState(), 8)).generate(world, random, new BlockPos(xCoord, yCoord, zCoord));
		}
		for(int j = 0; j < 2; j++)
		{
			int xCoord = chunkX + random.nextInt(16);
			int yCoord = random.nextInt(30);
			int zCoord = chunkZ + random.nextInt(16);
			(new WorldGenMinable(NuiCraftBlocks.heatstone_ore.getDefaultState(), 3)).generate(world, random, new BlockPos(xCoord, yCoord, zCoord));
		}
	}

	private void generateReeds(World worldIn, Random rand, BlockPos position){
        //for (int i = 0; i < 20; ++i){
            BlockPos blockpos = position.add(rand.nextInt(16) - rand.nextInt(16), 0, rand.nextInt(16) - rand.nextInt(16));
            System.out.println(blockpos);
            //int f = 0;
            while(!worldIn.isAirBlock(blockpos) && blockpos.getY() < 128/*f < 50*/){
            	blockpos = blockpos.up();
            	//f++;
            }            
            //if (f != 50){
            	BlockPos blockpos1 = blockpos.down();
            	
            	int j = 1 + rand.nextInt(1 + rand.nextInt(2));
            	for (int k = 0; k < j; ++k){
            		if (NuiCraftBlocks.bamboo.canPlaceBlockAt(worldIn, blockpos)){
            			worldIn.setBlockState(blockpos.up(k), NuiCraftBlocks.bamboo.getDefaultState(), 2);
            		}
            	}
            //}
        //}
    }
}