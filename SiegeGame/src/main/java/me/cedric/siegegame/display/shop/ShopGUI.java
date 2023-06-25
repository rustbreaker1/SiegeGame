package me.cedric.siegegame.display.shop;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.cedric.siegegame.model.game.WorldGame;
import me.cedric.siegegame.player.GamePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopGUI {

    private final List<ShopItem> shopItems = new ArrayList<>();
    private final WorldGame worldGame;
    private String guiName = "Shop";
    private ChestGui chestGui;
    private StaticPane pane;

    public ShopGUI(WorldGame worldGame) {
        this.worldGame = worldGame;
        createGUI();
    }

    public void addItem(ShopItem shopItem) {
        shopItems.add(shopItem);
        ItemStack displayItem = shopItem.getDisplayItem();
        int slot = shopItem.getSlot();
        pane.addItem(new GuiItem(displayItem, event -> {
            GamePlayer gamePlayer = worldGame.getPlayer(event.getWhoClicked().getUniqueId());
            if (gamePlayer != null) {
                shopItem.handlePurchase(gamePlayer);
            }
            event.setCancelled(true);
        }), slot % 9, slot / 9);
    }

    public void removeItem(ItemStack item) {
        shopItems.removeIf(shopItem -> shopItem.getDisplayItem().equals(item));
    }

    public List<ShopItem> getShopItems() {
        return new ArrayList<>(shopItems);
    }

    private void createGUI() {
        int rows = 3;
        this.chestGui = new ChestGui(rows, guiName);
        this.pane = new StaticPane(0, 0, 9, rows);
        chestGui.addPane(pane);
        chestGui.setOnGlobalClick(event -> event.setCancelled(true));
    }

    public void clear() {
        pane.clear();
    }

    public void setGUIName(String guiName) {
        this.guiName = guiName;
    }

    public ChestGui getGUI() {
        return chestGui;
    }
}

