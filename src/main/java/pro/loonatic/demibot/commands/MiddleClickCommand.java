package pro.loonatic.demibot.commands;

import java.awt.event.InputEvent;

public class MiddleClickCommand extends ClickCommand{
    public MiddleClickCommand() {
        super(InputEvent.BUTTON2_DOWN_MASK);
    }
}
