package orbital.orbitalradish.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;

/**
 * Babric / StationAPI port of OrbitalRadish — item registration.
 *
 * NOTE on scope vs. the Forge 1.20.1 original:
 *  - No hunger/saturation system exists in Beta 1.7.3 (added in Beta 1.8). Food items
 *    there heal HP directly instead of using FoodProperties — the exact StationAPI class
 *    for this is still a TODO below rather than guessed at.
 *  - No advancements system exists in b1.7.3 at all (1.12+ feature) — not ported.
 *  - The radish crop block, compressed blocks/bricks/stairs/slab/wall, the radish stick
 *    bow, the custom arrow entity+renderer, and the villager/pig AI hooks are NOT in this
 *    pass — those are queued up as the next step.
 */
public class ItemListener {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Item radish;
    public static Item cookedRadish;
    public static Item radishLeaf;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        // TODO (verify before shipping): look up StationAPI's preset for vanilla b1.7.3's
        // ItemFood(healAmount) so these actually heal the player on eat. Registering as
        // plain items for now so the project compiles and registration can be confirmed
        // end-to-end first.
        radish = new TemplateItem(NAMESPACE.id("radish"))
                .setTranslationKey(NAMESPACE, "radish");

        cookedRadish = new TemplateItem(NAMESPACE.id("cooked_radish"))
                .setTranslationKey(NAMESPACE, "cooked_radish");

        radishLeaf = new TemplateItem(NAMESPACE.id("radish_leaf"))
                .setTranslationKey(NAMESPACE, "radish_leaf");
    }
}