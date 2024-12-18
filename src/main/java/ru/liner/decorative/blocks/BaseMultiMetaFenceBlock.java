package ru.liner.decorative.blocks;

import net.minecraft.block.BlockFence;
import ru.liner.decorative.utils.MaterialFormatter;

@SuppressWarnings("unchecked")
public class BaseMultiMetaFenceBlock<MetaBlock extends BaseMultiMetaBlock> extends BlockFence {
    private MetaBlock metaBlock;

    public BaseMultiMetaFenceBlock(MetaBlock metaBlock, int id, int metadata) {
        super(id, metaBlock.getTextureFor(metadata), metaBlock.blockMaterial);
        this.metaBlock = metaBlock;
        setLightOpacity(0);
        setUnlocalizedName(String.format("fence.%s", metaBlock.getTypeByMetadata(metadata)));
    }

    public MetaBlock getMetaBlock() {
        return metaBlock;
    }

    public String getLocalizationFor(int metadata) {
        return MaterialFormatter.formatMaterial(MaterialFormatter.Type.FENCE, metaBlock.getTypeByMetadata(metadata));
    }
}