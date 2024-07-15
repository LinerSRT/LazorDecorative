package ru.liner.decorative;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import ru.liner.decorative.blocks.*;
import ru.liner.decorative.enity.Entities;
import ru.liner.decorative.items.BaseMultiItem;
import ru.liner.decorative.items.BaseMultiMetaItem;
import ru.liner.decorative.items.Items;
import ru.liner.decorative.recipes.BannerRecipes;
import ru.liner.decorative.render.Renderers;
import ru.liner.decorative.utils.ColoredText;
import ru.liner.decorative.utils.Input;
import ru.liner.decorative.utils.MinecraftField;
import ru.liner.decorative.utils.Ticker;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

@Mod(modid = DecorativeMod.MOD_ID, version = "1.0", name = "Lazor Decorative")
@NetworkMod
public class DecorativeMod {
    public static final String MOD_ID = "lazor_decorative";
    @Mod.Instance(MOD_ID)
    public static DecorativeMod INSTANCE;
    private static boolean alwaysDay = true;
    private static boolean alwaysClearWeather = true;
    private static float minecraftTickSpeed = 1;
    private static float originalTickSpeed = -1;
    private static boolean shouldBoosTickSpeed = false;

    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent e) {
        Decorative.init();
        BlockRegister.init();
        Items.init();
        Renderers.init();
        Entities.init();
        BannerRecipes bannerRecipes = new BannerRecipes();
        bannerRecipes.addRecipes();
    }

    @Mod.Init
    public void init(final FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.PostInit
    public void postInit(final FMLPostInitializationEvent e) {
        Ticker.register(new Ticker.ITick() {
            private int tickSpeed = 20;

            @Override
            public void onTick() {
                if (Input.isKeyPressed(Keyboard.KEY_NUMPAD0))
                    alwaysDay = !alwaysDay;
                if (Input.isKeyPressed(Keyboard.KEY_NUMPAD1))
                    alwaysClearWeather = !alwaysClearWeather;
                if (Input.isKeyPressed(Keyboard.KEY_LEFT)) {
                    minecraftTickSpeed = Math.max(0.1f, minecraftTickSpeed - .1f);
                }
                if (Input.isKeyPressed(Keyboard.KEY_RIGHT)) {
                    minecraftTickSpeed = Math.min(20, minecraftTickSpeed + .1f);
                }
                shouldBoosTickSpeed = Input.isKeyDown(Keyboard.KEY_NUMPAD2);
                if (shouldBoosTickSpeed) {
                    MinecraftField.TIMER.get(Minecraft.getMinecraft()).timerSpeed = shouldBoosTickSpeed ? 20 : 1;
                    if (originalTickSpeed == -1)
                        originalTickSpeed = minecraftTickSpeed;
                    minecraftTickSpeed = 20;
                } else {
                    if (originalTickSpeed != -1)
                        minecraftTickSpeed = originalTickSpeed;
                    originalTickSpeed = -1;
                }
                if (tickSpeed <= 0) {
                    WorldClient world = Minecraft.getMinecraft().theWorld;
                    if (world != null) {
                        if (alwaysDay) {
                            for (int i = 0; i < MinecraftServer.getServer().worldServers.length; ++i)
                                MinecraftServer.getServer().worldServers[i].setWorldTime(1000);
                        }
                        if (alwaysClearWeather) {
                            world.getWorldInfo().setRaining(false);
                            world.getWorldInfo().setThundering(false);
                        }
                    }
                    if (!shouldBoosTickSpeed) {
                        MinecraftField.TIMER.get(Minecraft.getMinecraft()).timerSpeed = minecraftTickSpeed;
                    }
                    tickSpeed = 20;
                }
                tickSpeed--;
            }
        }, TickType.CLIENT);
    }

    @ForgeSubscribe
    public void onApplyBoneMail(BonemealEvent event) {
        int blockId = event.world.getBlockId(event.X, event.Y, event.Z);
        Block block = Block.blocksList[blockId];
        if(block instanceof IUseBonemail){
            event.setResult(Event.Result.ALLOW);
            ((IUseBonemail)block).applyBonemail(event.world, event.X, event.Y, event.Z);
        }
    }

    @ForgeSubscribe
    public void onGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            GL11.glPushMatrix();
            GL11.glTranslatef(0, 0, 999);
            GL11.glScaled(2.0D / resolution.getScaleFactor(), 2.0D / resolution.getScaleFactor(), 0);
            int width = debugHeldItem(Minecraft.getMinecraft().thePlayer, 2, 2, resolution.getScaledWidth(), resolution.getScaledHeight());
            width += debugViewingBlock(Minecraft.getMinecraft().theWorld, width + 10, 2, resolution.getScaledWidth(), resolution.getScaledHeight());
            width += debugViewingTile(Minecraft.getMinecraft().theWorld, width + 18, 2, resolution.getScaledWidth(), resolution.getScaledHeight());
            ColoredText utilityDisplay = ColoredText.make()
                    .setBackgroundPadding(4)
                    .setBackgroundColor(new Color(22, 23, 30, 200))
                    .setDrawBackground(true)
                    .append("Tick speed: <[").append(String.valueOf(minecraftTickSpeed), EnumChatFormatting.GOLD).append("]> ").append("(arrow_left, arrow_right)", EnumChatFormatting.GRAY).newLine()
                    .append("Always day: [").append(String.valueOf(alwaysDay), alwaysDay ? EnumChatFormatting.GREEN : EnumChatFormatting.RED).append("] ").append("(num0)", EnumChatFormatting.GRAY).newLine()
                    .append("Always clear weather: [").append(String.valueOf(alwaysClearWeather), alwaysClearWeather ? EnumChatFormatting.GREEN : EnumChatFormatting.RED).append("] ").append("(num1)", EnumChatFormatting.GRAY).newLine()
                    .append("Boost tick speed: [").append(String.valueOf(shouldBoosTickSpeed), shouldBoosTickSpeed ? EnumChatFormatting.GREEN : EnumChatFormatting.RED).append("] ").append("(num2)", EnumChatFormatting.GRAY).newLine();

            int utilityWidth = utilityDisplay.calculateTextWidth();
            int utilityHeight = utilityDisplay.calculateTextHeight();
            utilityDisplay.draw(resolution.getScaledWidth() - utilityWidth - 2, resolution.getScaledHeight() - utilityHeight - 2, resolution.getScaledWidth(), resolution.getScaledHeight());


            GL11.glPopMatrix();
        }
    }


    private int debugViewingTile(World world, int x, int y, int windowWidth, int windowHeight) {
        if (world == null)
            return 0;
        ColoredText debug = ColoredText.make();
        debug.setBackgroundPadding(4);
        debug.setBackgroundColor(new Color(22, 23, 30, 200));
        debug.setDrawBackground(true);
        debug.append(" Viewing tile", EnumChatFormatting.GREEN).newLine();
        MovingObjectPosition movingObjectPosition = Minecraft.getMinecraft().renderViewEntity.rayTrace(5, 1f);
        if (movingObjectPosition == null) {
            debug.append("  You ").append("need ", EnumChatFormatting.DARK_RED).append("view at block to see more info...").newLine();
        } else {
            TileEntity tileEntity = world.getBlockTileEntity(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
            if (tileEntity != null) {
                debug.append("  TileEntity: ").append(tileEntity.getBlockType().getLocalizedName(), EnumChatFormatting.AQUA).newLine();
                debug.append("  TileEntity class: ").append(tileEntity.getClass().getSimpleName(), EnumChatFormatting.AQUA).newLine();
                debug.append("  TileEntity unlocalized name: ").append(tileEntity.getBlockType().getUnlocalizedName(), EnumChatFormatting.AQUA).newLine();
                NBTTagCompound tagCompound = new NBTTagCompound();
                tileEntity.writeToNBT(tagCompound);
                debug.append("  TileEntity has NBT: ").append(tagCompound.getTags().isEmpty() ? "false" : "true", tagCompound.getTags().isEmpty() ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
                if (!tagCompound.getTags().isEmpty()) {
                    nbtToString(tagCompound, 4, debug);
                }
            } else {
                debug.append("  You ").append("need ", EnumChatFormatting.DARK_RED).append("view at block to see more info...").newLine();
            }
        }
        debug.draw(x, y, windowWidth, windowHeight);
        return debug.calculateTextWidth();
    }

    private int debugViewingBlock(World world, int x, int y, int windowWidth, int windowHeight) {
        if (world == null)
            return 0;
        ColoredText debug = ColoredText.make();
        debug.setBackgroundPadding(4);
        debug.setBackgroundColor(new Color(22, 23, 30, 200));
        debug.setDrawBackground(true);
        debug.append(" Viewing block", EnumChatFormatting.GREEN).newLine();
        MovingObjectPosition movingObjectPosition = Minecraft.getMinecraft().renderViewEntity.rayTrace(5, 1f);
        if (movingObjectPosition == null) {
            debug.append("  You ").append("need ", EnumChatFormatting.DARK_RED).append("view at block to see more info...").newLine();
        } else {
            int blockId = world.getBlockId(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
            int blockMetadata = world.getBlockMetadata(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
            Block block = Block.blocksList[blockId];
            Icon icon = block.getIcon(2, blockMetadata);
            boolean isMetaBlock = (block instanceof BaseMultiMetaBlock || block.getClass().getSimpleName().contains("Meta") || block.getClass().getSimpleName().contains("Multi"));
            debug.append("  Block: ").append(block.getLocalizedName(), EnumChatFormatting.AQUA).format(" [%s:%s]", blockId, blockMetadata, EnumChatFormatting.DARK_AQUA).newLine();
            debug.append("  Block class: ").append(block.getClass().getSimpleName(), EnumChatFormatting.AQUA).newLine();
            debug.append("  Block unlocalized name: ").append(block.getUnlocalizedName(), EnumChatFormatting.AQUA).newLine();
            debug.append("  Block icon: ").append(icon == null ? "Unknown" : icon.getIconName(), EnumChatFormatting.AQUA).newLine();
            debug.append("  Block render type: ").append(String.valueOf(block.getRenderType()), EnumChatFormatting.AQUA).newLine();
            debug.append("  Block random tick: ").append(String.valueOf(block.getTickRandomly()), EnumChatFormatting.AQUA).newLine();
            debug.append("  Block random tick speed: ").append(String.valueOf(block.tickRate(world)), EnumChatFormatting.AQUA).newLine();
            debug.append("  Meta block: ").append(isMetaBlock ? "true" : "false", !isMetaBlock ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
            if (isMetaBlock) {
                if (block instanceof BaseMultiMetaBlock)
                    debugMetaBlock((BaseMultiMetaBlock) block, debug, blockMetadata, icon);
                if (block instanceof BaseMultiMetaSlabBlock)
                    debugMetaBlock(((BaseMultiMetaSlabBlock<?>) block).getMetaBlock(), debug, blockMetadata, icon);
                if (block instanceof BaseMultiMetaStairsBlock)
                    debugMetaBlock(((BaseMultiMetaStairsBlock<?>) block).getMetaBlock(), debug, blockMetadata, icon);
            }
        }
        debug.draw(x, y, windowWidth, windowHeight);
        return debug.calculateTextWidth();
    }

    private void debugMetaBlock(BaseMultiMetaBlock metaBlock, ColoredText coloredText, int blockMetadata, Icon icon) {
        coloredText.append("  Meta has types: ").append(!metaBlock.hasTypes() ? "false" : "true", !metaBlock.hasTypes() ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
        if (metaBlock.hasTypes()) {
            coloredText.append("  Meta type count: ").append(String.valueOf(metaBlock.getTypesCount()), EnumChatFormatting.AQUA).newLine();
            coloredText.append("  Meta type: ").append(metaBlock.getTypeByMetadata(blockMetadata), EnumChatFormatting.AQUA).newLine();
            coloredText.append("  Meta icon: ").append(icon == null ? "Unknown" : metaBlock.getIcon(2, blockMetadata).getIconName(), EnumChatFormatting.AQUA).newLine();
        }
    }

    private int debugHeldItem(EntityClientPlayerMP player, int x, int y, int windowWidth, int windowHeight) {
        if (player == null)
            return 0;
        ColoredText debug = ColoredText.make();
        debug.setBackgroundPadding(4);
        debug.setBackgroundColor(new Color(22, 23, 30, 200));
        debug.setDrawBackground(true);
        debug.append(" Held item", EnumChatFormatting.GREEN).newLine();
        ItemStack itemStack = player.getHeldItem();
        if (itemStack == null) {
            debug.append("  You ").append("need ", EnumChatFormatting.DARK_RED).append("hold an item to see more info...").newLine();
        } else {
            Item item = itemStack.getItem();
            Icon icon = item == null ? null : item.getIconFromDamage(itemStack.getItemDamage());
            debug.append("  Item: ").append(itemStack.getDisplayName(), EnumChatFormatting.AQUA).format(" [%s:%s]", itemStack.itemID, itemStack.getItemDamage(), EnumChatFormatting.DARK_AQUA).newLine();
            debug.append("  Item class: ").append(item == null ? "Unknown" : item.getClass().getSimpleName(), item == null ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
            debug.append("  Item unlocalized name: ").append(item == null ? "Unknown" : item.getUnlocalizedName(itemStack), item == null ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
            debug.append("  Item icon: ").append(icon == null ? "Unknown" : icon.getIconName(), item == null ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
            debug.append("  Meta item: ").append((item instanceof BaseMultiMetaItem || item instanceof BaseMultiItem) ? "true" : "false", !(item instanceof BaseMultiMetaItem || item instanceof BaseMultiItem) ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
            if (item instanceof BaseMultiMetaItem) {
                BaseMultiMetaItem<?> metaItem = (BaseMultiMetaItem<?>) item;
                debug.append("  Meta has types: ").append(!metaItem.getMetaBlock().hasTypes() ? "false" : "true", !metaItem.getMetaBlock().hasTypes() ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
                if (metaItem.getMetaBlock().hasTypes()) {
                    debug.append("  Meta type count: ").append(String.valueOf(metaItem.getMetaBlock().getTypesCount()), EnumChatFormatting.AQUA).newLine();
                    debug.append("  Meta type: ").append(metaItem.getMetaBlock().getTypeByMetadata(itemStack.getItemDamage()), EnumChatFormatting.AQUA).newLine();
                    debug.append("  Meta icon: ").append(icon == null ? "Unknown" : metaItem.getMetaBlock().getIcon(2, itemStack.getItemDamage()).getIconName(), EnumChatFormatting.AQUA).newLine();
                }
            }
            if (item instanceof BaseMultiItem) {
                BaseMultiItem multiItem = (BaseMultiItem) item;
                debug.append("  Meta has types: ").append(multiItem.getMultiBlock().getTypesCount() == 0 ? "false" : "true", multiItem.getMultiBlock().getTypesCount() == 0 ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
                if (multiItem.getMultiBlock().getTypesCount() != 0) {
                    debug.append("  Meta type count: ").append(String.valueOf(multiItem.getMultiBlock().getTypesCount()), EnumChatFormatting.AQUA).newLine();
                    debug.append("  Meta type: ").append(multiItem.getMultiBlock().typeAt(itemStack.getItemDamage()), EnumChatFormatting.AQUA).newLine();
                    debug.append("  Meta icon: ").append(icon == null ? "Unknown" : multiItem.getMultiBlock().getIcon(2, itemStack.getItemDamage()).getIconName(), EnumChatFormatting.AQUA).newLine();
                }
            }
            debug.append("  Stack has NBT: ").append(!itemStack.hasTagCompound() ? "false" : "true", !itemStack.hasTagCompound() ? EnumChatFormatting.RED : EnumChatFormatting.AQUA).newLine();
            if (itemStack.hasTagCompound()) {
                nbtToString(itemStack.getTagCompound(), 4, debug);
            }
        }
        debug.draw(x, y, windowWidth, windowHeight);
        return debug.calculateTextWidth();
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
            coloredText.append(repeat(" ", indent)).append("}").append(" " + NBTBase.getTagName(base.getId()), EnumChatFormatting.GRAY);
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
                if (!((NBTTagCompound) base).hasNoTags())
                    coloredText.newLine();
                coloredText = nbtToString((NBTTagCompound) base, indent + 4, coloredText);
                coloredText.append(repeat(" ", indent)).append("}").append(" " + NBTBase.getTagName(base.getId()), EnumChatFormatting.GRAY).newLine();
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
                coloredText.append(repeat(" ", indent)).append("}").append(" " + NBTBase.getTagName(base.getId()), EnumChatFormatting.GRAY).newLine();
            } else {
                coloredText = primitiveNbt(base, indent, coloredText);
            }
        }

        return coloredText;
    }
}
