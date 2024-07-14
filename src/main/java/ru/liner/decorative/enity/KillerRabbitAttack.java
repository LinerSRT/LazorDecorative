package ru.liner.decorative.enity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import ru.liner.decorative.enity.ai.EntityAIAttackMelee;
import ru.liner.decorative.enity.passive.EntityRabbit;

public class KillerRabbitAttack extends EntityAIAttackMelee {
    public KillerRabbitAttack(EntityRabbit entityRabbit) {
        super(entityRabbit, 1,true);
    }

    @Override
    protected double func_179512_a(EntityLiving entityLivingBase) {
        return 4.0f + entityLivingBase.width;
    }
}
