package orbital.orbitalradish.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.ItemTemplate;
import net.modificationstation.stationapi.api.util.Identifier;
import orbital.orbitalradish.entity.RadishStickEntity;

public class RadishStickItem extends BowItem implements ItemTemplate {

    public RadishStickItem(Identifier identifier) {
        this(ItemTemplate.getNextId());
        ItemTemplate.onConstructor(this, identifier);
    }

    public RadishStickItem(int id) {
        super(id);
        this.maxCount = 1;
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (user.inventory.remove(Item.STICK.id)) {
            world.playSound(user, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote) {
                world.spawnEntity(new RadishStickEntity(world, user));
            }
        }

        return stack;
    }
}