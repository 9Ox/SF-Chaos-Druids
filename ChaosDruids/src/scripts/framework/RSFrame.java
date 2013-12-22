package scripts.framework;

import org.tribot.api.General;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class should be the parent class of any JFrame created
 * for the purpose of a GUI.
 *
 * @author Starfox
 */
public abstract class RSFrame extends JFrame {

    private final Object message;

    /**
     * Creates a RSFrame with the specified message.
     *
     * @param message The message to display once the RSFrame
     *                has been disposed of. Use null for no
     *                message to be displayed.
     */
    public RSFrame(final Object message) {
        this.message = message;
    }

    /**
     * Processes any and all options selected from the GUI and
     * returns them as a HashMap.
     *
     * @return The HashMap created from the options selected.
     */
    public abstract HashMap<String, String> processOptions();

    /**
     * Processes any and all terminate conditions.
     *
     * @param options The options to base the conditions off of.
     * @return The HashMap created.
     */
    public abstract ArrayList<RSCondition> processTerminateConditions(HashMap<String, String> options);

    /**
     * This method is automatically called by the startButtonPressed
     * method and should not be called from any other source. Returns
     * an ArrayList of Actions to be initialized in the Manager.
     *
     * @return An ArrayList of Actions.
     */
    public abstract ArrayList<Action> processActions();

    /**
     * Gets the RSFrame's dispose message.
     *
     * @return The RSFrame's dispose message.
     */
    private Object getMessage() {
        return this.message;
    }

    /**
     * This method should be called inside the ActionListener of
     * the start button in your GUI. It should not be called from
     * any other source.
     *
     * @param e The ActionEvent triggered when the start button was
     *          pressed.
     */
    protected final void startButtonPressed(ActionEvent e) {
        Manager.addActions(processActions());
        Manager.initActions(processOptions());
        Manager.addTerminateConditions(processTerminateConditions(processOptions()));
        disposeGUI(getMessage());
    }

    /**
     * Displays a message to the Client Debug and then disposes of the
     * RSFrame. This method is automatically called inside the
     * startButtonPressed method and should not be called from any
     * other source.
     *
     * @param message The message to be displayed before the RSFrame
     *                is disposed of.
     */
    private void disposeGUI(final Object message) {
        if(message != null) {
            General.println(message);
        }
        dispose();
    }
}
