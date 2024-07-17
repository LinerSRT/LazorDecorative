package ru.liner.decorative.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import ru.liner.decorative.register.Blocks;

public class TileEntityBed extends TileEntity {
    private int color;
    private int rotation;

    public void setColor(int color) {
        this.color = color;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getRotation() {
        return rotation;
    }

    public int getColor() {
        return color;
    }

    public float getRenderingRotation() {
        switch (rotation) {
            case 1:
                return 90f;
            case 2:
                return 0;
            case 3:
                return -90;
            case 0:
            default:
                return 180;
        }
    }

    public void writeToStack(ItemStack stack) {
        if (stack != null && stack.itemID == Blocks.bed.blockID) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.setItemDamage(color);
            tagCompound.setInteger("color", color);
            tagCompound.setInteger("rotation", rotation);
            stack.setTagCompound(tagCompound);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("color", color);
        nbtTagCompound.setInteger("rotation", rotation);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        color = nbtTagCompound.getInteger("color");
        rotation = nbtTagCompound.getInteger("rotation");
    }

    public void reloadNbt() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        readFromNBT(tagCompound);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tagCompound);
    }

    @Override
    public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData entityData) {
        readFromNBT(entityData.customParam1);
    }
}
