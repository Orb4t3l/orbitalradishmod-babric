package orbital.orbitalradish.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.Random;

public class RadishStairsBlock extends Block implements BlockTemplate {

    public static final IntProperty FACING = IntProperty.of("facing", 0, 3);

    private final Block baseBlock;

    public RadishStairsBlock(Identifier identifier, Block baseBlock) {
        this(BlockTemplate.getNextId(), baseBlock);
        BlockTemplate.onConstructor(this, identifier);
    }

    public RadishStairsBlock(int id, Block baseBlock) {
        super(id, baseBlock.material);
        this.baseBlock = baseBlock;
        this.textureId = baseBlock.textureId;
        this.setSoundGroup(baseBlock.soundGroup);
        this.setOpacity(255);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.appendProperties(builder);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        LivingEntity placer = context.getPlayer();
        int facing;
        if (placer == null) {
            facing = 0;
        } else {
            facing = MathHelper.floor((double) (placer.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        }
        return this.getDefaultState().with(FACING, facing);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    public void addIntersectingBoundingBox(World world, int x, int y, int z, Box box, ArrayList boxes) {
        int facing = world.getBlockMeta(x, y, z);
        if (facing == 0) {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
            super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
            this.setBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
        } else if (facing == 1) {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
            super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
            this.setBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
        } else if (facing == 2) {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
            super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
            this.setBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
            super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
        } else if (facing == 3) {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
            super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
            this.setBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
            super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
        }
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return this.id;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return 1;
    }
}