package ru.liner.decorative.blocks.list;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

import java.util.Random;

public class BlockWoodenDoor extends BaseMultiMetaBlock {
    public BlockWoodenDoor(int blockID) {
        super(blockID, Material.wood);
        setCreativeTab(CreativeTabs.tabDecorations);
        setTextureParent("door");
        setUnlocalizedName("door");
        setBaseLocalizedName("Дверь");
        registerType("acacia", "Дверь из акации", 2, "acacia");
        registerType("birch", "Дверь из березы", 2, "birch");
        registerType("dark_oak", "Дверь из большого дуба", 2, "dark_oak");
        registerType("jungle", "Дверь из дерева джунглей", 2, "jungle");
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

//
//    @SideOnly(Side.CLIENT)
//    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
//        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
//        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
//    }
//
//    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
//        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
//        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
//    }
//
//    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
//        this.setDoorRotation(this.getFullMetadata(par1IBlockAccess, par2, par3, par4));
//    }
//
//    public int getDoorOrientation(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
//        return this.getFullMetadata(par1IBlockAccess, par2, par3, par4) & 3;
//    }
//
//    public boolean isDoorOpen(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
//        return (this.getFullMetadata(par1IBlockAccess, par2, par3, par4) & 4) != 0;
//    }
//
//    private void setDoorRotation(int par1) {
//        float f = 0.1875F;
//        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
//        int j = par1 & 3;
//        boolean flag = (par1 & 4) != 0;
//        boolean flag1 = (par1 & 16) != 0;
//        if (j == 0) {
//            if (flag) {
//                if (!flag1) {
//                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
//                } else {
//                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
//                }
//            } else {
//                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
//            }
//        } else if (j == 1) {
//            if (flag) {
//                if (!flag1) {
//                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//                } else {
//                    this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
//                }
//            } else {
//                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
//            }
//        } else if (j == 2) {
//            if (flag) {
//                if (!flag1) {
//                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
//                } else {
//                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
//                }
//            } else {
//                this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//            }
//        } else if (j == 3) {
//            if (flag) {
//                if (!flag1) {
//                    this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
//                } else {
//                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//                }
//            } else {
//                this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
//            }
//        }
//
//    }
//
//    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
//    }
//
//    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
//        if (this.blockMaterial == Material.iron) {
//            return false;
//        } else {
//            int i1 = this.getFullMetadata(par1World, par2, par3, par4);
//            int j1 = i1 & 7;
//            j1 ^= 4;
//            if ((i1 & 8) == 0) {
//                par1World.setBlockMetadataWithNotify(par2, par3, par4, j1, 2);
//                par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
//            } else {
//                par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, j1, 2);
//                par1World.markBlockRangeForRenderUpdate(par2, par3 - 1, par4, par2, par3, par4);
//            }
//
//            par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
//            return true;
//        }
//    }
//
//    public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5) {
//        int l = this.getFullMetadata(par1World, par2, par3, par4);
//        boolean flag1 = (l & 4) != 0;
//        if (flag1 != par5) {
//            int i1 = l & 7;
//            i1 ^= 4;
//            if ((l & 8) == 0) {
//                par1World.setBlockMetadataWithNotify(par2, par3, par4, i1, 2);
//                par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
//            } else {
//                par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, i1, 2);
//                par1World.markBlockRangeForRenderUpdate(par2, par3 - 1, par4, par2, par3, par4);
//            }
//
//            par1World.playAuxSFXAtEntity((EntityPlayer)null, 1003, par2, par3, par4, 0);
//        }
//
//    }
//
//    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
//        int i1 = par1World.getBlockMetadata(par2, par3, par4);
//        if ((i1 & 8) == 0) {
//            boolean flag = false;
//            if (par1World.getBlockId(par2, par3 + 1, par4) != this.blockID) {
//                par1World.setBlockToAir(par2, par3, par4);
//                flag = true;
//            }
//
//            if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) {
//                par1World.setBlockToAir(par2, par3, par4);
//                flag = true;
//                if (par1World.getBlockId(par2, par3 + 1, par4) == this.blockID) {
//                    par1World.setBlockToAir(par2, par3 + 1, par4);
//                }
//            }
//
//            if (flag) {
//                if (!par1World.isRemote) {
//                    this.dropBlockAsItem(par1World, par2, par3, par4, i1, 0);
//                }
//            } else {
//                boolean flag1 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
//                if ((flag1 || par5 > 0 && Block.blocksList[par5].canProvidePower()) && par5 != this.blockID) {
//                    this.onPoweredBlockChange(par1World, par2, par3, par4, flag1);
//                }
//            }
//        } else {
//            if (par1World.getBlockId(par2, par3 - 1, par4) != this.blockID) {
//                par1World.setBlockToAir(par2, par3, par4);
//            }
//
//            if (par5 > 0 && par5 != this.blockID) {
//                this.onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
//            }
//        }
//    }
//
//    public int idDropped(int par1, Random par2Random, int par3) {
//        return (par1 & 8) != 0 ? 0 : (this.blockMaterial == Material.iron ? Item.doorIron.itemID : Item.doorWood.itemID);
//    }
}
