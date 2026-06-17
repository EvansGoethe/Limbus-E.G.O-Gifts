package me.yisang.limbusegogift;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface Accessory {
    String getId();
    ItemStack createItem();
    void give(Player player);

    default void onPassiveTick(Player player) {}
    default void onAttack(EntityDamageByEntityEvent event, Player attacker) {}
    default void onDamaged(EntityDamageByEntityEvent event, Player victim) {}
    default void onAnyDamage(EntityDamageEvent event, Player victim) {}
    default void onKill(EntityDeathEvent event, Player killer) {}
    default void onDeath(PlayerDeathEvent event, Player deceased) {}
    default void onInteract(PlayerInteractEvent event, Player player) {}
}
