package orbital.orbitalradish.block;

import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;
import orbital.orbitalradish.events.init.ItemListener;

import java.util.Random;

/**
 * Radish crop with proper StationAPI block state property registration.
 *
 * The AGE IntProperty (0-7) is what makes the blockstate JSON variants
 * "age=0" through "age=7" actually resolve to models. Without declaring
 * and appending this property, the blockstate selector can't match any
 * variant and renders nothing — confirmed from StationAPI wiki.
 */
public class RadishCropBlock extends CropBlock implements BlockTemplate {

    public static final IntProperty AGE = IntProperty.of("age", 0, 7);

    public RadishCropBlock(Identifier identifier, int textureId) {
        this(BlockTemplate.getNextId(), textureId);
        BlockTemplate.onConstructor(this, identifier);
    }

    public RadishCropBlock(int id, int textureId) {
        super(id, textureId);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        super.appendProperties(builder);
    }

    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck) {
        if (!world.isRemote) {
            // Always drop exactly 1 radish — radish is its own seed
            this.dropStack(world, x, y, z, new ItemStack(ItemListener.radish, 1, 0));

            // Bonus radishes only at full growth
            if (meta == 7) {
                for (int i = 0; i < 3; ++i) {
                    if (world.random.nextInt(15) == 0) {
                        float f = 0.7F;
                        float fx = world.random.nextFloat() * f + (1.0F - f) * 0.5F;
                        float fy = world.random.nextFloat() * f + (1.0F - f) * 0.5F;
                        float fz = world.random.nextFloat() * f + (1.0F - f) * 0.5F;
                        ItemEntity entity = new ItemEntity(world,
                                (double)((float)x + fx),
                                (double)((float)y + fy),
                                (double)((float)z + fz),
                                new ItemStack(ItemListener.radish));
                        entity.pickupDelay = 10;
                        world.spawnEntity(entity);
                    }
                }
            }
        }
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return -1; // handled entirely in dropStacks above
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return 0;
    }
}