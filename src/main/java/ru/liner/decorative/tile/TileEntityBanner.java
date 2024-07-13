package ru.liner.decorative.tile;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileEntityBanner extends TileEntity {
    private int color;
    private int rotation;
    private boolean standBanner;
    private PatternType patternType;

    public TileEntityBanner() {
        patternType = PatternType.values()[new Random().nextInt(PatternType.values().length)];
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("color", color);
        nbtTagCompound.setInteger("rotation", rotation);
        nbtTagCompound.setBoolean("standBanner", standBanner);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        color = nbtTagCompound.getInteger("color");
        rotation = nbtTagCompound.getInteger("rotation");
        standBanner = nbtTagCompound.getBoolean("standBanner");
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

    public int getColor() {
        return color;
    }

    public int getRotation() {
        return rotation;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public boolean isStandBanner() {
        return standBanner;
    }

    public void setStandBanner(boolean standBanner) {
        this.standBanner = standBanner;
    }

    public PatternType getPatternType() {
        return patternType;
    }

    public void setPatternType(PatternType patternType) {
        this.patternType = patternType;
    }

    public enum PatternType{
        BASE("base", "b"),
        SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "),
        SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"),
        SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "),
        SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "),
        STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"),
        STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "),
        STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "),
        STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"),
        STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "),
        STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "),
        STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"),
        STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "),
        STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "),
        CROSS("cross", "cr", "# #", " # ", "# #"),
        STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "),
        TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"),
        TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "),
        TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "),
        TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "),
        DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "),
        DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),
        DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "),
        DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "),
        CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "),
        RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "),
        HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "),
        HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "),
        HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"),
        HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"),
        BORDER("border", "bo", "###", "# #", "###"),
        CURLY_BORDER("curly_border", "cbo", new ItemStack(Block.vine)),
        CREEPER("creeper", "cre", new ItemStack(Item.skull, 1, 4)),
        GRADIENT("gradient", "gra", "# #", " # ", " # "),
        GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"),
        BRICKS("bricks", "bri", new ItemStack(Block.brick)),
        SKULL("skull", "sku", new ItemStack(Item.skull, 1, 1)),
        FLOWER("flower", "flo", new ItemStack(Block.flowerPot, 1, 1)),
        MOJANG("mojang", "moj", new ItemStack(Item.appleGold, 1, 1));
        private String patternName;

        
        
        private String patternID;

        
        
        private String[] craftingLayers;

        
        
        private ItemStack patternCraftingStack;

        PatternType(String str, String str2) {
            this.craftingLayers = new String[3];
            this.patternName = str;
            this.patternID = str2;
        }

        PatternType(String str, String str2, ItemStack itemStack) {
            this(str, str2);
            this.patternCraftingStack = itemStack;
        }

        PatternType(String str, String str2, String str3, String str4, String str5) {
            this(str, str2);
            this.craftingLayers[0] = str3;
            this.craftingLayers[1] = str4;
            this.craftingLayers[2] = str5;
        }

        public String getPatternName() {
            return this.patternName;
        }

        
        
        public String getPatternID() {
            return this.patternID;
        }

        
        
        public String[] getCraftingLayers() {
            return this.craftingLayers;
        }

    }
}
