package orbital.orbitalradish.block;

import net.minecraft.block.CropBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;
import orbital.orbitalradish.events.init.ItemListener;

import java.util.Random;

/**
 * Radish crop — mirrors vanilla CropBlock's growth (moisture-based random tick, meta 0-7)
 * and harvest behavior exactly, just with radish/radish_seeds substituted for wheat/seeds.
 *
 * dropStacks is fully reimplemented rather than calling super.dropStacks(), because
 * CropBlock's own override hardcodes a bonus drop of vanilla Item.SEEDS — calling it would
 * give players vanilla wheat seeds instead of radish seeds. What's below mirrors the same
 * two pieces (Block's base drop-by-id/count logic, then CropBlock's bonus-drop loop) with
 * our own items wired in.
 *
 * Growth-stage texture variation (meta 0-7 showing different sprites) isn't wired up yet —
 * same open question as stairs rotation, both depend on understanding StationAPI's
 * metadata-to-blockstate-property bridge. Renders as one static texture for now.
 */
public class RadishCropBlock extends CropBlock implements BlockTemplate {

    public RadishCropBlock(Identifier identifier, int textureId) {
        this(BlockTemplate.getNextId(), textureId);
        BlockTemplate.onConstructor(this, identifier);
    }

    public RadishCropBlock(int id, int textureId) {
        super(id, textureId);
    }

    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck) {
        if (!world.isRemote) {
            int count = this.getDroppedItemCount(world.random);
            for (int i = 0; i < count; ++i) {
                if (!(world.random.nextFloat() > luck)) {
                    int droppedId = this.getDroppedItemId(meta, world.random);
                    if (droppedId > 0) {
                        this.dropStack(world, x, y, z, new ItemStack(droppedId, 1, this.getDroppedItemMeta(meta)));
                    }
                }
            }

            // Bonus seed drops, same odds curve as vanilla wheat (higher growth stage = better odds)
            for (int i = 0; i < 3; ++i) {
                if (world.random.nextInt(15) <= meta) {
                    float f = 0.7F;
                    float fx = world.random.nextFloat() * f + (1.0F - f) * 0.5F;
                    float fy = world.random.nextFloat() * f + (1.0F - f) * 0.5F;
                    float fz = world.random.nextFloat() * f + (1.0F - f) * 0.5F;
                    ItemEntity entity = new ItemEntity(world, (double) ((float) x + fx), (double) ((float) y + fy), (double) ((float) z + fz), new ItemStack(ItemListener.radish));
                    entity.pickupDelay = 10;
                    world.spawnEntity(entity);
                }
            }
        }
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return blockMeta == 7 ? ItemListener.radish.id : -1;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return 1;
    }
}