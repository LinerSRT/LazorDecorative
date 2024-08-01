package ru.liner.decorative.blocks.list;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BaseMetaBlock;

import java.util.HashMap;

public class BlockTest extends BaseMetaBlock {
    
    private HashMap<String, Icon> textureMap;

    public BlockTest(int blockId) {
        super(blockId, Material.rock);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("test");
        setTextureName("test");
        setBaseLocalizedName("Test");
    }


    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return Decorative.REDSTONE_LOGIC_BLOCK_RENDERER;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemStack) {
        world.setBlockMetadataWithNotify(x, y, z, (((MathHelper.floor_double(((entity.rotationYaw * 4.0f) / 360.0f) + 0.5d) & 3) + 2) % 4), 2);
    }

    @Override
    public void registerIcons(IconRegister register) {
        textureMap = new HashMap<>();
        textureMap.put(getTexture("0"), register.registerIcon(getTexture("0")));
        textureMap.put(getTexture("1"), register.registerIcon(getTexture("1")));
        textureMap.put(getTexture("2"), register.registerIcon(getTexture("2")));
        textureMap.put(getTexture("3"), register.registerIcon(getTexture("3")));
        textureMap.put(getTexture("4"), register.registerIcon(getTexture("4")));
        textureMap.put(getTexture("5"), register.registerIcon(getTexture("5")));
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (side == 1)
            return false;
        return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        int orientation = (metadata & 3);

        return textureMap.get(getTexture(String.valueOf(side)));
    }

    private String getTexture(String type) {
        return String.format("%s_%s", textureName, type);
    }
}
