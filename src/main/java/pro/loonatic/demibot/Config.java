package pro.loonatic.demibot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

public class Config {
    private static final String CONFIG_FILE = "config/botconfig.json";
    private static final String DEFAULT_BOT_TOKEN = "default";
    private static final String DEFAULT_OWNER_ID = "owner";
    private static final ArrayList<String> DEFAULT_USER_IDS = new ArrayList<>();
    private static final String DEFAULT_SERVER_ID = "Put your primary server ID here.";
    private static final ArrayList<String> DEFAULT_SERVER_IDS = new ArrayList<>();
    private static JSONObject configObj;
    private static String botToken;
    private static String serverID;
    private static String OwnerID;
    private static final ArrayList<String> UserIDs = new ArrayList<>();
    private static final ArrayList<String> serverIDs = new ArrayList<>();
    private static Logger log = Logger.getLogger(Config.class.getName());
    private static boolean setupDone = false;
    private static Field JSONObjectMapField = null;

    static {
        DEFAULT_USER_IDS.add("Put");
        DEFAULT_USER_IDS.add("Other");
        DEFAULT_USER_IDS.add("User");
        DEFAULT_USER_IDS.add("IDs");
        DEFAULT_USER_IDS.add("Here");
        DEFAULT_SERVER_IDS.add("Put");
        DEFAULT_SERVER_IDS.add("Other");
        DEFAULT_SERVER_IDS.add("Server");
        DEFAULT_SERVER_IDS.add("IDs");
        DEFAULT_SERVER_IDS.add("Here");
    }

    public static JSONObject getConfigObj() {
        return configObj;
    }
    public static ArrayList<String> getUserIDs() { return UserIDs; }
    public static String getOwnerID() { return OwnerID; }
    public static String getBotToken() {
        return botToken;
    }
    public static boolean hasBotToken() {
        return botToken != null && !botToken.equals(DEFAULT_BOT_TOKEN);
    }
    public static String getServerID() {
        return serverID;
    }
    public static ArrayList<String> getServerIDs() {
        return serverIDs;
    }
    public static boolean MultipleSIDsExist() {
        if(!getServerIDs().isEmpty())
            return true;
        else
            return false;
    }
    public static boolean MultipleUIDsExist() {
        if(!getUserIDs().isEmpty())
            return true;
        else
            return false;
    }

    // https://stackoverflow.com/a/36156142
    private static void setupFieldAccessor() {
        if( !setupDone ) {
            setupDone = true;
            try {
                JSONObjectMapField = JSONObject.class.getDeclaredField("map");
                JSONObjectMapField.setAccessible(true);
            } catch (NoSuchFieldException ignored) {
                log.warning("JSONObject implementation has changed, returning unmodified instance");
            }
        }
    }
    private static JSONObject create(Map in) {
        setupFieldAccessor();
        JSONObject object = new JSONObject();
        try {
            if (JSONObjectMapField != null) {
                JSONObjectMapField.set(object, new LinkedHashMap<>());
            }
        }catch (IllegalAccessException ignored) {}
        return object;
    }

    public static Map orderJSON() {
        Map jsonOrdered = new LinkedHashMap();
        jsonOrdered.put("a", getBotToken());
        jsonOrdered.put("b", getOwnerID());
        jsonOrdered.put("c", getUserIDs());
        jsonOrdered.put("d", getServerID());
        jsonOrdered.put("e", getServerIDs());
        return jsonOrdered;
    }

    public static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public static void loadConfig() {
        JSONArray ServerIDs = new JSONArray(serverIDs);
        JSONArray UserIDs = new JSONArray(serverIDs);
        File file = new File(CONFIG_FILE);
        file.getParentFile().mkdirs();

        if (!file.exists() || file.length() == 0) {
            JSONObject object = create(orderJSON());

            //JSONObject object = new JSONObject();
            object.put("botToken", DEFAULT_BOT_TOKEN);
            object.put("ServerID", DEFAULT_SERVER_ID);
            object.put("ServerIDs", DEFAULT_SERVER_IDS);
            object.put("OwnerID", DEFAULT_OWNER_ID);
            object.put("UserIDs", DEFAULT_USER_IDS);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                object.write(writer, 4, 0);
                new ConfigSetup(object);
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
            serverID = configObj.getString("ServerID");
            if(MultipleSIDsExist()) {
                ServerIDs = configObj.getJSONArray("ServerIDs");
            }
            if(MultipleUIDsExist()) {
                UserIDs = configObj.getJSONArray("UserIDs");
            }
            OwnerID = configObj.getString("OwnerID");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't read config");
            System.exit(0);
        }
    }

}