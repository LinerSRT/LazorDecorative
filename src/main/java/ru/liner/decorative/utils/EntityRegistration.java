package ru.liner.decorative.utils;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import ru.liner.decorative.DecorativeMod;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class EntityRegistration<E extends Entity, R extends RenderLiving> {
    private static int nextEntityId = 228;
    private final R renderer;
    private final Class<E> entityClass;
    private final String entityName;
    private int tickingRange;
    private int updateFrequency;
    private boolean sendVelocityUpdates;
    private int spawnChance;
    private int spawnMinimumCount;
    private int spawnMaximumCount;
    private EnumCreatureType type;
    private final List<BiomeGenBase> spawnBiomes;

    public static <E extends Entity, R extends RenderLiving> EntityRegistration<E,R> newEntity(Class<E> entityClass, String entityName, R renderer) {
        return new EntityRegistration<>(entityClass, entityName, renderer);
    }

    private EntityRegistration(Class<E> entityClass, String entityName, R renderer) {
        this.entityClass = entityClass;
        this.entityName = entityName;
        this.renderer = renderer;
        this.tickingRange = 100;
        this.updateFrequency = 1;
        this.sendVelocityUpdates = false;
        this.spawnChance = 100;
        this.spawnMinimumCount = 1;
        this.spawnMaximumCount = 1;
        this.type = EnumCreatureType.creature;
        this.spawnBiomes = new ArrayList<>();
    }

    public EntityRegistration<E, R> tickingRange(int tickingRange) {
        this.tickingRange = tickingRange;
        return this;
    }

    public EntityRegistration<E, R> updateFrequency(int updateFrequency) {
        this.updateFrequency = updateFrequency;
        return this;
    }

    public EntityRegistration<E, R> sendVelocityUpdates(boolean sendVelocityUpdates) {
        this.sendVelocityUpdates = sendVelocityUpdates;
        return this;
    }

    public EntityRegistration<E, R> spawnChance(int spawnChance) {
        this.spawnChance = spawnChance;
        return this;
    }

    public EntityRegistration<E, R> spawnMinimumCount(int spawnMinimumCount) {
        this.spawnMinimumCount = spawnMinimumCount;
        return this;
    }

    public EntityRegistration<E, R> spawnMaximumCount(int spawnMaximumCount) {
        this.spawnMaximumCount = spawnMaximumCount;
        return this;
    }

    public EntityRegistration<E, R> type(EnumCreatureType type) {
        this.type = type;
        return this;
    }

    public EntityRegistration<E, R> addBiome(BiomeGenBase biome) {
        this.spawnBiomes.add(biome);
        return this;
    }

    public void register() {
        int entityID = getNextEntityId();
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
        EntityRegistry.registerModEntity(
                entityClass,
                entityName,
                entityID,
                DecorativeMod.INSTANCE,
                tickingRange,
                updateFrequency,
                sendVelocityUpdates
        );
        EntityRegistry.addSpawn(
                String.format("%s.%s", DecorativeMod.MOD_ID, entityName),
                spawnChance,
                spawnMinimumCount,
                spawnMaximumCount,
                type,
                spawnBiomes.toArray(new BiomeGenBase[0])
        );
        EntityList.addMapping(entityClass, entityName, entityID, 10051392, 7555121);
    }


    private static int getNextEntityId() {
        nextEntityId++;
        return nextEntityId;
    }
}