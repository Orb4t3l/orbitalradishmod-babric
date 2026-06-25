package orbital.orbitalradish.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;
import orbital.orbitalradish.item.RadishFoodItem;
import orbital.orbitalradish.events.init.BlockListener;

public class ItemListener {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Item radish;
    public static Item cookedRadish;
    public static Item radishLeaf;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        // healAmount, isMeat, plantable
        radish = new RadishFoodItem(NAMESPACE.id("radish"), 2, false, true)
                .setTranslationKey(NAMESPACE, "radish");

        cookedRadish = new RadishFoodItem(NAMESPACE.id("cooked_radish"), 5, false, false)
                .setTranslationKey(NAMESPACE, "cooked_radish");

        radishLeaf = new TemplateItem(NAMESPACE.id("radish_leaf"))
                .setTranslationKey(NAMESPACE, "radish_leaf");

        // Block items
        BlockListener.radishBlock.asItem();
        BlockListener.radishBricks.asItem();
        BlockListener.radishBrickStairs.asItem();
        BlockListener.radishSlab.asItem();
        BlockListener.radishDoubleSlab.asItem();
    }
}