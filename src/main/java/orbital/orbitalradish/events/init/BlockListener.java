package orbital.orbitalradish.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Namespace;
import orbital.orbitalradish.block.RadishCropBlock;
import orbital.orbitalradish.block.RadishSlabBlock;
import orbital.orbitalradish.block.RadishStairsBlock;

public class BlockListener {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Block radishBlock;
    public static Block radishBricks;
    public static Block radishBrickStairs;
    public static Block radishSlab;
    public static Block radishDoubleSlab;
    public static Block radishCrop;

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

        // Stairs inherit hardness/resistance from the base block automatically.
        radishBrickStairs = new RadishStairsBlock(NAMESPACE.id("radish_brick_stairs"), radishBricks)
                .setTranslationKey(NAMESPACE, "radish_brick_stairs");

        // Slab pair — both must exist before either is placed in-world, since their
        // placement-merge logic references each other (see RadishSlabBlock).
        radishDoubleSlab = new RadishSlabBlock(NAMESPACE.id("radish_brick_double_slab"), true)
                .setHardness(2.0F)
                .setResistance(10.0F)
                .setTranslationKey(NAMESPACE, "radish_brick_double_slab");

        radishSlab = new RadishSlabBlock(NAMESPACE.id("radish_brick_slab"), false)
                .setHardness(2.0F)
                .setResistance(10.0F)
                .setTranslationKey(NAMESPACE, "radish_brick_slab");

        // Crop — values mirror vanilla WHEAT exactly (insta-break, grass-family sound,
        // no stat tracking, ignores its own frequent meta changes from growth ticks).
        radishCrop = new RadishCropBlock(NAMESPACE.id("radish_crop"), 0)
                .setHardness(0.0F)
                .setSoundGroup(Block.DIRT_SOUND_GROUP)
                .disableTrackingStatistics()
                .ignoreMetaUpdates()
                .setTranslationKey(NAMESPACE, "radish_crop");
    }
}