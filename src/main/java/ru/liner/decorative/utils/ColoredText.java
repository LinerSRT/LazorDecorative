package ru.liner.decorative.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringTranslate;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings({"unchecked", "UseJBColor"})
public class ColoredText {
    private static final Pattern COLORS_PATTERN = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    private final StringBuilder stringColoredText;
    private final Minecraft minecraft;
    private final HashMap<String, Runnable> intractableActions;
    private final HashMap<String, Rectangle2D.Float> intractableZones;
    private final List<String> obfuscatedList; //TODO This is fix for 1.5.2 obfuscated text issue
    private final Random random;
    private int changingTick;
    private boolean cursorWasChanged = false;
    private boolean drawBackground = false;
    private int backgroundPadding = 0;
    private int backgroundColor = Color.DARK_GRAY.getRGB();
    private int textColor = Color.WHITE.getRGB();

    public static ColoredText make(){
        return new ColoredText();
    }


    public ColoredText() {
        this.stringColoredText = new StringBuilder();
        this.minecraft = Minecraft.getMinecraft();
        this.intractableActions = new HashMap<>();
        this.intractableZones = new HashMap<>();
        this.obfuscatedList = new ArrayList<>();
        this.random = new Random();
    }


    public ColoredText setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public ColoredText setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
        return this;
    }

    public ColoredText setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    public ColoredText setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor.getRGB();
        return this;
    }

    public ColoredText setBackgroundPadding(int backgroundPadding) {
        this.backgroundPadding = backgroundPadding;
        return this;
    }

    public ColoredText append(String text) {
        stringColoredText.append(text.replace('&', '§').replace(">>", "»"));
        return this;
    }

    public ColoredText append(String text, EnumChatFormatting color) {
        if(color == null)
            return append(text);
        return append(color).append(text).append(EnumChatFormatting.RESET);
    }

    public ColoredText appendObfuscated(String text, EnumChatFormatting color) {
        String saveText = removeColors(text);
        if (!obfuscatedList.contains(saveText))
            obfuscatedList.add(saveText);
        return append(text, color);
    }

    public ColoredText appendUnderline(String text, EnumChatFormatting color) {
        return append(color).append(EnumChatFormatting.UNDERLINE).append(text).append(EnumChatFormatting.RESET);
    }

    public ColoredText appendIntractable(String text, EnumChatFormatting color, Runnable action) {
        intractableActions.put(text, action);
        return append(color).append(EnumChatFormatting.UNDERLINE).append(text).append(EnumChatFormatting.RESET);
    }

    public ColoredText appendLocalized(String key) {
        return append(StringTranslate.getInstance().translateKey(key));
    }

    public ColoredText appendLocalized(String key, EnumChatFormatting color) {
        return append(StringTranslate.getInstance().translateKey(key), color);
    }

    public ColoredText append(EnumChatFormatting color) {
        return append(color.toString());
    }

    public ColoredText format(String text, Object... objects) {
        if (objects.length > 0 && objects[objects.length - 1] instanceof EnumChatFormatting)
            return append(String.format(text, Arrays.copyOfRange(objects, 0, objects.length - 1)), (EnumChatFormatting) objects[objects.length - 1]);
        return append(String.format(text, objects));
    }

    public ColoredText formatLocalized(String key, Object... objects) {
        if (objects.length > 0 && objects[objects.length - 1] instanceof EnumChatFormatting)
            return append(String.format(StringTranslate.getInstance().translateKey(key), Arrays.copyOfRange(objects, 0, objects.length - 1)), (EnumChatFormatting) objects[objects.length - 1]);
        return append(String.format(StringTranslate.getInstance().translateKey(key), objects));
    }

    public String get() {
        return stringColoredText.toString();
    }

    public void sendToChat() {
        if (minecraft.theWorld != null && minecraft.thePlayer != null)
            minecraft.thePlayer.sendQueue.addToSendQueue(new Packet3Chat(get()));
    }

    public void addToChat() {
        if (minecraft.theWorld != null && minecraft.thePlayer != null)
            minecraft.thePlayer.addChatMessage(get());
    }

    public void drawCenterX(int x, int y){
        minecraft.fontRenderer.drawString(get(), x - calculateTextWidth() / 2, y, textColor);
    }
    public void drawCenterXY(int x, int y){
        int startY = y;
        String[] strings = get().split("\n");
        for (String string : strings) {
            minecraft.fontRenderer.drawString(string, x - calculateTextWidth() / 2, startY, textColor);
            startY += minecraft.fontRenderer.FONT_HEIGHT;
        }
    }

    public void draw(int x, int y, int windowWidth, int windowHeight) {
        List<String> stringToWidth = minecraft.fontRenderer.listFormattedStringToWidth(get(), windowWidth);
        int startY = y;
        for (String text : stringToWidth) {
            handleInteraction(text, x, startY, windowWidth, windowHeight);
            for (String obfuscatedPart : obfuscatedList) {
                if (text.contains(obfuscatedPart))
                    text = text.replace(obfuscatedPart, randomString(obfuscatedPart.length()));
            }
            minecraft.fontRenderer.drawString(text, x, startY, textColor);
            startY += minecraft.fontRenderer.FONT_HEIGHT;
        }
    }

    private String removeColors(String text) {
        return COLORS_PATTERN.matcher(text).replaceAll("");
    }

    public void drawCentered(int x, int y, int windowWidth, int windowHeight) {
        drawCentered(x, y, windowWidth, windowHeight, 0);
    }


    public void drawCentered(int x, int y, int windowWidth, int windowHeight, int padding) {
        List<String> stringToWidth = minecraft.fontRenderer.listFormattedStringToWidth(get(), windowWidth - padding);
        int startY = y - (minecraft.fontRenderer.FONT_HEIGHT * stringToWidth.size()) / 2;
        for (String text : stringToWidth) {
            handleInteraction(text, x, startY, windowWidth, windowHeight);
            for (String obfuscatedPart : obfuscatedList) {
                if (text.contains(obfuscatedPart))
                    text = text.replace(obfuscatedPart, randomString(obfuscatedPart.length()));
            }
            minecraft.fontRenderer.drawString(text, x - minecraft.fontRenderer.getStringWidth(text) / 2, startY, textColor);
            startY += minecraft.fontRenderer.FONT_HEIGHT;
        }
    }


    private int calculateTextWidth() {
        int width = 0;
        for (String line : get().split("\n")) {
            int lineWidth = minecraft.fontRenderer.getStringWidth(line);
            if (lineWidth >= width)
                width = lineWidth;
        }
        return width;
    }

    private int calculateTextHeight() {
        return Math.max(minecraft.fontRenderer.FONT_HEIGHT, minecraft.fontRenderer.FONT_HEIGHT * get().split("\n").length);
    }

    public void drawCenteredX(int x, int y, int windowWidth, int windowHeight, int padding) {

        List<String> stringToWidth = minecraft.fontRenderer.listFormattedStringToWidth(get(), windowWidth - padding);
        int startY = y;
        for (String text : stringToWidth) {
            handleInteraction(text, x, startY, windowWidth, windowHeight);
            for (String obfuscatedPart : obfuscatedList) {
                if (text.contains(obfuscatedPart))
                    text = text.replace(obfuscatedPart, randomString(obfuscatedPart.length()));
            }
            minecraft.fontRenderer.drawString(text, x - minecraft.fontRenderer.getStringWidth(text) / 2, startY, textColor);
            startY += minecraft.fontRenderer.FONT_HEIGHT;
        }
    }

    private String randomString(int size) {
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder(size);
        for (int i = 0; i < size; i++)
            stringBuilder.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return stringBuilder.toString();
    }

    private boolean hasAction(String text) {
        for (String key : intractableActions.keySet()) {
            if (text.contains(key))
                return true;
        }
        return false;
    }

    private boolean hasObfuscated(String text) {
        for (String key : obfuscatedList)
            if (text.contains(key))
                return true;
        return false;
    }

    private Runnable getAction(String text) {
        for (String key : intractableActions.keySet()) {
            if (text.contains(key))
                return intractableActions.get(key);
        }
        return null;
    }

    public ColoredText newLine() {
        return append("\n");
    }


    private boolean isMouseOverIntractableZone(int windowWidth, int windowHeight) {
        int mouseX = Input.mouseX() * windowWidth / minecraft.displayWidth;
        int mouseY = windowHeight - Input.mouseY() * windowHeight / minecraft.displayHeight - 1;
        for (Rectangle2D.Float textZone : intractableZones.values()) {
            if (textZone.contains(mouseX, mouseY))
                return true;
        }
        return false;
    }

    private void handleInteraction(String line, int x, int y, int windowWidth, int windowHeight) {
        for (String key : intractableActions.keySet()) {
            if (line.contains(key)) {
                String firstPart = line.substring(0, line.indexOf(key));
                int startX = x - minecraft.fontRenderer.getStringWidth(line) / 2;
                Rectangle2D.Float textBounds = new Rectangle2D.Float(
                        startX + minecraft.fontRenderer.getStringWidth(firstPart),
                        y,
                        minecraft.fontRenderer.getStringWidth(removeColors(key)),
                        minecraft.fontRenderer.FONT_HEIGHT
                );
                if (!intractableZones.containsKey(key)) {
                    intractableZones.put(key, textBounds);
                } else if (!intractableZones.get(key).equals(textBounds)) {
                    intractableZones.remove(key);
                    intractableZones.put(key, textBounds);
                }

                boolean mouseOver = isMouseOverIntractableZone(windowWidth, windowHeight);
                if (mouseOver && !cursorWasChanged) {
                    cursorWasChanged = true;
                } else if (!mouseOver && cursorWasChanged) {
                    cursorWasChanged = false;
                }
                int mouseX = Input.mouseX() * windowWidth / minecraft.displayWidth;
                int mouseY = windowHeight - Input.mouseY() * windowHeight / minecraft.displayHeight - 1;
                if (textBounds.contains(mouseX, mouseY)) {
                    if (Input.isMousePressed(0)) {
                        Runnable runnable = intractableActions.get(key);
                        if (runnable != null)
                            runnable.run();
                    }
                }
            }
        }
    }

    public static boolean containColor(String text) {
        return COLORS_PATTERN.matcher(text).find();
    }
}
