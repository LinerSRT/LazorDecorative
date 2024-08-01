package ru.liner.decorative.blocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Icon;
import ru.liner.decorative.core.Direction;

import java.util.HashMap;

public class BaseTextureSidedBlock extends BaseMetaBlock{
    
    private final HashMap<Direction, Icon> direction2Texture;
    
    private final HashMap<String, Direction> texture2Direction;
    public BaseTextureSidedBlock(int blockId, Material material) {
        super(blockId, material);
        this.direction2Texture = new HashMap<>();
        this.texture2Direction = new HashMap<>();
    }

    public BaseTextureSidedBlock addTexture(Direction direction, String texture){
        texture2Direction.put(texture, direction);
        return this;
    }

    @Override
    
    public void registerIcons(IconRegister register) {
        direction2Texture.clear();
        for (String texture : texture2Direction.keySet()) {
            direction2Texture.put(texture2Direction.get(texture), register.registerIcon(texture));
        }
    }

    @Override
    
    public Icon getIcon(int side, int metadata) {
        return direction2Texture.get(Direction.fromFacing(EnumFacing.values()[side]));
    }
}
