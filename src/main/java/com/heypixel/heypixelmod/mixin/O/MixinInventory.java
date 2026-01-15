package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.Naven;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventInventoryUpdate;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class MixinInventory {

    @Inject(
            method = {"setItem"},
            at = @At("RETURN")
    )
    private void onSetItem(int slot, ItemStack stack, CallbackInfo ci) {
        Inventory inventory = (Inventory) (Object) this;
        Naven.getInstance().getEventManager().call(new EventInventoryUpdate(inventory, slot));
    }

    @Inject(
            method = {"removeItem"},
            at = @At("RETURN")
    )
    private void onRemoveItem(int slot, int amount, CallbackInfoReturnable<ItemStack> cir) {
        Inventory inventory = (Inventory) (Object) this;
        Naven.getInstance().getEventManager().call(new EventInventoryUpdate(inventory, slot));
    }

    @Inject(
            method = {"clearContent"},
            at = @At("RETURN")
    )
    private void onClearContent(CallbackInfo ci) {
        Inventory inventory = (Inventory) (Object) this;
        Naven.getInstance().getEventManager().call(new EventInventoryUpdate(inventory, -1));
    }

    @Inject(
            method = {"replaceWith"},
            at = @At("RETURN")
    )
    private void onReplaceWith(CallbackInfo ci) {
        Inventory inventory = (Inventory) (Object) this;
        Naven.getInstance().getEventManager().call(new EventInventoryUpdate(inventory, -1));
    }
}