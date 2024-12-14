package ru.liner.decorative.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.list.BlockBed;
import ru.liner.decorative.register.Registry;
import ru.liner.decorative.tile.TileEntityBanner;
import ru.liner.decorative.tile.TileEntityBed;

public class ItemBed extends BaseMultiMetaItem<BlockBed>{
    public ItemBed(BlockBed block) {
        super(block);
    }

    public ItemBed(int blockID, Block block) {
        super(blockID, block);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entity, World world, int x, int y, int z, int side, float var8, float var9, float var10) {
        if (side == 0 || !world.getBlockMaterial(x, y, z).isSolid())
            return false;
        switch (side) {
            case 1:
                ++y;
                break;
            case 2:
                --z;
                break;
            case 3:
                ++z;
                break;
            case 4:
                --x;
                break;
            case 5:
                ++x;
                break;
        }
        if (!entity.canPlayerEdit(x, y, z, side, itemStack) || !Registry.getInstance().block(BlockBed.class).canPlaceBlockAt(world, x, y, z))
            return false;
        world.setBlock(x, y, z, Registry.getInstance().block(BlockBed.class).blockID, itemStack.getItemDamage(), 2);
        TileEntity e = world.getBlockTileEntity(x, y, z);
        if (!(e instanceof TileEntityBed))
            return false;
        TileEntityBed bed = (TileEntityBed) e;
        bed.setColor(itemStack.getItemDamage());
        int rotation = (((MathHelper.floor_double(((entity.rotationYaw * 4.0f) / 360.0f) + 0.5d) & 3) + 2) % 4);
        if (entity.isSneaking()) {
            rotation = (rotation + 2) % 4;
        }
        bed.setRotation(rotation);
        bed.reloadNbt();
        --itemStack.stackSize;
        return true;
    }
}
