package tech.arauk.ark.activesupport.configurable;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import tech.arauk.ark.activesupport.annotations.Beta;

/**
 * Provides a wrapper for the Android SharedPreferences.
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class Settings {
    private Context mContext;
    private Integer mSettingsMode;
    private String mSettingsName;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    public Settings(Context context, String settingsName) {
        this(context, settingsName, Context.MODE_PRIVATE);
    }

    public Settings(Context context, String settingsName, Integer settingsMode) {
        mContext = context;
        mSettingsName = settingsName;
        mSettingsMode = settingsMode;
    }

    /**
     * Retrieve and hold the contents of the preferences file named with your
     * application's metadata, returning a SharedPreferences through which you
     * can retrieve and modify its values.
     * If you don't have any name set in your Manifest file, the framework will
     * use the default internal name.
     * <p>
     * Only one instance of the SharedPreferences object is returned to any
     * callers for the same name, meaning they will see each other's edits as
     * soon as they are made.
     *
     * @return The single SharedPreferences instance that can be used to
     * retrieve and modify the preference values.
     */
    public SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(mSettingsName, mSettingsMode);
        }

        return mSharedPreferences;
    }

    /**
     * Create a new Editor for the SharedPreferences, through which you can make
     * modifications to the data in the preferences and atomically commit those
     * changes back to the SharedPreferences object.
     * <p>
     * Note that you must call commit() to have any changes you perform in the
     * Editor actually show up in the SharedPreferences.
     *
     * @return A new instance of the SharedPreferences.Editor interface,
     * allowing you to modify the values in this SharedPreferences object.
     */
    public SharedPreferences.Editor getSharedPreferencesEditor() {
        if (mSharedPreferencesEditor == null) {
            mSharedPreferencesEditor = getSharedPreferences().edit();
        }

        return mSharedPreferencesEditor;
    }

    /**
     * Retrieve a Boolean value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defaultValue. Throws
     * ClassCastException if there is a preference with this name that is not a
     * boolean.
     * @throws ClassCastException
     */
    public Boolean getBooleanSharedPreference(String key, Boolean defaultValue) throws ClassCastException {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    /**
     * Retrieve a Float value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defaultValue. Throws
     * ClassCastException if there is a preference with this name that is not a
     * float.
     * @throws ClassCastException
     */
    public Float getFloatSharedPreference(String key, Float defaultValue) throws ClassCastException {
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    /**
     * Retrieve an Integer value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defaultValue. Throws
     * ClassCastException if there is a preference with this name that is not an
     * integer.
     * @throws ClassCastException
     */
    public Integer getIntegerSharedPreference(String key, Integer defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    /**
     * Retrieve a Long value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defaultValue. Throws
     * ClassCastException if there is a preference with this name that is not a
     * long.
     * @throws ClassCastException
     */
    public Long getLongSharedPreference(String key, Long defaultValue) {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defaultValue. Throws
     * ClassCastException if there is a preference with this name that is not a
     * String.
     * @throws ClassCastException
     */
    public String getStringSharedPreference(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    /**
     * Retrieve a set of String values from the preferences.
     * <p>
     * <p>Note that you <em>must not</em> modify the set instance returned
     * by this call.  The consistency of the stored data is not guaranteed
     * if you do, nor is your ability to modify the instance at all.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defaultValue. Throws
     * ClassCastException if there is a preference with this name that is not a
     * String Set.
     * @throws ClassCastException
     */
    public Set<String> getStringSetSharedPreference(String key, Set<String> defaultValue) {
        return getSharedPreferences().getStringSet(key, defaultValue);
    }

    /**
     * Set a boolean value in the preferences editor.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     * @return The current instance of the SharedPreferences.Editor interface.
     */
    public SharedPreferences.Editor setBooleanSharedPreference(String key, Boolean newValue) {
        return getSharedPreferencesEditor().putBoolean(key, newValue);
    }

    /**
     * Set a float value in the preferences editor.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     * @return The current instance of the SharedPreferences.Editor interface.
     */
    public SharedPreferences.Editor setFloatSharedPreference(String key, Float newValue) {
        return getSharedPreferencesEditor().putFloat(key, newValue);
    }

    /**
     * Set an integer value in the preferences editor.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     * @return The current instance of the SharedPreferences.Editor interface.
     */
    public SharedPreferences.Editor setIntegerSharedPreference(String key, Integer newValue) {
        return getSharedPreferencesEditor().putInt(key, newValue);
    }

    /**
     * Set a long value in the preferences editor.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     * @return The current instance of the SharedPreferences.Editor interface.
     */
    public SharedPreferences.Editor setLongSharedPreference(String key, Long newValue) {
        return getSharedPreferencesEditor().putLong(key, newValue);
    }

    /**
     * Set a String value in the preferences editor.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     * @return The current instance of the SharedPreferences.Editor interface.
     */
    public SharedPreferences.Editor setStringSharedPreference(String key, String newValue) {
        return getSharedPreferencesEditor().putString(key, newValue);
    }

    /**
     * Set a set of String values in the preferences editor.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     * @return The current instance of the SharedPreferences.Editor interface.
     */
    public SharedPreferences.Editor setStringSetSharedPreference(String key, Set<String> newValue) {
        return getSharedPreferencesEditor().putStringSet(key, newValue);
    }

    /**
     * Set a boolean value in the preferences and persists it.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     */
    public void updateBooleanSharedPreference(String key, Boolean newValue) {
        setBooleanSharedPreference(key, newValue).apply();
    }

    /**
     * Set a float value in the preferences and persists it.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     */
    public void updateFloatSharedPreference(String key, Float newValue) {
        setFloatSharedPreference(key, newValue).apply();
    }

    /**
     * Set an integer value in the preferences and persists it.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     */
    public void updateIntegerSharedPreference(String key, Integer newValue) {
        setIntegerSharedPreference(key, newValue).apply();
    }

    /**
     * Set a long value in the preferences and persists it.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     */
    public void updateLongSharedPreference(String key, Long newValue) {
        setLongSharedPreference(key, newValue).apply();
    }

    /**
     * Set a String value in the preferences and persists it.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     */
    public void updateStringSharedPreference(String key, String newValue) {
        setStringSharedPreference(key, newValue).apply();
    }

    /**
     * Set a set of String values in the preferences and persists it.
     *
     * @param key      The name of the preference to modify.
     * @param newValue The new value for the preference.
     */
    public void updateStringSetSharedPreference(String key, Set<String> newValue) {
        setStringSetSharedPreference(key, newValue).apply();
    }

    /**
     * Removes a value in the preferences and persists it.
     *
     * @param key The name of the preference to remove.
     */
    public void unsetSharedPreference(String key) {
        getSharedPreferencesEditor().remove(key).apply();
    }
}
