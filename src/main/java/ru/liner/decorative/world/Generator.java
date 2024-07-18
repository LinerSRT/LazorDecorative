package ru.liner.decorative.world;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import ru.liner.decorative.register.Blocks;
import ru.liner.decorative.world.generator.FlowerGenerator;

public class Generator {
    public static Generator instance;
    private FlowerGenerator flowerGenerator;
    public Generator() {
        flowerGenerator = new FlowerGenerator(Blocks.flower);
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
    }

    @ForgeSubscribe
    public void decorateBiome(DecorateBiomeEvent.Decorate event){
        switch (event.type){
            case BIG_SHROOM:
                break;
            case CACTUS:
                break;
            case CLAY:
                break;
            case DEAD_BUSH:
                break;
            case LILYPAD:
                break;
            case FLOWERS:
                for(int flowerCount = 0; flowerCount < 2; ++flowerCount) {
                    int x = event.chunkX + event.rand.nextInt(36) + 8;
                    int y = event.rand.nextInt(128);
                    int z = event.chunkZ + event.rand.nextInt(16) + 8;
                    flowerGenerator.generate(event.world, event.rand, x, y, z);
                }
                break;
            case GRASS:
                break;
            case LAKE:
                break;
            case PUMPKIN:
                break;
            case REED:
                break;
            case SAND:
                break;
            case SAND_PASS2:
                break;
            case SHROOM:
                break;
            case TREE:
                break;
            case CUSTOM:
                break;
        }
    }

    public static void init(){
        instance = new Generator();
    }
}
