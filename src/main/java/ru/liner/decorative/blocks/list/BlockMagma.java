package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.BaseMetaBlock;
import ru.liner.decorative.register.DamageSources;
import ru.liner.decorative.utils.Vector3;

import java.util.Random;

public class BlockMagma extends BaseMetaBlock {
    public BlockMagma(int itemId) {
        super(itemId, Material.rock);
        setHardness(5f);
        setResistance(10f);
        setTickRandomly(false);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("blockMagma");
        setBaseLocalizedName("Блок магмы");
        setTextureName("magma");
    }


    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (!entity.isImmuneToFire() && (entity instanceof EntityLiving)) {
            entity.attackEntityFrom(DamageSource.lava, 5);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        onEntityWalking(world, x,y,z, entity);
    }

    @Override
    public int getBlockColor() {
        return 16777215;
    }

    @Override
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        return 16777215;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @Override
    public int tickRate(World par1World) {
        return 30;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        //for(Vector3 waterPosition : new Vector3(x, y, z).locateMaterials(Material.water)){
        //    world.setBlockToAir((int) waterPosition.x, (int) waterPosition.y, (int) waterPosition.z);
        //    if (!world.isRemote)
        //        triggerLavaMixEffects(world, x, y, z);
        //}
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) {
        return !type.getCreatureMaterial().getCanBurn();
    }

    public void triggerLavaMixEffects(World world, int x, int y, int z) {
        world.playSoundEffect(x + 0.5f, y + 0.5f, z + 0.5f, "random.fizz", 0.5f, 2.6f + ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f));
        world.spawnParticle("smoke", x + Math.random(), y + 1.2d, z + Math.random(), 0.0d, 0.0d, 0.0d);
    }

}
