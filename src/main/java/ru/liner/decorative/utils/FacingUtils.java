package ru.liner.decorative.utils;

import net.minecraft.util.EnumFacing;

public class FacingUtils {
    public static EnumFacing direction2Facing(int direction){
        switch (direction){
            case 0:
            default:
                return EnumFacing.SOUTH;
            case 1:
                return EnumFacing.WEST;
            case 2:
                return EnumFacing.NORTH;
            case 3:
                return EnumFacing.EAST;
        }
    }

    public static EnumFacing metadata2Facing(int metadata){
        return direction2Facing(metadata & 3);
    }

    public static int facing2Direction(EnumFacing facing){
        switch (facing){
            case SOUTH:
            default:
                return 0;
            case WEST:
                return 1;
            case NORTH:
                return 2;
            case EAST:
                return 3;
        }
    }

    public static int facing2Metadata(EnumFacing facing, int metadata){
        return metadata | facing2Direction(facing);
    }
}
