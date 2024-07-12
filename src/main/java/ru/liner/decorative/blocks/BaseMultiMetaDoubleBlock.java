package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class BaseMultiMetaDoubleBlock extends Block {
    @SideOnly(Side.CLIENT)
    protected HashMap<String, String> localizationMap;
    @SideOnly(Side.CLIENT)
    protected HashMap<String, TextureData> textureDataMap;
    @SideOnly(Side.CLIENT)
    protected String textureParent;
    @SideOnly(Side.CLIENT)
    protected HashMap<String, Icon> textureMap;
    @SideOnly(Side.CLIENT)
    protected String baseLocalizedName;

    public BaseMultiMetaDoubleBlock(int blockID, Material material) {
        super(blockID, material);
        localizationMap = new HashMap<>();
        textureDataMap = new HashMap<>();
        textureMap = new HashMap<>();
    }

    public BaseMultiMetaDoubleBlock setBaseLocalizedName(String localizedName) {
        this.baseLocalizedName = localizedName;
        return this;
    }

    public BaseMultiMetaDoubleBlock setTextureParent(String textureParent) {
        this.textureParent = textureParent;
        return this;
    }

    public BaseMultiMetaDoubleBlock registerType(String unlocalizedName, String localizedName, int textureSide, String textureName) {
        localizationMap.put(unlocalizedName, localizedName);
        textureDataMap.put(String.format("%s_lower", unlocalizedName), new TextureData(textureSide, String.format("%s_lower", textureName)));
        textureDataMap.put(String.format("%s_upper", unlocalizedName), new TextureData(textureSide, String.format("%s_upper", textureName)));
        return this;
    }


    public String getLocalizationFor(String type){
        if(localizationMap.containsKey(type))
            return localizationMap.get(type);
        return baseLocalizedName;
    }


    public static class TextureData {
        public int textureSide;
        public String textureName;
        public MapColor mapColor;

        public TextureData(int textureSide, String textureName) {
            this.textureSide = textureSide;
            this.textureName = textureName;
        }

        public TextureData setMapColor(MapColor mapColor) {
            this.mapColor = mapColor;
            return this;
        }
    }

    public String getTypeByMetadata(int metadata) {
        if (localizationMap.isEmpty())
            return getUnlocalizedName();
        String[] unlocalizedTypes = localizationMap.keySet().toArray(new String[0]);
        if (metadata < 0 || metadata >= getTypesCount())
            return unlocalizedTypes[0];
        return unlocalizedTypes[metadata];
    }


    public int getTypesCount() {
        return localizationMap.size();
    }

    public boolean hasTypes(){
        return !localizationMap.isEmpty();
    }

    public String getBaseLocalizedName() {
        return baseLocalizedName;
    }

    @Override
    public void registerIcons(IconRegister register) {
        textureMap.clear();
        for (int index = 0; index < getTypesCount(); index++) {
            String unlocalizedType = getTypeByMetadata(index);
            TextureData lowerTexture = textureDataMap.get(String.format("%s_lower", unlocalizedType));
            TextureData upperTexture = textureDataMap.get(String.format("%s_upper", unlocalizedType));
            String textureNameLower = String.format("%s_%s", textureParent, lowerTexture.textureName);
            textureMap.put(textureNameLower, register.registerIcon(textureNameLower));
            String textureNameUpper = String.format("%s_%s", textureParent, upperTexture.textureName);
            textureMap.put(textureNameUpper, register.registerIcon(textureNameUpper));
        }
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        String unlocalizedType = getTypeByMetadata(metadata);
        return textureMap.get(String.format("%s_%s", textureParent, textureDataMap.get(String.format("%s_lower", unlocalizedType)).textureName));
    }

    @Override
    public void getSubBlocks(int blockId, CreativeTabs creativeTabs, List itemList) {
        for (int index = 0; index < getTypesCount(); index++)
            itemList.add(new ItemStack(blockId, 1, index));
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }
}
