package orbital.orbitalradish.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Namespace;
import orbital.orbitalradish.block.RadishStairsBlock;

public class BlockListener {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Block radishBlock;
    public static Block radishBricks;
    public static Block radishBrickStairs;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        // Dirt-tier: soft, fast to dig, no tool required (matches vanilla DIRT: setHardness(0.5F))
        radishBlock = new TemplateBlock(NAMESPACE.id("radish_block"), Material.WOOD)
                .setHardness(0.5F)
                .setTranslationKey(NAMESPACE, "radish_block");

        // Stone-tier (no "Stone Bricks" block exists yet in b1.7.3 to copy directly,
        // matched against Cobblestone/Bricks instead, which share the same values)
        radishBricks = new TemplateBlock(NAMESPACE.id("radish_bricks"), Material.STONE)
                .setHardness(2.0F)
                .setResistance(10.0F)
                .setTranslationKey(NAMESPACE, "radish_bricks");

        // Stairs inherit hardness/resistance from the base block automatically (see
        // RadishStairsBlock's javadoc for why we're confident about that).
        radishBrickStairs = new RadishStairsBlock(NAMESPACE.id("radish_brick_stairs"), radishBricks)
                .setTranslationKey(NAMESPACE, "radish_brick_stairs");
    }
}