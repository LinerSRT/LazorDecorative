package ru.liner.decorative.register;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import scala.util.parsing.combinator.testing.Str;

public class DamageSources {
    public static DamageSourceEx hotFloor;


    public static void init(){
        hotFloor = new DamageSourceEx("hotFloor",  "%s обнаружил что пол это лава", "%s наступил на магму из-за %s");
    }

    private static class DamageSourceEx extends DamageSource{
        public DamageSourceEx(String type, String ownKilledTranslate, String otherKilledTranslate) {
            super(type);
            LanguageRegistry.instance().addStringLocalization(String.format("death.attack.%s", type), ownKilledTranslate);
            LanguageRegistry.instance().addStringLocalization(String.format("death.attack.%s.player", type), otherKilledTranslate);
        }

        @Override
        public String getDeathMessage(EntityLiving diedEntity) {
            EntityLiving killerEntity = diedEntity.func_94060_bK();
            boolean wasKilledBySelf = killerEntity == null;
            if(wasKilledBySelf || !StatCollector.func_94522_b(String.format("death.attack.%s.player", damageType))){
                return String.format(LanguageRegistry.instance().getStringLocalization(String.format("death.attack.%s", damageType)), diedEntity.getTranslatedEntityName());
            } else {
                return String.format(LanguageRegistry.instance().getStringLocalization(String.format("death.attack.%s.player", damageType)), diedEntity.getTranslatedEntityName(), killerEntity.getTranslatedEntityName());
            }
        }
    }
}
