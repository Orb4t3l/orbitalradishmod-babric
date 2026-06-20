package orbital.orbitalradish.item;

import net.minecraft.item.FoodItem;
import net.modificationstation.stationapi.api.template.item.ItemTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

/**
 * Same wrapping trick StationAPI's own TemplateItem uses (see TemplateItem.java) — just
 * applied to vanilla's FoodItem instead of plain Item, so we get an Identifier-based
 * constructor while keeping FoodItem's heal-on-eat behavior (confirmed from the
 * decompiled net.minecraft.item.Item static init block, e.g. APPLE = new FoodItem(4, 4, false)).
 */
public class RadishFoodItem extends FoodItem implements ItemTemplate {

    public RadishFoodItem(Identifier identifier, int healAmount, boolean isMeat) {
        this(ItemTemplate.getNextId(), healAmount, isMeat);
        ItemTemplate.onConstructor(this, identifier);
    }

    public RadishFoodItem(int id, int healAmount, boolean isMeat) {
        super(id, healAmount, isMeat);
    }
}