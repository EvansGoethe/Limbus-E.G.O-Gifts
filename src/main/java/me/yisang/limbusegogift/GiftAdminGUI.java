package me.yisang.limbusegogift;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GiftAdminGUI implements InventoryHolder {

    private static final int PAGE_SIZE = 45;
    private static final int SLOT_PREV = 45;
    private static final int SLOT_PAGE = 49;
    private static final int SLOT_NEXT = 53;

    private final LimbusEGOGift plugin;
    private final List<ItemStack> allItems;
    private final Inventory inventory;
    private int page;
    private final int maxPage;

    public GiftAdminGUI(LimbusEGOGift plugin, int page) {
        this.plugin = plugin;
        this.allItems = buildItemList(plugin);
        this.maxPage = Math.max(0, (allItems.size() - 1) / PAGE_SIZE);
        this.page = Math.min(page, maxPage);
        this.inventory = Bukkit.createInventory(this, 54, plugin.color("&#FFD700✦ &f飾品管理"));
        refresh();
    }

    private static List<ItemStack> buildItemList(LimbusEGOGift plugin) {
        List<ItemStack> list = new ArrayList<>();
        list.add(plugin.createMenuOpener());
        for (Accessory acc : plugin.getAllAccessories()) {
            list.add(acc.createItem());
        }
        return list;
    }

    private void refresh() {
        ItemStack filler = makeFiller();
        for (int i = 0; i < 54; i++) inventory.setItem(i, filler.clone());

        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, allItems.size());
        for (int i = start; i < end; i++) {
            inventory.setItem(i - start, allItems.get(i));
        }

        // Navigation row
        if (page > 0) inventory.setItem(SLOT_PREV, makeNavItem(Material.ARROW, plugin.color("&f◀ &7上一頁")));
        inventory.setItem(SLOT_PAGE, makePageIndicator());
        if (page < maxPage) inventory.setItem(SLOT_NEXT, makeNavItem(Material.ARROW, plugin.color("&f下一頁 &7▶")));
    }

    public void changePage(int delta) {
        page = Math.max(0, Math.min(maxPage, page + delta));
        refresh();
    }

    public boolean isNavSlot(int slot) {
        return slot == SLOT_PREV || slot == SLOT_PAGE || slot == SLOT_NEXT;
    }

    @Override
    public Inventory getInventory() { return inventory; }

    private ItemStack makeFiller() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack makeNavItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack makePageIndicator() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.color("&f頁 " + (page + 1) + "/" + (maxPage + 1)));
        item.setItemMeta(meta);
        return item;
    }
}