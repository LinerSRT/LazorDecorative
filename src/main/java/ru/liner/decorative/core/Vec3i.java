package ru.liner.decorative.core;

public class Vec3i implements Comparable<Vec3i> {
    public static final Vec3i ZERO = new Vec3i(0, 0, 0);
    private int x;
    private int y;
    private int z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Vec3i)) {
            return false;
        } else {
            Vec3i vec3i = (Vec3i) other;
            if (this.getX() != vec3i.getX()) {
                return false;
            } else if (this.getY() != vec3i.getY()) {
                return false;
            } else {
                return this.getZ() == vec3i.getZ();
            }
        }
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int compareTo(Vec3i vector) {
        if (this.getY() == vector.getY()) {
            return this.getZ() == vector.getZ() ? this.getX() - vector.getX() : this.getZ() - vector.getZ();
        } else {
            return this.getY() - vector.getY();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    protected Vec3i setX(int x) {
        this.x = x;
        return this;
    }

    protected Vec3i setY(int y) {
        this.y = y;
        return this;
    }

    protected Vec3i setZ(int z) {
        this.z = z;
        return this;
    }

    public Vec3i offset(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new Vec3i(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    public Vec3i offset(Vec3i vector) {
        return this.offset(vector.getX(), vector.getY(), vector.getZ());
    }

    public Vec3i subtract(Vec3i vector) {
        return this.offset(-vector.getX(), -vector.getY(), -vector.getZ());
    }

    public Vec3i multiply(int value) {
        if (value == 1) {
            return this;
        } else {
            return value == 0 ? ZERO : new Vec3i(this.getX() * value, this.getY() * value, this.getZ() * value);
        }
    }

    public Vec3i above() {
        return this.above(1);
    }

    public Vec3i above(int offset) {
        return this.relative(Direction.UP, offset);
    }

    public Vec3i below() {
        return this.below(1);
    }

    public Vec3i below(int offset) {
        return this.relative(Direction.DOWN, offset);
    }

    public Vec3i north() {
        return this.north(1);
    }

    public Vec3i north(int offset) {
        return this.relative(Direction.NORTH, offset);
    }

    public Vec3i south() {
        return this.south(1);
    }

    public Vec3i south(int offset) {
        return this.relative(Direction.SOUTH, offset);
    }

    public Vec3i west() {
        return this.west(1);
    }

    public Vec3i west(int offset) {
        return this.relative(Direction.WEST, offset);
    }

    public Vec3i east() {
        return this.east(1);
    }

    public Vec3i east(int offset) {
        return this.relative(Direction.EAST, offset);
    }

    public Vec3i relative(Direction direction) {
        return this.relative(direction, 1);
    }

    public Vec3i relative(Direction direction, int offset) {
        return offset == 0 ? this : new Vec3i(this.getX() + direction.getStepX() * offset, this.getY() + direction.getStepY() * offset, this.getZ() + direction.getStepZ() * offset);
    }

    public Vec3i relative(Direction.Axis axis, int offset) {
        if (offset == 0) {
            return this;
        } else {
            int x = axis == Direction.Axis.X ? offset : 0;
            int y = axis == Direction.Axis.Y ? offset : 0;
            int z = axis == Direction.Axis.Z ? offset : 0;
            return new Vec3i(this.getX() + x, this.getY() + y, this.getZ() + z);
        }
    }

    public Vec3i cross(Vec3i vector) {
        return new Vec3i(this.getY() * vector.getZ() - this.getZ() * vector.getY(), this.getZ() * vector.getX() - this.getX() * vector.getZ(), this.getX() * vector.getY() - this.getY() * vector.getX());
    }

    public boolean closerThan(Vec3i vector, double distance) {
        return this.distSqr(vector) < distance * distance;
    }

    public boolean closerToCenterThan(Position position, double distance) {
        return this.distToCenterSqr(position) < distance * distance;
    }

    public double distSqr(Vec3i vector) {
        return this.distToLowCornerSqr(vector.getX(), vector.getY(), vector.getZ());
    }

    public double distToCenterSqr(Position position) {
        return this.distToCenterSqr(position.x(), position.y(), position.z());
    }

    public double distToCenterSqr(double x, double y, double z) {
        double d0 = (double) this.getX() + 0.5D - x;
        double d1 = (double) this.getY() + 0.5D - y;
        double d2 = (double) this.getZ() + 0.5D - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distToLowCornerSqr(double x, double y, double z) {
        double d0 = (double) this.getX() - x;
        double d1 = (double) this.getY() - y;
        double d2 = (double) this.getZ() - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public int distManhattan(Vec3i vector) {
        float f = (float) Math.abs(vector.getX() - this.getX());
        float f1 = (float) Math.abs(vector.getY() - this.getY());
        float f2 = (float) Math.abs(vector.getZ() - this.getZ());
        return (int) (f + f1 + f2);
    }

    public int get(Direction.Axis axis) {
        return axis.choose(this.x, this.y, this.z);
    }


    @Override
    public String toString() {
        return "Vec3i{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public String toShortString() {
        return this.getX() + ", " + this.getY() + ", " + this.getZ();
    }
}