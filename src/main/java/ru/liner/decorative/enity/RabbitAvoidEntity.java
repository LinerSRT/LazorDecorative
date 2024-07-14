package ru.liner.decorative.enity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import ru.liner.decorative.enity.passive.EntityRabbit;

public class RabbitAvoidEntity extends EntityAIAvoidEntity {
    private EntityRabbit entityRabbit;
    public RabbitAvoidEntity(EntityCreature entityCreature, Class creatureClass, float distanceFromEntity, float farSpeed, float nearSpeed) {
        super(entityCreature, creatureClass, distanceFromEntity, farSpeed, nearSpeed);
        entityRabbit = (EntityRabbit) entityCreature;
    }

    @Override
    public boolean shouldExecute() {
        return entityRabbit.getRabbitType() != 99 && super.shouldExecute();
    }
}
