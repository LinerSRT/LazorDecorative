package ru.liner.decorative.enity;

import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import ru.liner.decorative.enity.passive.EntityRabbit;

public class RabbitMoveHelper extends EntityMoveHelper {
    private EntityRabbit theEntity;
    private float movementSpeed;

    public RabbitMoveHelper(EntityRabbit entityRabbit) {
        super(entityRabbit);
        this.theEntity = entityRabbit;
    }

    @Override
    public void onUpdateMoveHelper() {
        if (this.theEntity.onGround && !this.theEntity.isJumping && !((RabbitJumpHelper) this.theEntity.jumpHelper).isJumping()) {
            this.theEntity.setMovementSpeed(0);
        } else if (isUpdating()) {
            this.theEntity.setMovementSpeed(this.movementSpeed);
        }
        super.onUpdateMoveHelper();
    }

    @Override
    public void setMoveTo(double v, double v1, double v2, float v3) {
        if (this.theEntity.isInWater()) {
            v3 = 1.5f;
        }
        super.setMoveTo(v, v1, v2, v3);
        if (v3 > 0.0d) {
            this.movementSpeed = v3;
        }
    }
}
