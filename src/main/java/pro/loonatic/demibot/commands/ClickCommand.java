package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.util.List;

public class ClickCommand implements Command {

    private int clickType;
    private int clickType2;

    public ClickCommand(int clickType) {
        this.clickType = clickType;
    }
    public ClickCommand(int clickType, int clickType2) {
        this.clickType = clickType;
        this.clickType2 = clickType2;
    }

    public int getClickType() {
        return clickType;
    }

    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        Robot robot = new Robot();
        robot.mousePress(clickType);
        robot.mouseRelease(clickType);
        if(clickType2 != 0) {
            robot.mousePress(clickType2);
            robot.mouseRelease(clickType2);
        }
        robot.delay(1000);
        CommandUtils.sendScreenshot(event);
    }
}
