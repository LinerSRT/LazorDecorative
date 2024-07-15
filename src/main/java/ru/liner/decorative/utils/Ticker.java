package ru.liner.decorative.utils;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.UUID;

public class Ticker {
    public static void register(final ITick callback, final TickType... tickTypes) {
        if (tickTypes.length == 0)
            return;
        TickRegistry.registerTickHandler(new ITickHandler() {
            @Override
            public void tickStart(EnumSet<TickType> enumSet, Object... objects) {
            }

            @Override
            public void tickEnd(EnumSet<TickType> enumSet, Object... objects) {
                callback.onTick();
            }

            @Override
            public EnumSet<TickType> ticks() {
                EnumSet<TickType> result = EnumSet.noneOf(TickType.class);
                result.addAll(Arrays.asList(tickTypes));
                return result;
            }

            @Override
            public String getLabel() {
                return UUID.randomUUID().toString();
            }
        }, Side.CLIENT);
    }

    public interface ITick {
        void onTick();
    }
}
