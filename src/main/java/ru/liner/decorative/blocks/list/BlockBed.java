package ru.liner.decorative.blocks.list;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import ru.liner.decorative.items.ItemBed;
import ru.liner.decorative.register.IItemProvider;
import ru.liner.decorative.tile.TileEntityBed;

public class BlockBed extends BaseMultiMetaBlock implements ITileEntityProvider, IItemProvider<ItemBed> {
    public BlockBed(int blockId) {
        super(blockId, Material.cloth);
        setCreativeTab(CreativeTabs.tabDecorations);
        setStepSound(soundClothFootstep);
        setUnlocalizedName("blockBed");
        setBaseLocalizedName("Кровать");
        setTextureParent("bed");
        setBlockBounds(0, 0, 0, 1, .5f, 1);
        for (int i = 0; i < ItemDye.dyeColorNames.length; i++) {
            registerType(ItemDye.dyeColorNames[i], String.format("%s %s", Decorative.colorNames[i + 16], getBaseLocalizedName().toLowerCase()), ItemDye.dyeColorNames[i]);
        }
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        String unlocalizedType = getTypeByMetadata(metadata);
        String textureName = String.format("%s_%s", unlocalizedType, textureParent);
        return textureMap.get(textureName);
    }

    @Override
    public void registerIcons(IconRegister register) {
        textureMap.clear();
        for (int index = 0; index < getTypesCount(); index++) {
            String unlocalizedType = getTypeByMetadata(index);
            String textureName = String.format("%s_%s", unlocalizedType, textureParent);
            textureMap.put(textureName, register.registerIcon(textureName));
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemStack) {
        world.setBlockMetadataWithNotify(x, y, z, itemStack.getItemDamage(), 2);
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public int getRenderType() {
        return -1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityBed();
    }

    @Override
    public Class<ItemBed> getItemClass() {
        return ItemBed.class;
    }
}
