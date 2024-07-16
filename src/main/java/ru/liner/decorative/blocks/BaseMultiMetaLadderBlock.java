package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLadder;
import net.minecraft.client.renderer.texture.IconRegister;
import ru.liner.decorative.register.BlockRegister;
import ru.liner.decorative.utils.MaterialFormatter;

@SuppressWarnings("unchecked")
public class BaseMultiMetaLadderBlock<MetaBlock extends BaseMultiMetaBlock> extends BlockLadder {
    private MetaBlock metaBlock;
    private String texture;

    public BaseMultiMetaLadderBlock(MetaBlock metaBlock, int metadata) {
        super(BlockRegister.nextAvailableId(440 + metaBlock.blockID + metadata + 1));
        this.metaBlock = metaBlock;
        this.texture = String.format("ladder_%s", metaBlock.getTypeByMetadata(metadata));
        setLightOpacity(0);
        setUnlocalizedName(String.format("ladder.%s", metaBlock.getTypeByMetadata(metadata)));
    }

    public MetaBlock getMetaBlock() {
        return metaBlock;
    }

    public String getLocalizationFor(int metadata) {
        return MaterialFormatter.formatMaterial(MaterialFormatter.Type.LADDER, metaBlock.getTypeByMetadata(metadata));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon(this.texture);
    }

}