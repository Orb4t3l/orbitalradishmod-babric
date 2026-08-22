package net.minecraft.src;

import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.template.item.ItemTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

public class RadishStewItem extends FoodItem implements ItemTemplate {

    public RadishStewItem(Identifier identifier) {
        this(ItemTemplate.getNextId());
        ItemTemplate.onConstructor(this, identifier);
    }

    public RadishStewItem(int id) {
        super(id, 10, false);
        this.setMaxCount(1);
        this.setCraftingReturnItem(Item.BOWL);
    }
}