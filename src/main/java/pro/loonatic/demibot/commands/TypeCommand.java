package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.util.List;

public class TypeCommand implements Command {

    public void process(MessageReceivedEvent event, List < String > args) throws Exception {
        Robot robot = new Robot();
        String text = String.join(" ", args);

        CommandUtils.typeString(robot, text);
        robot.delay(2000);
        CommandUtils.sendScreenshot(event);
    }
}