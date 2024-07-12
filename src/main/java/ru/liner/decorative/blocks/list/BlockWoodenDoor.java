package ru.liner.decorative.blocks.list;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMultiMetaDoorBlock;
import scala.util.parsing.combinator.testing.Str;

public class BlockWoodenDoor extends BaseMultiMetaDoorBlock {
    public BlockWoodenDoor(int blockID) {
        super(blockID, Material.wood);
        setCreativeTab(CreativeTabs.tabDecorations);
        setTextureParent("door");
        setUnlocalizedName("door");
        setBaseLocalizedName("Дверь");
        registerDoor("acacia", "Дверь из акации", "acacia");
        registerDoor("birch", "Дверь из березы", "birch");
        registerDoor("dark_oak", "Дверь из темного дуба", "dark_oak");
        registerDoor("jungle", "Дверь из дерева джунглей", "jungle");
        registerDoor("spruce", "Дверь из ели", "spruce");
    }



    private void registerDoor(String type, String localizedName, String textureName){
        registerType(type, localizedName,  textureName);
        registerTexture(type, String.format("%s_lower",textureName));
        registerTexture(type, String.format("%s_upper",textureName));
    }
}
