package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.BaseMetaBlock;
import ru.liner.decorative.recipes.IProvideShapedRecipe;
import ru.liner.decorative.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockNetherWart extends BaseMetaBlock implements IProvideShapedRecipe {

    public BlockNetherWart(int itemId) {
        super(itemId, Material.rock);
        setHardness(5f);
        setResistance(10f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("nether.wart");
        setTextureName("nether_wart_block");
        setBaseLocalizedName("Блок адского нароста");
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
        description.add(new Pair<>('#', new ItemStack(Item.netherStalkSeeds)));
        return description;
    }
}
