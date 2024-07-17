package ru.liner.decorative.core;

import com.google.common.collect.Iterators;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.*;

@SuppressWarnings({"DuplicatedCode", "unused"})
public enum Direction {
    DOWN(0, 1, -1, "down", Direction.AxisDirection.NEGATIVE, Direction.Axis.Y, new Vec3i(0, -1, 0)),
    UP(1, 0, -1, "up", Direction.AxisDirection.POSITIVE, Direction.Axis.Y, new Vec3i(0, 1, 0)),
    NORTH(2, 3, 2, "north", Direction.AxisDirection.NEGATIVE, Direction.Axis.Z, new Vec3i(0, 0, -1)),
    SOUTH(3, 2, 0, "south", Direction.AxisDirection.POSITIVE, Direction.Axis.Z, new Vec3i(0, 0, 1)),
    WEST(4, 5, 1, "west", Direction.AxisDirection.NEGATIVE, Direction.Axis.X, new Vec3i(-1, 0, 0)),
    EAST(5, 4, 3, "east", Direction.AxisDirection.POSITIVE, Direction.Axis.X, new Vec3i(1, 0, 0));
    private final int data3d;
    private final int oppositeIndex;
    private final int data2d;
    private final String name;
    private final Direction.Axis axis;
    private final Direction.AxisDirection axisDirection;
    private final Vec3i normal;
    private static final Direction[] VALUES = values();
    private static final Direction[] BY_3D_DATA;
    private static final Direction[] BY_2D_DATA;

    static {
        Direction[] values = VALUES.clone();
        Arrays.sort(values, new Comparator<Direction>() {
            public int compare(Direction direction, Direction direction1) {
                return Integer.compare(direction.data3d, direction1.data3d);
            }
        });
        BY_3D_DATA = new Direction[values.length];
        System.arraycopy(values, 0, BY_3D_DATA, 0, values.length);
        List<Direction> horizontalDirections = new ArrayList<>();
        for (Direction direction : VALUES) {
            if (direction.getAxis().isHorizontal()) {
                horizontalDirections.add(direction);
            }
        }
        Collections.sort(horizontalDirections, new Comparator<Direction>() {
            public int compare(Direction direction, Direction direction1) {
                return Integer.compare(direction.data2d, direction1.data2d);
            }
        });
        BY_2D_DATA = new Direction[horizontalDirections.size()];
        horizontalDirections.toArray(BY_2D_DATA);
    }

    Direction(int data3d, int oppositeIndex, int data2d, String name, Direction.AxisDirection axisDirection, Direction.Axis axis, Vec3i normal) {
        this.data3d = data3d;
        this.data2d = data2d;
        this.oppositeIndex = oppositeIndex;
        this.name = name;
        this.axis = axis;
        this.axisDirection = axisDirection;
        this.normal = normal;
    }

    public static Direction[] orderedByNearest(Entity entity) {
        float f = entity.rotationYaw * ((float) Math.PI / 180F);
        float f1 = -entity.rotationPitch * ((float) Math.PI / 180F);
        float f2 = (float) Math.sin(f);
        float f3 = (float) Math.cos(f);
        float f4 = (float) Math.sin(f1);
        float f5 = (float) Math.cos(f1);
        boolean flag = f4 > 0.0F;
        boolean flag1 = f2 < 0.0F;
        boolean flag2 = f5 > 0.0F;
        float f6 = flag ? f4 : -f4;
        float f7 = flag1 ? -f2 : f2;
        float f8 = flag2 ? f5 : -f5;
        float f9 = f6 * f3;
        float f10 = f8 * f3;
        Direction direction = flag ? EAST : WEST;
        Direction direction1 = flag1 ? UP : DOWN;
        Direction direction2 = flag2 ? SOUTH : NORTH;
        if (f6 > f8) {
            if (f7 > f9) {
                return makeDirectionArray(direction1, direction, direction2);
            } else {
                return f10 > f7 ? makeDirectionArray(direction, direction2, direction1) : makeDirectionArray(direction, direction1, direction2);
            }
        } else if (f7 > f10) {
            return makeDirectionArray(direction1, direction2, direction);
        } else {
            return f9 > f7 ? makeDirectionArray(direction2, direction, direction1) : makeDirectionArray(direction2, direction1, direction);
        }
    }

    private static Direction[] makeDirectionArray(Direction directionX, Direction directionY, Direction directionZ) {
        return new Direction[]{directionX, directionY, directionZ, directionZ.getOpposite(), directionY.getOpposite(), directionX.getOpposite()};
    }

    public int get3DDataValue() {
        return this.data3d;
    }

    public int get2DDataValue() {
        return this.data2d;
    }

    public Direction.AxisDirection getAxisDirection() {
        return this.axisDirection;
    }

