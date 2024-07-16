package ru.liner.decorative.blocks.list;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.liner.decorative.blocks.BaseMetaBlock;
import ru.liner.decorative.blocks.BaseMetaRotatableBlock;
import ru.liner.decorative.recipes.IProvideShapedRecipe;
import ru.liner.decorative.recipes.IProvideShapelessRecipe;
import ru.liner.decorative.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class BlockBone extends BaseMetaRotatableBlock implements IProvideShapedRecipe, IProvideShapelessRecipe {
    public BlockBone(int itemId) {
        super(itemId, Material.rock);
        setHardness(2f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setBaseLocalizedName("Костный блок");
        setUnlocalizedName("blockBone");
        setTextureName("bone_block");
        setSideTexture("bone_block_side");
        setTopTexture("bone_block_top");
    }

    @Override
    public ItemStack getShapelessCraftResult() {
        return new ItemStack(Item.bone, 9, 0);
    }

    @Override
    public Object[] getRecipeIngredients() {
        return new Object[]{
                this
        };
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
        description.add(new Pair<>('#', new ItemStack(Item.bone)));
        return description;
    }
}
