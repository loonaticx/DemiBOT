package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.util.List;

public class ScreenshotCommand implements Command {

    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        CommandUtils.sendEmbedFor(event.getChannel(), event.getAuthor(), ";ss", "Sending screenshot...", 2);

        if (!CommandUtils.sendScreenshot(event)) {
            event.getChannel().sendMessage("Couldn't send screenshot :(");
        }
    }
}
