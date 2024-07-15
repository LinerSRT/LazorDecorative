package ru.liner.decorative.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialFlower extends Material {
    public MaterialFlower() {
        super(MapColor.foliageColor);
        setAlwaysHarvested();
    }
}
