package ru.liner.decorative.blocks.list;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BaseMetaBlock;
import ru.liner.decorative.utils.FacingUtils;
import ru.liner.decorative.utils.Vector3;

import java.util.HashMap;
import java.util.Random;

public class BlockObserver extends BaseMetaBlock {
    
    private HashMap<String, Icon> textureMap;

    public BlockObserver(int blockId) {
        super(blockId, Material.rock);
        setCreativeTab(CreativeTabs.tabRedstone);
        setUnlocalizedName("observer");
        setTextureName("observer");
        setBaseLocalizedName("Наблюдатель");
    }


    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return Decorative.REDSTONE_LOGIC_BLOCK_RENDERER;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemStack) {
        world.setBlockMetadataWithNotify(x, y, z, (((MathHelper.floor_double(((entity.rotationYaw * 4.0f) / 360.0f) + 0.5d) & 3) + 2) % 4), 2);
    }

    @Override
    public void registerIcons(IconRegister register) {
        textureMap = new HashMap<>();
        textureMap.put(getTexture("back"), register.registerIcon(getTexture("back")));
        textureMap.put(getTexture("back_lit"), register.registerIcon(getTexture("back_lit")));
        textureMap.put(getTexture("front"), register.registerIcon(getTexture("front")));
        textureMap.put(getTexture("side"), register.registerIcon(getTexture("side")));
        textureMap.put(getTexture("top"), register.registerIcon(getTexture("top")));
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (side == 1)
            return false;
        return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if(isPowered(blockAccess.getBlockMetadata(x,y,z)) && side == FacingUtils.metadata2Facing(blockAccess.getBlockMetadata(x,y,z)).ordinal())
            return 15;
        return 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if(isPowered(blockAccess.getBlockMetadata(x,y,z)) && side == FacingUtils.metadata2Facing(blockAccess.getBlockMetadata(x,y,z)).ordinal())
            return 15;
        return 0;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        super.updateTick(world, x, y, z, random);
        int metadata = world.getBlockMetadata(x, y, z);
        if(isPowered(metadata)){
            world.setBlockMetadataWithNotify(x,y,z, metadata & 8, 2);
        } else {
            world.setBlockMetadataWithNotify(x,y,z, metadata | 8, 2);
            world.scheduleBlockUpdate(x,y,z, blockID, 2);
        }
        updateNeighborsInFront(world, x,y,z, metadata);
    }

    private void updateNeighborsInFront(World world, int x, int y, int z, int metadata) {
        EnumFacing facing = FacingUtils.metadata2Facing(metadata);
        Vector3 position = new Vector3(x,y,z).offset(EnumFacing.getFront(facing.ordinal()));
        world.notifyBlockOfNeighborChange((int) position.x, (int) position.y, (int) position.z, blockID);
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        Vector3 position = new Vector3(x,y,z);
        if(!world.isRemote && position.offset(FacingUtils.metadata2Facing(world.getBlockMetadata(x,y,z))).blockId(world) == blockId)
            startSignal(world, x,y,z);
    }

    private void startSignal(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (isPowered(metadata))
            world.scheduleBlockUpdate(x, y, z, blockID, 2);
    }

    private boolean isPowered(int metadata) {
        return (metadata & 8) != 0;
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        int orientation = (metadata & 3);
        if (side == 1)
            return textureMap.get(getTexture("top"));
        switch (orientation) {
            case 0:
                if (side == 2) {
                    return textureMap.get(getTexture("front"));
                } else if (side == 3) {
                    return textureMap.get(getTexture("back"));
                } else {
                    return textureMap.get(getTexture("side"));
                }
            case 1:
                if (side == 5) {
                    return textureMap.get(getTexture("front"));
                } else if (side == 4) {
                    return textureMap.get(getTexture("back"));
                } else {
                    return textureMap.get(getTexture("side"));
                }
            case 2:
                if (side == 3) {
                    return textureMap.get(getTexture("front"));
                } else if (side == 2) {
                    return textureMap.get(getTexture("back"));
                } else {
                    return textureMap.get(getTexture("side"));
                }
            case 3:
                if (side == 4) {
                    return textureMap.get(getTexture("front"));
                } else if (side == 5) {
                    return textureMap.get(getTexture("back"));
                } else {
                    return textureMap.get(getTexture("side"));
                }
        }
        return textureMap.get(getTexture("side"));
    }

    private String getTexture(String type) {
        return String.format("%s_%s", textureName, type);
    }
}
