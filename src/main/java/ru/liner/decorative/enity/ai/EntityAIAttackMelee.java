package ru.liner.decorative.enity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class EntityAIAttackMelee extends EntityAIBase {
    World worldObj;
    protected EntityCreature attacker;
    int attackTick;
    float speedTowardsTarget;
    boolean longMemory;
    PathEntity entityPathEntity;
    private int delayCounter;
    private double targetX;
    private double targetY;
    private double targetZ;
    protected final int field_188493_g = 20;

    public EntityAIAttackMelee(EntityCreature entityCreature, float speedTowardsTarget, boolean longMemory) {
        this.attacker = entityCreature;
        this.worldObj = entityCreature.worldObj;
        this.speedTowardsTarget = speedTowardsTarget;
        this.longMemory = longMemory;
        setMutexBits(3);
    }

    @Override

    public boolean shouldExecute() {
        EntityLiving A = this.attacker.getAttackTarget();
        if (A == null || !A.isEntityAlive()) {
            return false;
        }
        this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(A);
        return this.entityPathEntity != null;
    }

    @Override

    public boolean continueExecuting() {
        EntityLiving A = this.attacker.getAttackTarget();
        if (A == null || !A.isEntityAlive()) {
            return false;
        }
        if (!this.longMemory) {
            if (this.attacker.getNavigator().noPath()) {
                return false;
            }
            return true;
        }
        if (!(A instanceof EntityPlayer)) {
            return true;
        }
        if (((EntityPlayer) A).capabilities.isCreativeMode) {
            return false;
        }
        return true;
    }

    @Override

    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.delayCounter = 0;
    }

    @Override

    public void resetTask() {
        EntityLiving A = this.attacker.getAttackTarget();
        if ((A instanceof EntityPlayer) && (((EntityPlayer) A).capabilities.isCreativeMode)) {
            this.attacker.setAttackTarget((EntityLiving) null);
        }
        this.attacker.getNavigator().clearPathEntity();
    }

    @Override

    public void updateTask() {
        EntityLiving A = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(A, 30.0f, 30.0f);
        double e = this.attacker.getDistanceSq(A.posX, A.boundingBox.minY, A.posZ);
        double func_179512_a = func_179512_a(A);
        this.delayCounter--;
        if ((this.longMemory || this.attacker.getEntitySenses().canSee(A)) && this.delayCounter <= 0 && ((this.targetX == 0.0d && this.targetY == 0.0d && this.targetZ == 0.0d) || A.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0d || this.attacker.getRNG().nextFloat() < 0.05f)) {
            this.targetX = A.posX;
            this.targetY = A.boundingBox.minY;
            this.targetZ = A.posZ;
            this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
            if (e > 1024.0d) {
                this.delayCounter += 10;
            } else if (e > 256.0d) {
                this.delayCounter += 5;
            }
            if (!this.attacker.getNavigator().tryMoveToEntityLiving(A, this.speedTowardsTarget)) {
                this.delayCounter += 15;
            }
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
        if (e <= func_179512_a && this.attackTick <= 0) {
            this.attackTick = 20;
            this.attacker.swingItem();
            this.attacker.attackEntityAsMob(A);
        }
    }


    protected double func_179512_a(EntityLiving EntityLiving) {
        return (this.attacker.width * 2.0f * this.attacker.width * 2.0f) + EntityLiving.width;
    }
}