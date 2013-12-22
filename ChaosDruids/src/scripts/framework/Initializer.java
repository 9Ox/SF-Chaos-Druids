package scripts.framework;

import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;


/**
 * @author Starfox
 */
public class Initializer {
    public static void initialize(final int loopSpeed) {
        Initializer i = new Initializer();
        i.waitForGUI();
        Mouse.setSpeed(Manager.getMouseSpeed());
        Loop loop = new Loop(loopSpeed);
        loop.loop();
    }

    private void waitForGUI() {
        Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                return Manager.isHasClosed();
            }
        }, 600000);
    }
}
