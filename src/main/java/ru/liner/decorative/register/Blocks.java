package ru.liner.decorative.register;

import ru.liner.decorative.blocks.BlockDoublePlant;
import ru.liner.decorative.blocks.BlockFlowerPlant;
import ru.liner.decorative.blocks.BlockSapling;
import ru.liner.decorative.blocks.MetaBlockHardenedClay;
import ru.liner.decorative.blocks.list.*;

public class Blocks {
    private static int nextBlockId = 3200;
    public static final BlockWoodenLog woodenLog = new BlockWoodenLog(nextBlockId = nextBlockId + 1);
    public static final BlockWoodenPlank planks = new BlockWoodenPlank(nextBlockId = nextBlockId + 1);
    public static final BlockStainedClay clayStained = new BlockStainedClay(nextBlockId = nextBlockId + 1);
    public static final BlockCarpet carpet = new BlockCarpet(nextBlockId = nextBlockId + 1);
    public static final BlockStainedGlass glassStained = new BlockStainedGlass(nextBlockId = nextBlockId + 1);
    public static final BlockStainedGlassPane glassPaneStained = new BlockStainedGlassPane(nextBlockId = nextBlockId + 1);
    public static final BlockLeaves leaves = new BlockLeaves(nextBlockId = nextBlockId + 1);
    public static final BlockSapling saplingBlock = new BlockSapling(nextBlockId = nextBlockId + 1);
    public static final BlockHayBale hayBale = new BlockHayBale(nextBlockId = nextBlockId + 1);
    public static final MetaBlockHardenedClay hardenedClay = new MetaBlockHardenedClay(nextBlockId = nextBlockId + 1);
    public static final BlockCoal coal = new BlockCoal(nextBlockId = nextBlockId + 1);
    public static final BlockDirtPodzol podzol = new BlockDirtPodzol(nextBlockId = nextBlockId + 1);
    public static final BlockDoublePlant flowerDouble = new BlockDoublePlant(nextBlockId = nextBlockId + 1);
    public static final BlockFlowerPlant flower = new BlockFlowerPlant(nextBlockId = nextBlockId + 1);
    public static final BlockStoneNew stone = new BlockStoneNew(nextBlockId = nextBlockId + 1);
    public static final BlockPrismarine prismarine = new BlockPrismarine(nextBlockId = nextBlockId + 1);
    public static final BlockRedSandStone redSandStone = new BlockRedSandStone(nextBlockId = nextBlockId + 1);
    public static final BlockRedSand sandRed = new BlockRedSand(nextBlockId = nextBlockId + 1);
    public static final BlockBanner banner = new BlockBanner(nextBlockId = nextBlockId + 1);
    public static final BlockEndstoneBricks endstoneBricks = new BlockEndstoneBricks(nextBlockId = nextBlockId + 1);
    public static final BlockPurpur purpur = new BlockPurpur(nextBlockId = nextBlockId + 1);
    public static final BlockChorusFlower chorusFlower = new BlockChorusFlower(nextBlockId = nextBlockId + 1);
    public static final BlockChorusPlant chorusPlant = new BlockChorusPlant(nextBlockId = nextBlockId + 1);
    public static final BlockMagma magma = new BlockMagma(nextBlockId = nextBlockId + 1);
    public static final BlockNetherWart netherWart = new BlockNetherWart(nextBlockId = nextBlockId + 1);
    public static final BlockWarpedNylium warpedNylium = new BlockWarpedNylium(nextBlockId = nextBlockId + 1);
    public static final BlockNetherRack netherRack = new BlockNetherRack(nextBlockId = nextBlockId + 1);
    public static final BlockBone bone = new BlockBone(nextBlockId = nextBlockId + 1);
    public static final BlockBed bed = new BlockBed(nextBlockId = nextBlockId + 1);
}
