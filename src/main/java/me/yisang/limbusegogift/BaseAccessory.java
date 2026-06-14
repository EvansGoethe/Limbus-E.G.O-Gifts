package me.yisang.limbusegogift;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class BaseAccessory implements Accessory {

    protected final LimbusEGOGift plugin;
    private final String id;
    private final String name;
    private final String lore;

    protected BaseAccessory(LimbusEGOGift plugin, String id, String name, String lore) {
        this.plugin = plugin;
        this.id = id;
        this.name = name;
        this.lore = lore;
    }

    @Override
    public String getId() { return id; }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.color("&#FFD700" + name));
        meta.setLore(List.of(plugin.color("&#AAAAAA" + lore)));
        meta.setItemModel(new NamespacedKey("gifts", id));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        meta.getPersistentDataContainer().set(plugin.getItemIdKey(), PersistentDataType.STRING, id);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void give(Player player) {
        player.getInventory().addItem(createItem());
    }
}
