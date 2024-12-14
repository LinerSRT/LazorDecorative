package ru.liner.decorative.blocks;

import net.minecraft.block.BlockWall;
import net.minecraft.util.Icon;
import ru.liner.decorative.DecorativeMod;
import ru.liner.decorative.utils.MaterialFormatter;

@SuppressWarnings("unchecked")
public class BaseMultiMetaWallBlock<MetaBlock extends BaseMultiMetaBlock> extends BlockWall {
    private MetaBlock metaBlock;
    public BaseMultiMetaWallBlock(MetaBlock metaBlock, int id, int metadata) {
        super(id, metaBlock);
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