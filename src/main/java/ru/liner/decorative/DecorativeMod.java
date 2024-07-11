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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import org.lwjgl.opengl.GL11;
import ru.liner.decorative.blocks.BlockColoredPane;
import ru.liner.decorative.render.Renderers;
import ru.liner.decorative.utils.ColoredText;

import java.awt.*;

@Mod(modid = "lazor_decorative", version = "1.0", name = "Lazor Decorative")
public class DecorativeMod {
    @Mod.Instance("lazor_decorative")
    public static DecorativeMod INSTANCE;

    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent e) {
        Decorative.init();
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
                ItemStack itemStack = new ItemStack(block, 1, blockMetadata);
                Item item = itemStack.getItem();
                ColoredText coloredText = ColoredText.make()
                        .append("Look coordinates: ").format("x:%s y:%s z:%s", lookPosition.blockX, lookPosition.blockY, lookPosition.blockZ, EnumChatFormatting.GOLD).newLine()
                        .append("Block: ").append("(").append(block.getLocalizedName(), EnumChatFormatting.GOLD).append(") ").format("%s:%s", blockId, blockMetadata, EnumChatFormatting.GOLD).newLine()
                        .append("Block item: ").append(item == null ? "Unknown" : item.getItemDisplayName(itemStack), EnumChatFormatting.GOLD).append(" [").append(item == null ? "Unknown" : item.getClass().getSimpleName(), EnumChatFormatting.GOLD).append("]").newLine();
                Icon blockIcon = block.getIcon(0, blockMetadata);
                if(blockIcon != null){
                    coloredText.append("Icon: ").append(blockIcon.getIconName(), EnumChatFormatting.GOLD).newLine();
                }
                if(block instanceof BlockColoredPane){
                    BlockColoredPane coloredPane = (BlockColoredPane) block;
                    Icon sidedIcon = coloredPane.getSideIcon(0, blockMetadata);
                    if(sidedIcon != null){
                        coloredText.append("Icon side: ").append(sidedIcon.getIconName(), EnumChatFormatting.GOLD).newLine();
                    }
                }
                ItemStack heldItem = Minecraft.getMinecraft().thePlayer.getHeldItem();
                if (heldItem != null) {
                    coloredText.append("---------------------------------------").newLine();
                    coloredText.append("Item: ").newLine();
                    coloredText.append("   Class: ").append(heldItem.getItem().getClass().getSimpleName(), EnumChatFormatting.GOLD).newLine();
                    coloredText.append("   Id/meta: ").format("%s:%s", heldItem.itemID, heldItem.getItemDamage(), EnumChatFormatting.GOLD).newLine();
                    coloredText.append("   Unlocalized name: ").append(heldItem.getItem().getUnlocalizedName(heldItem), EnumChatFormatting.GOLD).newLine();
                    coloredText.append("   Localized name: ").append(heldItem.getItem().getLocalizedName(heldItem), EnumChatFormatting.GOLD).newLine();
                    coloredText.append("   Display name: ").append(heldItem.getItem().getItemDisplayName(heldItem), EnumChatFormatting.GOLD).newLine();
                }
                coloredText.setDrawBackground(true);
                coloredText.setBackgroundColor(Color.DARK_GRAY);
                coloredText.setBackgroundPadding(4);
                coloredText.draw(16, 16, resolution.getScaledWidth(), resolution.getScaledHeight());
            }

            GL11.glPopMatrix();
        }
    }
}
