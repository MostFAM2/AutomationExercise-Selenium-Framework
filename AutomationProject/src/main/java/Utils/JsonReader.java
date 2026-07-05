package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class JsonReader
{
    private static final String testDataFolderPath = "src/main/resources/TestData/";
    private static final String resourcesFolderPath = "src/main/resources/";

    private static final Gson gson = new Gson();

    /**
     * Reads a JSON file and returns its contents as a Map (key-value pairs).
     *
     * @param filePath Relative path from resources folder
     *                 Example: "TestData/registerUser.json"
     * @return Map<String, Object> containing JSON data
     */
    public static Map<String, Object> readJsonAsMap(String filePath)
    {
        try (Reader reader = new FileReader(testDataFolderPath + filePath))
        {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            return gson.fromJson(reader, type);

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to read JSON file: " + filePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads a JSON file and maps it to a specific Java class.
     * Use this when you have a predefined data structure.
     *
     * @param filePath Relative path from test resources folder
     * @param clazz    The target class to map JSON data into
     * @return Object of type T populated with JSON values
     */
    public static <T> T readJsonAsObject(String filePath, Class<T> clazz)
    {
        try (Reader reader = new FileReader(testDataFolderPath + filePath))
        {
            return gson.fromJson(reader, clazz);
        } catch (IOException e)
        {
            System.err.println("[ERROR] Failed to read JSON file: " + filePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads a JSON array file and maps it to a List of the specified class.
     * Use this when JSON contains an array of objects.
     *
     * @param filePath Relative path from test resources folder
     * @param clazz    The target class for each array element
     * @return List of objects of type T
     */
    public static <T> List<T> readJsonAsList(String filePath, Class<T> clazz)
    {
        try (Reader reader = new FileReader(testDataFolderPath + filePath))
        {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(reader, listType);
        }
        catch (IOException e)
        {
            System.err.println("[ERROR] Failed to read JSON file: " + filePath);
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Loads a .properties file from the test resources' folder.
     *
     * @param filePath Relative path
     * @return Properties object
     */
    public static Properties loadProperties(String filePath)
    {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(resourcesFolderPath + filePath))
        {
            props.load(fis);
        }
        catch (IOException e)
        {
            System.err.println("[ERROR] Failed to load properties: " + filePath);
            e.printStackTrace();
        }
        return props;
    }
}