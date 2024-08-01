package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.ILocalized;
import ru.liner.decorative.materials.MaterialBarrier;

public class BlockBarrier extends Block implements ILocalized {
    public BlockBarrier(int blockId) {
        super(blockId, MaterialBarrier.barrier);
        setUnlocalizedName("barrier");
        setBlockUnbreakable();
        setResistance(6000001f);
        setLightOpacity(0);
        setCreativeTab(CreativeTabs.tabMisc);
        disableStats();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int blockId, float chance, int par7) {

    }

    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon("barrier");
    }

    @Override
    public String getBaseLocalizedName() {
        return "Барьер";
    }
}
