package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.Icon;
import ru.liner.decorative.blocks.ILocalized;
import ru.liner.decorative.recipes.IProvideShapelessRecipe;
import ru.liner.decorative.recipes.ISmellable;
import ru.liner.decorative.register.Blocks;

public class BlockRedSand extends BlockSand implements IProvideShapelessRecipe, ISmellable, ILocalized {
    public BlockRedSand(int blockID) {
        super(blockID, Material.sand);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("sand.red");
        setHardness(0.5f);
        setStepSound(soundSandFootstep);
    }

    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon("red_sand");
    }


    @Override
    public ItemStack getCraftResult() {
        return new ItemStack(this);
    }

    @Override
    public Object[] getRecipeIngredients() {
        return new Object[]{
                Block.sand,
                new ItemStack(Item.dyePowder, 1, 1)
        };
    }

    @Override
    public float getXp() {
        return 0;
    }

    @Override
    public ItemStack getSmeltingResult() {
        return new ItemStack(Blocks.redSandStone);
    }

    @Override
    public int getItemId() {
        return blockID;
    }

    @Override
    public String getBaseLocalizedName() {
        return "Красный песок";
    }
}
