package orbital.orbitalradish.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;
import orbital.orbitalradish.events.init.BlockListener;

import java.util.Random;

/**
 * Custom slab pair for radish bricks.
 *
 * Vanilla's SlabBlock (confirmed from decompiled source) can't be reused for a new
 * material as-is — it hardcodes a 4-texture switch AND hardcodes its placement-merge logic
 * to check specifically against the singleton Block.SLAB / Block.DOUBLE_SLAB instances. This
 * mirrors that exact algorithm, but checks against BlockListener.radishSlab /
 * radishDoubleSlab instead — same trick vanilla uses with its own static fields, since
 * onPlaced only ever runs later at runtime, long after both are registered.
 *
 * No texture-meta switch needed since we only have one slab "flavor" (unlike vanilla's
 * stone/sandstone/wood/cobble combo) — texturing goes through the model/blockstate JSON,
 * same as our other blocks.
 */
public class RadishSlabBlock extends Block implements BlockTemplate {

    private final boolean isDouble;

    public RadishSlabBlock(Identifier identifier, boolean isDouble) {
        this(BlockTemplate.getNextId(), isDouble);
        BlockTemplate.onConstructor(this, identifier);
    }

    public RadishSlabBlock(int id, boolean isDouble) {
        super(id, Material.STONE);
        this.isDouble = isDouble;
        if (!isDouble) {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        this.setOpacity(255);
    }

    @Override
    public boolean isOpaque() {
        return this.isDouble;
    }

    @Override
    public boolean isFullCube() {
        return this.isDouble;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        super.onPlaced(world, x, y, z);

        if (this.isDouble) {
            return;
        }

        Block singleSlab = BlockListener.radishSlab;
        Block doubleSlab = BlockListener.radishDoubleSlab;

        int belowId = world.getBlockId(x, y - 1, z);
        int hereMeta = world.getBlockMeta(x, y, z);
        int belowMeta = world.getBlockMeta(x, y - 1, z);

        if (hereMeta == belowMeta && belowId == singleSlab.id) {
            world.setBlock(x, y, z, 0);
            world.setBlock(x, y - 1, z, doubleSlab.id, belowMeta);
        }
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return BlockListener.radishSlab.id;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return this.isDouble ? 2 : 1;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side) {
        if (side == 1) {
            return true;
        } else if (!super.isSideVisible(blockView, x, y, z, side)) {
            return false;
        } else if (side == 0) {
            return true;
        } else {
            return blockView.getBlockId(x, y, z) != this.id;
        }
    }
}