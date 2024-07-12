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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class BaseMultiMetaBlock extends Block {
    @SideOnly(Side.CLIENT)
    protected HashMap<String, String> localizationMap;
    @SideOnly(Side.CLIENT)
    protected HashMap<String, List<TextureData>> textureDataMap;
    @SideOnly(Side.CLIENT)
    protected String textureParent;
    @SideOnly(Side.CLIENT)
    protected HashMap<String, Icon> textureMap;
    @SideOnly(Side.CLIENT)
    protected String baseLocalizedName;

    public BaseMultiMetaBlock(int blockID, Material material) {
        super(blockID, material);
        localizationMap = new HashMap<>();
        textureDataMap = new HashMap<>();
        textureMap = new HashMap<>();
    }

    public BaseMultiMetaBlock setBaseLocalizedName(String localizedName) {
        this.baseLocalizedName = localizedName;
        return this;
    }

    public BaseMultiMetaBlock setTextureParent(String textureParent) {
        this.textureParent = textureParent;
        return this;
    }

    public BaseMultiMetaBlock registerType(String unlocalizedName, String localizedName, int textureSide, String textureName) {
        localizationMap.put(unlocalizedName, localizedName);
        registerTexture(unlocalizedName, textureSide, textureName);
        return this;
    }

    public BaseMultiMetaBlock registerTexture(String unlocalizedName, int textureSide, String textureName) {
        List<TextureData> textureDataList = new ArrayList<>();
        if (!textureDataMap.containsKey(unlocalizedName)) {
            textureDataList.add(new TextureData(textureSide, textureName));
        } else {
            textureDataList = textureDataMap.get(unlocalizedName);
            textureDataList.add(new TextureData(textureSide, textureName));
            textureDataMap.remove(unlocalizedName);
        }
        textureDataMap.put(unlocalizedName, textureDataList);
        return this;
    }


    public String getLocalizationFor(String type) {
        if (localizationMap.containsKey(type))
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
        metadata &= 7;
        if (localizationMap.isEmpty())
            return getUnlocalizedName();
        String[] unlocalizedTypes = localizationMap.keySet().toArray(new String[0]);
        if (metadata >= getTypesCount())
            return unlocalizedTypes[0];
        return unlocalizedTypes[metadata];
    }


    public int getTypesCount() {
        return localizationMap.size();
    }

    public boolean hasTypes() {
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
            for (TextureData textureData : textureDataMap.get(unlocalizedType)) {
                String textureName = String.format("%s_%s", textureParent, textureData.textureName);
                textureMap.put(textureName, register.registerIcon(textureName));
            }
        }
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        String unlocalizedType = getTypeByMetadata(metadata);
        TextureData textureData = textureDataMap.get(unlocalizedType).get(0);
        String textureName = String.format("%s_%s", textureParent, textureData.textureName);
        return textureMap.get(textureName);
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
