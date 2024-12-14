package ru.liner.decorative.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.list.BlockBanner;
import ru.liner.decorative.register.Registry;
import ru.liner.decorative.tile.TileEntityBanner;
import ru.liner.decorative.utils.ColoredText;

import java.util.List;

@SuppressWarnings("unchecked")
public class BaseMultiMetaBannerItem extends BaseMultiMetaItem<BlockBanner> {
    public BaseMultiMetaBannerItem(BlockBanner block) {
        super(block);
        setMaxStackSize(64);
    }

    public BaseMultiMetaBannerItem(int blockID, Block block) {
        super(blockID, block);
        setMaxStackSize(64);
    }

    @Override
    public String getItemDisplayName(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (tagCompound.hasKey("patterns")) {
                String originalName = super.getItemDisplayName(itemStack);
                return String.format("%s - содержит узоры", originalName);
            }
        }
        return super.getItemDisplayName(itemStack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltipList, boolean par4) {
        super.addInformation(stack, player, tooltipList, par4);
        if (stack.hasTagCompound()) {
            NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound.hasKey("patterns")) {
                NBTTagList patterns = tagCompound.getTagList("patterns");
                tooltipList.add(ColoredText.make().append("Нанесено узоров: ").append(String.valueOf(patterns.tagCount()), EnumChatFormatting.GOLD).get());
                tooltipList.add(ColoredText.make().append("Узоры: ").get());
                for (int i = 0; i < patterns.tagCount(); i++) {
                    String patternData = ((NBTTagString) patterns.tagAt(i)).data;
                    String patternId = patternData.contains("_") ? patternData.split("_")[0] : patternData;
                    int patternColor = patternData.contains("_") ? Integer.parseInt(patternData.split("_")[1]) : stack.getItemDamage();
                    TileEntityBanner.PatternType pattern = TileEntityBanner.PatternType.getPatternByID(patternId);
                    if(pattern != null){
                        tooltipList.add(ColoredText.make().format("     #%s", i, EnumChatFormatting.GOLD).append(" - ").append(pattern.getPatternLocalizedName(), EnumChatFormatting.GOLD).append(" | ").append(Decorative.colorNames[patternColor]).get());
                    }
                }
                tooltipList.add(ColoredText.make().append("Что бы снять последний нанесенный узор, промойте его ведром с водой").get());
            } else {
                tooltipList.add(ColoredText.make().append("Без узора", EnumChatFormatting.DARK_GRAY).get());
            }
        } else {
            tooltipList.add(ColoredText.make().append("Без узора", EnumChatFormatting.DARK_GRAY).get());
        }
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
        if (!entity.canPlayerEdit(x, y, z, side, itemStack) || !Registry.getInstance().block(BlockBanner.class).canPlaceBlockAt(world, x, y, z))
            return false;
        world.setBlock(x, y, z, Registry.getInstance().block(BlockBanner.class).blockID, itemStack.getItemDamage(), 2);
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
        banner.applyItem(itemStack);
        --itemStack.stackSize;
        return true;
    }
}


