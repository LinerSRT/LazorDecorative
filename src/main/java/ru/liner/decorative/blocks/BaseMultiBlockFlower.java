package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;

public class BaseMultiBlockFlower extends BlockFlower implements IMultiTexturedBlock{
    protected final HashMap<String, String> types;
    @SideOnly(Side.CLIENT)
    protected Icon[] iconTypes;
    @SideOnly(Side.CLIENT)
    protected String textureName;
    @SideOnly(Side.CLIENT)
    protected Icon[] topBottomIcons;

    public BaseMultiBlockFlower(int blockId, Material material) {
        super(blockId, material);
        this.types = new HashMap<>();
    }

    public BaseMultiBlockFlower registerType(String type, String localizedName) {
        types.put(type, localizedName);
        return this;
    }

    public BaseMultiBlockFlower setTextureName(String textureName) {
        this.textureName = textureName;
        return this;
    }

    @Override
    public int damageDropped(int metadata) {
        return getMetadataExcludeType(metadata);
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
        return iconTypes[getMetadataExcludeType(metadata) % types.size()];
    }

    public String typeAt(int index) {
        return types.keySet().toArray(new String[0])[getMetadataExcludeType(index) % types.size()];
    }

    public int getTypesCount() {
        return types.size();
    }

    @Override
    public int getBlockId() {
        return blockID;
    }

    public String localizedAt(int index) {
        return types.get(typeAt(index));
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

    public int getMetadataExcludeType(int metadata) {
        return metadata & 7;
    }


}
