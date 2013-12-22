
package scripts.actions;

import java.util.HashMap;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import scripts.framework.Action;

/**
 * @author Starfox
 * @version 12/22/13
 */
public class WithdrawFood extends Action {
    
    private String foodName;

    @Override
    public boolean shouldExecute() {
        return Banking.isBankScreenOpen() && Inventory.find(foodName).length <= 0;
    }

    @Override
    public void execute() {
        if(Banking.withdraw(1, foodName)) {
            final int start = Inventory.getCount(new String[]{foodName});
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    return Inventory.getCount(new String[]{foodName}) > start;
                }
            }, 2000);
        }
    }

    @Override
    public void initOptions(HashMap<String, String> options) {
        foodName = options.get("Food name");
    }
}
