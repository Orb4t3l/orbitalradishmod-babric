package net.minecraft.src;

import java.util.Map;

/**
 * ModLoader entrypoint for Orbital Radish (b1.7.3 port).
 *
 * ModLoader-era mods are just a class named "mod_<YourModName>" on the classpath
 * extending BaseMod. There's no fabric.mod.json/mods.toml equivalent — this class
 * itself IS the manifest. ModLoader finds it via classpath scanning.
 *
 * NOTE: field/method names below (BaseMod.load(), ModLoader.addName, etc.) are the
 * well-documented ModLoader public API and are stable across versions — but the
 * exact vanilla obfuscated field names inside Block/Item constructors (like which
 * numeric slot is free, or Item.itemsList indices) depend on YOUR deobfuscated
 * b1.7.3 jar. Double-check ID collisions against your MCP mappings before shipping.
 */
public class mod_OrbitalRadish extends BaseMod {

    // ---- Item instances -------------------------------------------------
    // Vanilla b1.7.3 already uses item ids up into the 300s (arrows, potions,
    // etc). Pick free custom ids — 2000+ is the conventional "modded" range
    // used by most ModLoader-era mods to avoid stepping on other mods' toes.
    public static final Item radish        = new RadishFoodItem(2000, 2, false, true)
            .setItemName("radish");
    public static final Item cookedRadish  = new RadishFoodItem(2001, 5, false, false)
            .setItemName("radish_cooked");
    public static final Item radishLeaf    = new Item(2002).setItemName("radish_leaf");
    public static final Item radishStew    = new RadishStewItem(2003)
            .setItemName("radish_stew");
    public static final Item radishStick   = new RadishStickItem(2004)
            .setItemName("radish_stick");

    // ---- Block instances --------------------------------------------------
    // Vanilla block ids run 0-127ish in b1.7.3; free custom ids conventionally
    // start around 130-200 depending what else is installed. Adjust if you
    // hit a collision with another mod.
    public static final Block radishCrop = new RadishCropBlock(130, 0)
            .setHardness(0.0F)
            .setStepSound(Block.soundGrassFootstep)
            .setUnlocalizedName("radishCrop");

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public void load() {
        // ---- Item registration -------------------------------------------
        ModLoader.addName(radish, "Radish");
        ModLoader.addName(cookedRadish, "Cooked Radish");
        ModLoader.addName(radishLeaf, "Radish Leaf");
        ModLoader.addName(radishStew, "Radish Stew");
        ModLoader.addName(radishStick, "Radish Stick");

        // ---- Block registration -------------------------------------------
        // registerBlock(Block) alone gives it a plain BlockItem-style ItemStack
        // representation automatically. Crop blocks normally are NOT directly
        // placeable/pickup-able the way a regular block is (see RadishFoodItem's
        // useOnBlock plant-on-farmland logic instead) — but the block still needs
        // a name registered for tooltips/debug.
        ModLoader.registerBlock(radishCrop);
        ModLoader.addName(radishCrop, "Radish Crop");

        // ---- Entity registration -------------------------------------------
        // addEntityID(Class, name, networkId). Custom entity ids conventionally
        // start at 200+ in the ModLoader era to avoid vanilla's own ranges.
        ModLoader.addEntityID(RadishStickEntity.class, "RadishStick", 200);

        // ---- Recipes -------------------------------------------------------
        // No JSON recipe system exists — everything is registered here as code.
        // addRecipe(ItemStack result, Object... pattern) mirrors vanilla's
        // CraftingManager shaped-recipe format: alternating "row" strings then
        // 'char', Item/Block pairs.
        ModLoader.addRecipe(new ItemStack(radishStew, 1),
                new Object[] {
                        "R", "B",
                        Character.valueOf('R'), radish,
                        Character.valueOf('B'), Item.bowl
                });

        ModLoader.addShapelessRecipe(new ItemStack(radishLeaf, 1),
                new Object[] { Block.tallGrass });

        // Smelting: addSmelting(inputItemId, ItemStack result)
        ModLoader.addSmelting(radish.shiftedIndex, new ItemStack(cookedRadish, 1));
    }

    // ---- Rendering (client-side only) --------------------------------------
    @Override
    public void addRenderer(Map renderers) {
        renderers.put(RadishStickEntity.class, new RadishStickEntityRenderer());
    }
}