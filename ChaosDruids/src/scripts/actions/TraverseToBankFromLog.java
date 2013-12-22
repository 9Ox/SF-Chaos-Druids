
package scripts.actions;

import java.util.HashMap;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.RSUtil;
import scripts.Variables;
import scripts.framework.Action;

/**
 * @author Starfox
 * @version 12/22/13
 */
public class TraverseToBankFromLog extends Action {
    
    private String foodName;
    private final RSTile BANK_TILE = new RSTile(2615, 3332, 0);

    @Override
    public boolean shouldExecute() {
        return isEastOfLog() && !Variables.BANK_AREA.contains(Player.getPosition()) &&
               Inventory.find(foodName).length <= 0;
    }

    @Override
    public void execute() {
        if(Game.getDestination() == null || !RSUtil.isAcceptableDestination(null)) {
            PathFinding.aStarWalk(BANK_TILE);
        }
    }

    @Override
    public void initOptions(HashMap<String, String> options) {
        foodName = options.get("Food name");
    }

    private boolean isEastOfLog() {
        return Player.getPosition().getPosition().getX() >= 2601;
    }
}
