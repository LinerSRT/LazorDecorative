package ru.liner.decorative.enity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import ru.liner.decorative.utils.Vector3;

public abstract class EntityAIMoveToBlock extends EntityAIBase {
    private final EntityCreature theEntity;
    private final float movementSpeed;
    protected int runDelay;
    private int timeoutCounter;
    private int field_179490_f;
    protected Vector3 destinationBlock = Vector3.ZERO;
    private boolean isAboveDestination;
    private final int searchLength;
    private float maximumHomeDistance;
    private Vector3 homePosition = Vector3.ZERO;

    
    protected abstract boolean shouldMoveTo(World world, Vector3 blockPos);

    public EntityAIMoveToBlock(EntityCreature entityCreature, float movementSpeed, float maximumHomeDistance, int searchLength) {
        this.theEntity = entityCreature;
        this.movementSpeed = movementSpeed;
        this.maximumHomeDistance = maximumHomeDistance;
        this.searchLength = searchLength;
        setMutexBits(5);
    }

    @Override 
    
    public boolean shouldExecute() {
        if (runDelay > 0) {
            runDelay--;
            return false;
        }
        runDelay = 200 + theEntity.getRNG().nextInt(200);
        return searchForDestination();
    }

    @Override 
    
    public boolean continueExecuting() {
        return timeoutCounter >= (-field_179490_f) && timeoutCounter <= 1200 && shouldMoveTo(theEntity.worldObj, destinationBlock);
    }

    @Override 
    
    public void startExecuting() {
        theEntity.getNavigator().tryMoveToXYZ(destinationBlock.x + 0.5d, destinationBlock.y + 1, destinationBlock.z + 0.5d, movementSpeed);
        timeoutCounter = 0;
        field_179490_f = theEntity.getRNG().nextInt(theEntity.getRNG().nextInt(1200) + 1200) + 1200;
    }

    @Override 
    
    public void resetTask() {
    }

    @Override 
    
    public void updateTask() {
        if (Vector3.entityCenterPosition(theEntity).distanceSquared(destinationBlock.up()) > 1.0d) {
            isAboveDestination = false;
            timeoutCounter++;
            if (timeoutCounter % 40 == 0) {
                theEntity.getNavigator().tryMoveToXYZ(destinationBlock.x + 0.5d, destinationBlock.y + 1, destinationBlock.z + 0.5d, movementSpeed);
                return;
            }
            return;
        }
        isAboveDestination = true;
        timeoutCounter--;
    }

    
    public boolean getIsAboveDestination() {
        return isAboveDestination;
    }

    private boolean searchForDestination() {
        Vector3 tempPosition = Vector3.entityPosition(theEntity);
        for (int offset = 0; offset <= 1; offset++) {
            for (int x = 0; x < searchLength; x++) {
                for (int z = -x; z <= x; z++) {
                    for (int y = -x; y <= x; y++) {

                        Vector3 newPos = tempPosition.add(z, offset - 1, y);
                        if (isWithinHomeDistanceFromPosition(newPos) && shouldMoveTo(theEntity.worldObj, newPos)) {
                            destinationBlock = newPos;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean isWithinHomeDistanceCurrentPosition() {
        return isWithinHomeDistanceFromPosition(Vector3.entityPosition(theEntity));
    }

    
    public boolean isWithinHomeDistanceFromPosition(Vector3 entityPosition) {
        return maximumHomeDistance == -1.0f || homePosition.distanceSquared(entityPosition) < ((double) (maximumHomeDistance * maximumHomeDistance));
    }

    public void setHomePosAndDistance(Vector3 homePosition, int maximumHomeDistance) {
        this.homePosition = homePosition;
        this.maximumHomeDistance = maximumHomeDistance;
    }

    public Vector3 getHomePosition() {
        return homePosition;
    }

    public float getMaximumHomeDistance() {
        return maximumHomeDistance;
    }
}