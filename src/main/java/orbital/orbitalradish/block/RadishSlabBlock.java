package orbital.orbitalradish.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;
import orbital.orbitalradish.events.init.BlockListener;

import java.util.Random;

public class RadishSlabBlock extends Block implements BlockTemplate {

    public static final IntProperty TYPE = IntProperty.of("type", 0, 2);

    public static final int TYPE_BOTTOM = 0;
    public static final int TYPE_TOP = 1;
    public static final int TYPE_DOUBLE = 2;

    private final boolean isDouble;

    public RadishSlabBlock(Identifier identifier, boolean isDouble) {
        this(BlockTemplate.getNextId(), isDouble);
        BlockTemplate.onConstructor(this, identifier);
    }

    public RadishSlabBlock(int id, boolean isDouble) {
        super(id, Material.WOOD);
        this.isDouble = isDouble;
        if (!isDouble) {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        this.setOpacity(255);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
        super.appendProperties(builder);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        if (this.isDouble) {
            return this.getDefaultState().with(TYPE, TYPE_DOUBLE);
        }
        if (context.getHitPos().y > 0.5F) {
            return this.getDefaultState().with(TYPE, TYPE_BOTTOM);
        }
        return this.getDefaultState().with(TYPE, TYPE_TOP);
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
            BlockState doubleState = doubleSlab.getDefaultState().with(TYPE, TYPE_DOUBLE);
            world.setBlockState(x, y - 1, z, doubleState, TYPE_DOUBLE);
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