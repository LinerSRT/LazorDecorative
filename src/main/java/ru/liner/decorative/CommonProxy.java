package ru.liner.decorative;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import ru.liner.decorative.blocks.*;
import ru.liner.decorative.blocks.list.*;
import ru.liner.decorative.enity.Entities;
import ru.liner.decorative.items.ItemChorusFruit;
import ru.liner.decorative.items.ItemChorusFruitPopped;
import ru.liner.decorative.recipes.BannerRecipes;
import ru.liner.decorative.register.*;
import ru.liner.decorative.world.Generator;

public class CommonProxy {
    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent e){
        DamageSources.init();
        TileEntities.init();
        Decorative.init();
        Registry
                .getInstance()
                .add(new BlockDoublePlant(getBlockId("doublePlant", 2330)))
                .add(new BlockFlowerPlant(getBlockId("flower", 2331)))
                .add(new BlockSapling(getBlockId("sapling", 2375)))
                .add(new BlockRedSand(getBlockId("sand.red", 2332)))
                .add(new BlockCarpet(getBlockId("carpet", 2333)))
                .add(new BlockStainedClay(getBlockId("clay.stained", 2334)))
                .add(new BlockStainedGlass(getBlockId("glass.stained", 2335)))
                .add(new BlockStainedGlassPane(getBlockId("glass_pane.stained", 2336)))
                .add(new BlockStoneNew(getBlockId("stone", 2337)))
                .add(new BlockPrismarine(getBlockId("prismarine", 2338)))
                .add(new BlockRedSandStone(getBlockId("sandstone", 2339)))
                .add(new BlockLeaves(getBlockId("leaves", 2340)))
                .add(new BlockWoodenLog(getBlockId("log",3341)))
                .add(new BlockWoodenPlank(getBlockId("planks", 2342)))
                .add(new BlockPurpur(getBlockId("purpur", 2345)))
                .add(new BlockBanner(getBlockId("banner", 2346)))
                .add(new BlockHayBale(getBlockId("hayBlock", 2347)))
                .add(new BlockDirtPodzol(getBlockId("blockDirtPodzol", 2348)))
                .add(new BlockEndstoneBricks(getBlockId("bricks.endstone", 2349)))
                .add(new MetaBlockHardenedClay(getBlockId("clayHardened", 2350)))
                .add(new BlockCoal(getBlockId("blockCoal", 2351)))
                .add(new BlockChorusFlower(getBlockId("flower.chorus", 2352)))
                .add(new BlockChorusPlant(getBlockId("flower.plant", 2354)))
                .add(new BlockMagma(getBlockId("blockMagma", 2355)))
                .add(new BlockNetherWart(getBlockId("nether.wart", 2356)))
                .add(new BlockNetherRack(getBlockId("netherrack", 2357)))
                .add(new BlockWarpedNylium(getBlockId("warpedNylium", 2358)))
                .add(new BlockBone(getBlockId("blockBone", 2359)))
                .add(new BlockBed(getBlockId("blockBed", 2380)))
                .add(new ItemChorusFruit(getItemId("chorusFruit", 2500)))
                .add(new ItemChorusFruitPopped(getItemId("chorusFruitPopped", 2501)))
                .registerBlocks()
                .registerItems();


        Entities.init();
        BannerRecipes.addRecipes();
        Generator.init();
    }

    @Mod.Init
    public void init(final FMLInitializationEvent e){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @ForgeSubscribe
    public void onApplyBoneMail(BonemealEvent event) {
        int blockId = event.world.getBlockId(event.X, event.Y, event.Z);
        Block block = Block.blocksList[blockId];
        if (block instanceof IUseBonemail) {
            event.setResult(Event.Result.ALLOW);
            ((IUseBonemail) block).applyBonemail(event.world, event.X, event.Y, event.Z);
        }
    }

    private static int getBlockId(String unlocalizedName, int defaultId){
        return DecorativeMod.configuration.get("block", unlocalizedName, defaultId).getInt();
    }
    private static int getItemId(String unlocalizedName, int defaultId){
        return DecorativeMod.configuration.get("item", unlocalizedName, defaultId).getInt();
    }
}
