# Limbus E.G.O Gifts — Paper Plugin

將邊獄公司（Limbus Company）的 E.G.O 飾品帶進 Minecraft 的 Paper 插件。

- **Minecraft 版本**：1.21.4
- **平台**：Paper
- **飾品數量**：57 件
- **資源包**：玩家加入時自動推送，拒絕將被踢出伺服器

---

## 飾品系統

每位玩家有 **5 個飾品欄位**，可同時裝備 5 件飾品，效果全部疊加生效。

飾品效果分為四種觸發時機：
- **被動（每 20 tick）**：持續性效果，如回血、速度提升
- **攻擊時**：攻擊敵人時觸發
- **受傷時**：被攻擊時觸發
- **擊殺時**：擊殺生物時觸發
- **死亡時**：玩家死亡時觸發

---

## 飾品一覽（57 件）

| ID | 名稱 |
|----|------|
| `ardent_flower` | 熱情之花 |
| `ashes_to_ashes` | 塵歸塵 |
| `black_sheet_music` | 黑色樂譜 |
| `blue_zippo_lighter` | 藍色 Zippo 打火機 |
| `brilliant_vestige` | 輝煌的殘影 |
| `broken_compass` | 破損的指南針 |
| `cask_spirits` | 木桶烈酒 |
| `chief_butlers_secret_arts` | 總管的秘法 |
| `clear_mirror_calm_water` | 明鏡止水 |
| `cold_illusion` | 寒冷幻象 |
| `cqc_manual` | CQC 教範 |
| `crystallized_blood` | 結晶血液 |
| `distant_star` | 遙遠的星 |
| `dreaming_electric_sheep` | 夢中電羊 |
| `dry_to_the_bone_breast` | 乾枯的胸骨 |
| `dueling_manual_book_3` | 決鬥教範第三冊 |
| `dust_to_dust` | 土歸於土 |
| `ebony_brooch` | 烏木胸針 |
| `emerald_elytra` | 翡翠鞘翅 |
| `finifugality` | 終末逃避 |
| `flower_mound` | 花丘 |
| `frozen_cries` | 凍結的哭聲 |
| `golden_urn` | 黃金骨灰罈 |
| `hardship` | 艱辛 |
| `harestride` | 兔步 |
| `homeward` | 歸鄉 |
| `hot_n_juicy_drumstick` | 鮮嫩多汁雞腿 |
| `illusory_hunt` | 幻象狩獵 |
| `jin_gang_bolus` | 金剛丸 |
| `keenbranch` | 銳枝 |
| `la_manchaland_all_day_pass` | 拉曼恰樂園全日票 |
| `la_manchaland_standard_pass` | 拉曼恰樂園標準票 |
| `lithograph` | 石版畫 |
| `mask_of_the_parade` | 遊行面具 |
| `mental_corruption_boosting_gas` | 精神腐蝕增強氣體 |
| `moon_in_the_water` | 水中月 |
| `oracle` | 神諭 |
| `pain_of_stifled_rage` | 壓抑怒火之痛 |
| `phantom_pain` | 幻肢痛 |
| `piece_of_a_torn_summer` | 破碎夏日的碎片 |
| `piece_of_crumbled_egg` | 破碎蛋殼的碎片 |
| `piece_of_relationship` | 羈絆的碎片 |
| `plume_of_proof` | 證明之羽 |
| `rags` | 破布 |
| `rest` | 安息 |
| `ruin` | 廢墟 |
| `smoking_gunpowder` | 冒煙的火藥 |
| `sour_liquor_aroma` | 酸酒香氣 |
| `spicebush_branch` | 山胡椒枝 |
| `strange_glyph_talisman` | 奇異符文護符 |
| `tangled_bones` | 糾纏的骨骸 |
| `tenacity_bolus` | 堅韌丸 |
| `the_book_of_vengeance` | 復仇之書 |
| `tranquil_lotus_bolus` | 靜蓮丸 |
| `trauma_shield` | 創傷盾牌 |
| `trial_plan_guide` | 試驗計劃指南 |
| `twinkling_vestige` | 閃耀的殘影 |

---

## 指令

| 指令 | 說明 |
|------|------|
| `/accessories` | 開啟飾品欄位 GUI |
| `/getgift <id>` | 取得指定飾品 |
| `/getgift menu` | 取得飾品欄位開啟物品（下界之星） |
| `/getgift admin` | 開啟管理員 GUI（可翻頁瀏覽所有飾品） |

> `/getgift` 需要權限節點 `limbus.admin` 或 OP。  
> `/accessories` 所有玩家皆可使用。

---

## 資源包

插件使用獨立資源包提供飾品外觀，玩家加入時會自動收到推送。  
資源包來源：[Limbus_E.G.O_Gifts_plugin_ResourcePack](https://github.com/EvansGoethe/Limbus_E.G.O_Gifts_plugin_ResourcePack)

---

## 安裝方式

1. 將編譯好的 `.jar` 放入伺服器的 `plugins/` 資料夾
2. 啟動伺服器，資源包會自動推送給玩家
3. 輸入 `/getgift menu` 取得飾品欄位開啟物品，右鍵即可開啟飾品 GUI
