package scripts.framework;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private final static ArrayList<Action> scriptActions = new ArrayList<>();
    private final static ArrayList<RSCondition> terminateConditions = new ArrayList<>();
    private static boolean hasClosed = false;
    private static int mouseSpeed = 125;

    public static ArrayList<Action> getScriptActions() {
        return scriptActions;
    }

    public static ArrayList<RSCondition> getTerminateConditions() {
        return terminateConditions;
    }
    
    public static boolean isHasClosed() {
        return hasClosed;
    }
    
    public static int getMouseSpeed() {
        return mouseSpeed;
    }
    
    public static void setHasClosed(final boolean b) {
        hasClosed = b;
    }
    
    public static void setMouseSpeed(final int speed) {
        mouseSpeed = speed;
    }

    public static void addTerminateConditions(final ArrayList<RSCondition> conditions) {
        for(final RSCondition condition : conditions) {
            getTerminateConditions().add(condition);
        }
    }

    public static void addActions(final ArrayList<Action> actions) {
        for(final Action action : actions) {
            getScriptActions().add(action);
        }
    }
    
    public static void initActions(final HashMap<String, String> options) {
        for(final Action action : getScriptActions()) {
            action.initOptions(options);
        }
    }
}
