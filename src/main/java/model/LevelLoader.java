//package model;
//
//public class LevelLoader {
//}


package model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * مسؤول عن تحميل مستويات اللعبة من ملفات JSON وتحويلها إلى كائنات Level.
 * هذا الكلاس يستخدم ClassLoader لتحميل الموارد بشكل صحيح.
 */
public class LevelLoader {

    // كائن Gson ثابت يمكن إعادة استخدامه
    private static final Gson gson = new Gson();

    /**
     * يقوم بتحميل مستوى معين بناءً على رقمه.
     *
     * @param levelId رقم المستوى المطلوب تحميله.
     * @return كائن Level جاهز يمثل المستوى المطلوب.
     * @throws IOException إذا كان الملف غير موجود أو حدث خطأ أثناء القراءة.
     * @throws IllegalArgumentException إذا كان تنسيق الملف (JSON) غير صحيح.
     */
    public static Level loadLevel(int levelId) throws IOException {
        // 1. بناء اسم الملف
        String fileName = "levels/level" + levelId + ".json";

        // 2. اطلب من "أمين المكتبة" (ClassLoader) العثور على الملف وإعطاؤه لك كـ InputStream
        InputStream inputStream = LevelLoader.class.getClassLoader().getResourceAsStream(fileName);

        // 3. تحقق مما إذا كان "أمين المكتبة" قد وجد الملف
        if (inputStream == null) {
            throw new IOException("Could not find resource file: " + fileName);
        }

        // 4. تحويل الـ InputStream إلى String
        // نستخدم InputStreamReader لتحويل البايتات إلى أحرف بترميز UTF-8
        String jsonContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        try {
            // 5. تحويل الـ String إلى كائن Level
            Level level = gson.fromJson(jsonContent, Level.class);
            return level;
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Error parsing JSON for level " + levelId + ". File might be corrupted.", e);
        }
    }
}