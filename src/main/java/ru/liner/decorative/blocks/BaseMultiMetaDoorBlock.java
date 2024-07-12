package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("unchecked")
public class BaseMultiMetaDoorBlock extends BaseMultiMetaBlock {
    public BaseMultiMetaDoorBlock(int blockID, Material material) {
        super(blockID, material);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int fullMetadata = getFullMetadata(blockAccess, x, y, z);
        if (side != 1 && side != 0) {
            int doorOrientation = getDoorOrientation(fullMetadata);
            boolean doorOpened = isDoorOpened(fullMetadata);
            boolean doorTopBlock = isDoorTopBlock(fullMetadata);
            boolean requireTopTexture = false;
            if (doorTopBlock) {
                if (
                        (doorOrientation == 0 && side == 2) ||
                                (doorOrientation == 1 && side == 5) ||
                                (doorOrientation == 2 && side == 3) ||
                                (doorOrientation == 3 && side == 4)
                )
                    requireTopTexture = true;
            } else {
                if (
                        (doorOrientation == 0 && side == 5) ||
                                (doorOrientation == 1 && side == 3) ||
                                (doorOrientation == 2 && side == 4) ||
                                (doorOrientation == 3 && side == 2) ||
                                isDoorHingeOnLeft(fullMetadata)

                )
                    requireTopTexture = true;
            }

            String unlocalizedType = getTypeByMetadata(fullMetadata);
            TextureData textureData = textureDataMap.get(unlocalizedType).get(doorTopBlock ? 2 : 1);
            String textureName = String.format("%s_%s", textureParent, textureData.textureName);
            return textureMap.get(textureName);
        }
        return getIcon(side, fullMetadata);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 7;
    }


