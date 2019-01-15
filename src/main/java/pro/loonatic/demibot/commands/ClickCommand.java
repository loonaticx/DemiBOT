package pro.loonatic.demibot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.util.List;

public class ClickCommand implements Command {

    private int clickType;

    public ClickCommand(int clickType) {
        this.clickType = clickType;
    }

    public int getClickType() {
        return clickType;
    }

    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        Robot robot = new Robot();
        robot.mousePress(clickType);
        robot.mouseRelease(clickType);
        robot.delay(1000);
        CommandUtils.sendScreenshot(event);
    }
}
