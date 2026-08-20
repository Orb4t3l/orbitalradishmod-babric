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
    public static Block doubleCompressedRadishBlock;
    public static Block tripleCompressedRadishBlock;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        radishBlock = new TemplateBlock(NAMESPACE.id("radish_block"), Material.WOOD)
                .setHardness(0.5F)
                .setTranslationKey(NAMESPACE, "radish_block");

        radishBricks = new TemplateBlock(NAMESPACE.id("radish_bricks"), Material.STONE)
                .setHardness(2.0F)
                .setResistance(10.0F)
                .setTranslationKey(NAMESPACE, "radish_bricks");

        radishBrickStairs = new RadishStairsBlock(NAMESPACE.id("radish_brick_stairs"), radishBricks)
                .setTranslationKey(NAMESPACE, "radish_brick_stairs");

        radishDoubleSlab = new RadishSlabBlock(NAMESPACE.id("radish_brick_double_slab"), true)
                .setHardness(2.0F)
                .setResistance(10.0F)
                .setTranslationKey(NAMESPACE, "radish_brick_double_slab");

        radishSlab = new RadishSlabBlock(NAMESPACE.id("radish_brick_slab"), false)
                .setHardness(2.0F)
                .setResistance(10.0F)
                .setTranslationKey(NAMESPACE, "radish_brick_slab");

        radishCrop = new RadishCropBlock(NAMESPACE.id("radish_crop"), 0)
                .setHardness(0.0F)
                .setSoundGroup(Block.DIRT_SOUND_GROUP)
                .disableTrackingStatistics()
                .ignoreMetaUpdates()
                .setTranslationKey(NAMESPACE, "radish_crop");

        doubleCompressedRadishBlock = new TemplateBlock(NAMESPACE.id("double_compressed_radish_block"), Material.STONE)
                .setHardness(4.0F)
                .setResistance(20.0F)
                .setTranslationKey(NAMESPACE, "double_compressed_radish_block");

        tripleCompressedRadishBlock = new TemplateBlock(NAMESPACE.id("triple_compressed_radish_block"), Material.STONE)
                .setHardness(6.0F)
                .setResistance(30.0F)
                .setTranslationKey(NAMESPACE, "triple_compressed_radish_block");
    }
}