package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.util.List;

public class ScreenshotCommand implements Command {

    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        // shell.exec('magick screenshotmouse.png -size 100x100 -set option:distort:viewport "%[w]x%[h]" xc:none -chop 1x1 -background black -splice 1x1 -virtual-pixel tile -distort SRT 0 -compose over -composite -bordercolor black -shave 1x1 -compose copy -border 1x1 screenshotgrid.png', cb);

        CommandUtils.sendEmbedFor(event.getChannel(), event.getAuthor(), ";ss", "Sending screenshot...", 2);

        if (!CommandUtils.sendScreenshot(event)) {
            event.getChannel().sendMessage("Couldn't send screenshot :(");
        }
    }
}
