package ru.liner.decorative.enity;

import net.minecraft.entity.ai.EntityJumpHelper;
import ru.liner.decorative.enity.passive.EntityRabbit;

public class RabbitJumpHelper extends EntityJumpHelper {
    private EntityRabbit theEntity;
    private boolean endOfJump;
    private boolean isJumping = false;

    public RabbitJumpHelper(EntityRabbit entityRabbit) {
        super(entityRabbit);
        this.endOfJump = false;
        this.theEntity = entityRabbit;
    }


    public boolean isEndOfJump() {
        return this.endOfJump;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    @Override
    public void setJumping() {

    }

    public void setEndOfJump(boolean endOfJump) {
        this.endOfJump = endOfJump;
    }

    @Override
    public void doJump() {
        if (isJumping) {
            this.theEntity.setJumping(true);
            isJumping = false;
        }
    }
}
