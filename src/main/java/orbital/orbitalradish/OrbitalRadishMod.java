package orbital.orbitalradish;


import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;

/**
 * Babric / StationAPI port of OrbitalRadish.
 *
 * NOTE on scope vs. the Forge 1.20.1 original:
 *  - No hunger/saturation system exists in Beta 1.7.3 (added in Beta 1.8). Food items
 *    there heal HP directly instead of using FoodProperties — the exact StationAPI class
 *    for this is flagged as a TODO below rather than guessed at.
 *  - No advancements system exists in b1.7.3 at all (1.12+ feature) — not ported.
 *  - The radish crop block, compressed blocks/bricks/stairs/slab/wall, the radish stick
 *    bow, the custom arrow entity+renderer, and the villager/pig AI hooks are NOT in this
 *    pass — those need their own grounded look at StationAPI's Block, Entity, and event
 *    APIs before I write them, so they're queued up as the next step rather than guessed at.
 */
public class OrbitalRadishMod {

    // StationAPI injects your mod's namespace into this field automatically.
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Item radish;
    public static Item cookedRadish;
    public static Item radishLeaf;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        // TODO (verify before shipping): StationAPI exposes preset versions of vanilla
        // classes (per its docs, "preset for each and every vanilla item and block with
        // public access to properties and constructors"), which should include something
        // mirroring vanilla b1.7.3's ItemFood(healAmount). I don't have its exact class
        // name/package confirmed, so I'm not guessing at it here — that's the very next
        // thing to look up in the StationAPI source/wiki before we wire up eating.
        // Until then these register as plain (non-edible) items so the project compiles
        // and you can confirm registration works end-to-end first.
        radish = new TemplateItem(NAMESPACE.id("radish"))
                .setTranslationKey(NAMESPACE, "radish");

        cookedRadish = new TemplateItem(NAMESPACE.id("cooked_radish"))
                .setTranslationKey(NAMESPACE, "cooked_radish");

        radishLeaf = new TemplateItem(NAMESPACE.id("radish_leaf"))
                .setTranslationKey(NAMESPACE, "radish_leaf");
    }
}