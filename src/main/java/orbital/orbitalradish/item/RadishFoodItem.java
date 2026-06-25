package orbital.orbitalradish.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.ItemTemplate;
import net.modificationstation.stationapi.api.util.Identifier;
import orbital.orbitalradish.events.init.BlockListener;

/**
 * Same wrapping trick as StationAPI's own TemplateItem — applied to vanilla's FoodItem.
 *
 * plantable items (the raw radish) also get SeedsItem's exact useOnBlock logic ported
 * directly onto them (confirmed source: right-click the top face of farmland, with air
 * above, places the linked crop block and consumes one). This is carrot/potato-style —
 * the food item doubles as its own seed, no separate seeds item exists.
 */
public class RadishFoodItem extends FoodItem implements ItemTemplate {

    private final boolean plantable;

    public RadishFoodItem(Identifier identifier, int healAmount, boolean isMeat, boolean plantable) {
        this(ItemTemplate.getNextId(), healAmount, isMeat, plantable);
        ItemTemplate.onConstructor(this, identifier);
    }

    public RadishFoodItem(int id, int healAmount, boolean isMeat, boolean plantable) {
        super(id, healAmount, isMeat);
        this.plantable = plantable;
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        if (!this.plantable || side != 1) {
            return false;
        }

        int blockBelow = world.getBlockId(x, y, z);
        if (blockBelow == Block.FARMLAND.id && world.isAir(x, y + 1, z)) {
            world.setBlock(x, y + 1, z, BlockListener.radishCrop.id);
            --stack.count;
            return true;
        }

        return false;
    }
}