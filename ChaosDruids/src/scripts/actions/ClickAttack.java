
package scripts.actions;

import java.util.HashMap;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import scripts.RSUtil;
import scripts.Variables;
import scripts.framework.Action;

/**
 * @author Starfox
 * @version
 */
public class ClickAttack extends Action {
    
    private String foodName;

    @Override
    public boolean shouldExecute() {
        return Variables.DRUID_AREA.contains(Player.getPosition()) &&
               Inventory.find(foodName).length > 0 && Player.getRSPlayer().getInteractingCharacter() == null;
    }

    @Override
    public void execute() {
        RSNPC bestNPC = null;
        RSNPC[] candidates = NPCs.findNearest(NPCs.generateFilterGroup(new Filter<RSNPC>() {
            @Override
            public boolean accept(RSNPC t) {
                return t != null && t.getName().equalsIgnoreCase("chaos druid") && !t.isInCombat();
            }
        }));
        if(candidates.length > 0) {
            bestNPC = candidates[0];
        }
        if(bestNPC != null && !Player.isMoving()) {
            if(bestNPC.isOnScreen()) {
                 if(RSUtil.interactNPC(bestNPC, "Attack", Player.getRSPlayer().getCombatLevel() < bestNPC.getCombatLevel())) {
                     Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return Player.getRSPlayer().getInteractingCharacter() != null;
                        }
                    }, 4000);
                 }
            } else {
                Camera.turnToTile(bestNPC);
            }
        }
    }

    @Override
    public void initOptions(HashMap<String, String> options) {
        foodName = options.get("Food name");
    }
}
