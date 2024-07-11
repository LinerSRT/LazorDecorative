package ru.liner.decorative.blocks;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockCustomSlab extends BlockHalfSlab implements IMultiTexturedBlock {
    protected BaseMultiBlock block;
    protected int subType;

    public BlockCustomSlab(int blockId, BaseMultiBlock block, int subType) {
        super(blockId, false, block.blockMaterial);
        this.block = block;
        this.subType = subType;
        setCreativeTab(block.getCreativeTabToDisplayOn());
        setLightOpacity(255);
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        return block.getIcon(side, subType);
    }

    @Override
    public String typeAt(int metadata) {
        return block.typeAt(subType);
    }

    @Override
    public String localizedAt(int metadata) {
        return String.format("%s %s", BaseMultiBlock.Type.SLABS.type, block.types.get(typeAt(subType)));
    }

    @Override
    public int getTypesCount() {
        return block.getTypesCount();
    }

    @Override
    public int getBlockId() {
        return blockID;
    }


    public String getFullSlabName(int i) {
        return null;
    }

    public int idPicked(World par1World, int par2, int par3, int par4) {
        return blockID;
    }

}
