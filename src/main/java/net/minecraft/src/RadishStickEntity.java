package net.minecraft.src;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RadishStickEntity extends ArrowEntity {

    private static final java.lang.reflect.Field IN_GROUND_FIELD;

    static {
        try {
            IN_GROUND_FIELD = ArrowEntity.class.getDeclaredField("inGround");
            IN_GROUND_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public RadishStickEntity(World world) {
        super(world);
    }

    public RadishStickEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public RadishStickEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    private boolean isInGround() {
        try {
            return IN_GROUND_FIELD.getBoolean(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPlayerInteraction(PlayerEntity player) {
        if (!this.world.isRemote) {
            if (this.isInGround() && this.pickupAllowed && this.shake <= 0 && player.inventory.addStack(new ItemStack(Item.STICK, 1))) {
                this.world.playSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.sendPickup(this, 1);
                this.markDead();
            }
        }
    }
}