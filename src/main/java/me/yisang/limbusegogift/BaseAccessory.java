package me.yisang.limbusegogift;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAccessory implements Accessory {

    protected final LimbusEGOGift plugin;
    private final String id;
    private final String name;
    private final List<String> loreLines;

    // 無描述（舊格式向下相容）
    protected BaseAccessory(LimbusEGOGift plugin, String id, String name, String effect) {
        this.plugin = plugin;
        this.id = id;
        this.name = name;
        this.loreLines = List.of("&#AAAAAA" + effect);
    }

    // 有描述（描述顏色 + 描述文字 + 效果文字）
    protected BaseAccessory(LimbusEGOGift plugin, String id, String name,
                            String descColor, String description, String effect) {
        this.plugin = plugin;
        this.id = id;
        this.name = name;
        List<String> lines = new ArrayList<>();
        for (String part : description.split("\n")) {
            lines.add(descColor + part);
        }
        lines.add("&#AAAAAA" + effect);
        this.loreLines = List.copyOf(lines);
    }

    @Override
    public String getId() { return id; }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.TRIAL_KEY);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.color("&#FFFFFF" + name));
        meta.setLore(loreLines.stream().map(plugin::color).toList());
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
