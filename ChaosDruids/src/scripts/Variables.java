
package scripts;

import java.util.ArrayList;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

/**
 * @author Starfox
 * @version
 */
public class Variables {
    
    public static int itemCount = 0;
    public static ArrayList<Integer> lootList = new ArrayList<>();

    public static final RSArea DRUID_AREA = new RSArea(new RSTile(2562 , 3356, 0), 2);
    public static final RSArea BANK_AREA = new RSArea(new RSTile(2612, 3334, 0), new RSTile(2621, 3331, 0));
}
