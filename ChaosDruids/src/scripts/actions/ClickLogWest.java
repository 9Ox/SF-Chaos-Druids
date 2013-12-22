
package scripts.actions;

import java.util.HashMap;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.RSUtil;
import scripts.framework.Action;

/**
 * @author Starfox
 * @version
 */
public class ClickLogWest extends Action {
    
    private final RSTile LOG_TILE = new RSTile(2602, 3336, 0);
    private String foodName;

    @Override
    public boolean shouldExecute() {
        return !isWestOfLog() && Inventory.find(foodName).length > 0 &&
               !Banking.isBankScreenOpen();
    }

    @Override
    public void execute() {
        RSObject log = null;
        if(Objects.findNearest(20, "Log balance").length > 0) {
            log = Objects.findNearest(20, "Log balance")[0];
        }
        if(log != null) {
            if(log.isOnScreen()) {
                if(RSUtil.interactObject(log, "Walk-across")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return isWestOfLog();
                        }
                    }, 4000);
                }
            } else {
                if(Game.getDestination() == null || !RSUtil.isAcceptableDestination(LOG_TILE)) {
                    Camera.turnToTile(log);
                    PathFinding.aStarWalk(LOG_TILE);
                }
            }
        } else {
            if(Game.getDestination() == null || !RSUtil.isAcceptableDestination(LOG_TILE)) {
                PathFinding.aStarWalk(LOG_TILE);
            }
        }
    }

    @Override
    public void initOptions(HashMap<String, String> options) {
        foodName = options.get("Food name");
    }
    
    private boolean isWestOfLog() {
        return Player.getPosition().getPosition().getX() < 2601;
    }
}
