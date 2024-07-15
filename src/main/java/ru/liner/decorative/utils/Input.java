package ru.liner.decorative.utils;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.nio.IntBuffer;

import static org.lwjgl.input.Keyboard.*;

public class Input {
    private static final boolean[] keyDownStates = new boolean[Keyboard.KEYBOARD_SIZE];
    private static final boolean[] mouseDownStates = new boolean[Mouse.getButtonCount()];
    private static Cursor currentCursor = Mouse.getNativeCursor();


    public static boolean isMouseDown(int key) {
        return Mouse.isButtonDown(key);
    }

    public static int mouseX() {
        return Mouse.getEventX();
    }

    public static int mouseY() {
        return Mouse.getEventY();
    }

    public static boolean isMousePressed(int key) {
        boolean pressed = isMouseDown(key);
        if (pressed && !mouseDownStates[key]) {
            mouseDownStates[key] = true;
            return true;
        } else if (!pressed) {
            mouseDownStates[key] = false;
        }
        return false;
    }

    public static boolean isCurrentCursor(Cursor cursor) {
        return currentCursor == cursor;
    }

    public static void hideCursor() {
        try {
            int cursorSize = Cursor.getMinCursorSize();
            IntBuffer buffer = BufferUtils.createIntBuffer(cursorSize * cursorSize);
            setCursor(new Cursor(cursorSize, cursorSize, cursorSize / 2, cursorSize / 2, 1, buffer, null));
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void showCursor() {
        setCursor(null);
    }

    public static boolean isKeyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

    public static boolean isKeyPressed(int key) {
        boolean pressed = Keyboard.isKeyDown(key);
        if (pressed && !keyDownStates[key]) {
            keyDownStates[key] = true;
            return true;
        } else if (!pressed) {
            keyDownStates[key] = false;
        }

        return false;
    }


    public static boolean isShiftDown() {
        return Keyboard.isKeyDown(KEY_LSHIFT) || Keyboard.isKeyDown(KEY_RSHIFT);
    }

    public static boolean isCtrlDown() {
        return Keyboard.isKeyDown(KEY_LCONTROL) || Keyboard.isKeyDown(KEY_RCONTROL);
    }

    public static boolean isKeyPressed(KeyBinding key) {
        return Keyboard.isKeyDown(key.keyCode);
    }

    public static boolean isKeyAvailable(int key) {
        switch (key) {
            case KEY_NONE:
            case KEY_ESCAPE:
            case KEY_SYSRQ:
            case KEY_LSHIFT:
            case KEY_LCONTROL:
            case KEY_LMENU:
            case KEY_BACK:
                return false;
        }
        return true;
    }

    public static void setCursor(Cursor cursor) {
        if (Mouse.isInsideWindow()) {
            try {
                if (currentCursor != cursor)
                    currentCursor = cursor;
                Mouse.setNativeCursor(cursor);
            } catch (LWJGLException ignored) {

            }
        }
    }
}
