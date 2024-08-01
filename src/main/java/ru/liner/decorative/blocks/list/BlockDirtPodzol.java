package ru.liner.decorative.blocks.list;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.BaseMetaRotatableBlock;

public class BlockDirtPodzol extends BaseMetaRotatableBlock {
    
    protected Icon topIcon;
    public BlockDirtPodzol(int itemId) {
        super(itemId, Material.ground);
        setTickRandomly(true);
        setStepSound(Block.soundGrassFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setBaseLocalizedName("Подзол");
        setHardness(5f);
        setResistance(10f);
        setUnlocalizedName("blockDirtPodzol");
        setSideTexture("dirt_podzol_side");
        setTopTexture("dirt_podzol_top");
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        return side == 1 ? textures[1] : textures[0];
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        return metadata;
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }
}
