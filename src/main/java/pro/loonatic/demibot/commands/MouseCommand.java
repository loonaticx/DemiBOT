package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.util.List;

public class MouseCommand implements Command {

    private int variation;

    public MouseCommand(int variation) {
        this.variation = variation;
    }

    public void process(MessageReceivedEvent event, List<String> args) throws Exception {

        //Current cursor position +- Args (x, y) --> New cur pos

        Robot robot = new Robot();
        if (variation == 1) {
            int x = Integer.parseInt(args.get(0));
            int y = Integer.parseInt(args.get(1));
            robot.mouseMove(x, y);
        }
        else if (variation == 2) {
            int x = MouseInfo.getPointerInfo().getLocation().x + Integer.parseInt(args.get(0));
            int y = MouseInfo.getPointerInfo().getLocation().y - Integer.parseInt(args.get(1));
            robot.mouseMove(x, y);
        }
        robot.delay(1000);
        CommandUtils.sendScreenshot(event);
    }
}
