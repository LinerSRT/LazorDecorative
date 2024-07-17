package ru.liner.decorative.core;

import org.joml.Vector3f;


public class BlockPos extends Vec3i {
    public static final BlockPos ZERO = new BlockPos(0, 0, 0);

    public BlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public BlockPos(Vec3i vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public static BlockPos containing(double x, double y, double z) {
        return new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    public static BlockPos containing(Position position) {
        return containing(position.x(), position.y(), position.z());
    }

    public static long getFlatIndex(long index) {
        return index & -16L;
    }

    public BlockPos offset(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new BlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    public Vector3f getCenter() {
        return new Vector3f(getX() + .5f, getY() + .5f, getZ() + .5f);
    }

    public BlockPos offset(Vec3i vector) {
        return this.offset(vector.getX(), vector.getY(), vector.getZ());
    }

    public BlockPos subtract(Vec3i vector) {
        return this.offset(-vector.getX(), -vector.getY(), -vector.getZ());
    }

    public BlockPos multiply(int value) {
        if (value == 1) {
            return this;
        } else {
            return value == 0 ? ZERO : new BlockPos(this.getX() * value, this.getY() * value, this.getZ() * value);
        }
    }

    public BlockPos above() {
        return this.relative(Direction.UP);
    }

    public BlockPos above(int offset) {
        return this.relative(Direction.UP, offset);
    }

    public BlockPos below() {
        return this.relative(Direction.DOWN);
    }

    public BlockPos below(int offset) {
        return this.relative(Direction.DOWN, offset);
    }

    public BlockPos north() {
        return this.relative(Direction.NORTH);
    }

    public BlockPos north(int offset) {
        return this.relative(Direction.NORTH, offset);
    }

    public BlockPos south() {
        return this.relative(Direction.SOUTH);
    }

    public BlockPos south(int offset) {
        return this.relative(Direction.SOUTH, offset);
    }

    public BlockPos west() {
        return this.relative(Direction.WEST);
    }

    public BlockPos west(int offset) {
        return this.relative(Direction.WEST, offset);
    }

    public BlockPos east() {
        return this.relative(Direction.EAST);
    }

    public BlockPos east(int offset) {
        return this.relative(Direction.EAST, offset);
    }

    public BlockPos relative(Direction direction) {
        return new BlockPos(this.getX() + direction.getStepX(), this.getY() + direction.getStepY(), this.getZ() + direction.getStepZ());
    }

    public BlockPos relative(Direction direction, int offset) {
        return offset == 0 ? this : new BlockPos(this.getX() + direction.getStepX() * offset, this.getY() + direction.getStepY() * offset, this.getZ() + direction.getStepZ() * offset);
    }

    public BlockPos relative(Direction.Axis axis, int offset) {
        if (offset == 0) {
            return this;
        } else {
            int i = axis == Direction.Axis.X ? offset : 0;
            int j = axis == Direction.Axis.Y ? offset : 0;
            int k = axis == Direction.Axis.Z ? offset : 0;
            return new BlockPos(this.getX() + i, this.getY() + j, this.getZ() + k);
        }
    }

    public BlockPos rotate(Rotation rotation) {
        switch (rotation) {
            case NONE:
            default:
                return this;
            case CLOCKWISE_90:
                return new BlockPos(-this.getZ(), this.getY(), this.getX());
            case CLOCKWISE_180:
                return new BlockPos(-this.getX(), this.getY(), -this.getZ());
            case COUNTERCLOCKWISE_90:
                return new BlockPos(this.getZ(), this.getY(), -this.getX());
        }
    }

    public BlockPos cross(Vec3i vector) {
        return new BlockPos(this.getY() * vector.getZ() - this.getZ() * vector.getY(), this.getZ() * vector.getX() - this.getX() * vector.getZ(), this.getX() * vector.getY() - this.getY() * vector.getX());
    }

    public BlockPos atY(int y) {
        return new BlockPos(this.getX(), y, this.getZ());
    }

    public BlockPos immutable() {
        return this;
    }

    public BlockPos.MutableBlockPos mutable() {
        return new BlockPos.MutableBlockPos(this.getX(), this.getY(), this.getZ());
    }


    public static class MutableBlockPos extends BlockPos {
        public MutableBlockPos() {
            this(0, 0, 0);
        }

        public MutableBlockPos(int x, int y, int z) {
            super(x, y, z);
        }

        public BlockPos offset(int x, int y, int z) {
            return super.offset(x, y, z).immutable();
        }

        public BlockPos multiply(int value) {
            return super.multiply(value).immutable();
        }

        public BlockPos relative(Direction direction, int offset) {
            return super.relative(direction, offset).immutable();
        }

        public BlockPos relative(Direction.Axis axis, int offset) {
            return super.relative(axis, offset).immutable();
        }

        public BlockPos rotate(Rotation rotation) {
            return super.rotate(rotation).immutable();
        }

        public BlockPos.MutableBlockPos set(int x, int y, int z) {
            this.setX(x);
            this.setY(y);
            this.setZ(z);
            return this;
        }

        public BlockPos.MutableBlockPos set(double x, double y, double z) {
            return this.set(Math.floor(x), Math.floor(y), Math.floor(z));
        }

        public BlockPos.MutableBlockPos set(Vec3i vector) {
            return this.set(vector.getX(), vector.getY(), vector.getZ());
        }


        public BlockPos.MutableBlockPos setWithOffset(Vec3i vector, Direction direction) {
            return this.set(vector.getX() + direction.getStepX(), vector.getY() + direction.getStepY(), vector.getZ() + direction.getStepZ());
        }

        public BlockPos.MutableBlockPos setWithOffset(Vec3i vector, int offsetX, int offsetY, int offsetZ) {
            return this.set(vector.getX() + offsetX, vector.getY() + offsetY, vector.getZ() + offsetZ);
        }

        public BlockPos.MutableBlockPos setWithOffset(Vec3i vector, Vec3i offset) {
            return this.set(vector.getX() + offset.getX(), vector.getY() + offset.getY(), vector.getZ() + offset.getZ());
        }

        public BlockPos.MutableBlockPos move(Direction direction) {
            return this.move(direction, 1);
        }

        public BlockPos.MutableBlockPos move(Direction direction, int distance) {
            return this.set(this.getX() + direction.getStepX() * distance, this.getY() + direction.getStepY() * distance, this.getZ() + direction.getStepZ() * distance);
        }

        public BlockPos.MutableBlockPos move(int distanceX, int distanceY, int distanceZ) {
            return this.set(this.getX() + distanceX, this.getY() + distanceY, this.getZ() + distanceZ);
        }

        public BlockPos.MutableBlockPos move(Vec3i distance) {
            return this.set(this.getX() + distance.getX(), this.getY() + distance.getY(), this.getZ() + distance.getZ());
        }

        public BlockPos.MutableBlockPos clamp(Direction.Axis axis, int min, int max) {
            switch (axis) {
                case X:
                    return this.set(clamp(this.getX(), min, max), this.getY(), this.getZ());
                case Y:
                    return this.set(this.getX(), clamp(this.getY(), min, max), this.getZ());
                case Z:
                    return this.set(this.getX(), this.getY(), clamp(this.getZ(), min, max));
                default:
                    throw new IllegalStateException("Unable to clamp axis " + axis);
            }
        }

        private int clamp(int value, int min, int max) {
            return Math.max(min, Math.min(value, max));
        }

        public BlockPos.MutableBlockPos setX(int x) {
            super.setX(x);
            return this;
        }

        public BlockPos.MutableBlockPos setY(int y) {
            super.setY(y);
            return this;
        }

        public BlockPos.MutableBlockPos setZ(int z) {
            super.setZ(z);
            return this;
        }

        public BlockPos immutable() {
            return new BlockPos(this);
        }
    }
}