package scripts.framework;

/**
 * Allows for the creation of dynamic conditions.
 *
 * @author Starfox
 */
public abstract class RSCondition {

    /**
     * Checks to see if the RSCondition has been met.
     *
     * @return true if the RSCondition has been met; false otherwise.
     */
    public abstract boolean isConditionMet();
}
