package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;

public class BaseMultiBlock extends Block implements IMultiTexturedBlock{
    protected HashMap<String, String> types;
    protected Type type;
    @SideOnly(Side.CLIENT)
    protected Icon[] iconTypes;
    @SideOnly(Side.CLIENT)
    protected String textureName;
    @SideOnly(Side.CLIENT)
    protected Icon[] topBottomIcons;
    private boolean rotatedPillar;

    public BaseMultiBlock(int blockId, Material material, Type type) {
        super(blockId, material);
        this.types = new HashMap<>();
        this.type = type;
    }

    public BaseMultiBlock registerType(String type, String localizedName) {
        types.put(type, localizedName);
        return this;
    }

    public BaseMultiBlock cloneTypes(Block otherBlock){
        if(otherBlock instanceof BaseMultiBlock){
            BaseMultiBlock otherMultiBlock = (BaseMultiBlock) otherBlock;
            types.clear();;
            for (int i = 0; i < otherMultiBlock.getTypesCount(); i++) {
                types.put(otherMultiBlock.typeAt(i), otherMultiBlock.types.get(typeAt(i)));
            }
        }
        return this;
    }

    public BaseMultiBlock setTextureName(String textureName) {
        this.textureName = textureName;
        return this;
    }

    public BaseMultiBlock setRotatedPillar(boolean rotatedPillar) {
        this.rotatedPillar = rotatedPillar;
        return this;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        int orientationMetadata = 0;
        if (rotatedPillar) {
            switch (side) {
                case 2:
                case 3:
                    orientationMetadata = 8;
                    break;
                case 4:
                case 5:
                    orientationMetadata = 4;
                    break;
                default:
                    orientationMetadata = 0;
            }
        }
        int finalMetadata = (metadata & 3) | orientationMetadata;
        world.setBlockMetadataWithNotify(x, y, z, finalMetadata, 2);
        return finalMetadata;
    }



    @Override
    public int damageDropped(int metadata) {
        return metaExcludeOrientation(metadata);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubBlocks(int blockId, CreativeTabs creativeTabs, List itemList) {
        for (int i = 0; i < types.size(); i++)
            itemList.add(new ItemStack(blockId, 1, i));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int metadata) {
        if (rotatedPillar) {
            int orientation = metadata & 12;
            int type = metadata & 3;
            if (orientation == 0 && (side == 1 || side == 0))
                return topBottomIcons[metadata % topBottomIcons.length];
            if (orientation == 4 && (side == 5 || side == 4))
                return topBottomIcons[metadata % topBottomIcons.length];
            if (orientation == 8 && (side == 2 || side == 3))
                return topBottomIcons[metadata % topBottomIcons.length];
            return iconTypes[metadata % iconTypes.length];
        }
        return iconTypes[metadata % iconTypes.length];
    }

    public String typeAt(int index) {
        return types.keySet().toArray(new String[0])[types.size() == 0 ? metaExcludeOrientation(index) : metaExcludeOrientation(index) % types.size()];
    }

    public int getTypesCount() {
        return types.size();
    }

    @Override
    public int getBlockId() {
        return blockID;
    }

    public String localizedAt(int index) {
        return String.format("%s %s", type.type, types.get(typeAt(index)));
    }

    @Override
    public void registerIcons(IconRegister register) {
        iconTypes = new Icon[types.size()];
        topBottomIcons = new Icon[types.size()];
        for (int i = 0; i < iconTypes.length; i++) {
            iconTypes[i] = register.registerIcon(String.format("%s_%s", textureName, typeAt(i)));
            topBottomIcons[i] = register.registerIcon(String.format("%s_%s_top", textureName, typeAt(i)));
        }
    }

    public int metaExcludeOrientation(int metadata) {
        return rotatedPillar ? metadata & 3 : metadata;
    }

    public enum Type{
        BLOCK("Блок"),
        EMPTY(""),
        PLANKS("Доски"),
        STAIRS("Ступеньки"),
        LEAVES("Листва"),
        SAPLING("Сажанец"),
        SLABS("Полублок");

        public String type;

        Type(String type) {
            this.type = type;
        }
    }
}
