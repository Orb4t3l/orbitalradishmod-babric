package orbital.orbitalradish.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Namespace;

/**
 * Babric / StationAPI port of OrbitalRadish block registration.
 *
 * Starting with just a basic static "radish block" (decorative/storage block, like a
 * pumpkin or hay bale). The crop block is NOT here yet — growable crops need growth
 * stages + random tick scheduling, which is a separate, bigger piece of work.
 *
 * Confidence note: the package paths below (template.block.TemplateBlock,
 * event.registry.BlockRegistryEvent) are inferred by direct parallel with the verified
 * Item equivalents (template.item.TemplateItem, event.registry.ItemRegistryEvent), not
 * independently fetched from source like those were. If this doesn't compile, paste me
 * the error — it'll point at exactly which one is off.
 */
public class BlockListener {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Block radishBlock;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        radishBlock = new TemplateBlock(NAMESPACE.id("radish_block"), Material.WOOD)
                .setTranslationKey(NAMESPACE, "radish_block");
    }
}