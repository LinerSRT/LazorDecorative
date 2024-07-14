package ru.liner.decorative.tile;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import ru.liner.decorative.recipes.BannerRecipes;
import ru.liner.decorative.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class TileEntityBanner extends TileEntity {
    private int color;
    private int rotation;
    private boolean standBanner;

    private NBTTagList patterns;
    private List<Pair<PatternType, Integer>> patternList;

    public TileEntityBanner() {

    }

    private void initBannerData() {
        if (patternList != null)
            return;
        this.patternList = new ArrayList<>();
        this.patternList.add(new Pair<PatternType, Integer>(PatternType.BASE, color));
        if (patterns != null) {
            for (int i = 0; i < patterns.tagCount(); i++) {
                String patternData = ((NBTTagString) patterns.tagAt(i)).data;
                String patternId;
                int patternColor;
                if (patternData.contains("_")) {
                    patternId = patternData.split("_")[0];
                    patternColor = Integer.parseInt(patternData.split("_")[1]);
                } else {
                    patternId = patternData;
                    patternColor = color;
                }
                PatternType pattern = PatternType.getPatternByID(patternId);
                if (pattern != null) {
                    patternList.add(new Pair<>(pattern, patternColor));
                }
            }
        }
    }

    public void writeToStack(ItemStack stack) {
        if (BannerRecipes.isBannerStack(stack)) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            if (patterns != null)
                tagCompound.setTag("patterns", patterns);
            stack.setTagCompound(tagCompound);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("color", color);
        nbtTagCompound.setInteger("rotation", rotation);
        nbtTagCompound.setBoolean("standBanner", standBanner);
        if (patterns != null)
            nbtTagCompound.setTag("patterns", patterns);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        color = nbtTagCompound.getInteger("color");
        rotation = nbtTagCompound.getInteger("rotation");
        standBanner = nbtTagCompound.getBoolean("standBanner");
        patterns = nbtTagCompound.getTagList("patterns");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tagCompound);
    }

    private void reloadNbt() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        readFromNBT(tagCompound);
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

    public void applyItem(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (tagCompound.hasKey("patterns")) {
                patterns = tagCompound.getTagList("patterns");
            }
            reloadNbt();
        }
    }

    public List<Pair<PatternType, Integer>> getPatternList() {
        initBannerData();
        return patternList;
    }

    public boolean hasAppliedPatterns() {
        return getPatternList().size() > 1;
    }

    public NBTTagList getPatternCount() {
        return patterns;
    }


    public enum PatternType {
        BASE("base", "Сплошной цвет", "b"),
        SQUARE_BOTTOM_LEFT("square_bottom_left", "Квадрат в нижн. левый угол", "bl", "   ", "   ", "#  "),
        SQUARE_BOTTOM_RIGHT("square_bottom_right", "Квадрат в нижн. правый угол", "br", "   ", "   ", "  #"),
        SQUARE_TOP_LEFT("square_top_left", "Квадрат в верхн. левый угол", "tl", "#  ", "   ", "   "),
        SQUARE_TOP_RIGHT("square_top_right", "Квадрат в верхн. правый угол", "tr", "  #", "   ", "   "),
        STRIPE_BOTTOM("stripe_bottom", "Лиция внизу", "bs", "   ", "   ", "###"),
        STRIPE_TOP("stripe_top", "Лиция вверху", "ts", "###", "   ", "   "),
        STRIPE_LEFT("stripe_left", "Лиция слева", "ls", "#  ", "#  ", "#  "),
        STRIPE_RIGHT("stripe_right", "Лиция справа", "rs", "  #", "  #", "  #"),
        STRIPE_CENTER("stripe_center", "Верт. линия в центре", "cs", " # ", " # ", " # "),
        STRIPE_MIDDLE("stripe_middle", "Гориз. линия в центре", "ms", "   ", "###", "   "),
        STRIPE_DOWNRIGHT("stripe_downright", "Диаг. линия снизу справа", "drs", "#  ", " # ", "  #"),
        STRIPE_DOWNLEFT("stripe_downleft", "Диаг. линия снизу слева", "dls", "  #", " # ", "#  "),
        STRIPE_SMALL("small_stripes", "Мелкие верт. линии", "ss", "# #", "# #", "   "),
        CROSS("cross", "Диагональный крест", "cr", "# #", " # ", "# #"),
        STRAIGHT_CROSS("straight_cross", "Прямой крест", "sc", " # ", "###", " # "),
        TRIANGLE_BOTTOM("triangle_bottom", "Треугольник внизу", "bt", "   ", " # ", "# #"),
        TRIANGLE_TOP("triangle_top", "Треугольник вверху", "tt", "# #", " # ", "   "),
        TRIANGLES_BOTTOM("triangles_bottom", "Гребешки внизу", "bts", "   ", "# #", " # "),
        TRIANGLES_TOP("triangles_top", "Гребешки вверху", "tts", " # ", "# #", "   "),
        DIAGONAL_LEFT("diagonal_left", "Диагональ слева", "ld", "## ", "#  ", "   "),
        DIAGONAL_RIGHT("diagonal_up_right", "Диагональ справа", "rd", "   ", "  #", " ##"),
        DIAGONAL_LEFT_MIRROR("diagonal_up_left", "Диагональ слева (зерк)", "lud", "   ", "#  ", "## "),
        DIAGONAL_RIGHT_MIRROR("diagonal_right", "Диагональ слева (зерк)", "rud", " ##", "  #", "   "),
        CIRCLE_MIDDLE("circle", "Круг в центре", "mc", "   ", " # ", "   "),
        RHOMBUS_MIDDLE("rhombus", "Ромб в центре", "mr", " # ", "# #", " # "),
        HALF_VERTICAL("half_vertical", "Половинка верт.", "vh", "## ", "## ", "## "),
        HALF_HORIZONTAL("half_horizontal", "Половинка гориз.", "hh", "###", "###", "   "),
        HALF_VERTICAL_MIRROR("half_vertical_right", "Половинка верт. (зерк)", "vhr", " ##", " ##", " ##"),
        HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "Половинка гориз. (зерк)", "hhb", "   ", "###", "###"),
        BORDER("border", "Окантовка", "bo", "###", "# #", "###"),
        CURLY_BORDER("curly_border", "Рельеф. окантовка", "cbo", new ItemStack(Block.vine)),
        CREEPER("creeper", "Крипер", "cre", new ItemStack(Item.skull, 1, 4)),
        GRADIENT("gradient", "Градиент вниз", "gra", "# #", " # ", " # "),
        GRADIENT_UP("gradient_up", "Градиент вверх", "gru", " # ", " # ", "# #"),
        BRICKS("bricks", "Кирпичи", "bri", new ItemStack(Block.brick)),
        SKULL("skull", "Череп", "sku", new ItemStack(Item.skull, 1, 0)),
        FLOWER("flower", "Цветок", "flo", new ItemStack(Block.flowerPot, 1, 0)),
        MOJANG("mojang", "Моджанг", "moj", new ItemStack(Item.appleGold, 1, 0));
        private String patternName;
        private String patternLocalizedName;
        private String patternID;
        private String[] craftingLayers;
        private ItemStack patternCraftingStack;

        PatternType(String patternName, String patternLocalizedName, String patternID) {
            this.craftingLayers = new String[3];
            this.patternName = patternName;
            this.patternLocalizedName = patternLocalizedName;
            this.patternID = patternID;
        }

        PatternType(String patternName, String patternLocalizedName, String patternID, ItemStack itemStack) {
            this(patternName, patternLocalizedName, patternID);
            this.patternCraftingStack = itemStack;
        }

        PatternType(String patternName, String patternLocalizedName, String patternID, String layer0, String layer1, String layer2) {
            this(patternName, patternLocalizedName, patternID);
            this.craftingLayers[0] = layer0;
            this.craftingLayers[1] = layer1;
            this.craftingLayers[2] = layer2;
        }

        public String getPatternName() {
            return this.patternName;
        }

        public String getPatternLocalizedName() {
            return patternLocalizedName;
        }

        public String getPatternID() {
            return this.patternID;
        }

        public String[] getCraftingLayers() {
            return this.craftingLayers;
        }

        public static PatternType getPatternByID(String str) {
            for (PatternType aVar : values()) {
                if (aVar.patternID.equals(str)) {
                    return aVar;
                }
            }
            return null;
        }

        public boolean hasValidCrafting() {
            return this.patternCraftingStack != null || this.craftingLayers[0] != null;
        }

        public boolean hasCraftingStack() {
            return this.patternCraftingStack != null;
        }

        public ItemStack getCraftingStack() {
            return this.patternCraftingStack;
        }

        public boolean isMatchingCraftingStack(ItemStack stack) {
            return stack != null && hasCraftingStack() && stack.isItemEqual(getCraftingStack());
        }
    }


    public float getRenderingRotation() {
        if (isStandBanner())
            return -((rotation * 360) / 16.0f) + 180;
        switch (rotation) {
            case 1:
                return 90f;
            case 2:
                return 180f;
            case 3:
                return -90;
            case 0:
            default:
                return 0f;
        }
    }


    public int getDyeColor(int index) {
        return ItemDye.dyeColors[index & 15];
    }

    public int getDyeColor() {
        return getDyeColor(color);
    }
}
