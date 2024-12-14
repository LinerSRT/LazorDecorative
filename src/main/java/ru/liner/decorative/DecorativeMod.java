package ru.liner.decorative;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraftforge.common.Configuration;

import java.util.logging.Logger;

@Mod(modid = DecorativeMod.MOD_ID, version = "1.0", name = "Lazor Decorative")
@NetworkMod
public class DecorativeMod {
    public static final String MOD_ID = "lazor_decorative";
    @Mod.Instance(MOD_ID)
    public static DecorativeMod INSTANCE;
    @SidedProxy(
            clientSide = "ru.liner.decorative.ClientProxy",
            serverSide = "ru.liner.decorative.ServerProxy"
    )
    public static CommonProxy proxy;

    public static Logger logger = Logger.getLogger(DecorativeMod.class.getSimpleName());
    public static Configuration configuration;

    public DecorativeMod() {
        logger.setParent(FMLLog.getLogger());
    }

    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent e) {
        configuration = new Configuration(e.getSuggestedConfigurationFile());
        proxy.preInit(e);
    }

    @Mod.Init
    public void init(final FMLInitializationEvent e) {
        proxy.init(e);
    }
}
