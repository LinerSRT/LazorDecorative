package ru.liner.decorative.blocks.list;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.liner.decorative.blocks.BaseMetaRotatableBlock;
import ru.liner.decorative.recipes.IProvideShapedRecipe;
import ru.liner.decorative.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class BlockHayBale extends BaseMetaRotatableBlock implements IProvideShapedRecipe {
    public BlockHayBale(int blockID) {
        super(blockID, Material.grass);
        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(BlockHayBale.soundGrassFootstep);
        setBaseLocalizedName("Сноп сена");
        setUnlocalizedName("hayBlock");
        setHardness(0.5f);
        setSideTexture("hay_block_side");
        setTopTexture("hay_block_top");
    }

    @Override
    public ItemStack getCraftResult() {
        return new ItemStack(this);
    }

    @Override
    public String[] getCraftPattern() {
        String[] pattern = new String[3];
        pattern[0] = "###";
        pattern[1] = "###";
        pattern[2] = "###";
        return pattern;
    }

    @Override
    public List<Pair<Character, ?>> getCraftDescription() {
        List<Pair<Character, ?>> description = new ArrayList<>();
        description.add(new Pair<>('#', new ItemStack(Item.wheat)));
        return description;
    }
}
