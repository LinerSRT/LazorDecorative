package ru.liner.decorative.world.generator;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.liner.decorative.blocks.BlockFlowerPlant;

import java.util.Random;

public class FlowerGenerator extends WorldGenerator {
    private final BlockFlowerPlant flowerBlock;

    public FlowerGenerator(BlockFlowerPlant flowerBlock) {
        this.flowerBlock = flowerBlock;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        for (int i = 0; i < 64; i++) {
            int randomPositionX = (x + random.nextInt(8)) - random.nextInt(8);
            int randomPositionY = (y + random.nextInt(4)) - random.nextInt(4);
            int randomPositionZ = (z + random.nextInt(8)) - random.nextInt(8);
            if (world.isAirBlock(randomPositionX, randomPositionY, randomPositionZ) && ((!world.provider.hasNoSky || randomPositionY < 127) && flowerBlock.canBlockStay(world, randomPositionX, randomPositionY, randomPositionZ))) {
                world.setBlock(randomPositionX, randomPositionY, randomPositionZ, flowerBlock.blockID, random.nextInt(8), 2);
            }
        }
        return false;
    }
}
