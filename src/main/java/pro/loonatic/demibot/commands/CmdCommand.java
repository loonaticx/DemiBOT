package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

public class CmdCommand implements Command {


    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        MessageChannel channel = event.getChannel();
        Robot robot = new Robot();
        CommandUtils utils = new CommandUtils();

        try {
            String arg2com = String.join(" ", args);
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", arg2com);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            robot.delay(1000);

            String line;
            String Message = "";

                while(true) {
                    line = r.readLine();
                    if(line == null) {
                        break;
                    }
                    Message = Message + line + "\n";
                }

            //utils.sendEmbed(channel, author, "cmd", "cmd", "```html\n" + Message + "```");
            if (!Message.isEmpty()) {
                channel.sendMessage("```html\n" + Message + "```").queue();
            }



        } catch (Exception e) {
            event.getChannel().sendMessage("We couldn't run: " + String.join(" ", args) + " ***Exception:*** " + e.toString() ).queue();
        }
    }
}
