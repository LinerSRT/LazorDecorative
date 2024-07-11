package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public abstract class BlockRotatedPillar extends Block {
    @SideOnly(Side.CLIENT)
    protected Icon topBottomIcon;

    public BlockRotatedPillar(int blockId, Material material) {
        super(blockId, material);
    }

    public Icon getTopBottomIcon(int side, int metadata) {
        return topBottomIcon;
    }

    public abstract Icon getSideIcon(int side, int metadata);

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        int baseMetadata = metadata & 3;
        int orientationMetadata;
        switch (side) {
            case 2:
            case 3:
                orientationMetadata = 8;
                break;
            case 4:
            case 5:
                orientationMetadata = 4;
                break;
            default:
                orientationMetadata = 0;
        }
        return baseMetadata | orientationMetadata;
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        int orientation = metadata & 12;
        int type = metadata & 3;
        if (orientation == 0 && (side == 1 || side == 0))
            return getTopBottomIcon(type, metadata);
        if (orientation == 4 && (side == 5 || side == 4))
            return getTopBottomIcon(type, metadata);
        if (orientation == 8 && (side == 2 || side == 3))
            return getTopBottomIcon(type, metadata);
        return getSideIcon(type, metadata);
    }

    @Override
    public int damageDropped(int metadata) {
        return metaExcludeOrientation(metadata);
    }

    public int metaExcludeOrientation(int metadata) {
        return metadata & 3;
    }

    @Override
    protected ItemStack createStackedBlock(int metadata) {
        return new ItemStack(new ItemBlock(blockID - 256), 1, metaExcludeOrientation(metadata));
    }
}
