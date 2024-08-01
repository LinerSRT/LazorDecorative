package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import ru.liner.decorative.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class BaseMultiMetaBlock extends Block implements ILocalized {
    protected List<Pair<String, String>> localizationList;
    protected HashMap<String, List<TextureData>> textureDataMap;
    protected String textureParent;

    protected HashMap<String, Icon> textureMap;
    protected String baseLocalizedName;

    public BaseMultiMetaBlock(int blockID, Material material) {
        super(blockID, material);
        localizationList = new ArrayList<>();
        textureDataMap = new HashMap<>();
        textureMap = new HashMap<>();
    }

    @Override
    public Block setUnlocalizedName(String baseLocalizedName) {
        textureParent = baseLocalizedName;
        return super.setUnlocalizedName(baseLocalizedName);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn() {
        return super.getCreativeTabToDisplayOn();
    }

    public BaseMultiMetaBlock setBaseLocalizedName(String localizedName) {
        this.baseLocalizedName = localizedName;
        return this;
    }

    public BaseMultiMetaBlock setTextureParent(String textureParent) {
        this.textureParent = textureParent;
        return this;
    }

    public BaseMultiMetaBlock registerType(String unlocalizedName, String localizedName, String textureName, int... textureSides) {
        localizationList.add(new Pair<>(unlocalizedName, localizedName));
        registerTexture(unlocalizedName, textureName, textureSides);
        return this;
    }

    public BaseMultiMetaBlock registerTexture(String unlocalizedName, String textureName, int... textureSides) {
        List<TextureData> textureDataList = new ArrayList<>();
        if (!textureDataMap.containsKey(unlocalizedName)) {
            textureDataList.add(new TextureData(textureSides.length == 0 ? new int[]{-1} : textureSides, textureName));
        } else {
            textureDataList = textureDataMap.get(unlocalizedName);
            textureDataList.add(new TextureData(textureSides.length == 0 ? new int[]{-1} : textureSides, textureName));
            textureDataMap.remove(unlocalizedName);
        }
        textureDataMap.put(unlocalizedName, textureDataList);
        return this;
    }


    public String getLocalizationFor(String type) {
        for (Pair<String, String> localization : localizationList) {
            if (localization.key.equals(type))
                return localization.value;
        }
        return baseLocalizedName;
    }


    public static class TextureData {
        public int[] textureSides;
        public String textureName;
        public MapColor mapColor;

        public TextureData(int[] textureSides, String textureName) {
            this.textureSides = textureSides;
            this.textureName = textureName;
        }

        public boolean isMatchingSide(int side) {
            for (int textureSide : textureSides) {
                if (side == textureSide && textureSide != -1)
                    return true;
            }
            return false;
        }

        public TextureData setMapColor(MapColor mapColor) {
            this.mapColor = mapColor;
            return this;
        }
    }

    public String getTypeByMetadata(int metadata) {
        if (localizationList.isEmpty())
            return getUnlocalizedName();
        if (metadata < 0 || metadata >= getTypesCount())
            return localizationList.get(0).key;
        return localizationList.get(metadata).key;
    }


    public int getTypesCount() {
        return localizationList.size();
    }

    public boolean hasTypes() {
        return !localizationList.isEmpty();
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
        TextureData[] textureList = textureDataMap.get(unlocalizedType).toArray(new TextureData[0]);
        String textureName = String.format("%s_%s", textureParent, textureList[0].textureName);
        if (textureList.length > 1) {
            String candidateTexture = textureList[0].textureName;
            for (int i = 1; i < textureList.length; i++) {
                if (textureList[i].isMatchingSide(side)) {
                    candidateTexture = textureList[i].textureName;
                    break;
                }
            }
            textureName = String.format("%s_%s", textureParent, candidateTexture);
        }
        return textureMap.get(textureName);
    }

    public String getTextureFor(int metadata) {
        String unlocalizedType = getTypeByMetadata(metadata);
        TextureData[] textureList = textureDataMap.get(unlocalizedType).toArray(new TextureData[0]);
        return String.format("%s_%s", textureParent, textureList[0].textureName);
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
