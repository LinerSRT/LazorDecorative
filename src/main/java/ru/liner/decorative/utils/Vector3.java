package ru.liner.decorative.utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liner.decorative.tile.TileEntityBed;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Vector3 {
    public static final Vector3 NEGATIVE = new Vector3(-1, -1, -1);
    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public double x;
    public double y;
    public double z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(Vector3 vector3) {
        this(vector3.x, vector3.y, vector3.z);
    }

    public Vector3(Vec3 vec3) {
        this(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(DataInputStream stream) {
        try {
            this.x = stream.readInt();
            this.y = stream.readInt();
            this.z = stream.readInt();
        } catch (IOException e) {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
    }



    public static Vector3 entityEyeLocation(Entity entity) {
        double entityX = entity.posX;
        double entityY = entity instanceof EntityLiving ? entity.posY + entity.getEyeHeight() * 0.9 : entity.boundingBox.minY + entity.boundingBox.maxY;
        double entityZ = entity.posZ;
        return new Vector3(entityX, entityY, entityZ);
    }

    public static Vector3 entityPosition(Entity entity) {
        return new Vector3(entity.posX, entity.posY, entity.posZ);
    }

    public static Vector3 entityCenterPosition(Entity entity) {
        return new Vector3(entity.posX, entity.posY, entity.posZ).add(0.5f, 0.5f, 0.5f);
    }

    public static Vector3 entityTickLocation(EntityLiving entity, float partialTicks) {
        double posX = entity.posX + (entity.posX - entity.prevPosX) * partialTicks;
        double posY = entity.posY + (entity.posY - entity.prevPosY) * partialTicks;
        double posZ = entity.posZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        return new Vector3(posX, posY, posZ);
    }

    public static Vector3 tilePosition(TileEntity tileEntity) {
        return new Vector3(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
    }

    public Vec3 toMinecraftVec3() {
        return Vec3.createVectorHelper(x, y, z);
    }


    public Vector3 add(double x, double y, double z) {
        return new Vector3(this.x + x, this.y + y, this.z + z);
    }

    public Vector3 add(Vector3 x) {
        return this.add(x.x, x.y, x.z);
    }

    public Vector3 subtract(double x, double y, double z) {
        return this.add(-x, -y, -z);
    }

    public Vector3 subtract(Vector3 x) {
        return this.add(-x.x, -x.y, -x.z);
    }

    public Vector3 ceil() {
        return new Vector3(this.x + 1, this.y + 1, this.z + 1);
    }

    public Vector3 floor() {
        return new Vector3(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
    }

    public Vector3 round() {
        return new Vector3(Math.round(this.x), Math.round(this.y), Math.round(this.z));
    }

    public Vector3 abs() {
        return new Vector3(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }

    public Vector3 multiply(double number) {
        return new Vector3(this.x * number, this.y * number, this.z * number);
    }

    public Vector3 divide(double number) {
        return new Vector3(this.x / number, this.y / number, this.z / number);
    }

    public double distance(double x, double y, double z) {
        return Math.sqrt(this.distanceSquared(x, y, z));
    }

    public double distance(Vector3 vector) {
        return Math.sqrt(this.distanceSquared(vector.x, vector.y, vector.z));
    }

    public double distanceSquared(double x, double y, double z) {
        return Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2);
    }

    public double distanceSquared(Vector3 vector) {
        return this.distanceSquared(vector.x, vector.y, vector.z);
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double distance2d() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
    }

    public Vector3 normalize() {
        double len = this.lengthSquared();
        if (len != 0) {
            return this.divide(Math.sqrt(len));
        }
        return new Vector3(0, 0, 0);
    }

    public double dot(Vector3 v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public Vector3 copy() {
        return new Vector3(this);
    }

    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    public Vector3 centerOfBlock() {
        return add(0.5, 0, 0.5);
    }

    public float yawAngle() {
        return (float) (Math.atan2(z, x) * 180d / Math.PI) - 90f;
    }

    public float pitchAngle() {
        return (float) -(Math.atan2(y, distance2d()) * 180d / Math.PI);
    }

    public boolean isAirBlock(IBlockAccess blockAccess) {
        Block block = asBlock(blockAccess);
        return block == null || block.blockMaterial == Material.air;
    }

    public boolean hasAnyBlockAround(IBlockAccess blockAccess, int... blocks) {
        boolean founded = false;
        for (EnumFacing facing : EnumFacing.values()) {
            Vector3 offsetPosition = offset(facing);
            if (offsetPosition.isAirBlock(blockAccess) || offsetPosition.asBlock(blockAccess) == null)
                continue;
            for (int id : blocks)
                if (offsetPosition.blockId(blockAccess) == id) {
                    founded = true;
                    break;
                }
            if (founded)
                break;
        }
        return founded;
    }
    public boolean hasAnyMaterialAround(IBlockAccess blockAccess, Material... materials) {
        boolean founded = false;
        for (EnumFacing facing : EnumFacing.values()) {
            Vector3 offsetPosition = offset(facing);
            if (offsetPosition.isAirBlock(blockAccess) || offsetPosition.asBlock(blockAccess) == null)
                continue;
            for (Material material : materials)
                if (offsetPosition.asBlock(blockAccess).blockMaterial == material) {
                    founded = true;
                    break;
                }
            if (founded)
                break;
        }
        return founded;
    }

    public List<Vector3> locateMaterials(IBlockAccess blockAccess, Material... materials){
        List<Vector3> materialList = new ArrayList<>();
        for (EnumFacing facing : EnumFacing.values()) {
            Vector3 offsetPosition = offset(facing);
            if (offsetPosition.isAirBlock(blockAccess) || offsetPosition.asBlock(blockAccess) == null)
                continue;
            for (Material material : materials)
                if (offsetPosition.asBlock(blockAccess).blockMaterial == material)
                    materialList.add(offsetPosition);
        }
        return materialList;
    }

    public int countBlockAround(IBlockAccess blockAccess, int... blocks) {
        int count = 0;
        for (EnumFacing facing : EnumFacing.values()) {
            Vector3 offsetPosition = offset(facing);
            if (offsetPosition.isAirBlock(blockAccess))
                continue;
            for (int id : blocks)
                if (offsetPosition.blockId(blockAccess) == id) {
                    count++;
                }
        }
        return count;
    }

    public void write(DataOutputStream stream) {
        try {
            stream.writeInt((int) x);
            stream.writeInt((int) y);
            stream.writeInt((int) z);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Block asBlock(IBlockAccess blockAccess) {
        return Block.blocksList[blockId(blockAccess)];
    }


    public int blockId(IBlockAccess blockAccess) {

            return blockAccess.getBlockId((int) x, (int) y, (int) z);
    }





    public Vec3 asMcVector() {
        return Vec3.createVectorHelper(x, y, z);
    }


    public boolean isBlock(IBlockAccess blockAccess, Block block) {
        return isBlock(blockAccess,block.blockID);
    }

    public boolean isBlock(IBlockAccess blockAccess, int blockId) {
        return blockId(blockAccess) == blockId;
    }


    public Vector3 up() {
        return up(1);
    }


    public Vector3 up(int i) {
        return offset(EnumFacing.UP, i);
    }


    public Vector3 down() {
        return down(1);
    }


    public Vector3 down(int i) {
        return offset(EnumFacing.DOWN, i);
    }


    public Vector3 north() {
        return north(1);
    }


    public Vector3 north(int i) {
        return offset(EnumFacing.NORTH, i);
    }


    public Vector3 south() {
        return south(1);
    }


    public Vector3 south(int i) {
        return offset(EnumFacing.SOUTH, i);
    }


    public Vector3 west() {
        return west(1);
    }


    public Vector3 west(int i) {
        return offset(EnumFacing.WEST, i);
    }

    public Vector3 east() {
        return east(1);
    }

    public Vector3 east(int i) {
        return offset(EnumFacing.EAST, i);
    }


    public Vector3 offset(EnumFacing enumFacing) {
        return offset(enumFacing, 1);
    }

    public Vector3 offset(EnumFacing enumFacing, int amount) {
        if (amount == 0) {
            return this;
        }
        return add(enumFacing.getFrontOffsetX() * amount, enumFacing.getFrontOffsetY() * amount, enumFacing.getFrontOffsetZ() * amount);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        if (Double.compare(vector3.x, x) != 0) return false;
        if (Double.compare(vector3.y, y) != 0) return false;
        return Double.compare(vector3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public int blockMetadata(IBlockAccess blockAccess) {
        return blockAccess.getBlockMetadata((int) x, (int) y, (int) z);
    }
}
