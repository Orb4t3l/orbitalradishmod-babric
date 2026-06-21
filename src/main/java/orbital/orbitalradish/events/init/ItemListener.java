package orbital.orbitalradish.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.template.item.TemplateSeedsItem;
import net.modificationstation.stationapi.api.util.Namespace;
import orbital.orbitalradish.item.RadishFoodItem;

public class ItemListener {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Item radish;
    public static Item cookedRadish;
    public static Item radishLeaf;
    public static Item radishSeeds;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        // healAmount, isMeat — tune these however you like, they're not load-bearing on anything else
        radish = new RadishFoodItem(NAMESPACE.id("radish"), 2, false)
                .setTranslationKey(NAMESPACE, "radish");

        cookedRadish = new RadishFoodItem(NAMESPACE.id("cooked_radish"), 5, false)
                .setTranslationKey(NAMESPACE, "cooked_radish");

        // Not edible, but now also crafts into radish_seeds (see RadishCropBlock / recipes)
        radishLeaf = new TemplateItem(NAMESPACE.id("radish_leaf"))
                .setTranslationKey(NAMESPACE, "radish_leaf");

    }
}