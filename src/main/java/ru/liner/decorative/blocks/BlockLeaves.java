package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liner.decorative.Decorative;

import java.util.ArrayList;
import java.util.Random;

public class BlockLeaves extends BaseMultiBlock {
    @SideOnly(Side.CLIENT)
    private Icon[] opaqueIcons;
    @SideOnly(Side.CLIENT)
    private int graphicsDetails;
    int[] adjacentTreeBlocks;

    public BlockLeaves(int blockId) {
        super(blockId, Material.wood, Type.LEAVES);
        registerType("acacia", "акации");
        registerType("big_oak", "большого дуба");
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(0.2f);
        setLightOpacity(1);
        setStepSound(Block.soundGrassFootstep);
        setTextureName("leaves");
        setUnlocalizedName("leaves");
    }


    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        double d0 = 0.5;
        double d1 = 1.0;
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int par1) {
        return (par1 & 3) == 1 ? ColorizerFoliage.getFoliageColorPine() : ((par1 & 3) == 2 ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.getFoliageColorBasic());
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if ((l & 3) == 1) {
            return ColorizerFoliage.getFoliageColorPine();
        } else if ((l & 3) == 2) {
            return ColorizerFoliage.getFoliageColorBirch();
        } else {
            int i1 = 0;
            int j1 = 0;
            int k1 = 0;

            for (int l1 = -1; l1 <= 1; ++l1) {
                for (int i2 = -1; i2 <= 1; ++i2) {
                    int j2 = par1IBlockAccess.getBiomeGenForCoords(par2 + i2, par4 + l1).getBiomeFoliageColor();
                    i1 += (j2 & 16711680) >> 16;
                    j1 += (j2 & '\uff00') >> 8;
                    k1 += j2 & 255;
                }
            }

            return (i1 / 9 & 255) << 16 | (j1 / 9 & 255) << 8 | k1 / 9 & 255;
        }
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        byte b0 = 1;
        int j1 = b0 + 1;
        if (par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1)) {
            for (int k1 = -b0; k1 <= b0; ++k1) {
                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        int j2 = par1World.getBlockId(par2 + k1, par3 + l1, par4 + i2);
                        if (Block.blocksList[j2] != null) {
                            Block.blocksList[j2].beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
                        }
                    }
                }
            }
        }
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!par1World.isRemote) {
            int l = par1World.getBlockMetadata(par2, par3, par4);
            if ((l & 8) != 0 && (l & 4) == 0) {
                byte b0 = 4;
                int i1 = b0 + 1;
                byte b1 = 32;
                int j1 = b1 * b1;
                int k1 = b1 / 2;
                if (this.adjacentTreeBlocks == null) {
                    this.adjacentTreeBlocks = new int[b1 * b1 * b1];
                }

                int l1;
                if (par1World.checkChunksExist(par2 - i1, par3 - i1, par4 - i1, par2 + i1, par3 + i1, par4 + i1)) {
                    l1 = -b0;

                    label117:
                    while (true) {
                        int i2;
                        int j2;
                        int k2;
                        if (l1 > b0) {
                            l1 = 1;

                            while (true) {
                                if (l1 > 4) {
                                    break label117;
                                }

                                for (i2 = -b0; i2 <= b0; ++i2) {
                                    for (j2 = -b0; j2 <= b0; ++j2) {
                                        for (k2 = -b0; k2 <= b0; ++k2) {
                                            if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
                                                if (this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                                    this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
                                                }

                                                if (this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                                    this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
                                                }

                                                if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
                                                    this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1;
                                                }

                                                if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
                                                    this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1;
                                                }

                                                if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2) {
                                                    this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1;
                                                }

                                                if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
                                                    this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1;
                                                }
                                            }
                                        }
                                    }
                                }

                                ++l1;
                            }
                        }

                        for (i2 = -b0; i2 <= b0; ++i2) {
                            for (j2 = -b0; j2 <= b0; ++j2) {
                                k2 = par1World.getBlockId(par2 + l1, par3 + i2, par4 + j2);
                                Block block = Block.blocksList[k2];
                                if (block != null && block.canSustainLeaves(par1World, par2 + l1, par3 + i2, par4 + j2)) {
                                    this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
                                } else if (block != null && block.isLeaves(par1World, par2 + l1, par3 + i2, par4 + j2)) {
                                    this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
                                } else {
                                    this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
                                }
                            }
                        }

                        ++l1;
                    }
                }

                l1 = this.adjacentTreeBlocks[k1 * j1 + k1 * b1 + k1];
                if (l1 >= 0) {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, l & -9, 4);
                } else {
                    this.removeLeaves(par1World, par2, par3, par4);
                }
            }
        }

    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (par1World.canLightningStrikeAt(par2, par3 + 1, par4) && !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && par5Random.nextInt(15) == 1) {
            double d0 = (double) ((float) par2 + par5Random.nextFloat());
            double d1 = (double) par3 - 0.05;
            double d2 = (double) ((float) par4 + par5Random.nextFloat());
            par1World.spawnParticle("dripWater", d0, d1, d2, 0.0, 0.0, 0.0);
        }

    }

    private void removeLeaves(World par1World, int par2, int par3, int par4) {
        this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
        par1World.setBlockToAir(par2, par3, par4);
    }


    public int quantityDropped(Random par1Random) {
        return par1Random.nextInt(20) == 0 ? 1 : 0;
    }


    public int idDropped(int par1, Random par2Random, int par3) {
        return Decorative.saplingBlock.blockID;
    }


    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        if (!par1World.isRemote) {
            int j1 = 20;
            if ((par5 & 3) == 3) {
                j1 = 40;
            }

            if (par7 > 0) {
                j1 -= 2 << par7;
                if (j1 < 10) {
                    j1 = 10;
                }
            }

            if (par1World.rand.nextInt(j1) == 0) {
                int k1 = this.idDropped(par5, par1World.rand, par7);
                this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(k1, 1, this.damageDropped(par5)));
            }

            j1 = 200;
            if (par7 > 0) {
                j1 -= 10 << par7;
                if (j1 < 40) {
                    j1 = 40;
                }
            }

            if ((par5 & 3) == 0 && par1World.rand.nextInt(j1) == 0) {
                this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.appleRed, 1, 0));
            }
        }

    }

    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
        super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
    }

    public int damageDropped(int par1) {
        return par1 & 3;
    }

    public boolean isOpaqueCube() {
        return graphicsDetails == 1;
    }


    @Override
    public Icon getIcon(int side, int metadata) {
        return graphicsDetails == 1 ? opaqueIcons[metadata % opaqueIcons.length] : super.getIcon(side, metadata);
    }

    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        opaqueIcons = new Icon[types.size()];
        for (int i = 0; i < opaqueIcons.length; i++) {
            opaqueIcons[i] = register.registerIcon(String.format("%s_%s_opaque", textureName, typeAt(i)));
        }
    }


    @SideOnly(Side.CLIENT)
    public void setGraphicsLevel(boolean par1) {
        this.graphicsDetails = par1 ? 0 : 1;
    }


    protected ItemStack createStackedBlock(int metadata) {
        return new ItemStack(this.blockID, 1, metadata & types.size());
    }

    public boolean isShearable(ItemStack item, World world, int x, int y, int z) {
        return true;
    }

    public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & types.size()));
        return ret;
    }

    public void beginLeavesDecay(World world, int x, int y, int z) {
        world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8, 4);
    }


    public boolean isLeaves(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int blockId = blockAccess.getBlockId(x, y, z);
        int blockMetadata = blockAccess.getBlockMetadata(x, y, z);
        Block block = Block.blocksList[blockId];
        if (graphicsDetails == 0)
            return true;
        if (blockMetadata != blockAccess.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]))
            return true;
        if (block == this)
            return false;
        return (graphicsDetails == 0 || blockId != this.blockID);
    }
}