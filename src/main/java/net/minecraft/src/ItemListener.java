package net.minecraft.src;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;

public class ItemListener {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Item radish;
    public static Item cookedRadish;
    public static Item radishLeaf;
    public static Item radishStick;
    public static Item radishStew;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        radish = new RadishFoodItem(NAMESPACE.id("radish"), 2, false, true)
                .setTranslationKey(NAMESPACE, "radish");

        cookedRadish = new RadishFoodItem(NAMESPACE.id("cooked_radish"), 5, false, false)
                .setTranslationKey(NAMESPACE, "cooked_radish");

        radishLeaf = new TemplateItem(NAMESPACE.id("radish_leaf"))
                .setTranslationKey(NAMESPACE, "radish_leaf");

        radishStick = new RadishStickItem(NAMESPACE.id("radish_stick"))
                .setTranslationKey(NAMESPACE, "radish_stick");

        radishStew = new RadishStewItem(NAMESPACE.id("radish_stew"))
                .setTranslationKey(NAMESPACE, "radish_stew");

        BlockListener.radishBlock.asItem();
        BlockListener.radishBricks.asItem();
        BlockListener.radishBrickStairs.asItem();
        BlockListener.radishSlab.asItem();
        BlockListener.radishDoubleSlab.asItem();
        BlockListener.doubleCompressedRadishBlock.asItem();
        BlockListener.tripleCompressedRadishBlock.asItem();
    }
}