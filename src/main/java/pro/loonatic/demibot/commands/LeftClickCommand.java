package pro.loonatic.demibot.commands;

import java.awt.event.InputEvent;

public class LeftClickCommand extends ClickCommand {

    public LeftClickCommand() {
        super(InputEvent.BUTTON1_DOWN_MASK);
    }
}