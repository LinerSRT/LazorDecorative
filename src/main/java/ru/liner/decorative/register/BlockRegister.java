package ru.liner.decorative.register;

import net.minecraft.block.Block;
import ru.liner.decorative.blocks.list.BlockBarrier;
import ru.liner.decorative.blocks.list.BlockObserver;
import ru.liner.decorative.blocks.list.BlockTest;

import static ru.liner.decorative.register.Blocks.*;

public class BlockRegister {

    public static void init() {
        Registry
                .getInstance()
                .add(flowerDouble)
                .add(flower)
                .add(saplingBlock)
                .add(sandRed)
                .add(carpet)
                .add(clayStained)
                .add(glassStained)
                .add(glassPaneStained)
                .add(stone)
                .add(prismarine)
                .add(redSandStone)
                .add(leaves)
                .add(woodenLog)
                .add(planks)
                .add(purpur)
                .add(banner)
                .add(hayBale)
                .add(podzol)
                .add(endstoneBricks)
                .add(hardenedClay)
                .add(coal)
                .add(chorusFlower)
                .add(chorusPlant)
                .add(magma)
                .add(netherWart)
                .add(netherRack)
                .add(warpedNylium)
                .add(bone)
                .add(bed)
                //.add(new BlockBarrier(1064))
                //.add(new BlockObserver(1065))
                //.add(new BlockTest(1066))
                .registerBlocks();
    }


    public static int nextAvailableId(int startFrom) {
        for (int i = startFrom; i < Block.blocksList.length; i++) {
            if (Block.blocksList[i] == null)
                return i;
        }
        return 0;
    }
}
