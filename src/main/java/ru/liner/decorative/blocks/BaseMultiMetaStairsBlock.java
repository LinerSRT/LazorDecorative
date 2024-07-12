package ru.liner.decorative.blocks;

import net.minecraft.block.BlockStairs;
import ru.liner.decorative.utils.MaterialFormatter;

@SuppressWarnings("unchecked")
public class BaseMultiMetaStairsBlock<MetaBlock extends BaseMultiMetaBlock> extends BlockStairs {
    private MetaBlock metaBlock;
    protected BaseMultiMetaStairsBlock(MetaBlock metaBlock, int metadata) {
        super(metaBlock.blockID + 256 + ((metaBlock.blockID % metaBlock.getTypesCount()) + metadata), metaBlock, metadata);
        this.metaBlock = metaBlock;
        setLightOpacity(0);
        setUnlocalizedName(String.format("stairs.%s", metaBlock.getTypeByMetadata(metadata)));
    }

    public MetaBlock getMetaBlock() {
        return metaBlock;
    }

    public String getLocalizationFor(int metadata) {
        return MaterialFormatter.formatMaterial(MaterialFormatter.Type.STAIRS, metaBlock.getTypeByMetadata(metadata));
    }
}