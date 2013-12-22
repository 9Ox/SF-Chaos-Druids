package scripts.framework;

import java.util.HashMap;

/**
 * Allows for creation of dynamic actions.
 *
 * @author Starfox
 */
public abstract class Action {

    /**
     * Checks to see whether or not the Action should execute.
     *
     * @return true if the Action should execute; false otherwise.
     */
    public abstract boolean shouldExecute();

    /**
     * Executes the Action.
     */
    public abstract void execute();

    /**
     * Allows for the initialization of any options the Action is
     * using.
     *
     * @param options The options to use in the Action.
     */
    public abstract void initOptions(final HashMap<String, String> options);
}
