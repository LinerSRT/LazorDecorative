package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.BaseMetaBlock;
import ru.liner.decorative.register.Blocks;
import ru.liner.decorative.blocks.ILocalized;
import ru.liner.decorative.blocks.IUseBonemail;
import ru.liner.decorative.utils.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockChorusFlower extends BaseMetaBlock implements ILocalized, IUseBonemail {
    private static final float BLOCK_SIZE = .1f;
    private static final float BLOCK_UP_SIZE = .1f;
    public static final int MAX_AGE = 5;
    private int AGE = 0;
    private Icon deadTexture;

    public BlockChorusFlower(int itemId) {
        super(itemId, Material.rock);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(0.4f);
        //setBlockBounds(BLOCK_SIZE, 0f, BLOCK_SIZE, BLOCK_SIZE, 1 - BLOCK_UP_SIZE, BLOCK_SIZE);
        setStepSound(soundWoodFootstep);
        setUnlocalizedName("flower.chorus");
        setTextureName("chorus_flower");
        setBaseLocalizedName("Цветок хоруса");
        setTickRandomly(true);
    }

    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        deadTexture = register.registerIcon("chorus_flower_dead");
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return meta == 5 ? deadTexture : super.getIcon(side, meta);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        Vector3 pos = new Vector3(x, y, z);
        if (!canBlockStay(world, pos)) {
            world.destroyBlock(x, y, z, true);
            return;
        }
        Vector3 upPos = pos.up();
        int age = pos.blockMetadata(world);
        if (!upPos.isAirBlock(world) || upPos.y >= 256 || age >= 5) {
            return;
        }
        boolean canGrow = false;
        boolean canSupportGrowth = false;

        Block belowBlock = pos.down().asBlock(world);
        if (belowBlock == Block.whiteStone) {
            canGrow = true;
        } else if (belowBlock == Blocks.chorusPlant) {
            int depth = 1;
            int maxDepth = 4;

            while (depth <= maxDepth) {
                Block blockBelow = pos.down(depth + 1).asBlock(world);
                if (blockBelow == Blocks.chorusPlant) {
                    depth++;
                } else if (blockBelow == Block.whiteStone) {
                    canSupportGrowth = true;
                    break;
                } else {
                    break;
                }
            }

            if (canSupportGrowth) {
                maxDepth++;
            }

            if (depth < 2 || random.nextInt(maxDepth) >= depth) {
                canGrow = true;
            }
        } else if (belowBlock == null || belowBlock.blockMaterial == Material.air) {
            canGrow = true;
        }


        if (canGrow && canPlaceChorusPlant(world, upPos) && pos.up(2).isAirBlock(world)) {
            setBlockState(world, pos, Blocks.chorusPlant.blockID, 0);
            placeChorusFlower(world, upPos, age);
            return;
        }

        if (age == 4) {
            transformToChorusFlower(world, pos);
            return;
        }

        int branches = random.nextInt(4);
        if (canSupportGrowth) {
            branches++;
        }

        boolean branched = false;
        for (int i = 0; i < branches; i++) {
            EnumFacing direction = EnumFacing.values()[random.nextInt(3) + 2];
            Vector3 offsetPos = pos.offset(direction);
            if (offsetPos.isAirBlock(world) && offsetPos.down().isAirBlock(world) && canPlaceChorusPlant(world, offsetPos, getOpposite(direction))) {
                placeChorusFlower(world, offsetPos, age + 1);
                branched = true;
            }
        }

        if (branched) {
            setBlockState(world, pos, Blocks.chorusPlant.blockID, 0);
        } else {
            transformToChorusFlower(world, pos);
        }
    }

    private EnumFacing getOpposite(EnumFacing facing) {
        switch (facing) {
            case DOWN:
                return EnumFacing.UP;
            case UP:
                return EnumFacing.DOWN;
            case NORTH:
                return EnumFacing.SOUTH;
            case SOUTH:
                return EnumFacing.NORTH;
            case EAST:
                return EnumFacing.WEST;
            case WEST:
                return EnumFacing.EAST;
        }
        return facing;
    }

    private void setBlockState(World world, Vector3 position, int blockID, int metadata) {
        world.setBlock((int) position.x, (int) position.y, (int) position.z, blockID, metadata, 2);
    }

    private void placeChorusFlower(World world, Vector3 pos, int age) {
        setBlockState(world, pos, Blocks.chorusFlower.blockID, age);
    }

    private void transformToChorusFlower(World world, Vector3 pos) {
        setBlockState(world, pos, Blocks.chorusFlower.blockID, 5);
    }
    private void transformToChorusPlant(World world, Vector3 pos) {
        setBlockState(world, pos, Blocks.chorusPlant.blockID, 5);
    }

    private static boolean canPlaceChorusPlant(World world, Vector3 pos) {
        List<EnumFacing> facings = new ArrayList<>();
        facings.add(EnumFacing.NORTH);
        facings.add(EnumFacing.SOUTH);
        facings.add(EnumFacing.EAST);
        facings.add(EnumFacing.WEST);
        for (EnumFacing direction : facings) {
            if (!pos.offset(direction).isAirBlock(world)) {
                return false;
            }
        }
        return true;
    }

    private static boolean canPlaceChorusPlant(World world, Vector3 pos, EnumFacing excludeDirection) {
        List<EnumFacing> facings = new ArrayList<>();
        facings.add(EnumFacing.NORTH);
        facings.add(EnumFacing.SOUTH);
        facings.add(EnumFacing.EAST);
        facings.add(EnumFacing.WEST);
        for (EnumFacing direction : facings) {
            if (direction != excludeDirection && !pos.offset(direction).isAirBlock(world)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int block) {
        if (!canBlockStay(world, x, y, z)) {
            world.scheduleBlockUpdate(x, y, z, blockID, 1);
        }
    }


    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return canBlockStay(world, new Vector3(x, y, z));
    }

    public boolean canBlockStay(World world, Vector3 pos) {
        boolean foundValidBlock = false;
        for (EnumFacing face : EnumFacing.values()) {
            Vector3 position = pos.offset(face);
            if (position.isAirBlock(world))
                continue;
            if(position.hasAnyBlockAround(world, Blocks.chorusPlant.blockID, Block.whiteStone.blockID)){
                if(position.countBlockAround(world,Blocks.chorusPlant.blockID) < 2 && position.blockMetadata(world) > 1)
                    continue;
                foundValidBlock = true;
                break;
            }
        }
        return foundValidBlock;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata) {
        super.harvestBlock(world, player, x, y, z, metadata);
        //spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this)));
    }

    @Override
    protected ItemStack createStackedBlock(int block) {
        return null;
    }


    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
    }

    public void generateChorusPlant(World world, Vector3 pos, Random random, int radius) {
        setBlockState(world, pos, Blocks.chorusPlant.blockID, 0);
        growChorusPlant(world, pos, random, pos, radius, 0);
    }

    private void growChorusPlant(World world, Vector3 pos, Random random, Vector3 origin, int radius, int depth) {
        int height = random.nextInt(4) + 1;
        if (depth == 0) {
            height++;
        }

        for (int i = 0; i < height; i++) {
            Vector3 upPos = pos.up(i + 1);
            if (!canPlaceChorusPlant(world, upPos)) {
                return;
            }
            setBlockState(world, upPos, Blocks.chorusPlant.blockID, 0);
        }

        boolean branched = false;
        if (depth < 4) {
            int branches = random.nextInt(4);
            if (depth == 0) {
                branches++;
            }

            for (int i = 0; i < branches; i++) {
                EnumFacing direction = EnumFacing.values()[random.nextInt(3) + 2];
                Vector3 offsetPos = pos.up(height).offset(direction);

                if (Math.abs(offsetPos.x - origin.x) < radius && Math.abs(offsetPos.z - origin.z) < radius
                        && offsetPos.isAirBlock(world) && offsetPos.down().isAirBlock(world) && canPlaceChorusPlant(world, offsetPos, getOpposite(direction))) {
                    branched = true;
                    setBlockState(world, offsetPos, Blocks.chorusPlant.blockID, 0);
                    growChorusPlant(world, offsetPos, random, origin, radius, depth + 1);
                }
            }
        }

        if (!branched) {
            setBlockState(world, pos.up(height), Blocks.chorusFlower.blockID, 5);
        }
    }


    @Override
    public void applyBonemail(World world, int x, int y, int z) {
//        world.setBlockMetadataWithNotify(x,y,z, (world.getBlockMetadata(x,y,z)) | 8, 4);
//        updateTick(world, x,y,z, new Random());
    }
}
