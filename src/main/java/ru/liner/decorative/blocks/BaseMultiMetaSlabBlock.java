package ru.liner.decorative.blocks;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import ru.liner.decorative.utils.MaterialFormatter;

@SuppressWarnings("unchecked")
public class BaseMultiMetaSlabBlock<MetaBlock extends BaseMultiMetaBlock> extends BlockHalfSlab {
    private MetaBlock metaBlock;
    private int slabMetadata;

    protected BaseMultiMetaSlabBlock(MetaBlock metaBlock, int metadata) {
        super(metaBlock.blockID + 356 + ((metaBlock.blockID % metaBlock.getTypesCount()) + metadata), false, metaBlock.blockMaterial);
        this.metaBlock = metaBlock;
        this.slabMetadata = metadata;
        setLightOpacity(0);
        setUnlocalizedName(String.format("slab.%s", metaBlock.getTypeByMetadata(metadata)));
        setCreativeTab(metaBlock.getCreativeTabToDisplayOn());
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        return metaBlock.getIcon(side, slabMetadata);
    }

    public MetaBlock getMetaBlock() {
        return metaBlock;
    }

    public String getLocalizationFor(int metadata) {
        return MaterialFormatter.formatMaterial(MaterialFormatter.Type.SLAB, metaBlock.getTypeByMetadata(metadata));
    }

    public String getFullSlabName(int i) {
        return null;
    }

    public int idPicked(World world, int x, int y, int z) {
        return blockID;
    }
}