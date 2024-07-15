package ru.liner.decorative.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

@SuppressWarnings("unchecked")
public class MinecraftField<C, T> {
    public static final MinecraftField<Minecraft, Timer> TIMER = new MinecraftField<>(Minecraft.class, "timer", "field_71428_T", "V");
    public final Class<C> aClass;
    public final String mcpName;
    public final String srgName;
    public final String name;
    private final String fieldName;

    public MinecraftField(Class<C> aClass, String mcpName, String srgName, String name) {
        this.aClass = aClass;
        this.mcpName = mcpName;
        this.srgName = srgName;
        this.name = name;
        if(Reflection.hasField(aClass, mcpName)){
            this.fieldName = mcpName;
        } else if(Reflection.hasField(aClass, srgName)){
            this.fieldName = srgName;
        } else if(Reflection.hasField(aClass, name)){
            this.fieldName = name;
        } else {
            this.fieldName = null;
        }
    }

    public T get(C instance){
        return fieldName == null ? null : (T) Reflection.getSafety(instance, fieldName, null);
    }

    public void set(C instance, T value){
        if(fieldName == null)
            return;
        Reflection.setSafety(instance, fieldName, value);
    }
}
