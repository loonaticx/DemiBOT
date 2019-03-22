package pro.loonatic.demibot;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.commands.Command;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class CommandUtils {

    public static final String OpSystem = System.getProperty("os.name");
    public static final boolean isWindows = OpSystem.toLowerCase().contains("win");
    public static final boolean isLinux = OpSystem.toLowerCase().replace('u', 'i').contains("ix"); // LAZY LEVEL 9999
    public static final boolean isMac = OpSystem.toLowerCase().contains("mac");
    public static final boolean isSolaris = OpSystem.toLowerCase().contains("sunos");

    public static HashMap<String, String> exePaths = new HashMap<>();
    public static HashMap<String, Boolean> exeDependencies = new HashMap<>();

    private String PublicIP;
    public static String ffdesktop;
    public static String ffinterface;

    static {
        exePaths.put("ffmpeg", "./ffmpeg.exe");
        exePaths.put("ffprobe", "./ffprobe.exe");
        exePaths.put("nircmd", "./nircmd.exe");
        exePaths.put("wget", "./wget.exe");
        if(isWindows) { ffdesktop = "gdigrab"; ffinterface = "desktop"; }
        if(isWindows) { ffdesktop = "avfoundation"; ffinterface = ":0"; }
        if(isLinux || isSolaris) { ffdesktop = "x11grab"; ffinterface = ":0"; } //idk about solaris but whatever


    }


    private static int[] numpad = {
            KeyEvent.VK_NUMPAD0,
            KeyEvent.VK_NUMPAD1,
            KeyEvent.VK_NUMPAD2,
            KeyEvent.VK_NUMPAD3,
            KeyEvent.VK_NUMPAD4,
            KeyEvent.VK_NUMPAD5,
            KeyEvent.VK_NUMPAD6,
            KeyEvent.VK_NUMPAD7,
            KeyEvent.VK_NUMPAD8,
            KeyEvent.VK_NUMPAD9
    };
    private static DateFormat hourFormat = new SimpleDateFormat("hh:mm a");

    public CommandUtils() {
    }

    public static void typeString(Robot robot, String str) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        boolean numlockOn = toolkit.getLockingKeyState(KeyEvent.VK_NUM_LOCK);

        if (!numlockOn) {
            robot.keyPress(KeyEvent.VK_NUM_LOCK);
        }

        for (int i = 0; i < str.length(); i++) {
            int code = (int) str.charAt(i);
            String codeStr;

            if (code <= 999) {
                codeStr = "0" + code;
            } else {
                codeStr = String.valueOf(code);
            }

            robot.keyPress(KeyEvent.VK_ALT);

            for (int c = 0; c < codeStr.length(); c++) {
                int key = codeStr.charAt(c) - '0';
                robot.keyPress(numpad[key]);
                robot.keyRelease(numpad[key]);
            }

            robot.keyRelease(KeyEvent.VK_ALT);
        }

        if (!numlockOn) {
            robot.keyPress(KeyEvent.VK_NUM_LOCK);
        }
    }

    public static boolean sendScreenshot(MessageChannel channel) {
        try {
            Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage img = new Robot().createScreenCapture(screen);
            PointerInfo pointer = MouseInfo.getPointerInfo();
            int x = (int) pointer.getLocation().getX();
            int y = (int) pointer.getLocation().getY();

            Image mouseImage = ImageIO.read(new File("src/main/resources/mouse.png")).getScaledInstance(15, 24, 0);
            img.getGraphics().drawImage(mouseImage, x, y, null);
            File imgFile = new File("src/main/resources/ss.png");
            ImageIO.write(img, "png", imgFile);
            channel.sendFile(imgFile).queue();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean sendScreenshot(MessageReceivedEvent event) {
        return sendScreenshot(event.getChannel());
    }

    public static Image getScreenshot() throws IOException {
        BufferedImage img = ImageIO.read(new File("src/main/resources/ss.png"));
        return img;
    }

    public static boolean FileCheck() {
        return FileCheck(exePaths);
    }
    public static boolean FileCheck(HashMap<String, String> exePaths) {
        boolean exists = true;
        String[] PathArr = new String[exePaths.size()];

        for(String x : exePaths.keySet()) {
            exeDependencies.put(x, false);

            // this should check if filepath exists
            if (isWindows) {
                File check = new File(exePaths.get(x));
                if (check.exists()) {
                    exeDependencies.put(x, true);
                } else {
                    exists = false;
                }
            }
            if (isLinux) { // we can probably make linux and mac the same if statement, idek about solaris who knows /shrug xd

            }
            if(isMac) {

            }
            if(isSolaris) {

            }
        }
        return exists;
    }



    public static int getKeyCode(String str) { // JESUS FUCKING CHRIST DISYER
        str = str.toLowerCase();

        switch (str) {
            case "lalt":
            case "alt":
                return KeyEvent.VK_ALT;
            case "ralt":
            case "altgr":
                return KeyEvent.VK_ALT_GRAPH;
            case "lshift":
            case "rshift":
            case "shift":
                return KeyEvent.VK_SHIFT;
            case "lctrl":
            case "rctrl":
            case "ctrl":
                return KeyEvent.VK_CONTROL;
            case "lwin":
            case "rwin":
            case "win":
                return KeyEvent.VK_WINDOWS;
            case "home":
                return KeyEvent.VK_HOME;
            case "insert":
                return KeyEvent.VK_INSERT;
            case "delete":
            case "del":
                return KeyEvent.VK_DELETE;
            case "end":
                return KeyEvent.VK_END;
            case "pageup":
                return KeyEvent.VK_PAGE_UP;
            case "pagedown":
                return KeyEvent.VK_PAGE_DOWN;
            case "prntscr":
            case "printscreen":
            case "scr":
            case "screen":
                return KeyEvent.VK_PRINTSCREEN;
            case "up":
                return KeyEvent.VK_UP;
            case "down":
                return KeyEvent.VK_DOWN;
            case "left":
                return KeyEvent.VK_LEFT;
            case "right":
                return KeyEvent.VK_RIGHT;
            case "esc":
            case "escape":
                return KeyEvent.VK_ESCAPE;
            case "tab":
                return KeyEvent.VK_TAB;
            case "caps":
            case "capslock":
                return KeyEvent.VK_CAPS_LOCK;
            case "enter":
                return KeyEvent.VK_ENTER;
            case "backspace":
                return KeyEvent.VK_BACK_SPACE;
            case "f1":
                return KeyEvent.VK_F1;
            case "f2":
                return KeyEvent.VK_F2;
            case "f3":
                return KeyEvent.VK_F3;
            case "f4":
                return KeyEvent.VK_F4;
            case "f5":
                return KeyEvent.VK_F5;
            case "f6":
                return KeyEvent.VK_F6;
            case "f7":
                return KeyEvent.VK_F7;
            case "f8":
                return KeyEvent.VK_F8;
            case "f9":
                return KeyEvent.VK_F9;
            case "f10":
                return KeyEvent.VK_F10;
            case "f11":
                return KeyEvent.VK_F11;
            case "f12":
                return KeyEvent.VK_F12;
        }

        if (str.length() == 1) {
            char ch = str.charAt(0);

            if (Character.isLetterOrDigit(ch)) {
                return KeyEvent.getExtendedKeyCodeForChar(ch);
            }
        }

        return -1;
    }

    public static void sendEmbed(MessageChannel channel, User user, String command, String title, String description, Consumer<Message> callback) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setDescription(description);
        builder.setColor(Color.red);
        builder.setAuthor(user.getName(), null, user.getAvatarUrl());
        builder.setFooter(String.format("%s • Today at %s", command, hourFormat.format(new Date())), null);
        channel.sendMessage(builder.build()).queue(callback);
    }

    public static void sendEmbed(MessageChannel channel, User user, String command, String title, String description) {
        sendEmbed(channel, user, command, title, description, null);
    }

    public static void sendEmbed(MessageChannel channel, User user, String command, String title) {
        sendEmbed(channel, user, command, title, null, null);
    }

    public static void sendCommandEmbed(MessageChannel channel, User user, String command, String description) {
        sendEmbed(channel, user, command, description, description, null);
    }

    public static void sendEmbedFor(MessageChannel channel, User user, String command, String title, String description, long seconds) {
        sendEmbed(channel, user, command, title, description, message -> message.delete().queueAfter(seconds, TimeUnit.SECONDS));
    }

    public static void sendEmbedFor(MessageChannel channel, User user, String command, String description, long seconds) {
        sendEmbedFor(channel, user, command, description, description, seconds);
    }

    //Hardware Info


    // NETWORKING

    public String getPublicIP() throws IOException {

        String PublicIP = "";
        URL MyIPURL = new URL("http://bot.whatismyipaddress.com");

        BufferedReader sc = new BufferedReader(new InputStreamReader(MyIPURL.openStream()));

        PublicIP = sc.readLine().trim();
        this.PublicIP = PublicIP;
        return PublicIP;
    }

    public InetAddress getLocalHost() throws UnknownHostException {
        InetAddress LocalHost = InetAddress.getLocalHost();
        return LocalHost;
    }

    public InetAddress getLoopbackAddress() {
        InetAddress LoopbackAddress = InetAddress.getLoopbackAddress();
        return LoopbackAddress;
    }

    public String getCanonicalHostName() throws UnknownHostException {
        String CanonicalHN = InetAddress.getLocalHost().getCanonicalHostName();
        return CanonicalHN;
    }

    public String getLocalIP() throws IOException {
        String HostAddress = InetAddress.getLocalHost().getHostAddress();
        return HostAddress;
    }

    public String getHostName() throws UnknownHostException {
        String HostName = InetAddress.getLocalHost().getHostName();
        return HostName;
    }

    public int gethashCode() throws UnknownHostException {
        int HashCode = InetAddress.getLocalHost().hashCode();
        return HashCode;
    }

}
