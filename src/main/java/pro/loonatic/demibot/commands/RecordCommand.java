package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordCommand implements Command {
    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        Robot robot = new Robot();
        MessageChannel channel = event.getChannel();

        List<String> commands = new ArrayList<String>();
        String GAY = "ffmpeg -f gdigrab -draw_mouse 0 -i desktop -preset ultrafast -tune zerolatency -crf 0 -pix_fmt yuv420p -movflags +faststart -vframes 1 -q:v 1 screenshot.png -y";
        String[] GAYarr = GAY.split(" ");
            for(String a : GAYarr) {
                commands.add(a);
            }

        try {
            ProcessBuilder builder = new ProcessBuilder(commands);
            builder.start();
            robot.delay(1000);
            File recFile = new File("screenshot.png");
            channel.sendFile(recFile).queue();
        } catch (Exception e) {
            e.printStackTrace();
            event.getChannel().sendMessage("Couldn't send video! :( ***Exception: ***" + e.toString());
            System.out.println(System.getProperty("user.dir"));
        }
        }
    }
