package ru.liner.decorative.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.Blocks;
import ru.liner.decorative.blocks.list.BlockBanner;
import ru.liner.decorative.tile.TileEntityBanner;

public class BaseMultiMetaBannerItem extends BaseMultiMetaItem<BlockBanner> {
    public BaseMultiMetaBannerItem(BlockBanner block) {
        super(block);
    }

    public BaseMultiMetaBannerItem(int blockID, Block block) {
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
        if (!entity.canPlayerEdit(x, y, z, side, itemStack) || !Blocks.banner.canPlaceBlockAt(world, x, y, z))
            return false;
        world.setBlock(x, y, z, Blocks.banner.blockID, itemStack.getItemDamage(), 2);
        TileEntity e = world.getBlockTileEntity(x, y, z);
        if (!(e instanceof TileEntityBanner))
            return false;
        TileEntityBanner banner = (TileEntityBanner) e;
        banner.setStandBanner(side == 1);
        banner.setColor(itemStack.getItemDamage());
        int wallRotation = (((MathHelper.floor_double(((entity.rotationYaw * 4.0f) / 360.0f) + 0.5d) & 3) + 2) % 4);
        int standRotation = MathHelper.floor_double((double) (entity.rotationYaw * 16.0F / 360.0F) + 0.5) & 15;
        if (entity.isSneaking()) {
            wallRotation = (wallRotation + 2) % 4;
            standRotation = (standRotation + 8) % 16;
        }
        banner.setRotation(side == 1 ? standRotation : wallRotation);
        --itemStack.stackSize;
        return true;
    }
}


