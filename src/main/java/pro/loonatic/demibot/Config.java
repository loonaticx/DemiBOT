package pro.loonatic.demibot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

public class Config {
    private static final String CONFIG_FILE = "config/botconfig.json";
    private static final String DEFAULT_BOT_TOKEN = "default";
    private static final String DEFAULT_PREFIX = ";";
    private static final Map<String, Object> DEFAULT_CONFIG = new LinkedHashMap<String, Object>();
    private static final JSONObject DEFAULT_DIRECT_PATHS = new JSONObject();

    private static JSONObject configObj;
    private static String botToken;
    private static String prefix;
    private static boolean debug;
    private static long commandTimeout;
    private static int threadCount;
    private static int maxConcurrentCommands;
    private static final List<String> ownerIds = new ArrayList<String>();
    private static final List<String> userIds = new ArrayList<String>();
    private static final List<String> roleIds = new ArrayList<String>();
    private static final List<String> serverIds = new ArrayList<String>();
    private static final List<String> channelIds = new ArrayList<String>();
    private static final Map<String, String> directPaths = new HashMap<String, String>();
    private static Logger log = Logger.getLogger(Config.class.getName());
    private static boolean setupDone = false;
    private static Field JSONObjectMapField = null;

    static {
        JSONArray defaultServerIds = new JSONArray(Arrays.asList("Put your primary server ID here", "Put", "Other", "Server", "IDs", "Here"));
        JSONArray defaultChannelIds = new JSONArray(Arrays.asList("any"));
        JSONArray defaultRoleIds = new JSONArray(Arrays.asList("any"));
        JSONArray defaultOwnerIds = new JSONArray(Arrays.asList("owner"));
        JSONArray defaultUserIds = new JSONArray(Arrays.asList("Put", "Other", "User", "IDs", "Here"));

        DEFAULT_DIRECT_PATHS.put("downloads", "downloads");
        DEFAULT_DIRECT_PATHS.put("output", "output");

        DEFAULT_CONFIG.put("botToken", DEFAULT_BOT_TOKEN);
        DEFAULT_CONFIG.put("Prefix", DEFAULT_PREFIX);
        DEFAULT_CONFIG.put("DebugMode", false);
        DEFAULT_CONFIG.put("ServerIDs", defaultServerIds);
        DEFAULT_CONFIG.put("ChannelIDs", defaultChannelIds);
        DEFAULT_CONFIG.put("OwnerIDs", defaultOwnerIds);
        DEFAULT_CONFIG.put("UserIDs", defaultUserIds);
        DEFAULT_CONFIG.put("RoleIDs", defaultRoleIds);
        DEFAULT_CONFIG.put("DebugMode", false);
        DEFAULT_CONFIG.put("CommandTimeout", 5000);
        DEFAULT_CONFIG.put("ThreadCount", 8);
        DEFAULT_CONFIG.put("MaxConcurrentCommands", 1);
        DEFAULT_CONFIG.put("DirectPaths", DEFAULT_DIRECT_PATHS);
    }

    public static JSONObject getConfigObj() {
        return configObj;
    }
    public static boolean isDebugMode() {
        return debug;
    }
    public static String getBotToken() {
        return botToken;
    }
    public static boolean hasBotToken() {
        return botToken != null && !botToken.equals(DEFAULT_BOT_TOKEN);
    }
    public static String getPrefix() { return prefix; }
    public static List<String> getOwnerIds() {
        return ownerIds;
    }
    public static List<String> getUserIds() {
        return userIds;
    }
    public static List<String> getServerIds() {
        return serverIds;
    }
    public static List<String> getRoleIds() { return roleIds; }
    public static List<String> getChannelIds() {
        return channelIds;
    }
    public static Map<String, String> getDirectPaths() {
        return directPaths;
    }
    public static long getCommandTimeout() {
        return commandTimeout;
    }
    public static int getThreadCount() {
        return threadCount;
    }
    public static int getMaxConcurrentCommands() {
        return maxConcurrentCommands;
    }

    public static String getDirectFolder(String fileType) {
        if (directPaths.containsKey(fileType)) {
            return directPaths.get(fileType);
        } else {
            return DEFAULT_DIRECT_PATHS.getString(fileType);
        }
    }

    public static File getDirectFile(String fileType, String filename) {
        File file = Paths.get(getDirectFolder(fileType), filename).toFile();
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        return file;
    }

    public static File getConfigFile() {
        return new File(CONFIG_FILE);
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
    private static JSONObject getOrderedJSON(JSONObject object) {
        setupFieldAccessor();

        try {
            if (JSONObjectMapField != null) {
                JSONObjectMapField.set(object, new LinkedHashMap<>());
            }
        } catch (IllegalAccessException ignored) {
            // Not that important.
        }

        return object;
    }

    public static void saveConfig() {
        JSONObject orderedJSON = getOrderedJSON(new JSONObject());

        // Copy everything to the ordered JSON
        for (String key : DEFAULT_CONFIG.keySet()) {
            orderedJSON.put(key, configObj.get(key));
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getConfigFile()));
            orderedJSON.write(writer, 4, 0);
            //new ConfigSetup(object);
            writer.close();
        } catch (Exception e) {
            System.out.println("Couldn't write config");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void loadConfig() {
        File file = getConfigFile();
        file.getParentFile().mkdirs();
        boolean exists = file.exists() && file.length() > 0;
        boolean edited = false;

        try {
            configObj = new JSONObject(CommandUtils.readFile(CONFIG_FILE));
        } catch (Exception e) {
            if (exists) {
                e.printStackTrace();
                System.out.println("Oh no!! Your config file is corrupt! Please fix it!");
                System.exit(0);
            }

            configObj = new JSONObject();
        }

        for (Map.Entry<String, Object> entry : DEFAULT_CONFIG.entrySet()) {
            if (!configObj.has(entry.getKey())) {
                configObj.put(entry.getKey(), entry.getValue());
                edited = true;
            }
        }

        if (edited) {
            saveConfig();
        }

        try {
            botToken = configObj.getString("botToken");
            debug = configObj.getBoolean("DebugMode");
            prefix = configObj.getString("Prefix");
            commandTimeout = configObj.getLong("CommandTimeout");
            threadCount = configObj.getInt("ThreadCount");
            maxConcurrentCommands = configObj.getInt("MaxConcurrentCommands");
            ownerIds.clear();
            serverIds.clear();
            userIds.clear();
            roleIds.clear();
            channelIds.clear();
            directPaths.clear();

            for (Object ownerId : configObj.getJSONArray("OwnerIDs").toList()) {
                ownerIds.add(String.valueOf(ownerId));
            }

            for (Object serverId : configObj.getJSONArray("ServerIDs").toList()) {
                serverIds.add(String.valueOf(serverId));
            }
            for (Object channelId : configObj.getJSONArray("ChannelIDs").toList()) {
                serverIds.add(String.valueOf(channelId));
            }
            for (Object userId : configObj.getJSONArray("UserIDs").toList()) {
                userIds.add(String.valueOf(userId));
            }
            for (Object roleId : configObj.getJSONArray("RoleIDs").toList()) {
                userIds.add(String.valueOf(roleId));
            }

            directPaths.clear();

            for (Map.Entry<String, Object> entry : configObj.getJSONObject("DirectPaths").toMap().entrySet()) {
                directPaths.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't interpret config");
            System.exit(0);
        }

        Main.setupThreadPool(getThreadCount());
    }
}
