
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
 * @version 12/21/13
 */
public class ClickLogEast extends Action {
    
    private String foodName;
    private final RSTile LOG_TILE = new RSTile(2598, 3336, 0);

    @Override
    public boolean shouldExecute() {
        return !isEastOfLog() && Inventory.find(foodName).length <= 0;
    }

    @Override
    public void execute() {
        if(Variables.DRUID_AREA.contains(Player.getPosition())) {
            RSObject door = null;
            if(Objects.findNearest(6, "Door").length > 0) {
                if(RSUtil.hasAction(Objects.findNearest(6, "Door")[0], "Pick-lock")) {
                    door = Objects.findNearest(6, "Door")[0];
                }
            }
            if(door != null) {
                if(door.isOnScreen()) {
                    if(RSUtil.interactObject(door, "Open")) {
                        Timing.waitCondition(new Condition() {
                            @Override
                            public boolean active() {
                                return !Variables.DRUID_AREA.contains(Player.getPosition());
                            }
                        }, 3500);
                    }
                } else {
                    Camera.turnToTile(door);
                }
            }
        } else {
            RSObject log = null;
            if(Objects.findNearest(6, "Log balance").length > 0) {
                log = Objects.findNearest(6, "Log balance")[0];
            }
            if(log != null) {
                if(log.isOnScreen()) {
                    if(RSUtil.interactObject(log, "Walk-across")) {
                        Timing.waitCondition(new Condition() {
                            @Override
                            public boolean active() {
                                return isEastOfLog();
                            }
                        }, 6000);
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
    }

    @Override
    public void initOptions(HashMap<String, String> options) {
        foodName = options.get("Food name");
    }

    private boolean isEastOfLog() {
        return Player.getPosition().getPosition().getX() >= 2601;
    }
}
