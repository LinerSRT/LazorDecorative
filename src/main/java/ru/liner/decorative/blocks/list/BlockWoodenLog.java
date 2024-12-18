package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import ru.liner.decorative.register.Registry;

import java.util.Random;

public class BlockWoodenLog extends BaseMultiMetaBlock {
    public BlockWoodenLog(int blockID) {
        super(blockID, Material.wood);
        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(BlockWoodenLog.soundWoodFootstep);
        setBaseLocalizedName("Бревно");
        setUnlocalizedName("log");
        setHardness(2f);
        registerType("acacia", "Акация",  "acacia");
        registerTexture("acacia", "acacia_top", 0, 1);
        registerType("big_oak", "Тёмный дуб",  "big_oak");
        registerTexture("big_oak",  "big_oak_top", 0, 1);
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        int orientation = metadata & 12;
        int type = 0;
        if (
                orientation == 0 && (side == 1 || side == 0) ||
                        orientation == 4 && (side == 5 || side == 4) ||
                        orientation == 8 && (side == 2 || side == 3)
        ) {
            type = 1;
        }
        return textureMap.get(String.format("%s_%s", textureParent, textureDataMap.get(getTypeByMetadata(damageDropped(metadata) % getTypesCount())).get(type).textureName));
    }

    @Override
    public boolean isWood(World world, int x, int y, int z) {
        return true;
    }


    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        if (world.checkChunksExist(x - 4 + 1, y - 4 + 1, z - 4 + 1, x + 4 + 1, y + 4 + 1, z + 4 + 1)) {
            for (int xOffset = -4; xOffset <= 4; ++xOffset) {
                for (int yOffset = -4; yOffset <= 4; ++yOffset) {
                    for (int zOffset = -4; zOffset <= 4; ++zOffset) {
                        int blockId = world.getBlockId(x + xOffset, y + yOffset, z + zOffset);
                        if (Block.blocksList[blockId] != null) {
                            Block.blocksList[blockId].beginLeavesDecay(world, x + xOffset, y + yOffset, z + zOffset);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        int finalMetadata;
        switch (side) {
            case 2:
            case 3:
                finalMetadata = (metadata & 3) | 8;
                break;
            case 4:
            case 5:
                finalMetadata = (metadata & 3) | 4;
                break;
            default:
                finalMetadata = (metadata & 3);
        }
        world.setBlockMetadataWithNotify(x, y, z, finalMetadata, 2);
        return finalMetadata;
    }

    @Override
    protected ItemStack createStackedBlock(int metadata) {
        return new ItemStack(blockID, 1, damageDropped(metadata));
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public int idDropped(int par1, Random par2Random, int par3) {
        return Registry.getInstance().block(BlockWoodenPlank.class).blockID;
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata & 3;
    }
}
