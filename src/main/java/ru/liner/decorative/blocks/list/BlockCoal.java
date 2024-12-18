package ru.liner.decorative.blocks.list;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.liner.decorative.blocks.BaseMetaBlock;
import ru.liner.decorative.recipes.IProvideShapedRecipe;
import ru.liner.decorative.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class BlockCoal extends BaseMetaBlock implements IProvideShapedRecipe, IFuelHandler {
    public BlockCoal(int itemId) {
        super(itemId, Material.rock);
        setHardness(5f);
        setResistance(10f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("blockCoal");
        setTextureName("coal_block");
        setBaseLocalizedName("Блок угля");
    }

    @Override
    public ItemStack getCraftResult() {
        return new ItemStack(this);
    }

    @Override
    public String[] getCraftPattern() {
        return new String[]{
                "###",
                "###",
                "###"
        };
    }

    @Override
    public List<Pair<Character, ?>> getCraftDescription() {
        List<Pair<Character, ?>> description = new ArrayList<>();
        description.add(new Pair<>('#', new ItemStack(Item.coal)));
        return description;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 1600 * 9;
    }
}
