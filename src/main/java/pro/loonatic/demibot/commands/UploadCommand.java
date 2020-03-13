package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.util.List;
import pro.loonatic.demibot.CommandUtils;

public class UploadCommand implements Command {
    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        String file = args.toString();
        file = file.replaceAll(",", "");

        CommandUtils.sendEmbedFor(event.getChannel(), event.getAuthor(), ";upload", "Sending file...", 2);

        //if statement isn't working here for some dum reason :-(
        if (!(CommandUtils.sendFile(event, file))) {
            event.getChannel().sendMessage("Couldn't send file.");
        }
    }
}