package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import net.minecraft.world.entity.player.Inventory;

public class EventInventoryUpdate implements Event {
    private final Inventory inventory;
    private final int changedSlot;  // 变化的槽位，-1 表示整体变化
    
    public EventInventoryUpdate(Inventory inventory, int changedSlot) {
        this.inventory = inventory;
        this.changedSlot = changedSlot;
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public int getChangedSlot() {
        return changedSlot;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventInventoryUpdate that = (EventInventoryUpdate) o;
        return changedSlot == that.changedSlot && 
               (inventory == that.inventory || (inventory != null && inventory.equals(that.inventory)));
    }
    
    @Override
    public int hashCode() {
        int result = inventory != null ? inventory.hashCode() : 0;
        result = 31 * result + changedSlot;
        return result;
    }
    
    @Override
    public String toString() {
        return "EventInventoryUpdate{inventory=" + inventory + ", changedSlot=" + changedSlot + "}";
    }
}