    @Override
    public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z) {
        return isDoorOpened(blockAccess, x, y, z);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        setDoorRotation(getFullMetadata(blockAccess, x, y, z));
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityPlayer) {

    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int hitX, float hitY, float hitZ, float side) {
        int metadata = getFullMetadata(world, x, y, z);
        int doorType = metadata & getTypesCount();
        if (isDoorTopBlock(metadata)) {
            world.setBlockMetadataWithNotify(x, y, z, doorType ^ 4, 2);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
        } else {
            world.setBlockMetadataWithNotify(x, y - 1, z, doorType ^ 4, 2);
            world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
        }
        world.playAuxSFXAtEntity(entityPlayer, 1003, x, y, z, 0);
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockId) {
        int metadata = getFullMetadata(world, x, y, z);
        boolean shouldRemoveBlock = false;
        if (!isDoorTopBlock(metadata)) {
            if (world.getBlockId(x, y + 1, z) != blockID) {
                shouldRemoveBlock = true;
                world.setBlockToAir(x, y, z);
            }
            if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z)) {
                shouldRemoveBlock = true;
                world.setBlockToAir(x, y, z);
                if (world.getBlockId(x, y + 1, z) == blockID)
                    world.setBlockToAir(x, y + 1, z);
            }
            if (shouldRemoveBlock) {
                if (!world.isRemote)
                    dropBlockAsItem(world, x, y, z, metadata, 0);
            } else {
                boolean isPoweredByRedstone = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);
                if ((isPoweredByRedstone || neighborBlockId > 0 && Block.blocksList[neighborBlockId].canProvidePower()) && neighborBlockId != blockID) {
                    onPoweredBlockChange(world, x, y, z, isPoweredByRedstone);
                }
            }
        } else {
            if (world.getBlockId(x, y - 1, z) != blockID)
                world.setBlockToAir(x, y, z);
            if (neighborBlockId > 0 && neighborBlockId != blockID)
                onNeighborBlockChange(world, x, y - 1, z, neighborBlockId);
        }
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return Blocks.woodenDoor.blockID;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVector, Vec3 endVector) {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, startVector, endVector);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.doesBlockHaveSolidTopSurface(x, y - 1, z) && super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z);
    }

    @Override
    public int getMobilityFlag() {
        return 1;
    }

    @Override
    public int idPicked(World world, int x, int y, int z) {
        return Blocks.woodenDoor.blockID;
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int par5, EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode && (par5 & 8) != 0 && world.getBlockId(x, y - 1, z) == this.blockID) {
            world.setBlockToAir(x, y - 1, z);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving, ItemStack stack) {
        int metadata = stack.getItemDamage();
        metadata |= 8;
        world.setBlock(
                x,
                y + 1,
                z,
                blockID,
                metadata,
                4
        );
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        world.setBlock(x, y, z, blockID, metadata, 4);
        world.setBlock(x, y + 1, z, blockID, metadata | 8, 4);
        world.markBlockForRenderUpdate(x, y, z);
        world.markBlockForRenderUpdate(x, y + 1, z);
        return metadata;
    }

    private void onPoweredBlockChange(World world, int x, int y, int z, boolean poweredByRedstone) {
        int metadata = getFullMetadata(world, x, y, z);
        if(!isDoorOpened(metadata) && poweredByRedstone){
            int doorType = getMetadataExcludeType(metadata) ^ 4;
            if (!isDoorTopBlock(metadata) && poweredByRedstone) {
                world.setBlockMetadataWithNotify(x, y, z, doorType, 2);
                world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            } else {
                world.setBlockMetadataWithNotify(x, y - 1, z, doorType, 2);
                world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
            }
            world.playAuxSFXAtEntity(null, 1003, x, y, z, 0);
        }
    }

    private void setDoorRotation(int metadata) {
        float thickness = 0.1875F;
        int doorOrientation = getDoorOrientation(metadata);
        boolean doorOpened = isDoorOpened(metadata);
        boolean doorHingeOnLeft = isDoorHingeOnLeft(metadata);

        setBlockBounds(0f, 0f, 0f, 1f, 2.0F, 1f);
        if (doorOrientation == 0) {
            if (doorOpened) {
                if (!doorHingeOnLeft) {
                    setBlockBounds(0f, 0f, 0f, 1f, 1f, thickness);
                } else {
                    setBlockBounds(0f, 0f, 1f - thickness, 1f, 1f, 1f);
                }
            } else {
                setBlockBounds(0f, 0f, 0f, thickness, 1f, 1f);
            }
        } else if (doorOrientation == 1) {
            if (doorOpened) {
                if (!doorHingeOnLeft) {
                    setBlockBounds(1f - thickness, 0f, 0f, 1f, 1f, 1f);
                } else {
                    setBlockBounds(0f, 0f, 0f, thickness, 1f, 1f);
                }
            } else {
                setBlockBounds(0f, 0f, 0f, 1f, 1f, thickness);
            }
        } else if (doorOrientation == 2) {
            if (doorOpened) {
                if (!doorHingeOnLeft) {
                    setBlockBounds(0f, 0f, 1f - thickness, 1f, 1f, 1f);
                } else {
                    setBlockBounds(0f, 0f, 0f, 1f, 1f, thickness);
                }
            } else {
                setBlockBounds(1f - thickness, 0f, 0f, 1f, 1f, 1f);
            }
        } else if (doorOrientation == 3) {
            if (doorOpened) {
                if (!doorHingeOnLeft) {
                    setBlockBounds(0f, 0f, 0f, thickness, 1f, 1f);
                } else {
                    setBlockBounds(1f - thickness, 0f, 0f, 1f, 1f, 1f);
                }
            } else {
                setBlockBounds(0f, 0f, 1f - thickness, 1f, 1f, 1f);
            }
        }
    }

    public int getFullMetadata(IBlockAccess blockAccess, int x, int y, int z) {
        int blockMetadata = blockAccess.getBlockMetadata(x, y, z);
        int topMetadata;
        int bottomMetadata;
        if (isDoorTopBlock(blockMetadata)) {
            topMetadata = blockMetadata;
            bottomMetadata = blockAccess.getBlockMetadata(x, y - 1, z);
        } else {
            topMetadata = blockAccess.getBlockMetadata(x, y + 1, z);
            bottomMetadata = blockMetadata;
        }
        return getMetadataExcludeType(bottomMetadata) | (isDoorTopBlock(blockMetadata) ? 8 : 0) | (isDoorHingeOnLeft(topMetadata) ? 16 : 0);
    }

    public int getDoorOrientation(IBlockAccess blockAccess, int x, int y, int z) {
        return getDoorOrientation(blockAccess.getBlockMetadata(x, y, z));
    }

    public int getDoorOrientation(int metadata) {
        return metadata & 3;
    }

    public boolean isDoorOpened(IBlockAccess blockAccess, int x, int y, int z) {
        return isDoorOpened(blockAccess.getBlockMetadata(x, y, z));
    }

    public boolean isDoorOpened(int metadata) {
        return (metadata & 4) != 0;
    }

    public boolean isDoorTopBlock(IBlockAccess blockAccess, int x, int y, int z) {
        return isDoorTopBlock(blockAccess.getBlockMetadata(x, y, z));
    }

    public boolean isDoorTopBlock(int metadata) {
        return (metadata & 8) != 0;
    }

    public boolean isDoorHingeOnLeft(IBlockAccess blockAccess, int x, int y, int z) {
        return isDoorHingeOnLeft(blockAccess.getBlockMetadata(x, y, z));
    }

    public boolean isDoorHingeOnLeft(int metadata) {
        return (metadata & 16) != 0;
    }

    public int getMetadataExcludeType(int metadata){
        return metadata & 7;
    }
}
