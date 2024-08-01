package ru.liner.decorative.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialBarrier extends Material {
    public static MaterialBarrier barrier = new MaterialBarrier();
    public MaterialBarrier() {
        super(MapColor.airColor);
        setRequiresTool();
        setImmovableMobility();
    }
}
