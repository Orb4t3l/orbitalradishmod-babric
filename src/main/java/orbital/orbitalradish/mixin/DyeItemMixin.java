package orbital.orbitalradish.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import orbital.orbitalradish.block.RadishCropBlock;
import orbital.orbitalradish.events.init.BlockListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DyeItem.class)
public class DyeItemMixin {

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	private void orbitalradish_bonemealRadishCrop(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir) {
		if (stack.getDamage() == 15 && world.getBlockId(x, y, z) == BlockListener.radishCrop.id) {
			if (!world.isRemote) {
				((RadishCropBlock) BlockListener.radishCrop).applyFullGrowth(world, x, y, z);
				--stack.count;
			}
			cir.setReturnValue(true);
		}
	}
}