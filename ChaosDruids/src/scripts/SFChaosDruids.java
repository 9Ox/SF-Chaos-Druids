
package scripts;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.framework.Initializer;

/**
 * @author Starfox
 * @version 12/21/2013
 */
@ScriptManifest(name = "SF Chaos Druids", authors = "Starfox", category = "Money Making")
public class SFChaosDruids extends Script implements Painting {
    
    private long startTime = 0;

    @Override
    public void run() {
        Mouse.setSpeed(135);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SFChaosDruidsGui("Thank you for choosing SF Druids.").setVisible(true);
            }
        });
        startTime = System.currentTimeMillis();
        Initializer.initialize(50);
    }

    @Override
    public void onPaint(Graphics gr) {
        Graphics2D g = (Graphics2D)gr;
        g.setColor(Color.BLACK);
        g.drawRect(4, 4, 145, 62);
        g.setColor(new Color(0, 0, 0, 190));
        g.fillRect(4, 4, 145, 62);
        g.setColor(new Color(247, 247, 247, 190));
        g.drawString("SF Chaos Druids", 30, 20);
        g.drawString("_______________", 24, 23);
        g.drawString("Time ran: " + Timing.msToString(System.currentTimeMillis() - startTime), 8, 40);
        g.drawString("Herbs looted (/h): " + Variables.itemCount + " ("
                + RSUtil.getPerHour(Variables.itemCount, startTime) + ")", 8, 55);
    }
}
