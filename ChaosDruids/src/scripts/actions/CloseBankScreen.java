
package scripts.actions;

import java.util.HashMap;
import org.tribot.api2007.Banking;
import scripts.RSUtil;
import scripts.framework.Action;

/**
 * @author Starfox
 * @version
 */
public class CloseBankScreen extends Action {
    
    private String foodName;

    @Override
    public boolean shouldExecute() {
        return Banking.isBankScreenOpen() && RSUtil.inventoryContainsOnly(foodName);
    }

    @Override
    public void execute() {
        Banking.close();
    }

    @Override
    public void initOptions(HashMap<String, String> options) {
        foodName = options.get("Food name");
    }
}
