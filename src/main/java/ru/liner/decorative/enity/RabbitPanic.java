package ru.liner.decorative.enity;

import net.minecraft.entity.ai.EntityAIPanic;
import ru.liner.decorative.enity.passive.EntityRabbit;

public class RabbitPanic extends EntityAIPanic {
    private EntityRabbit theEntity;
    private float speed;
    public RabbitPanic(EntityRabbit entityRabbit, float speed) {
        super(entityRabbit, speed);
        this.theEntity = entityRabbit;
        this.speed = speed;
    }

    @Override
    public void updateTask() {
        super.updateTask();
        this.theEntity.setMovementSpeed(speed);
    }
}

