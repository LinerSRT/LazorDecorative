package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import ru.liner.decorative.utils.MaterialFormatter;

@SuppressWarnings("unchecked")
public class BaseMultiMetaWallBlock<MetaBlock extends BaseMultiMetaBlock> extends BlockWall {
    private MetaBlock metaBlock;
    protected BaseMultiMetaWallBlock(MetaBlock metaBlock, int metadata) {
        super(BlockRegister.nextAvailableId(430 + metaBlock.blockID + metadata + 1), metaBlock);
        this.metaBlock = metaBlock;
        setLightOpacity(0);
        setUnlocalizedName(String.format("wall.%s", metaBlock.getTypeByMetadata(metadata)));
    }

    public MetaBlock getMetaBlock() {
        return metaBlock;
    }

    public String getLocalizationFor(int metadata) {
        return MaterialFormatter.formatMaterial(MaterialFormatter.Type.WALL, metaBlock.getTypeByMetadata(metadata));
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        return metaBlock.getIcon(side, metadata);
    }

}