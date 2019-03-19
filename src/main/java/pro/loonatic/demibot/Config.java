package pro.loonatic.demibot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class Config {

    public static final String CONFIG_FILE = "config/botconfig.json";
    public static final String DEFAULT_BOT_TOKEN = "default";

    private static JSONObject configObj = null;
    private static String botToken = null;

    public static JSONObject getConfigObj() {
        return configObj;
    }

    public static String getBotToken() {
        return botToken;
    }
    public static boolean hasBotToken() {
        return botToken != null && !botToken.equals(DEFAULT_BOT_TOKEN);
    }


    public static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public static void loadConfig() {
        File file = new File(CONFIG_FILE);
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            JSONObject object = new JSONObject();
            object.put("botToken", DEFAULT_BOT_TOKEN);

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                object.write(writer, 4, 0);
                writer.close();
            } catch (Exception e) {
                System.out.println("Couldn't write default config");
                e.printStackTrace();
                System.exit(0);
            }
        }

        try {
            configObj = new JSONObject(readFile(CONFIG_FILE));
            botToken = configObj.getString("botToken");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't read config");
            System.exit(0);
        }
    }

}