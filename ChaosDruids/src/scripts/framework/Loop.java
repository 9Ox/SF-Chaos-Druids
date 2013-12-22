package scripts.framework;

import org.tribot.api.General;

/**
 * @author Starfox
 */
public class Loop {

    private final int loopSpeed;

    public Loop(final int loopSpeed) {
        this.loopSpeed = loopSpeed;
    }

    /**
     * The main script loop. Should return based on a delay
     * set by the Initializer.
     * 
     * @return
     */
    public int loop() {
        if(!executeActions()) {
            return -1;
        }
        General.sleep(this.loopSpeed);
        return loop();
    }

    private boolean processAction(final Action action) {
        if(action.shouldExecute()) {
            action.execute();
            return true;
        }
        return false;
    }

    private void processActions() {
        for(final Action action : Manager.getScriptActions()) {
            if(processAction(action)) {
                return;
            }
        }
    }

    /**
     * Executes the Actions from the Manager.
     *
     * @return true if execution was successful; false otherwise.
     */
    public boolean executeActions() {
        if(shouldTerminate()) {
            return false;
        }
        processActions();
        return true;
    }

    private boolean shouldTerminate() {
        for(RSCondition condition : Manager.getTerminateConditions()) {
            if(condition.isConditionMet()) {
                General.println("Terminate");
                return true;
            }
        }
        return false;
    }
}
