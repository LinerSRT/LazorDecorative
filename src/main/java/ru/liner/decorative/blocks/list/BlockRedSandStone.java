package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import scala.util.parsing.combinator.testing.Str;

import java.util.List;

public class BlockRedSandStone extends BaseMultiMetaBlock {
    public BlockRedSandStone(int itemId) {
        super(itemId, Material.rock);
        setHardness(1.5f);
        setResistance(10f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setBaseLocalizedName("Призмарин");
        setUnlocalizedName("sandstone");
        setTextureParent("red_sandstone");
        registerType("sandstone_smooth", "Гладкий песчаник",  "smooth");
        registerType("sandstone_chiseled", "Резной песчаник",  "carved");
        registerType("sandstone_normal", "Красный песчаник",  "normal");
        registerTexture("sandstone_chiseled", "smooth", 0, 1);
        registerTexture("sandstone_smooth", "smooth", 0, 1);
    }
}
