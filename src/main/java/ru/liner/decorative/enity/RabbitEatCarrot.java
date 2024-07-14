package ru.liner.decorative.enity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;
import ru.liner.decorative.enity.ai.EntityAIMoveToBlock;
import ru.liner.decorative.enity.passive.EntityRabbit;
import ru.liner.decorative.utils.Vector3;

public class RabbitEatCarrot extends EntityAIMoveToBlock {
    private final EntityRabbit rabbit;
    private boolean isCarrotWasEaten;

    /* renamed from: e */
    private boolean field_179499_e;


    public RabbitEatCarrot(EntityCreature entityCreature) {
        super(entityCreature, 0.699999988079071f, 1, 16);
        this.field_179499_e = false;
        this.rabbit = (EntityRabbit) entityCreature;

    }


    @Override // net.minecraft.entity.ai.EntityAIMoveToBlock, net.minecraft.entity.ai.EntityAIBase
    /* renamed from: a */
    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!this.rabbit.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                return false;
            }
            this.field_179499_e = false;
            this.isCarrotWasEaten = this.rabbit.isCarrotEaten();
        }
        return super.shouldExecute();
    }
    @Override // net.minecraft.entity.ai.EntityAIMoveToBlock, net.minecraft.entity.ai.EntityAIBase
    /* renamed from: b */
    public boolean continueExecuting() {
        return this.field_179499_e && super.continueExecuting();
    }


    @Override // net.minecraft.entity.ai.EntityAIMoveToBlock, net.minecraft.entity.ai.EntityAIBase
    /* renamed from: e */
    public void updateTask() {
        super.updateTask();
        this.rabbit.getLookHelper().setLookPosition(this.destinationBlock.x + 0.5d, this.destinationBlock.y + 1, this.destinationBlock.z + 0.5d, 10.0f, this.rabbit.getVerticalFaceSpeed());
        if (getIsAboveDestination()) {
            World world = this.rabbit.worldObj;
            Vector3 up = this.destinationBlock.up();
            Block block = up.asBlock();
            if (this.field_179499_e && (block instanceof BlockCarrot)) {
                //BlockCarrot carrot = (BlockCarrot) block;
                //carrot.
                //Integer num = (Integer) blockState.getValue(BlockCarrot.AGE);
                //if (num.intValue() == 0) {
                //    world.setBlockState(up, Blocks.air.getDefaultState(), 2);
                //    world.destroyBlock(up, true);
                //} else {
                //    world.setBlockState(up, blockState.withProperty(BlockCarrot.AGE, Integer.valueOf(num.intValue() - 1)), 2);
                //    world.playAuxSFX(2001, up, Block.getStateId(blockState));
                //}
                //this.rabbit.createEatingParticles();
            }
            this.field_179499_e = false;
            this.runDelay = 10;
        }
    }

    @Override // net.minecraft.entity.ai.EntityAIMoveToBlock
    /* renamed from: a */
    protected boolean shouldMoveTo(World world, Vector3 blockPos) {
        if (blockPos.blockId() == Block.grass.blockID && this.isCarrotWasEaten && !this.field_179499_e) {
            Block block = blockPos.up().asBlock();
            if ((block instanceof BlockCarrot) && blockPos.up().blockMetadata() >= 7) {
                this.field_179499_e = true;
                return true;
            }
            return false;
        }
        return false;
    }

}
