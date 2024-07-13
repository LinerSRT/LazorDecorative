package ru.liner.decorative;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import org.lwjgl.opengl.GL11;
import ru.liner.decorative.blocks.BlockRegister;
import ru.liner.decorative.items.BaseMultiMetaItem;
import ru.liner.decorative.render.Renderers;
import ru.liner.decorative.utils.ColoredText;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

@Mod(modid = "lazor_decorative", version = "1.0", name = "Lazor Decorative")
public class DecorativeMod {
    @Mod.Instance("lazor_decorative")
    public static DecorativeMod INSTANCE;

    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent e) {
        Decorative.init();
        BlockRegister.init();
        Renderers.init();
    }

    @Mod.Init
    public void init(final FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.PostInit
    public void postInit(final FMLPostInitializationEvent e) {
        //System.out.println("PIDOR: "+Decorative.carpetItem.getUnlocalizedName());
    }


    @ForgeSubscribe
    public void onGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            GL11.glPushMatrix();
            GL11.glTranslatef(0, 0, 999);
            GL11.glScaled(2.0D / resolution.getScaleFactor(), 2.0D / resolution.getScaleFactor(), 0);


            MovingObjectPosition lookPosition = Minecraft.getMinecraft().renderViewEntity.rayTrace(200, 1f);
            WorldClient world = Minecraft.getMinecraft().theWorld;
            if (lookPosition != null && world != null) {
                int blockId = world.getBlockId(lookPosition.blockX, lookPosition.blockY, lookPosition.blockZ);
                int blockMetadata = world.getBlockMetadata(lookPosition.blockX, lookPosition.blockY, lookPosition.blockZ);
                Material blockMaterial = world.getBlockMaterial(lookPosition.blockX, lookPosition.blockY, lookPosition.blockZ);
                Block block = Block.blocksList[blockId];
                TileEntity tileEntity = world.getBlockTileEntity(lookPosition.blockX, lookPosition.blockY, lookPosition.blockZ);
                ItemStack itemStack = new ItemStack(block, 1, blockMetadata);
                Item item = itemStack.getItem();
                ColoredText coloredText = ColoredText.make()
                        .append("Look coordinates: ").format("x:%s y:%s z:%s", lookPosition.blockX, lookPosition.blockY, lookPosition.blockZ, EnumChatFormatting.GOLD).newLine()
                        .append("Block: ").append("(").append(block.getLocalizedName(), EnumChatFormatting.GOLD).append(") ").format("%s:%s", blockId, blockMetadata, EnumChatFormatting.GOLD).newLine()
                        .append("Block item: ").append(item == null ? "Unknown" : item.getItemDisplayName(itemStack), EnumChatFormatting.GOLD).append(" [").append(item == null ? "Unknown" : item.getClass().getSimpleName(), EnumChatFormatting.GOLD).append("]").newLine();

                coloredText.append("Block class: ").append(block.getClass().getSimpleName(), EnumChatFormatting.GOLD).newLine();
                coloredText.append("Block unlocalized name: ").append(block.getUnlocalizedName(), EnumChatFormatting.GOLD).newLine();
                coloredText.append("Block orientation: ").append(String.valueOf(blockMetadata & 3), EnumChatFormatting.GOLD).newLine();
                Icon blockIcon = block.getIcon(0, blockMetadata);
                Icon blockTexture = block.getBlockTexture(world, lookPosition.blockX, lookPosition.blockY, lookPosition.blockZ, lookPosition.sideHit);
                if (blockIcon != null) {
                    coloredText.append("Icon: ").append(blockIcon.getIconName(), EnumChatFormatting.GOLD).newLine();
                }
                if(blockTexture != null){
                    coloredText.append("Block texture: ").append(blockTexture.getIconName(), EnumChatFormatting.GOLD).newLine();
                }
                ItemStack heldItem = Minecraft.getMinecraft().thePlayer.getHeldItem();
                if (heldItem != null) {
                    coloredText.append("---------------------------------------").newLine();
                    coloredText.append("Held item: ").newLine();
                    coloredText.append("   Class: ").append(heldItem.getItem().getClass().getSimpleName(), EnumChatFormatting.GOLD).newLine();
                    coloredText.append("   Id/meta: ").format("%s:%s", heldItem.itemID, heldItem.getItemDamage(), EnumChatFormatting.GOLD).newLine();
                    coloredText.append("   Unlocalized name: ").append(heldItem.getItem().getUnlocalizedName(heldItem), EnumChatFormatting.GOLD).newLine();
                    coloredText.append("   Localized name: ").append(heldItem.getItem().getLocalizedName(heldItem), EnumChatFormatting.GOLD).newLine();
                    coloredText.append("   Display name: ").append(heldItem.getItem().getItemDisplayName(heldItem), EnumChatFormatting.GOLD).newLine();
                    Icon heldIcon = heldItem.getItem().getIconFromDamage(heldItem.getItemDamage());
                    if (heldIcon != null)
                        coloredText.append("   Icon name: ").append(heldIcon.getIconName(), EnumChatFormatting.GOLD).newLine();
                    Item currentItem = heldItem.getItem();
                    if(currentItem instanceof BaseMultiMetaItem){
                        BaseMultiMetaItem<?> multiMetaItem = (BaseMultiMetaItem<?>) currentItem;
                        coloredText.append("   Multi-meta type: ").append(multiMetaItem.getMetaBlock().getTypeByMetadata(heldItem.getItemDamage()), EnumChatFormatting.GOLD).newLine();
                    }
                }
                if(tileEntity != null){
                    coloredText.append("---------------------------------------").newLine();
                    coloredText.append("Tile Entity").newLine();
                    coloredText.append("   Class: ").append(tileEntity.getClass().getSimpleName(), EnumChatFormatting.GOLD).newLine();
                    coloredText.append("   Block metadata: ").append(String.valueOf(tileEntity.blockMetadata), EnumChatFormatting.GOLD).newLine();
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tileEntity.writeToNBT(tagCompound);
                    coloredText.append("   NTB tags: ").append(String.valueOf(tagCompound.getTags().size()), EnumChatFormatting.GOLD).newLine();
                    nbtToString(tagCompound, 4, coloredText);

                }
                coloredText.setDrawBackground(true);
                coloredText.setBackgroundColor(new Color(24, 28, 42, 195));
                coloredText.setBackgroundPadding(4);
                coloredText.draw(8, 8, resolution.getScaledWidth(), resolution.getScaledHeight());
            }

            GL11.glPopMatrix();
        }
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
            if(!((NBTTagCompound) base).hasNoTags())
                coloredText.newLine();
            coloredText = nbtToString((NBTTagCompound) base, indent, coloredText);
            coloredText.append(repeat(" ", indent)).append("}").append(" "+NBTBase.getTagName(base.getId()), EnumChatFormatting.GRAY);
            if (!((NBTTagCompound) base).getTags().isEmpty()) coloredText.newLine();
        } else if (base instanceof NBTTagByte) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagByte) base).data), EnumChatFormatting.DARK_PURPLE).append(" (Byte)", EnumChatFormatting.GRAY).newLine();
        } else if (base instanceof NBTTagByteArray) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(Arrays.toString(((NBTTagByteArray) base).byteArray), EnumChatFormatting.AQUA).append(" (Byte array)", EnumChatFormatting.GRAY).newLine();
        } else if (base instanceof NBTTagDouble) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagDouble) base).data), EnumChatFormatting.DARK_AQUA).append(" (Double)", EnumChatFormatting.GRAY).newLine();
        } else if (base instanceof NBTTagFloat) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagFloat) base).data), EnumChatFormatting.YELLOW).append(" (Float)", EnumChatFormatting.GRAY).newLine();
        } else if (base instanceof NBTTagInt) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagInt) base).data), EnumChatFormatting.DARK_BLUE).append(" (Int)", EnumChatFormatting.GRAY).newLine();
        } else if (base instanceof NBTTagIntArray) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(Arrays.toString(((NBTTagIntArray) base).intArray), EnumChatFormatting.AQUA).append(" (Int array)", EnumChatFormatting.GRAY).newLine();
        } else if (base instanceof NBTTagLong) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagLong) base).data), EnumChatFormatting.LIGHT_PURPLE).append(" (Long)", EnumChatFormatting.GRAY).newLine();
        } else if (base instanceof NBTTagShort) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagShort) base).data), EnumChatFormatting.DARK_PURPLE).append(" (Short)", EnumChatFormatting.GRAY).newLine();
        } else if (base instanceof NBTTagString) {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(((NBTTagString) base).data), EnumChatFormatting.DARK_GREEN).append(" (String)", EnumChatFormatting.GRAY).newLine();
        } else {
            coloredText.append(repeat(" ", indent)).append(base.getName()).append(": ").append(String.valueOf(base), EnumChatFormatting.DARK_AQUA).append(" (?)", EnumChatFormatting.GRAY).newLine();
        }
        return coloredText;
    }

    public ColoredText nbtToString(NBTTagCompound tagCompound, int indent, ColoredText coloredText) {
        Collection<NBTBase> tags = tagCompound.getTags();
        for (NBTBase base : tags) {
            if (base instanceof NBTTagCompound) {
                coloredText.append(repeat(" ", indent)).append(base.getName()).append(": {");
                if(!((NBTTagCompound) base).hasNoTags())
                    coloredText.newLine();
                coloredText = nbtToString((NBTTagCompound) base, indent + 4, coloredText);
                coloredText.append(repeat(" ", indent)).append("}").append(" "+NBTBase.getTagName(base.getId()), EnumChatFormatting.GRAY).newLine();
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
                coloredText.append(repeat(" ", indent)).append("}").append(" "+NBTBase.getTagName(base.getId()), EnumChatFormatting.GRAY).newLine();
            } else {
                coloredText = primitiveNbt(base, indent, coloredText);
            }
        }

        return coloredText;
    }
}
