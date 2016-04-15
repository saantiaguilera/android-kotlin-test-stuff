package com.example.santiago.my_java_libs.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Custom shared prefs that interact easier and features json processing
 *
 * @note Just as this works, you can simply do it for passing jsons in intents or any place you like
 * @note TODO Refactor. Class should work in a bg thread
 *
 * Created by santiago on 19/03/16.
 */
public class JSONSharedPreferences {

    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public JSONSharedPreferences(Context context) {
        this.context = context;
    }

    /*-------------------------------------------Access Methods----------------------------------------*/

    public void openDocument(String key) {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
    }

    private void validateSharedPreferences() {
        if(sharedPreferences==null)
            throw new NullPointerException("SharedPreferences==null. Forgot to openDocument() ?");
    }

    private void validateEditor() {
        if (sharedPreferences == null)
            throw new NullPointerException("Editor==null. Forgot to startEditing() ?");
    }

    /*----------------------------------------------Getters--------------------------------------------*/

    /**
     * Common methods the shared preferences has with the primitives, nothing savage
     */

    public int getInteger(String key, int defValue) {
        validateSharedPreferences();

        return sharedPreferences.getInt(key, defValue);
    }

    public String getString(String key, String defValue) {
        validateSharedPreferences();

        return sharedPreferences.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        validateSharedPreferences();

        return sharedPreferences.getBoolean(key, defValue);
    }

    public long getLong(String key, long defValue) {
        validateSharedPreferences();

        return sharedPreferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        validateSharedPreferences();

        return sharedPreferences.getFloat(key, defValue);
    }

    /**
     * Generic one for just getting whatever
     * @param key
     * @return
     */
    public Object get(String key) {
        validateSharedPreferences();

        Map<String, ?> objects = sharedPreferences.getAll();

        for(Map.Entry entry : objects.entrySet()) {
            if(entry.getKey() == key)
                return entry.getValue();
        }

        return null;
    }

    /**
     * getter of an object that can be JSONified
     * @throws JSONException
     */
    public <T> T get(String key, JSONSharedPreferencesHidrater<T> hidrate) throws JSONException {
        validateSharedPreferences();

        String jsonString = sharedPreferences.getString(key, null);

        if(jsonString == null)
            return null;

        return hidrate.hidrateFromSP(jsonString);
    }

    public <T> List<T> get(String key, JSONSharedPreferencesListHidrater<T> hidrate) throws JSONException {
        validateSharedPreferences();

        String jsonString = sharedPreferences.getString(key, null);

        if (jsonString == null)
            return new ArrayList<>();

        return hidrate.hidrateListFromSP(jsonString);
    }

    /*----------------------------------------------Setters-------------------------------------------*/

    /**
     * Works similar to a builder but inside the JSONSP
     * flow:
     * 1. start editing
     * 2. put / remove whatever you need to
     * 3. commit/apply
     * 4. start editing again if needed
     *
     * @throws com.santiago.shared_preferences.JSONSharedPreferences.JSONSharedPreferencesEditorException if trying to edit twice without discarding or commiting.
     */
    public JSONSharedPreferences startEditing() {
        if (editor != null)
            throw new JSONSharedPreferencesEditorException("Editor session active, please discard or commit changes before starting a new one");

        validateSharedPreferences();

        editor = sharedPreferences.edit();

        return this;
    }

    /**
     * Puts in the same way of the Editor class, primitive types.
     */

    public JSONSharedPreferences put(String key, int value) {
        validateEditor();

        editor.putInt(key, value);
        return this;
    }

    public JSONSharedPreferences put(String key, long value) {
        validateEditor();

        editor.putLong(key, value);
        return this;
    }

    public JSONSharedPreferences put(String key, float value) {
        validateEditor();

        editor.putFloat(key, value);
        return this;
    }

    public JSONSharedPreferences put(String key, boolean value) {
        validateEditor();

        editor.putBoolean(key, value);
        return this;
    }

    public JSONSharedPreferences put(String key, String value) {
        validateEditor();

        editor.putString(key, value);
        return this;
    }

    /**
     * Method for serializing an object inside the SP. Object must implement JSONSerializer
     */
    public <T> JSONSharedPreferences put(String key, T t, JSONSharedPreferencesSerializer<T> serializer) {
        validateEditor();

        editor.putString(key, serializer.serializeFromSP(t));
        return this;
    }

    /**
     * Method for serializing a list of object inside the SP. Object must implement JSONSerializer
     */
    public <T> JSONSharedPreferences put(String key, List<T> t, JSONSharedPreferencesListSerializer<T> serializer) {
        validateEditor();

        editor.putString(key, serializer.serializeListFromSP(t));
        return this;
    }

    /**
     * Remove a key
     */
    public JSONSharedPreferences remove(String key) {
        validateEditor();

        editor.remove(key);
        return this;
    }

    /**
     * Commit the changes
     * @Synchronous
     */
    public void commit() {
        validateEditor();

        editor.commit();
        discard();
    }

    /**
     * Commit the changes
     * @Asynchronous
     */
    public void apply() {
        validateEditor();

        editor.apply();
        discard();
    }

    /**
     * Discard changes
     */
    public void discard() {
        editor = null;
    }

    /*------------------------------------Exceptions----------------------------------------------*/

    public class JSONSharedPreferencesEditorException extends RuntimeException {
        public JSONSharedPreferencesEditorException(String message) {
            super(message);
        }
    }

    /*----------------------------------------Interface-------------------------------------------*/

    public interface JSONSharedPreferencesListHidrater<T> {
        List<T> hidrateListFromSP(String string);
    }

    public interface JSONSharedPreferencesListSerializer<T> {
        String serializeListFromSP(List<T> tList);
    }

    public interface JSONSharedPreferencesSerializer<T> {
        String serializeFromSP(T t);
    }

    public interface JSONSharedPreferencesHidrater<T> {
        T hidrateFromSP(String string);
    }

}
