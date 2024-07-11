package ru.liner.decorative.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLogic;

public class MaterialCarpet extends MaterialLogic {
    public MaterialCarpet() {
        super(MapColor.clothColor);
        setBurning();
    }
}
