package com.inframeworks.inandroid.activesupport.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.inframeworks.inandroid.activesupport.Application;
import com.inframeworks.inandroid.activesupport.annotations.Beta;

/**
 * Provides the framework's base configuration.
 *
 * @author rodrigoscna
 */
@Beta
public class Configuration {
  private Context mContext;
  private Bundle mMetaData;

  public Configuration(Context context) {
    mContext = context;

    initializeMetaData();
  }

  /**
   * Initialize the Application's metadata cache.
   */
  private void initializeMetaData() {
    try {
      mMetaData = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA).metaData;
    } catch (NameNotFoundException nnfe) {
      throw new RuntimeException("Application info not found. Have you configured your AndroidManifest.xml's <application> tag properly?");
    }
  }

  /**
   * Terminates the current configuration instance.
   *
   * @return A null value if the configuration was terminated, or the configuration itself if not.
   */
  public Configuration terminate() {
    mMetaData = null;

    return null;
  }

  /**
   * Additional meta-data associated with the application. The meta-data can be defined in your
   * AndroidManifest.xml file.
   *
   * @return The application's associated meta-data.
   */
  public Bundle getMetaData() {
    return mMetaData;
  }

  /**
   * Retrieve and hold the contents of the preferences file named with your application's
   * metadata, returning a SharedPreferences through which you can retrieve and modify its values.
   * If you don't have any name set in your Manifest file, the framework will use the default
   * internal name.
   * <p/>
   * Only one instance of the SharedPreferences object is returned to any callers for the same
   * name, meaning they will see each other's edits as soon as they are made.
   *
   * @return The single SharedPreferences instance that can be used to retrieve and
   * modify the preference values.
   */
  public SharedPreferences getSharedPreferences() {
    String sharedPreferencesName = getMetaData().getString("sharedPreferencesName");
    if (sharedPreferencesName == null) {
      sharedPreferencesName = Application.APPLICATION_NAME;
    }
    return mContext.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
  }

  /**
   * Create a new Editor for the SharedPreferences, through which you can make modifications to
   * the data in the preferences and atomically commit those changes back to the SharedPreferences
   * object.
   * <p/>
   * Note that you must call commit() to have any changes you perform in the Editor actually show
   * up in the SharedPreferences.
   *
   * @return A new instance of the SharedPreferences.Editor interface, allowing you to modify the
   * values in this SharedPreferences object.
   */
  public SharedPreferences.Editor getSharedPreferencesEditor() {
    return getSharedPreferences().edit();
  }

  // <editor-fold desc="SharedPreferences Getters/Setters">

  /**
   * Retrieve a Boolean value from the preferences.
   *
   * @param key          The name of the preference to retrieve.
   * @param defaultValue Value to return if this preference does not exist.
   * @return The preference value if it exists, or defaultValue. Throws ClassCastException if
   * there is a preference with this name that is not a boolean.
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
   * @return The preference value if it exists, or defaultValue. Throws ClassCastException if
   * there is a preference with this name that is not a float.
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
   * @return The preference value if it exists, or defaultValue. Throws ClassCastException if
   * there is a preference with this name that is not an integer.
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
   * @return The preference value if it exists, or defaultValue. Throws ClassCastException if
   * there is a preference with this name that is not a long.
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
   * @return The preference value if it exists, or defaultValue. Throws ClassCastException if
   * there is a preference with this name that is not a String.
   * @throws ClassCastException
   */
  public String getStringSharedPreference(String key, String defaultValue) {
    return getSharedPreferences().getString(key, defaultValue);
  }

  /**
   * Set a boolean value in the preferences editor and call the commit method.
   *
   * @param key      The name of the preference to modify.
   * @param newValue The new value for the preference.
   * @return Returns true if the new value was successfully written to persistent storage.
   */
  public Boolean setBooleanSharedPreference(String key, Boolean newValue) {
    return getSharedPreferencesEditor().putBoolean(key, newValue).commit();
  }

  /**
   * Set a float value in the preferences editor and call the commit method.
   *
   * @param key      The name of the preference to modify.
   * @param newValue The new value for the preference.
   * @return Returns true if the new value was successfully written to persistent storage.
   */
  public Boolean setFloatSharedPreference(String key, Float newValue) {
    return getSharedPreferencesEditor().putFloat(key, newValue).commit();
  }

  /**
   * Set an integer value in the preferences editor and call the commit method.
   *
   * @param key      The name of the preference to modify.
   * @param newValue The new value for the preference.
   * @return Returns true if the new value was successfully written to persistent storage.
   */
  public Boolean setIntegerSharedPreference(String key, Integer newValue) {
    return getSharedPreferencesEditor().putInt(key, newValue).commit();
  }

  /**
   * Set a long value in the preferences editor and call the commit method.
   *
   * @param key      The name of the preference to modify.
   * @param newValue The new value for the preference.
   * @return Returns true if the new value was successfully written to persistent storage.
   */
  public Boolean setLongSharedPreference(String key, Long newValue) {
    return getSharedPreferencesEditor().putLong(key, newValue).commit();
  }

  /**
   * Set a String value in the preferences editor and call the commit method.
   *
   * @param key      The name of the preference to modify.
   * @param newValue The new value for the preference.
   * @return Returns true if the new value was successfully written to persistent storage.
   */
  public Boolean setStringSharedPreference(String key, String newValue) {
    return getSharedPreferencesEditor().putString(key, newValue).commit();
  }

  /**
   * Mark in the editor that a preference value should be removed and call the commit method.
   *
   * @param key The name of the preference to remove.
   * @return Returns true if the preference was successfully removed from persistent storage.
   */
  public Boolean unsetSharedPreference(String key) {
    return getSharedPreferencesEditor().remove(key).commit();
  }
  // </editor-fold>
}
