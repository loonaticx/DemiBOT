package pro.loonatic.demibot;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ConfigSetup {
    private static Scanner title;
    private static Scanner input = new Scanner(System.in);

    public ConfigSetup(JSONObject object) throws IOException {
        setup(object);
    }
    public JSONObject setup(JSONObject object) throws IOException {
        title = new Scanner(new File("src/main/resources/asciiart"));
        while(title.hasNextLine()) {
            System.out.println(title.nextLine());
        }
        System.out.println();
        while(!object.isEmpty()) {

        }

        return object;
    }
}
