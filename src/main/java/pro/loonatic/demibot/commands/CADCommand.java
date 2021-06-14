package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.util.List;

import static pro.loonatic.demibot.Config.isDebugMode;

public class CADCommand implements Command {

    public void process(MessageReceivedEvent event, List< String > args) throws Exception {
        MessageChannel channel = event.getChannel();
        List<Integer> keyCodes = new ArrayList<Integer>();

        Robot robot = new Robot();
        ArrayList<String> com = new ArrayList<>();
        com.add("ctrl");
        com.add("alt");
        com.add("delete");


        for (String str : com) {
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
        }
            robot.delay(250);


        robot.delay(2000);
        if(isDebugMode()) {
            System.out.println(event.getAuthor() + "Keypressed: " + args + " | Keycode: " + keyCodes);
        }
        channel.sendMessage("<@" + event.getAuthor().getId() + "> has ran: ``Control + Alt + Delete``").queue();
        CommandUtils.sendScreenshot(event);
    }
}
