package model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LevelLoader {

    private static final Gson gson = new Gson();


    public static Level loadLevel(int levelId) throws IOException {

        String fileName = "levels/level" + levelId + ".json";
        InputStream inputStream = LevelLoader.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IOException("Could not find resource file: " + fileName);
        }

        String jsonContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        try {
            Level level = gson.fromJson(jsonContent, Level.class);
            return level;
        } catch (JsonSyntaxException e) {
            throw e;
        }
    }
}