package ru.liner.decorative.enity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import ru.liner.decorative.enity.*;
import ru.liner.decorative.utils.Vector3;


public class EntityRabbit extends EntityAnimal {
    private int rabbitType;
    private int jumpDuration;
    private int jumpTicks;
    public boolean isJumping;
    private int moveTypeDuration;
    private int carrotTicks;

    public EntityRabbit(World world) {
        super(world);
        this.texture = "/textures/entity/rabbit/gold.png";
        this.rabbitType = 0;
        this.jumpDuration = 0;
        this.jumpTicks = 0;
        this.isJumping = false;
        this.moveTypeDuration = 0;
        this.carrotTicks = 0;
        setSize(0.4f, 0.5f);
        this.jumpHelper = new RabbitJumpHelper(this);
        this.moveHelper = new RabbitMoveHelper(this);
        setMovementSpeed(0.0f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new RabbitPanic(this, 0.2f));
        this.tasks.addTask(2, new EntityAIMate(this, 0.23F));
        this.tasks.addTask(3, new EntityAITempt(this, 0.18F, Item.carrot.itemID, true));
        this.tasks.addTask(3, new EntityAITempt(this, 0.18F, Item.goldenCarrot.itemID, true));
        this.tasks.addTask(3, new EntityAITempt(this, 0.18F, Block.plantYellow.blockID, true));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0f, 2.2f, 2.2f));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityWolf.class, 10.0f, 2.2f, 2.2f));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityMob.class, 4.0f, 2.2f, 2.2f));
        this.tasks.addTask(5, new RabbitEatCarrot(this));
        this.tasks.addTask(6, new EntityAIWander(this, 0.23F));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.jumpTicks != this.jumpDuration) {
            this.jumpTicks++;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            setJumping(false);
        }
    }

    @Override
    protected void jump() {
        this.motionY = getJumpUpwardMotion();
        if (this.isPotionActive(Potion.jump)) {
            this.motionY += (float) (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
        }
        if (this.isSprinting()) {
            float f = this.rotationYaw * 0.017453292F;
            this.motionX -= MathHelper.sin(f) * 0.2F;
            this.motionZ += MathHelper.cos(f) * 0.2F;
        }
        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);


        if (this.moveHelper.getSpeed() > 0.0d && (this.motionX * this.motionX) + (this.motionZ * this.motionZ) < 0.01d) {
            moveFlying(0.0f, 1.0f, 0.1f);
        }
        if (!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte) 1);
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        EntityRabbit child = new EntityRabbit(this.worldObj);
        int type = getRabbitTypeForChild();
        if (this.rand.nextInt(20) != 0) {
            if ((entityAgeable instanceof EntityRabbit) && this.rand.nextBoolean()) {
                type = ((EntityRabbit) entityAgeable).getRabbitType();
            } else {
                type = getRabbitType();
            }
        }
        child.setRabbitType(type);
        return child;
    }

    public float getJumpCompletion(float delta) {
        if (this.jumpTicks == 0) {
            return 0.0f;
        }
        return (this.jumpDuration + delta) / this.jumpTicks;
    }

    public void setMovementSpeed(float speed) {
        getNavigator().setSpeed(speed);
        this.moveHelper.setMoveTo(this.moveHelper.posX, this.moveHelper.posY, this.moveHelper.posZ, speed);
    }

    @Override
    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            playSound(getJumpSound(), getSoundVolume(), (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f) + 1.0f) * 0.8f);
        }
    }


    public void startJumping() {
        setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }


    @Override
    public void updateAITasks() {
        super.updateAITasks();
        EntityLiving target;

        if (this.moveTypeDuration > 0) {
            this.moveTypeDuration--;
        }
        if (this.carrotTicks > 0) {
            this.carrotTicks -= this.rand.nextInt(3);
            if (this.carrotTicks < 0) {
                this.carrotTicks = 0;
            }
        }

        if (this.onGround) {
            if (!this.isJumping) {
                setJumping(false);
                updateMoveTypeDuration();
            }

            if (getRabbitType() == 99 && this.moveTypeDuration == 0 && (target = getAttackTarget()) != null && getDistanceSqToEntity(target) < 16.0d) {
                calculateRotationYaw(target.posX, target.posZ);
                this.moveHelper.setMoveTo(target.posX, target.posY, target.posZ, this.moveHelper.getSpeed());
                startJumping();
                this.isJumping = true;
            }

            RabbitJumpHelper jumpHelper = (RabbitJumpHelper) this.jumpHelper;
            if (!jumpHelper.isJumping()) {
                if (this.moveHelper.isUpdating() && this.moveTypeDuration == 0) {
                    PathEntity path = this.navigator.getPath();
                    Vector3 targetPos = new Vector3(this.moveHelper.posX, this.moveHelper.posY, this.moveHelper.posZ);
                    if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                        targetPos = new Vector3(path.getPosition(this));
                    }
                    calculateRotationYaw(targetPos.x, targetPos.z);
                    startJumping();
                }
            } else if (!jumpHelper.isEndOfJump()) {
                continueJump();
            }
        }

        this.isJumping = this.onGround;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (getRabbitType() == 99) {
            playSound(getHurtSound(), 1.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f) + 1.0f);
            return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 8);
        }
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, int amount) {
        if (isEntityInvulnerable()) {
            return false;
        }
        return super.attackEntityFrom(damageSource, amount);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack != null && isRabbitBreedingItem(stack);
    }

    private boolean isRabbitBreedingItem(ItemStack item) {
        return item.itemID == net.minecraft.item.Item.carrot.itemID || item.itemID == net.minecraft.item.Item.goldenCarrot.itemID || item.itemID == Block.plantYellow.blockID;
    }

    public int getRabbitType() {
        return rabbitType;
    }

    public void setRabbitType(int type) {
        if (type == 99) {
            this.tasks.addTask(4, new KillerRabbitAttack(this));
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16, 0, true));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWolf.class, 16, 0, true));
        }
        this.rabbitType = type;
        //this.dataWatcher.setObjectWatched(type);
    }


    @Override
    public ItemStack getCurrentArmor(int par1) {
        return super.getCurrentArmor(par1);
    }

    @Override
    protected String getLivingSound() {
        return "none";
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("RabbitType", getRabbitType());
        compound.setInteger("MoreCarrotTicks", this.carrotTicks);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setRabbitType(compound.getInteger("RabbitType"));
        this.carrotTicks = compound.getInteger("MoreCarrotTicks");
    }

    private String getJumpSound() {
        return "none";
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    private void updateMoveTypeDuration() {
        if (this.moveTypeDuration == 0) {
            this.moveTypeDuration = 10;
        }
    }

    private void continueJump() {
        ((RabbitJumpHelper) this.jumpHelper).setJumping(true);
    }

    private int getRabbitTypeForChild() {
        return this.rand.nextInt(6);
    }

    private void calculateRotationYaw(double x, double z) {
        this.rotationYaw = (float) (Math.atan2(z - this.posZ, x - this.posX) * (180d / Math.PI)) - 90.0f;
    }

    protected float getJumpUpwardMotion() {
        if (this.isCollidedHorizontally) {
            return 0.5f;
        }
        if (this.moveHelper.isUpdating() && this.moveHelper.posX > this.posY + 0.5d) {
            return 0.5f;
        }
        PathEntity path = this.navigator.getPath();
        if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength() && path.getPosition(this).yCoord > this.posY) {
            return 0.5f;
        }
        if (this.moveHelper.getSpeed() <= 0.6d) {
            return 0.2f;
        }
        return 0.3f;
    }

    public boolean isCarrotEaten() {
        return carrotTicks == 0;
    }
}
