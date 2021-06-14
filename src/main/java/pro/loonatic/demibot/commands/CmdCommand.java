package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;
import pro.loonatic.demibot.Config;

import static pro.loonatic.demibot.Config.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static pro.loonatic.demibot.CommandUtils.*;

public class CmdCommand implements Command {

    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        MessageChannel channel = event.getChannel();
        List<String> commands = new ArrayList<String>();

        if (isWindows) {
            commands.add("cmd.exe");
            commands.add("/c");
        } else if (isLinux || isSolaris || isMac) {
            commands.add("/bin/bash");
            commands.add("-c");
        }

        try {
            String arg2com = String.join(" ", args);
            String[] argArr = arg2com.split(" ");

            if (isWindows) {
                for (String elements : argArr) {
                    commands.add(elements);
                }
            } else if (isLinux || isSolaris || isMac) {
                commands.add(arg2com);
            }
            if(Config.isDebugMode()) {
                System.out.println(commands);
            }

            ProcessBuilder builder = new ProcessBuilder(commands).redirectErrorStream(true);
            Process process = builder.start();
            ExecutorService threadPool = Executors.newFixedThreadPool(1);
            Future<String> output = threadPool.submit(() -> CommandUtils.readStream(process.getInputStream()));

            threadPool.shutdown();

            if (!process.waitFor(Config.getCommandTimeout(), TimeUnit.MILLISECONDS)) {
                output.cancel(true);
                process.destroy();
                process.waitFor();
                channel.sendMessage("Command `" + String.join(" ", args) + "` timed out.").queue();
                return;
            }

            String message = output.get();
            //utils.sendEmbed(channel, author, "cmd", "cmd", "```html\n" + Message + "```");

            if (!message.isEmpty()) {
                while (message.length() > 1995) {
                    String msg2 = message.substring(0, 1995);
                    message = message.substring(1995);
                    channel.sendMessage(">>> " + msg2 + "").queue();
                }
                if (message.length() < 1995) {
                    channel.sendMessage(">>> " + message + "").queue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            event.getChannel().sendMessage("We couldn't run `" + String.join(" ", args) + "`\n\n***Exception:*** " + e.toString()).queue();
        }
    }
}
