package orbital.orbitalradish.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

/**
 * Same wrapping trick as StationAPI's own TemplateBlock (see TemplateBlock.java) — applied
 * to vanilla's StairsBlock instead of plain Block, so we get an Identifier-based constructor
 * while keeping StairsBlock's stair shape/placement behavior.
 *
 * Hardness/resistance is inherited from the base block automatically — confirmed from the
 * decompiled Block.java static init: vanilla WOODEN_STAIRS/COBBLESTONE_STAIRS never call
 * setHardness/setResistance themselves, yet clearly aren't insta-mine, so StairsBlock's
 * constructor must be copying those properties from the base block it's given.
 */
public class RadishStairsBlock extends StairsBlock implements BlockTemplate {

    public RadishStairsBlock(Identifier identifier, Block baseBlock) {
        this(BlockTemplate.getNextId(), baseBlock);
        BlockTemplate.onConstructor(this, identifier);
    }

    public RadishStairsBlock(int id, Block baseBlock) {
        super(id, baseBlock);
    }
}