package pro.loonatic.demibot.commands;

import java.awt.event.InputEvent;

public class RightClickCommand extends ClickCommand {

    public RightClickCommand() {
        super(InputEvent.BUTTON3_DOWN_MASK);
    }
}