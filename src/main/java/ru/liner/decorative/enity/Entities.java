package ru.liner.decorative.enity;

import net.minecraft.world.biome.BiomeGenBase;
import ru.liner.decorative.enity.passive.EntityRabbit;
import ru.liner.decorative.render.entity.RenderRabbit;
import ru.liner.decorative.utils.EntityRegistration;

public class Entities {

    public static void init() {
        EntityRegistration
                .newEntity(EntityRabbit.class, "Rabbit", new RenderRabbit())
                .sendVelocityUpdates(true)
                .spawnMaximumCount(5)
                .addBiome(BiomeGenBase.forest)
                .addBiome(BiomeGenBase.forestHills)
                .addBiome(BiomeGenBase.plains)
                .addBiome(BiomeGenBase.extremeHills)
                .addBiome(BiomeGenBase.extremeHillsEdge)
                .register();
    }


}
