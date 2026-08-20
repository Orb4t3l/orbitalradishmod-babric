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
    public void onTick(World world, int x, int y, int z, Random random) {
        if (world.getLightLevel(x, y + 1, z) >= 9) {
            int meta = world.getBlockMeta(x, y, z);
            if (meta < 7) {
                float moisture = 1.0F;
                if (random.nextInt((int) (100.0F / moisture)) == 0) {
                    ++meta;
                    world.setBlock(x, y, z, this.id, meta);
                }
            }
        }
    }

    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck) {
        if (!world.isRemote) {
            this.dropStack(world, x, y, z, new ItemStack(ItemListener.radish, 1, 0));

            if (meta == 7) {
                for (int i = 0; i < 3; ++i) {
                    if (world.random.nextInt(15) == 0) {
                        float f = 0.7F;
                        float fx = world.random.nextFloat() * f + (1.0F - f) * 0.5F;
                        float fy = world.random.nextFloat() * f + (1.0F - f) * 0.5F;
                        float fz = world.random.nextFloat() * f + (1.0F - f) * 0.5F;
                        ItemEntity entity = new ItemEntity(world,
                                (double) ((float) x + fx),
                                (double) ((float) y + fy),
                                (double) ((float) z + fz),
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
        return -1;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return 0;
    }
}