package ru.liner.decorative.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public class ViewDebugger {
    private static final Color backgroundColor = new Color(22, 23, 30, 200);
    private static final boolean drawBackground = true;
    private static final EnumChatFormatting NBT_STRING_COLOR = EnumChatFormatting.DARK_GREEN;
    private static final EnumChatFormatting NBT_INT_COLOR = EnumChatFormatting.DARK_AQUA;
    private static final EnumChatFormatting NBT_FLOAT_COLOR = EnumChatFormatting.GOLD;
    private static final EnumChatFormatting NBT_BYTE_COLOR = EnumChatFormatting.LIGHT_PURPLE;
    private static final EnumChatFormatting NBT_DOUBLE_COLOR = EnumChatFormatting.AQUA;
    private static final EnumChatFormatting NBT_LONG_COLOR = EnumChatFormatting.BLUE;
    private static final EnumChatFormatting NBT_SHORT_COLOR = EnumChatFormatting.YELLOW;
    private static final EnumChatFormatting TEXT_COLOR = EnumChatFormatting.GRAY;
    private static final EnumChatFormatting VALUES_COLOR = EnumChatFormatting.AQUA;
    private static final EnumChatFormatting TITLE_TEXT_COLOR = EnumChatFormatting.GREEN;
    private boolean enabled;
    private Minecraft minecraft;
    private World world;
    private EntityClientPlayerMP player;

    private int debugHeldItem(int x, int y, int windowWidth, int windowHeight) {
        ColoredText debug = ColoredText.make();
        debug.setBackgroundPadding(4);
        debug.setBackgroundColor(backgroundColor);
        debug.setDrawBackground(drawBackground);
        debug.append(" Предмет", EnumChatFormatting.GREEN).newLine();
        ItemStack itemStack = player.getHeldItem();
        if (itemStack == null) {
            debug.append("  Вам ").append("нужно ", EnumChatFormatting.DARK_RED).append("держать предмет в руке, что бы увидеть больше информации...").newLine();
        } else {
            Item item = itemStack.getItem();
            Icon icon = item == null ? null : item.getIconFromDamage(itemStack.getItemDamage());
            debug.append("  Предмет: ").append(itemStack.getDisplayName(), VALUES_COLOR).format(" [%s:%s]", itemStack.itemID, itemStack.getItemDamage(), VALUES_COLOR).newLine();
            debug.append("  Класс: ").append(item == null ? "Неизвестно" : item.getClass().getSimpleName(), item == null ? EnumChatFormatting.RED : VALUES_COLOR).newLine();
            debug.append("  Внутреннее имя: ").append(item == null ? "Неизвестно" : item.getUnlocalizedName(itemStack), item == null ? EnumChatFormatting.RED : VALUES_COLOR).newLine();
            debug.append("  Иконка: ").append(icon == null ? "Неизвестно" : icon.getIconName(), item == null ? EnumChatFormatting.RED : VALUES_COLOR).newLine();
            debug.append("  Содержит NBT: ").append(!itemStack.hasTagCompound() ? "нет" : "да", !itemStack.hasTagCompound() ? EnumChatFormatting.RED : VALUES_COLOR).newLine();
            if (itemStack.hasTagCompound()) {
                nbtToString(itemStack.getTagCompound(), 4, debug);
            }
        }
        debug.draw(x, y, windowWidth, windowHeight);
        return debug.calculateTextWidth();
    }

    private int debugViewingTile(int x, int y, int windowWidth, int windowHeight) {
        if (world == null)
            return 0;
        ColoredText debug = ColoredText.make();
        debug.setBackgroundPadding(4);
        debug.setBackgroundColor(backgroundColor);
        debug.setDrawBackground(drawBackground);
        debug.append(" Тайл", TITLE_TEXT_COLOR).newLine();
        MovingObjectPosition movingObjectPosition = minecraft.renderViewEntity.rayTrace(5, 1f);
        if (movingObjectPosition == null) {
            debug.append("  Вам ").append("нужно ", EnumChatFormatting.DARK_RED).append("смотреть на блок, что бы уидеть больше информации...").newLine();
        } else {
            TileEntity tileEntity = world.getBlockTileEntity(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
            if (tileEntity != null) {
                debug.append("  Тайл: ").append(tileEntity.getBlockType().getLocalizedName(), VALUES_COLOR).newLine();
                debug.append("  Класс: ").append(tileEntity.getClass().getSimpleName(), VALUES_COLOR).newLine();
                debug.append("  Внутреннее имя: ").append(tileEntity.getBlockType().getUnlocalizedName(), VALUES_COLOR).newLine();
                NBTTagCompound tagCompound = new NBTTagCompound();
                tileEntity.writeToNBT(tagCompound);
                debug.append("  Содержит NBT: ").append(tagCompound.getTags().isEmpty() ? "нет" : "да", tagCompound.getTags().isEmpty() ? EnumChatFormatting.RED : VALUES_COLOR).newLine();
                if (!tagCompound.getTags().isEmpty()) {
                    nbtToString(tagCompound, 4, debug);
                }
            } else {
                debug.append("  Вам ").append("нужно ", EnumChatFormatting.DARK_RED).append("смотреть на блок, что бы уидеть больше информации...").newLine();
            }
        }
        debug.draw(x, y, windowWidth, windowHeight);
        return debug.calculateTextWidth();
    }

    private int debugViewingBlock(int x, int y, int windowWidth, int windowHeight) {
        if (world == null)
            return 0;
        ColoredText debug = ColoredText.make();
        debug.setBackgroundPadding(4);
        debug.setBackgroundColor(backgroundColor);
        debug.setDrawBackground(drawBackground);
        debug.append(" Блок", TITLE_TEXT_COLOR).newLine();
        MovingObjectPosition movingObjectPosition = minecraft.renderViewEntity.rayTrace(5, 1f);
        if (movingObjectPosition == null) {
            debug.append("  Вам ").append("нужно ", EnumChatFormatting.DARK_RED).append("смотреть на блок, что бы увидеть больше информации..").newLine();
        } else {
            int blockId = world.getBlockId(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
            int blockMetadata = world.getBlockMetadata(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
            Block block = Block.blocksList[blockId];
            Icon icon = block.getIcon(2, blockMetadata);
            debug.append("  Блок: ").append(block.getLocalizedName(), VALUES_COLOR).format(" [%s:%s]", blockId, blockMetadata, EnumChatFormatting.DARK_AQUA).newLine();
            debug.append("  Класс: ").append(block.getClass().getSimpleName(), VALUES_COLOR).newLine();
            debug.append("  Внутреннее имя: ").append(block.getUnlocalizedName(), VALUES_COLOR).newLine();
            debug.append("  Иконка: ").append(icon == null ? "Неизвестно" : icon.getIconName(), VALUES_COLOR).newLine();
            debug.append("  Тип отрисовки: ").append(String.valueOf(block.getRenderType()), VALUES_COLOR).newLine();
            debug.append("  Рандомное обновление тиков: ").append(String.valueOf(block.getTickRandomly()), VALUES_COLOR).newLine();
            debug.append("  Скорость тиков: ").append(String.valueOf(block.tickRate(world)), VALUES_COLOR).newLine();
        }
        debug.draw(x, y, windowWidth, windowHeight);
        return debug.calculateTextWidth();
    }

    @ForgeSubscribe
    public void onGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL && enabled) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0,0,999);
            GL11.glScaled(2d / event.resolution.getScaleFactor(), 2d / event.resolution.getScaleFactor(), 0);
            if(world == null || player == null)
                return;
            int windowWidth = event.resolution.getScaledWidth();
            int windowHeight = event.resolution.getScaledHeight();
            int lastDrawWidth = debugHeldItem(2,2, windowWidth, windowHeight);
            lastDrawWidth += debugViewingBlock(lastDrawWidth + 10, 2, windowWidth, windowHeight);
            lastDrawWidth += debugViewingTile(lastDrawWidth + 18, 2, windowWidth, windowHeight);
            GL11.glPopMatrix();
        }
    }

    public void enable(){
        this.enabled = true;
        this.minecraft = Minecraft.getMinecraft();
        this.world = minecraft.theWorld;
        this.player = minecraft.thePlayer;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void disable(){
        this.enabled = false;
        MinecraftForge.EVENT_BUS.unregister(this);
        this.minecraft = null;
        this.player = null;
        this.world = null;
    }

    public boolean isEnabled() {
        return enabled;
    }


    private static String repeat(String text, int b) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < b; i++)
            stringBuilder.append(text);
        return stringBuilder.toString();
    }

    private ColoredText primitiveNbt(NBTBase base, int indent, ColoredText coloredText) {
        if (base instanceof NBTTagCompound) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": {");
            if (!((NBTTagCompound) base).hasNoTags())
                coloredText.newLine();
            coloredText = nbtToString((NBTTagCompound) base, indent, coloredText);
            coloredText.append(repeat(" ", indent)).append("}").append(" " + NBTBase.getTagName(base.getId()), TEXT_COLOR);
            if (!((NBTTagCompound) base).getTags().isEmpty()) coloredText.newLine();
        } else if (base instanceof NBTTagByte) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagByte) base).data), NBT_BYTE_COLOR).append(" (Byte)", TEXT_COLOR).newLine();
        } else if (base instanceof NBTTagByteArray) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(Arrays.toString(((NBTTagByteArray) base).byteArray), NBT_BYTE_COLOR).append(" (Byte array)", TEXT_COLOR).newLine();
        } else if (base instanceof NBTTagDouble) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagDouble) base).data), NBT_DOUBLE_COLOR).append(" (Double)", TEXT_COLOR).newLine();
        } else if (base instanceof NBTTagFloat) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagFloat) base).data), NBT_FLOAT_COLOR).append(" (Float)", TEXT_COLOR).newLine();
        } else if (base instanceof NBTTagInt) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagInt) base).data), NBT_INT_COLOR).append(" (Int)", TEXT_COLOR).newLine();
        } else if (base instanceof NBTTagIntArray) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(Arrays.toString(((NBTTagIntArray) base).intArray), NBT_INT_COLOR).append(" (Int array)", TEXT_COLOR).newLine();
        } else if (base instanceof NBTTagLong) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagLong) base).data), NBT_LONG_COLOR).append(" (Long)", TEXT_COLOR).newLine();
        } else if (base instanceof NBTTagShort) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagShort) base).data), NBT_SHORT_COLOR).append(" (Short)", TEXT_COLOR).newLine();
        } else if (base instanceof NBTTagString) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagString) base).data), NBT_STRING_COLOR).append(" (String)", TEXT_COLOR).newLine();
        } else {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(base), NBT_STRING_COLOR).append(" (?)", TEXT_COLOR).newLine();
        }
        return coloredText;
    }

    @SuppressWarnings("unchecked")
    public ColoredText nbtToString(NBTTagCompound tagCompound, int indent, ColoredText coloredText) {
        Collection<NBTBase> tags = tagCompound.getTags();
        for (NBTBase base : tags) {
            if (base instanceof NBTTagCompound) {
                coloredText.append(repeat(" ", indent)).append(base.getName()).append(": {");
                if (!((NBTTagCompound) base).hasNoTags())
                    coloredText.newLine();
                coloredText = nbtToString((NBTTagCompound) base, indent + 4, coloredText);
                coloredText.append(repeat(" ", indent)).append("}").append(" " + NBTBase.getTagName(base.getId()), TEXT_COLOR).newLine();
            } else if (base instanceof NBTTagList) {
                NBTTagList nbtTagList = (NBTTagList) base;
                coloredText.append(repeat(" ", indent)).append(base.getName()).append(": {");
                if (nbtTagList.tagCount() != 0) coloredText.newLine();
                for (int i = 0; i < nbtTagList.tagCount(); i++) {
                    NBTBase tagAt = nbtTagList.tagAt(i);
                    if (tagAt instanceof NBTTagCompound) {
                        coloredText = nbtToString((NBTTagCompound) tagAt, indent + 4, coloredText);
                    } else {
                        coloredText = primitiveNbt(tagAt, indent + 4, coloredText);
                    }
                }
                coloredText.append(repeat(" ", indent)).append("}").append(" " + NBTBase.getTagName(base.getId()), TEXT_COLOR).newLine();
            } else {
                coloredText = primitiveNbt(base, indent, coloredText);
            }
        }

        return coloredText;
    }
}
