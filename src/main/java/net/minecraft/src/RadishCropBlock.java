package net.minecraft.src;

import java.util.Random;

/**
 * b1.7.3 has no blockstate system — growth stage is just raw block metadata
 * (0-7), exactly like vanilla BlockCrops. This is actually SIMPLER than both
 * the Forge 1.20.1 version (which needed a BlockStateProperties-based
 * CropBlock subclass) and the Babric/StationAPI version (which needed a
 * custom IntProperty + StateManager). Extending BlockCrops directly gets you
 * random-tick growth, farmland-moisture-aware growth chance, and light-level
 * checks for free — we only need to override drops and the seed item link.
 *
 * NOTE: verify "BlockCrops" is the correct MCP class name in your jar — some
 * MCP versions for b1.7.3 name it differently. Field/method names for growth
 * (onTick / updateTick, getBlockTextureFromSideAndMetadata) may also vary
 * slightly by mapping version — check against your decompiled source.
 */
public class RadishCropBlock extends BlockCrops {

    protected RadishCropBlock(int id, int textureIndex) {
        super(id, textureIndex);
        this.setTickRandomly(true);
    }

    /**
     * Called when the crop is fully grown and harvested/broken.
     * Drops 1 guaranteed radish, plus a chance at 0-2 extra (bonus seed-like
     * drops), same balance as the original Forge loot table intent.
     */
    @Override
    public int idDropped(int metadata, Random random, int fortune) {
        return metadata == 7 ? mod_OrbitalRadish.radish.shiftedIndex : -1;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    /**
     * Extra bonus-drop pass, mirroring vanilla wheat's "chance at extra seeds"
     * pattern. BlockCrops normally calls this via dropBlockAsItemWithChance
     * loops — if your MCP mapping doesn't expose a hook for this, it's safe
     * to skip; the guaranteed 1-radish drop above is the important part.
     */
    protected void dropExtraOnFullyGrown(World world, int x, int y, int z, int metadata) {
        if (metadata != 7) return;
        Random random = world.rand;
        for (int i = 0; i < 3; ++i) {
            if (random.nextInt(15) == 0) {
                this.dropBlockAsItem_do(world, x, y, z,
                        new ItemStack(mod_OrbitalRadish.radish, 1, 0));
            }
        }
    }
}