package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pro.loonatic.demibot.CommandUtils.*;

public class CmdCommand implements Command {


    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        MessageChannel channel = event.getChannel();
        List<String> commands = new ArrayList<String>();
        if(isWindows) {
            commands.add("cmd.exe");
            commands.add("/c");
        } else if (isLinux || isSolaris || isMac) {
            commands.add("/bin/bash");
            commands.add("-c");
        }


        CommandUtils utils = new CommandUtils();

        try {
            String arg2com = String.join(" ", args);
            String[] argArr = arg2com.split(" ");
            if(isWindows) {
                for(String elements : argArr) {
                    commands.add(elements);
                }
            } else if(isLinux || isSolaris || isMac) {
                commands.add(arg2com);
            }
            ProcessBuilder builder = new ProcessBuilder(commands);
            System.out.println(commands);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            Thread.sleep(1000);

            String line;
            String Message = "";

                while(true) {
                    line = r.readLine();
                    System.out.println(line);
                    if(line == null) {
                        break;
                    }
                    Message = Message + line + "\n";
                }
             String totalMessage = Arrays.toString(Message.split("(?<=\\G.{9})"));
            //utils.sendEmbed(channel, author, "cmd", "cmd", "```html\n" + Message + "```");
            if (!Message.isEmpty()) {
                    while(Message.length() > 1989) {
                        String Msg2 = Message.substring(0,1989);
                        Message = Message.substring(1989);
                        channel.sendMessage("```html\n" + Msg2 + "```").queue();

                    }
               if(Message.length()<1989) {
                        channel.sendMessage("```html\n" + Message + "```").queue();
               }
            }

        } catch (Exception e) {
            e.printStackTrace();
            event.getChannel().sendMessage("We couldn't run: " + String.join(" ", args) + " ***Exception:*** " + e.toString() ).queue();
        }
    }
}
