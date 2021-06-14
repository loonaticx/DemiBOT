package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class HelpCommand implements Command {

    private static String miscCommands = "`;ss` - screenshot screen\n" +
            "`;rs` - record screen for 5 seconds\n" +
            "`;run <process>` - run a process\n" +
            "`;cmd <command, flags>` - run a process from cmd/terminal\n" +
            "`;wget <website>` - download file from website ***WIP -- UNFINISHED***\n" +
            "`;upload <file>` - uploads a file (less than 8MB) to Discord.\n" +
            "`;ping` - check ping\n" +
            "`;sysinfo` - display system information\n" +
            "`;setstatus <status>` - set status of DemiBOT ***WIP***\n" +
            "`;help` - show help";
    private static String mouseCommands = "`;lc`, `;leftclick` - use left click button\n" +
            "`;dlc`, `;doubleleftclick` - double left click\n" +
            "`;rc`, `;rightclick` - use right click button\n" +
            "`;mc`, `;middleclick` - use middle click button\n" +
            "`;sm <x> <y>`, `;setmouse <x> <y>` - set mouse coordinates\n" +
            "`;mm <x> <y>`, `;movemouse <x> <y>` - move mouse by coordinates";
    private static String keyboardCommands = "`;t <msg>`, `;type <msg>` - type text\n" +
            "`;ks <keys>`, `;k <keys>` `;keystroke <keys>` - send keystrokes\n" +
            "**Keystrokes must be between brackets, for example:** `;ks [win+r] notepad [enter]`";

    public void process(MessageReceivedEvent event, List<String> args) throws Exception {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("DemiBOT", null);
        builder.setColor(Color.red);
        builder.setDescription("DemiBOT is an amazing remote control bot, created by the infamous Loonatic and Disyer.");
        builder.addField("Miscellaneous Commands", miscCommands, false);
        builder.addField("Keyboard Commands", keyboardCommands, false);
        builder.addField("Mouse Commands", mouseCommands, false);
        builder.setImage("https://camo.githubusercontent.com/a9d5089b66dbdb9ee04d9dbfdfe187d1658d6afa/687474703a2f2f692e696d6775722e636f6d2f707037736372762e706e67");
        event.getChannel().sendMessage(builder.build()).queue();
    }
}
