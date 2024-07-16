package ru.liner.decorative.register;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundPool;

public class Sounds {

    public static void init() {

    }

    private static void register(String type, String soundName, String soundPath) {
        SoundPool soundPoolSounds = Minecraft.getMinecraft().sndManager.soundPoolSounds;
        soundPoolSounds.addSound(soundName, Sounds.class.getResource(String.format("/sounds/%s/%s", type, soundPath)));
    }
}
