package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeystrokeCommand implements Command {

    public void process(MessageReceivedEvent event, List< String > args) throws Exception {
        String msg = event.getMessage().getContentRaw().replaceAll("\\s\\+\\s", "+");
        args = new ArrayList<String>(Arrays.asList(msg.split(" ")));
        args.remove(0);

        if (args.isEmpty()) {
            return;
        }

        Robot robot = new Robot();

        for (String arg : args) {
            if (arg.startsWith("[") && arg.endsWith("]")) {
                arg = arg.substring(1, arg.length() - 1);

                List<Integer> keyCodes = new ArrayList<Integer>();

                for (String str : arg.split("\\+")) {
                    int keyCode = CommandUtils.getKeyCode(str);

                    if (keyCode != -1) {
                        keyCodes.add(keyCode);
                    }
                }

                if (!keyCodes.isEmpty()) {
                    for (int keyCode : keyCodes) {
                        robot.keyPress(keyCode);
                    }

                    for (int keyCode : keyCodes) {
                        robot.keyRelease(keyCode);
                    }
                } else {
                    CommandUtils.typeString(robot, arg);
                }
            } else {
                CommandUtils.typeString(robot, arg);
            }

            robot.delay(250);
        }

        robot.delay(2000);
        CommandUtils.sendScreenshot(event);
    }
}