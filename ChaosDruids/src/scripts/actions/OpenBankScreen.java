
package scripts.actions;

import java.util.HashMap;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import scripts.Variables;
import scripts.framework.Action;

/**
 * @author Starfox
 * @version 12/22/13
 */
public class OpenBankScreen extends Action {
    
    private String foodName;

    @Override
    public boolean shouldExecute() {
        return !Banking.isBankScreenOpen() && Variables.BANK_AREA.contains(Player.getPosition()) &&
               Inventory.find(foodName).length <= 0;
    }

    @Override
    public void execute() {
        if(Banking.openBank()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    return Banking.isBankScreenOpen();
                }
            }, 1500);
        }
    }

    @Override
    public void initOptions(HashMap<String, String> options) {
        foodName = options.get("Food name");
    }
}
