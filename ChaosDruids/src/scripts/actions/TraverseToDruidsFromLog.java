
package scripts.actions;

import java.util.HashMap;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.RSUtil;
import scripts.Variables;
import scripts.framework.Action;

/**
 * @author Starfox
 * @version
 */
public class TraverseToDruidsFromLog extends Action {
    
    private String foodName;
    private final RSTile DEST = new RSTile(2565, 3356, 0);

    @Override
    public boolean shouldExecute() {
        return isWestOfLog() && !Variables.DRUID_AREA.contains(Player.getPosition()) &&
               Inventory.find(foodName).length > 0;
    }

    @Override
    public void execute() {
        RSObject door = null;
        if(Objects.findNearest(5, "Door").length > 0) {
            if(RSUtil.hasAction(Objects.findNearest(5, "Door")[0], "Pick-lock")) {
                door = Objects.findNearest(5, "Door")[0];
            }
        }
        if(door != null) {
            if(door.isOnScreen()) {
                if(RSUtil.interactObject(door, "Pick-lock")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return Variables.DRUID_AREA.contains(Player.getPosition());
                        }
                    }, 750);
                }
            } else {
                if(Game.getDestination() == null || !RSUtil.isAcceptableDestination(DEST)) {
                    Camera.turnToTile(door);
                    PathFinding.aStarWalk(DEST);
                }
            }
        } else {
            if(Game.getDestination() == null || !RSUtil.isAcceptableDestination(DEST)) {
                PathFinding.aStarWalk(DEST);
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
