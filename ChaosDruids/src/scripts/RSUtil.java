
package scripts;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

/**
 * @author Starfox
 * @version 12/21/2013
 */
public class RSUtil {
    
    /**
     * Gets the per hour value for the specified value.
     * @param value The value to evaluate.
     * @param startTime The time in ms when the script started.
     * @return The per hour value.
     */
    public static long getPerHour(final int value, final long startTime) {
        return (long)(value * 3600000D / (System.currentTimeMillis() - startTime));    
    }
    
    public static boolean hasAction(final RSObject object, final String action) {
        for(String s : object.getDefinition().getActions()) {
            if(s.equalsIgnoreCase(action)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the players current hit points.
     * @return the players current hit points.
     */
    public static int getPlayerHPPercent() {
        return (int) (100.0 * ((double)Skills.SKILLS.HITPOINTS.getCurrentLevel() / (double)Skills.SKILLS.HITPOINTS.getActualLevel()));
    }
    
    /**
     * Gets all of the tile adjacent to the specified tile.
     * @param tile The tile to get the adjacent tiles of.
     * @return An array of tiles containing the adjacent tiles.
     */
    public static RSTile[] getAdjacentTiles(final RSTile tile) {
        return new RSTile[]{ tile,
            new RSTile(tile.getX() + 1, tile.getY(), tile.getPlane()),
            new RSTile(tile.getX(), tile.getY() + 1, tile.getPlane()),
            new RSTile(tile.getX() + 1, tile.getY() + 1, tile.getPlane()),
            new RSTile(tile.getX() - 1, tile.getY(), tile.getPlane()),
            new RSTile(tile.getX(), tile.getY() - 1, tile.getPlane()),
            new RSTile(tile.getX() - 1, tile.getY() - 1, tile.getPlane()),
            new RSTile(tile.getX() + 1, tile.getY() - 1, tile.getPlane()),
            new RSTile(tile.getX() - 1, tile.getY() + 1, tile.getPlane())
        };
    }
    
    /**
     * Checks to see whether or not the second tile is adjacent to the first.
     * @param tile1 The tile that will be the basis for the second tile.
     * @param tile2 The tile to be checked.
     * @return true if the second tile is adjacent to the first; false otherwise.
     */
    public static boolean isAdjacentTile(final RSTile tile1, final RSTile tile2) {
        if(tile1 == null || tile2 == null) {
            return false;
        }
        for(RSTile t : getAdjacentTiles(tile1)) {
            if(t.equals(tile2)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks to see whether or not the current game destination is an
     * acceptable destination based on the destination specified.
     * @param dest The destination to be checked.
     * @return true if the destination is acceptable; false otherwise.
     */
    public static boolean isAcceptableDestination(final RSTile dest) {
        return Game.getDestination() != null && isAdjacentTile(dest, Game.getDestination());
    }
    
    /**
     * Interacts with the specified NPC using the specified action.
     * @param npc The NPC to interact with.
     * @param action The action to select when interacting.
     * @param rightClick true to right click; false otherwise.
     * @return true if the interaction was successful; false otherwise.
     */
    public static boolean interactNPC(final RSNPC npc, final String action, final boolean rightClick) {
        if(npc != null) {
            if(rightClick) {
                Mouse.hop(npc.getModel().getCentrePoint());
                General.sleep(25);
                if(DynamicClicking.clickRSNPC(npc, 3)) {
                    return ChooseOption.select(action);
                }
            }
            Mouse.hop(npc.getModel().getCentrePoint());
            return npc.click(action);
        }
        return false;
    }
    
    /**
     * Interacts with the specified RSObject using the specified action.
     * @param object The RSObject to interact with.
     * @param action The action to select when interacting.
     * @return true if the interaction was successful; false otherwise.
     */
    public static boolean interactObject(final RSObject object, final String action) {
        if(object != null) {
            if(object.hover()) {
                return DynamicClicking.clickRSObject(object, action);
            }
        }
        return false;
    }
    
    public static boolean interactGroundItem(final RSGroundItem item, final String action) {
        if(item != null) {
            if(item.hover()) {
                Mouse.click(3);
                if(ChooseOption.isOpen() && ChooseOption.isOptionValid(action)) {
                    return ChooseOption.select(action + " " + item.getDefinition().getName());
                } else {
                    Mouse.click(1);
                    interactGroundItem(item, action);
                }
            }
        }
        return false;
    }
    
    /**
     * Checks to see if the inventory contains only the RSItems that
     * have the same names as the Strings specified.
     * @param names The names to check for.
     * @return true if the inventory contains only the RSItems with the names
     * specified; false otherwise
     */
    public static boolean inventoryContainsOnly(final String... names) {
        if(Inventory.getAll() != null) {
            return Inventory.getAll().length <= Inventory.find(names).length;
        }
        return false;
    }
}