    public static Direction getFacingAxis(Entity entity, Direction.Axis axis) {
        Direction direction;
        switch (axis) {
            case X:
                direction = EAST.isFacingAngle(entity.rotationYaw) ? EAST : WEST;
                break;
            case Z:
                direction = SOUTH.isFacingAngle(entity.rotationYaw) ? SOUTH : NORTH;
                break;
            case Y:
                direction = entity.rotationPitch < 0.0F ? UP : DOWN;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return direction;
    }

    public Direction getOpposite() {
        return from3DDataValue(this.oppositeIndex);
    }

    public Direction getClockWise(Direction.Axis axis) {
        Direction direction;
        switch (axis) {
            case X:
                direction = this != WEST && this != EAST ? this.getClockWiseX() : this;
                break;
            case Z:
                direction = this != NORTH && this != SOUTH ? this.getClockWiseZ() : this;
                break;
            case Y:
                direction = this != UP && this != DOWN ? this.getClockWise() : this;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return direction;
    }

    public Direction getCounterClockWise(Direction.Axis axis) {
        Direction direction;
        switch (axis) {
            case X:
                direction = this != WEST && this != EAST ? this.getCounterClockWiseX() : this;
                break;
            case Z:
                direction = this != NORTH && this != SOUTH ? this.getCounterClockWiseZ() : this;
                break;
            case Y:
                direction = this != UP && this != DOWN ? this.getCounterClockWise() : this;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return direction;
    }

    public Direction getClockWise() {
        Direction direction;
        switch (this) {
            case NORTH:
                direction = EAST;
                break;
            case SOUTH:
                direction = WEST;
                break;
            case WEST:
                direction = NORTH;
                break;
            case EAST:
                direction = SOUTH;
                break;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }

        return direction;
    }

    private Direction getClockWiseX() {
        Direction direction;
        switch (this) {
            case DOWN:
                direction = SOUTH;
                break;
            case UP:
                direction = NORTH;
                break;
            case NORTH:
                direction = DOWN;
                break;
            case SOUTH:
                direction = UP;
                break;
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
        }

        return direction;
    }

    private Direction getCounterClockWiseX() {
        Direction direction;
        switch (this) {
            case DOWN:
                direction = NORTH;
                break;
            case UP:
                direction = SOUTH;
                break;
            case NORTH:
                direction = UP;
                break;
            case SOUTH:
                direction = DOWN;
                break;
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
        }

        return direction;
    }

    private Direction getClockWiseZ() {
        Direction direction;
        switch (this) {
            case DOWN:
                direction = WEST;
                break;
            case UP:
                direction = EAST;
                break;
            case NORTH:
            case SOUTH:
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            case WEST:
                direction = UP;
                break;
            case EAST:
                direction = DOWN;
        }

        return direction;
    }

    private Direction getCounterClockWiseZ() {
        Direction direction;
        switch (this) {
            case DOWN:
                direction = EAST;
                break;
            case UP:
                direction = WEST;
                break;
            case NORTH:
            case SOUTH:
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            case WEST:
                direction = DOWN;
                break;
            case EAST:
                direction = UP;
        }

        return direction;
    }

    public Direction getCounterClockWise() {
        Direction direction;
        switch (this) {
            case NORTH:
                direction = WEST;
                break;
            case SOUTH:
                direction = EAST;
                break;
            case WEST:
                direction = SOUTH;
                break;
            case EAST:
                direction = NORTH;
                break;
            default:
                throw new IllegalStateException("Unable to get CCW facing of " + this);
        }

        return direction;
    }

    public int getStepX() {
        return this.normal.getX();
    }

    public int getStepY() {
        return this.normal.getY();
    }

    public int getStepZ() {
        return this.normal.getZ();
    }

    public Vector3f step() {
        return new Vector3f((float) this.getStepX(), (float) this.getStepY(), (float) this.getStepZ());
    }

    public String getName() {
        return this.name;
    }

    public Direction.Axis getAxis() {
        return this.axis;
    }


    public static Direction byName(String name) {
        for (Direction direction : values()) {
            if (direction.name.equals(name))
                return direction;
        }
        return null;
    }

    public static Direction from3DDataValue(int value) {
        return BY_3D_DATA[Math.abs(value % BY_3D_DATA.length)];
    }

    public static Direction from2DDataValue(int value) {
        return BY_2D_DATA[Math.abs(value % BY_2D_DATA.length)];
    }


    public static Direction fromDelta(int x, int y, int z) {
        if (x == 0) {
            if (y == 0) {
                if (z > 0) {
                    return SOUTH;
                }

                if (z < 0) {
                    return NORTH;
                }
            } else if (z == 0) {
                if (y > 0) {
                    return UP;
                }

                return DOWN;
            }
        } else if (y == 0 && z == 0) {
            if (x > 0) {
                return EAST;
            }

            return WEST;
        }

        return null;
    }

    public static Direction fromYRot(double angle) {
        int rotation = (int) (angle / 90 + .5f) & 3;
        return from2DDataValue(rotation);
    }

    public static Direction fromAxisAndDirection(Direction.Axis axis, Direction.AxisDirection axisDirection) {
        Direction direction;
        switch (axis) {
            case X:
                direction = axisDirection == Direction.AxisDirection.POSITIVE ? EAST : WEST;
                break;
            case Z:
                direction = axisDirection == Direction.AxisDirection.POSITIVE ? SOUTH : NORTH;
                break;
            case Y:
                direction = axisDirection == Direction.AxisDirection.POSITIVE ? UP : DOWN;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return direction;
    }

    public float toYRot() {
        return (float) ((this.data2d & 3) * 90);
    }

    public static Direction getRandom(Random random) {
        return VALUES[random.nextInt(VALUES.length)];
    }

    public static Direction getNearest(double x, double y, double z) {
        return getNearest((float) x, (float) y, (float) z);
    }

    public static Direction getNearest(float x, float y, float z) {
        Direction direction = NORTH;
        float f = Float.MIN_VALUE;
        for (Direction direction1 : VALUES) {
            float f1 = x * (float) direction1.normal.getX() + y * (float) direction1.normal.getY() + z * (float) direction1.normal.getZ();
            if (f1 > f) {
                f = f1;
                direction = direction1;
            }
        }
        return direction;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }

    public static Direction get(Direction.AxisDirection axisDirection, Direction.Axis axis) {
        for (Direction direction : VALUES) {
            if (direction.getAxisDirection() == axisDirection && direction.getAxis() == axis) {
                return direction;
            }
        }

        throw new IllegalArgumentException("No such direction: " + axisDirection + " " + axis);
    }

    public Vec3i getNormal() {
        return this.normal;
    }

    public boolean isFacingAngle(float angle) {
        float f = angle * ((float) Math.PI / 180F);
        float f1 = (float) -Math.sin(f);
        float f2 = (float) Math.cos(f);
        return (float) this.normal.getX() * f1 + (float) this.normal.getZ() * f2 > 0.0F;
    }

    public enum Axis {
        X("x") {
            public int choose(int x, int y, int z) {
                return x;
            }

            public double choose(double x, double y, double z) {
                return x;
            }
        },
        Y("y") {
            public int choose(int x, int y, int z) {
                return y;
            }

            public double choose(double x, double y, double z) {
                return y;
            }
        },
        Z("z") {
            public int choose(int x, int y, int z) {
                return z;
            }

            public double choose(double x, double y, double z) {
                return z;
            }
        };

        public static final Direction.Axis[] VALUES = values();
        private final String name;

        Axis(String name) {
            this.name = name;
        }

        public static Direction.Axis byName(String name) {
            for (Axis value : VALUES) {
                if (value.name.equals(name))
                    return value;
            }
            return null;
        }

        public String getName() {
            return this.name;
        }

        public boolean isVertical() {
            return this == Y;
        }

        public boolean isHorizontal() {
            return this == X || this == Z;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static Direction.Axis getRandom(Random random) {
            return VALUES[random.nextInt(VALUES.length)];
        }

        public boolean test(Direction direction) {
            return direction != null && direction.getAxis() == this;
        }

        public Direction.Plane getPlane() {
            Direction.Plane direction$plane;
            switch (this) {
                case X:
                case Z:
                    direction$plane = Direction.Plane.HORIZONTAL;
                    break;
                case Y:
                    direction$plane = Direction.Plane.VERTICAL;
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }

            return direction$plane;
        }

        public String getSerializedName() {
            return this.name;
        }

        public abstract int choose(int x, int y, int z);

        public abstract double choose(double x, double y, double z);
    }

    public enum AxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        private final int step;
        private final String name;
        AxisDirection(int step, String name) {
            this.step = step;
            this.name = name;
        }

        public int getStep() {
            return this.step;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public Direction.AxisDirection opposite() {
            return this == POSITIVE ? NEGATIVE : POSITIVE;
        }
    }

    public enum Plane implements Iterable<Direction> {
        HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}),
        VERTICAL(new Direction[]{Direction.UP, Direction.DOWN}, new Direction.Axis[]{Direction.Axis.Y});

        private final Direction[] faces;
        private final Direction.Axis[] axis;

        Plane(Direction[] faces, Direction.Axis[] axis) {
            this.faces = faces;
            this.axis = axis;
        }

        public Direction getRandomDirection(Random random) {
            return faces[random.nextInt(faces.length)];
        }

        public Direction.Axis getRandomAxis(Random random) {
            return axis[random.nextInt(axis.length)];
        }

        public boolean test(Direction direction) {
            return direction != null && direction.getAxis().getPlane() == this;
        }

        @NotNull
        @Override
        public Iterator<Direction> iterator() {
            return Iterators.forArray(faces);
        }
    }
}