package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pro.loonatic.demibot.Config.isDebugMode;

public class KeystrokeCommand implements Command {

    public void process(MessageReceivedEvent event, List< String > args) throws Exception {
        MessageChannel channel = event.getChannel();
        String msg = event.getMessage().getContentRaw().replaceAll("\\s\\+\\s", "+");
        args = new ArrayList<String>(Arrays.asList(msg.split(" ")));
        args.remove(0);
        List<Integer> keyCodes = new ArrayList<Integer>();


        if (args.isEmpty()) {
            return;
        }

        Robot robot = new Robot();

        for (String arg : args) {
            if (arg.startsWith("[") && arg.endsWith("]")) {
                arg = arg.substring(1, arg.length() - 1);


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
        if(isDebugMode()) {
            System.out.println(event.getAuthor() + "Keypressed: " + args + " | Keycode: " + keyCodes);
        }
        channel.sendMessage("<@" + event.getAuthor().getId() + "> has keypressed: ``" + args + "``").queue();
        CommandUtils.sendScreenshot(event);
    }
}