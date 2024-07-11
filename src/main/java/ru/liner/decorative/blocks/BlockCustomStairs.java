package ru.liner.decorative.blocks;
import net.minecraft.block.BlockStairs;

public class BlockCustomStairs extends BlockStairs implements IMultiTexturedBlock{
    protected BaseMultiBlock block;
    protected int subType;
    public BlockCustomStairs(int blockId, BaseMultiBlock block, int subType) {
        super(blockId, block, subType);
        this.block = block;
        this.subType = subType;
    }

    @Override
    public String typeAt(int metadata) {
        return block.typeAt(subType);
    }

    @Override
    public String localizedAt(int metadata) {
        return String.format("%s %s", BaseMultiBlock.Type.STAIRS.type, block.types.get(typeAt(subType)));
    }

    @Override
    public int getTypesCount() {
        return block.getTypesCount();
    }

    @Override
    public int getBlockId() {
        return blockID;
    }
}
