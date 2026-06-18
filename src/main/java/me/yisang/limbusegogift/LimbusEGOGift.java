package me.yisang.limbusegogift;

import me.yisang.limbusegogift.gifts.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LimbusEGOGift extends JavaPlugin implements Listener, TabCompleter {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    private static final String PACK_URL  = "https://github.com/EvansGoethe/Limbus_E.G.O_Gifts_plugin_ResourcePack/releases/download/pre_releases/Limbus_E.G.O_Gifts_plugin_ResourcePack.v.20.zip";
    private static final String PACK_HASH = "35b68648a8b1f1aef3c614b615f5e1bb8733e6ff";
    private static final java.util.UUID PACK_UUID = java.util.UUID.nameUUIDFromBytes(
            PACK_URL.getBytes(java.nio.charset.StandardCharsets.UTF_8));

    private static byte[] hexToBytes(String hex) {
        byte[] data = new byte[hex.length() / 2];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) ((Character.digit(hex.charAt(i * 2), 16) << 4)
                             + Character.digit(hex.charAt(i * 2 + 1), 16));
        }
        return data;
    }

    private NamespacedKey ITEM_ID_KEY;
    private NamespacedKey MENU_OPENER_KEY;
    private NamespacedKey[] SLOT_KEYS;
    private NamespacedKey UPGRADE_KEY_PREFIX;

    private final Map<String, Accessory> accessories = new LinkedHashMap<>();
    private final Map<UUID, AccessoryMenu> openMenus = new HashMap<>();
    private final Map<UUID, GiftAdminGUI> openAdminMenus = new HashMap<>();

    private GachaChestManager gachaChestManager;
    private GachaListener gachaListener;

    // ── 階級表 ──────────────────────────────────────────────────────────────

    private static final Map<String, Integer> TIER_MAP = new HashMap<>();
    private static final Set<String> VESTIGE_IDS = new HashSet<>();

    static {
        // ─ Tier I (9) ─
        for (String id : new String[]{
            "rags", "ashes_to_ashes", "lithograph",
            "emerald_elytra", "plume_of_proof", "blue_zippo_lighter",
            // 新飾品
            "nixie_divergence", "prejudice", "bloody_gadget"
        }) TIER_MAP.put(id, 1);

        // ─ Tier II (23) ─
        for (String id : new String[]{
            "harestride", "strange_glyph_talisman", "frozen_cries",
            "crystallized_blood", "ebony_brooch", "tangled_bones",
            "tenacity_bolus", "oracle", "dreaming_electric_sheep",
            "trauma_shield", "homeward", "pain_of_stifled_rage",
            "golden_urn", "smoking_gunpowder", "cqc_manual",
            "mental_corruption_boosting_gas",
            // 新飾品
            "carmilla", "child_within_a_flask", "green_spirit",
            "sanguine_blossom_bolus", "late_bloomers_tattoo",
            "e_type_dimensional_dagger", "bloodflame_sword"
        }) TIER_MAP.put(id, 2);

        // ─ Tier III (27) ─
        for (String id : new String[]{
            "dust_to_dust", "phantom_pain", "moon_in_the_water",
            "ardent_flower", "hot_n_juicy_drumstick", "dry_to_the_bone_breast",
            "distant_star", "broken_compass", "illusory_hunt",
            "keenbranch", "chief_butlers_secret_arts", "finifugality",
            "trial_plan_guide", "sour_liquor_aroma", "spicebush_branch",
            "hardship", "rest", "la_manchaland_all_day_pass",
            "la_manchaland_standard_pass",
            // 新飾品
            "nebulizer", "strange_glyph_inscriptions", "rusty_commemorative_coin",
            "someones_device", "special_contract", "flower_in_the_mirror",
            "sunshower", "thunderbranch"
        }) TIER_MAP.put(id, 3);

        // ─ Tier IV (21) ─
        for (String id : new String[]{
            "clear_mirror_calm_water", "flower_mound", "piece_of_crumbled_egg",
            "piece_of_a_torn_summer", "mask_of_the_parade", "black_sheet_music",
            "cold_illusion", "ruin", "jin_gang_bolus",
            "piece_of_relationship", "tranquil_lotus_bolus", "cask_spirits",
            "the_book_of_vengeance", "dueling_manual_book_3",
            // 新飾品
            "endless_hunger", "royal_jelly_perfume", "millarca",
            "artistic_sense", "handheld_mirror", "glimpse_of_flames", "sownpour"
        }) TIER_MAP.put(id, 4);

        // ─ 殘影 ─
        VESTIGE_IDS.addAll(List.of(
            "dark_vestige", "faint_vestige", "twinkling_vestige", "brilliant_vestige"
        ));
    }

    // ── 初始化 ──────────────────────────────────────────────────────────────

    @Override
    public void onEnable() {
        ITEM_ID_KEY          = new NamespacedKey(this, "accessory_id");
        MENU_OPENER_KEY      = new NamespacedKey(this, "menu_opener");
        UPGRADE_KEY_PREFIX   = new NamespacedKey(this, "upgrade_prefix_placeholder");
        SLOT_KEYS = new NamespacedKey[5];
        for (int i = 0; i < 5; i++) SLOT_KEYS[i] = new NamespacedKey(this, "slot_" + i);

        // ── 現有飾品 ─────────────────────────────────────────────────────
        registerAccessory(new ArdentFlower(this));
        registerAccessory(new AshesToAshes(this));
        registerAccessory(new BlackSheetMusic(this));
        registerAccessory(new BlueZippoLighter(this));
        registerAccessory(new BrilliantVestige(this));
        registerAccessory(new BrokenCompass(this));
        registerAccessory(new CaskSpirits(this));
        registerAccessory(new ChiefButlersSecretArts(this));
        registerAccessory(new ClearMirrorCalmWater(this));
        registerAccessory(new ColdIllusion(this));
        registerAccessory(new CQCManual(this));
        registerAccessory(new CrystallizedBlood(this));
        registerAccessory(new DistantStar(this));
        registerAccessory(new DreamingElectricSheep(this));
        registerAccessory(new DryToTheBoneBreast(this));
        registerAccessory(new DuelingManualBook3(this));
        registerAccessory(new DustToDust(this));
        registerAccessory(new EbonyBrooch(this));
        registerAccessory(new EmeraldElytra(this));
        registerAccessory(new Finifugality(this));
        registerAccessory(new FlowerMound(this));
        registerAccessory(new FrozenCries(this));
        registerAccessory(new GoldenUrn(this));
        registerAccessory(new Hardship(this));
        registerAccessory(new Harestride(this));
        registerAccessory(new Homeward(this));
        registerAccessory(new HotNJuicyDrumstick(this));
        registerAccessory(new IllusoryHunt(this));
        registerAccessory(new JinGangBolus(this));
        registerAccessory(new Keenbranch(this));
        registerAccessory(new LaManchalandAllDayPass(this));
        registerAccessory(new LaManchalandStandardPass(this));
        registerAccessory(new Lithograph(this));
        registerAccessory(new MaskOfTheParade(this));
        registerAccessory(new MentalCorruptionBoostingGas(this));
        registerAccessory(new MoonInTheWater(this));
        registerAccessory(new Oracle(this));
        registerAccessory(new PainOfStifledRage(this));
        registerAccessory(new PhantomPain(this));
        registerAccessory(new PieceOfATornSummer(this));
        registerAccessory(new PieceOfCrumbledEgg(this));
        registerAccessory(new PieceOfRelationship(this));
        registerAccessory(new PlumeOfProof(this));
        registerAccessory(new Rags(this));
        registerAccessory(new Rest(this));
        registerAccessory(new Ruin(this));
        registerAccessory(new SmokingGunpowder(this));
        registerAccessory(new SourLiquorAroma(this));
        registerAccessory(new SpicebushBranch(this));
        registerAccessory(new StrangeGlyphTalisman(this));
        registerAccessory(new TangledBones(this));
        registerAccessory(new TenacityBolus(this));
        registerAccessory(new TheBookOfVengeance(this));
        registerAccessory(new TranquilLotusBolus(this));
        registerAccessory(new TraumaShield(this));
        registerAccessory(new TrialPlanGuide(this));
        registerAccessory(new TwinklingVestige(this));

        // ── 新飾品 ───────────────────────────────────────────────────────
        registerAccessory(new ArtisticSense(this));
        registerAccessory(new BloodFlameSword(this));
        registerAccessory(new BloodyGadget(this));
        registerAccessory(new Carmilla(this));
        registerAccessory(new ChildWithinAFlask(this));
        registerAccessory(new DarkVestige(this));
        registerAccessory(new EndlessHunger(this));
        registerAccessory(new ETypeDimensionalDagger(this));
        registerAccessory(new FaintVestige(this));
        registerAccessory(new FlowerInTheMirror(this));
        registerAccessory(new GlimpseOfFlames(this));
        registerAccessory(new GreenSpirit(this));
        registerAccessory(new HandheldMirror(this));
        registerAccessory(new LateBloomersTattoo(this));
        registerAccessory(new Millarca(this));
        registerAccessory(new Nebulizer(this));
        registerAccessory(new NixieDivergence(this));
        registerAccessory(new Prejudice(this));
        registerAccessory(new RoyalJellyPerfume(this));
        registerAccessory(new RustyCommemorativeCoin(this));
        registerAccessory(new SanguineBlossomBolus(this));
        registerAccessory(new SomeonesDevice(this));
        registerAccessory(new Sownpour(this));
        registerAccessory(new SpecialContract(this));
        registerAccessory(new StrangeGlyphInscriptions(this));
        registerAccessory(new Sunshower(this));
        registerAccessory(new Thunderbranch(this));

        // ── Gacha 系統 ──────────────────────────────────────────────────
        gachaChestManager = new GachaChestManager(this);
        gachaListener = new GachaListener(this, gachaChestManager);
        getServer().getPluginManager().registerEvents(gachaListener, this);

        startPassiveTick();
        getServer().getPluginManager().registerEvents(this, this);

        Objects.requireNonNull(getCommand("accessories")).setExecutor(
                (sender, cmd, label, args) -> { if (sender instanceof Player p) openMenu(p); return true; });

        PluginCommand getgift = getCommand("getgift");
        if (getgift != null) {
            getgift.setExecutor(this::onGetGift);
            getgift.setTabCompleter(this);
        }

        PluginCommand gachachest = getCommand("gachachest");
        if (gachachest != null) {
            gachachest.setExecutor(this::onGachaChest);
        }

        PluginCommand egogift = getCommand("egogift");
        if (egogift != null) {
            egogift.setExecutor(this::onEgoGift);
            egogift.setTabCompleter(this);
        }
    }

    @Override
    public void onDisable() {
        if (gachaChestManager != null) gachaChestManager.onDisable();
    }

    public void registerAccessory(Accessory acc) {
        accessories.put(acc.getId(), acc);
    }

    // ── 公開工具方法 ────────────────────────────────────────────────────────

    public NamespacedKey getItemIdKey()      { return ITEM_ID_KEY; }
    public NamespacedKey getMenuOpenerKey()  { return MENU_OPENER_KEY; }
    public NamespacedKey getSlotKey(int i)   { return SLOT_KEYS[i]; }

    public String getAccessoryId(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        return item.getItemMeta().getPersistentDataContainer()
                .get(ITEM_ID_KEY, PersistentDataType.STRING);
    }

    public boolean isAccessory(ItemStack item) {
        return getAccessoryId(item) != null;
    }

    public Accessory getAccessory(String id) {
        return accessories.get(id);
    }

    public java.util.Collection<Accessory> getAllAccessories() {
        return accessories.values();
    }

    public List<Accessory> getEquippedAccessories(Player player) {
        return AccessoryMenu.getEquipped(player, this);
    }

    public int getTier(String id) {
        return TIER_MAP.getOrDefault(id, 1);
    }

    public boolean isVestige(String id) {
        return VESTIGE_IDS.contains(id);
    }

    // ── 升級系統 ────────────────────────────────────────────────────────────

    public int getUpgradeLevel(Player player, String accessoryId) {
        NamespacedKey key = new NamespacedKey(this, "upgrade_" + accessoryId);
        return player.getPersistentDataContainer().getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    public void setUpgradeLevel(Player player, String accessoryId, int level) {
        NamespacedKey key = new NamespacedKey(this, "upgrade_" + accessoryId);
        player.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, Math.min(3, Math.max(0, level)));
    }

    public double getUpgradeMultiplier(Player player, String accessoryId) {
        return switch (getUpgradeLevel(player, accessoryId)) {
            case 1 -> 1.25;
            case 2 -> 1.50;
            case 3 -> 2.00;
            default -> 1.0;
        };
    }

    // 殘影 ID → 可升級的目標飾品等級
    private int vestigeTier(String vestigeId) {
        return switch (vestigeId) {
            case "dark_vestige"      -> 1;
            case "faint_vestige"     -> 2;
            case "twinkling_vestige" -> 3;
            case "brilliant_vestige" -> 4;
            default -> -1;
        };
    }

    // ── 開啟選單 ────────────────────────────────────────────────────────────

    public void openMenu(Player player) {
        AccessoryMenu menu = new AccessoryMenu(player, this);
        openMenus.put(player.getUniqueId(), menu);
        menu.open();
    }

    // ── 飾品選單開啟物品（NETHER_STAR + PDC menu_opener）────────────────────

    public ItemStack createMenuOpener() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color("&#FFD700✦ &f飾品欄位"));
        meta.setLore(List.of(color("&#AAAAAA右鍵開啟飾品欄位")));
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(MENU_OPENER_KEY, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    private boolean isMenuOpener(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer()
                .has(MENU_OPENER_KEY, PersistentDataType.BYTE);
    }

    // ── 被動 Tick（每 20 tick 呼叫所有裝備飾品的 onPassiveTick）──────────────

    private void startPassiveTick() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Accessory acc : getEquippedAccessories(player)) {
                    acc.onPassiveTick(player);
                }
            }
        }, 0L, 20L);
    }

    // ── GUI 事件 ────────────────────────────────────────────────────────────

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        // 飾品圖鑑 click
        if (event.getInventory().getHolder() instanceof GiftCatalogGUI catalog) {
            int slot = event.getRawSlot();
            if (slot >= event.getInventory().getSize()) {
                if (event.getClick().isShiftClick()) event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
            int tier = catalog.getTierForSlot(slot);
            if (tier != -1 && tier != catalog.getCurrentTier()) {
                catalog.switchTier(player, tier);
            } else if (catalog.isCloseSlot(slot)) {
                player.closeInventory();
            }
            return;
        }

        // Admin GUI click
        GiftAdminGUI adminGui = openAdminMenus.get(player.getUniqueId());
        if (adminGui != null && event.getInventory().equals(adminGui.getInventory())) {
            event.setCancelled(true);
            int slot = event.getSlot();
            if (slot < 0 || slot >= 54) return;
            if (slot == 45) { adminGui.changePage(-1); return; }
            if (slot == 53) { adminGui.changePage(+1); return; }
            if (adminGui.isNavSlot(slot)) return;
            ItemStack item = event.getCurrentItem();
            if (item != null && !item.getType().isAir()) {
                player.getInventory().addItem(item.clone());
                player.sendMessage(color("&#FFD700已給予物品。"));
            }
            return;
        }

        AccessoryMenu menu = openMenus.get(player.getUniqueId());
        if (menu == null || !event.getInventory().equals(menu.getInventory())) return;

        int raw = event.getRawSlot();
        int size = menu.getInventory().getSize();

        if (raw < size) {
            if (!menu.isAccessorySlot(raw)) {
                event.setCancelled(true);
                return;
            }
            // 飾品欄位：檢查是否用殘影升級
            ItemStack cursor = event.getCursor();
            if (cursor != null && !cursor.getType().isAir()) {
                String cursorId = getAccessoryId(cursor);
                if (cursorId != null && isVestige(cursorId)) {
                    // 嘗試升級當前格子的飾品
                    ItemStack current = event.getCurrentItem();
                    String currentId = getAccessoryId(current);
                    if (currentId != null && !isVestige(currentId)) {
                        int vTier = vestigeTier(cursorId);
                        int aTier = getTier(currentId);
                        if (vTier == aTier) {
                            int curLevel = getUpgradeLevel(player, currentId);
                            if (curLevel < 3) {
                                event.setCancelled(true);
                                setUpgradeLevel(player, currentId, curLevel + 1);
                                // 消耗一個殘影
                                if (cursor.getAmount() > 1) cursor.setAmount(cursor.getAmount() - 1);
                                else event.getView().setCursor(null);
                                int newLevel = curLevel + 1;
                                player.sendMessage(color("&#FFD700飾品升級成功！&#AAAAAA等級：&#FFFFFF▲ Lv." + newLevel));
                                // 刷新選單
                                Bukkit.getScheduler().runTaskLater(this, () -> {
                                    menu.saveEquipped();
                                    menu.refresh();
                                }, 1L);
                            } else {
                                event.setCancelled(true);
                                player.sendMessage(color("&#FF5555此飾品已達最高等級（Lv.3）。"));
                            }
                            return;
                        }
                    }
                    event.setCancelled(true);
                    return;
                }
                // 非殘影：只允許飾品
                if (!isAccessory(cursor)) {
                    event.setCancelled(true);
                }
            }
        } else {
            if (event.getClick().isShiftClick()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getInventory().getHolder() instanceof GiftCatalogGUI) {
            event.setCancelled(true);
            return;
        }
        AccessoryMenu menu = openMenus.get(player.getUniqueId());
        if (menu == null || !event.getInventory().equals(menu.getInventory())) return;

        // 殘影不可拖入飾品欄
        String draggedId = getAccessoryId(event.getOldCursor());
        if (draggedId != null && isVestige(draggedId)) {
            event.setCancelled(true);
            return;
        }

        for (int slot : event.getRawSlots()) {
            if (slot < menu.getInventory().getSize() && !menu.isAccessorySlot(slot)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        openAdminMenus.remove(player.getUniqueId());
        AccessoryMenu menu = openMenus.remove(player.getUniqueId());
        if (menu == null || !event.getInventory().equals(menu.getInventory())) return;
        menu.saveEquipped();
    }

    // ── 資源包推送 ───────────────────────────────────────────────────────────

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        getLogger().info("[ResourcePack] Sending pack to " + player.getName());
        try {
            player.addResourcePack(PACK_UUID, PACK_URL, hexToBytes(PACK_HASH),
                    "Receiving resource pack...", true);
            getLogger().info("[ResourcePack] Sent successfully to " + player.getName());
        } catch (Exception e) {
            getLogger().severe("[ResourcePack] Failed to send: " + e.getMessage());
        }
    }

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        if (!PACK_UUID.equals(event.getID())) return;
        getLogger().info("[ResourcePack] " + event.getPlayer().getName()
                + " status: " + event.getStatus().name());
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            event.getPlayer().kickPlayer("§c請接受資源包以加入伺服器。");
        }
    }

    // ── 阻止飾品（試煉鑰匙）開啟試煉寶箱 ────────────────────────────────────

    @EventHandler
    public void onVaultInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.VAULT) return;
        if (isAccessory(event.getItem())) event.setCancelled(true);
    }

    // ── 選單開啟物品右鍵 ────────────────────────────────────────────────────

    @EventHandler
    public void onMenuOpenerUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!isMenuOpener(event.getItem())) return;
        event.setCancelled(true);
        openMenu(event.getPlayer());
    }

    // ── 飾品 onInteract 分發（右鍵任意互動） ────────────────────────────────

    @EventHandler
    public void onAccessoryInteract(PlayerInteractEvent event) {
        if (isMenuOpener(event.getItem())) return; // 已由上方處理
        Player player = event.getPlayer();
        for (Accessory acc : getEquippedAccessories(player)) {
            acc.onInteract(event, player);
        }
    }

    // ── 攻擊 / 受傷 / 受任何傷害 / 擊殺事件，分發給裝備中的飾品 ──────────────

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker) {
            for (Accessory acc : getEquippedAccessories(attacker)) {
                acc.onAttack(event, attacker);
            }
        }
        if (event.getEntity() instanceof Player victim) {
            for (Accessory acc : getEquippedAccessories(victim)) {
                acc.onDamaged(event, victim);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent) return; // 已由 onEntityDamageByEntity 處理
        if (event.getEntity() instanceof Player victim) {
            for (Accessory acc : getEquippedAccessories(victim)) {
                acc.onAnyDamage(event, victim);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deceased = event.getEntity();
        for (Accessory acc : getEquippedAccessories(deceased)) {
            acc.onDeath(event, deceased);
        }
        // 飾品欄開啟工具死亡不掉落
        event.getDrops().removeIf(item -> {
            if (isMenuOpener(item)) {
                event.getItemsToKeep().add(item);
                return true;
            }
            return false;
        });
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;
        for (Accessory acc : getEquippedAccessories(killer)) {
            acc.onKill(event, killer);
        }
    }

    // ── 指令 ────────────────────────────────────────────────────────────────

    private boolean onGetGift(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length == 0) return false;

        String id = args[0].toLowerCase();
        if (id.equals("admin")) {
            if (!player.hasPermission("limbus.admin") && !player.isOp()) return true;
            GiftAdminGUI gui = new GiftAdminGUI(this, 0);
            openAdminMenus.put(player.getUniqueId(), gui);
            player.openInventory(gui.getInventory());
            return true;
        }
        if (id.equals("menu")) {
            player.getInventory().addItem(createMenuOpener());
        } else if (id.equals("lunacy")) {
            if (!player.hasPermission("limbus.admin") && !player.isOp()) return true;
            int amount = 1;
            if (args.length >= 2) {
                try { amount = Math.min(64, Integer.parseInt(args[1])); } catch (NumberFormatException ignored) {}
            }
            player.getInventory().addItem(gachaListener.createLunacy(amount));
        } else {
            Accessory acc = accessories.get(id);
            if (acc != null) acc.give(player);
        }
        return true;
    }

    private boolean onEgoGift(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) return false;
        switch (args[0].toLowerCase()) {
            case "category" -> {
                if (!(sender instanceof Player player)) return true;
                GiftCatalogGUI gui = new GiftCatalogGUI(this, 1);
                player.openInventory(gui.getInventory());
            }
            case "reload" -> {
                if (sender instanceof Player p && !p.hasPermission("limbus.admin") && !p.isOp()) {
                    sender.sendMessage(color("&#FF5555你沒有權限使用此指令。"));
                    return true;
                }
                gachaChestManager.reload();
                sender.sendMessage(color("&#FFD700[LimbusEGOGift] &#AAAAAA插件資料已重新載入。"));
            }
            default -> { return false; }
        }
        return true;
    }

    private boolean onGachaChest(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (!player.hasPermission("limbus.admin") && !player.isOp()) {
            player.sendMessage(color("&#FF5555你沒有權限使用此指令。"));
            return true;
        }
        if (args.length == 0) return false;

        org.bukkit.block.Block target = player.getTargetBlockExact(10);
        if (target == null || target.getType() != Material.CHEST) {
            player.sendMessage(color("&#FF5555請看向一個箱子（10格內）。"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "set" -> {
                if (gachaChestManager.register(target.getLocation())) {
                    player.sendMessage(color("&#FFD700已設定此箱子為抽取箱。"));
                } else {
                    player.sendMessage(color("&#AAAAAA此箱子已是抽取箱。"));
                }
            }
            case "remove" -> {
                if (gachaChestManager.unregister(target.getLocation())) {
                    player.sendMessage(color("&#FFD700已移除此箱子的抽取箱狀態。"));
                } else {
                    player.sendMessage(color("&#AAAAAA此箱子不是抽取箱。"));
                }
            }
            default -> { return false; }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equals("egogift")) {
            if (args.length == 1) return List.of("category", "reload").stream()
                .filter(s -> s.startsWith(args[0].toLowerCase())).toList();
            return Collections.emptyList();
        }
        if (command.getName().equals("gachachest")) {
            if (args.length == 1) return List.of("set", "remove").stream()
                .filter(s -> s.startsWith(args[0].toLowerCase())).toList();
            return Collections.emptyList();
        }
        if (args.length == 1) {
            List<String> ids = new ArrayList<>(List.of("admin", "menu", "lunacy"));
            ids.addAll(accessories.keySet());
            return ids.stream().filter(s -> s.startsWith(args[0].toLowerCase())).toList();
        }
        return Collections.emptyList();
    }

    // ── 顏色代碼工具 ─────────────────────────────────────────────────────────

    public String color(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder r = new StringBuilder("§x");
            for (char c : hex.toCharArray()) r.append('§').append(c);
            matcher.appendReplacement(sb, r.toString());
        }
        matcher.appendTail(sb);
        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }
}
