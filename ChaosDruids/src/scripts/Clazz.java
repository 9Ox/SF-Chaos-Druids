
package scripts;

import org.tribot.api2007.GroundItems;
import org.tribot.script.Script;

/**
 * @author Starfox
 * @version
 */
public class Clazz extends Script {

    @Override
    public void run() {
        RSUtil.interactGroundItem(GroundItems.find("Bones")[0], "Take");
    }

}
