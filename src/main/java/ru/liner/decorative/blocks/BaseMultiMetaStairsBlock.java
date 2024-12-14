package ru.liner.decorative.blocks;

import net.minecraft.block.BlockStairs;
import ru.liner.decorative.DecorativeMod;
import ru.liner.decorative.utils.MaterialFormatter;

@SuppressWarnings("unchecked")
public class BaseMultiMetaStairsBlock<MetaBlock extends BaseMultiMetaBlock> extends BlockStairs {
    private MetaBlock metaBlock;
    public BaseMultiMetaStairsBlock(MetaBlock metaBlock, int id, int metadata) {
        super(id, metaBlock, metadata);
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