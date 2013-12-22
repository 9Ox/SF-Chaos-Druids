
package scripts.actions;

import java.util.HashMap;
import org.tribot.api.General;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;
import scripts.RSUtil;
import scripts.Variables;
import scripts.framework.Action;

/**
 * @author Starfox
 * @version
 */
public class LootItem extends Action {
    
    private int[] loot;
    private String foodName;

    @Override
    public boolean shouldExecute() {
        RSGroundItem[] items = GroundItems.findNearest(loot);
        return Variables.DRUID_AREA.contains(Player.getPosition()) && 
               items.length > 0 &&
               Variables.DRUID_AREA.contains(items[0]);
    }

    @Override
    public void execute() {
        RSGroundItem[] items = GroundItems.findNearest(loot);
        RSGroundItem item = null;
        if(items.length > 0) {
            item = items[0];
        }
        if(item != null) {
            String name = item.getDefinition().getName();
            if(item.isOnScreen()) {
                if(Inventory.isFull() && Inventory.find(foodName).length > 0) {
                    Inventory.find(foodName)[0].click();
                } else {
                    if(RSUtil.interactGroundItem(item, "Take")) {
                        final int start = Inventory.getCount(item.getID());
                        final int check = Inventory.getAll().length;
                        for(int i = 0; i < 250; i++) {
                            if(Inventory.getCount(item.getID()) > start) {
                                Variables.itemCount += Inventory.getCount(item.getID()) - start;
                                break;
                            }
                            if(Inventory.getAll().length > check) {
                                break;
                            }
                            if(Player.isMoving()) {
                                General.println("Resetting");
                                i = 0;
                            }
                            General.sleep(10);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void initOptions(HashMap<String, String> options) { 
        foodName = options.get("Food name");
        loot = new int[Variables.lootList.size()];
        for(int i = 0; i < loot.length; i++) {
            loot[i] = Variables.lootList.get(i);
            General.println(loot[i]);
        }
    }
}
