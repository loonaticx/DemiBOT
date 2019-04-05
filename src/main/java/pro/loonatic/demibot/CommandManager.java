package pro.loonatic.demibot;

import pro.loonatic.demibot.commands.*;

import java.util.HashMap;
import java.util.Map;

import static pro.loonatic.demibot.Config.getOwnerID;
import static pro.loonatic.demibot.Config.getUserIDs;

public class CommandManager {
    private static Map<String, Command> commands = new HashMap<String, Command>();
    static {
        addCommand("ss", new ScreenshotCommand());
        addCommand("rs", new RecordCommand());
        addCommand("run", new RunCommand());
        addCommand("cmd", new CmdCommand());
        addCommand("ping", new PingCommand());
        addCommand("leftclick", new LeftClickCommand());
        addCommand("lc", new LeftClickCommand());
        addCommand("rightclick", new RightClickCommand());
        addCommand("rc", new RightClickCommand());
        addCommand("middleclick", new MiddleClickCommand());
        addCommand("mc", new MiddleClickCommand());
        addCommand("type", new TypeCommand());
        addCommand("t", new TypeCommand());
        addCommand("setmouse", new MouseCommand(1));
        addCommand("sm", new MouseCommand(1));
        addCommand("movemouse", new MouseCommand(2));
        addCommand("mm", new MouseCommand(2));
        addCommand("ks", new KeystrokeCommand());
        addCommand("k", new KeystrokeCommand());
        addCommand("keystroke", new KeystrokeCommand());
        addCommand("help", new HelpCommand());
        addCommand("h", new HelpCommand());
        //addCommand("gitpush", new );
        addCommand("chava", new AvatarCommand()); //gay boy dun work
        try {
            addCommand("sysinfo", new SInfoCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandManager(boolean debug) {
        if(debug) {
            System.out.println("Headless? " + java.awt.GraphicsEnvironment.isHeadless());
            System.out.println("User IDs: " + getUserIDs());
            System.out.println("Owner ID: " + getOwnerID());
        }
    }

    public static void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    public static Command getCommandStarts(String msg) {
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            if (msg.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }
}

//Development / Debug commmands
