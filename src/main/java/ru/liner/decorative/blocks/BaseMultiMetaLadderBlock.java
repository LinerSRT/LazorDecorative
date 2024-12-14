package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLadder;
import net.minecraft.client.renderer.texture.IconRegister;
import ru.liner.decorative.DecorativeMod;
import ru.liner.decorative.utils.MaterialFormatter;

@SuppressWarnings("unchecked")
public class BaseMultiMetaLadderBlock<MetaBlock extends BaseMultiMetaBlock> extends BlockLadder {
    private MetaBlock metaBlock;
    private String texture;

    public BaseMultiMetaLadderBlock(MetaBlock metaBlock, int id, int metadata) {
        super(id);
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
    
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon(this.texture);
    }

}