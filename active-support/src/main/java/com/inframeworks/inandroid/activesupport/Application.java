package com.inframeworks.inandroid.activesupport;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.inframeworks.inandroid.activesupport.annotations.Beta;
import com.inframeworks.inandroid.activesupport.config.Configuration;

/**
 * inAndroid framework's base class. You must provide this class for your application by specifying
 * its name in your AndroidManifest.xml's <application> tag which will cause this class to be
 * instantiated for you when the process for your application/package is created.
 *
 * @author rodrigoscna
 */
@Beta
public class Application extends android.app.Application {
  /**
   * Default application name used for fallbacks when needed.
   */
  public static String APPLICATION_NAME = "inAndroid";
  /**
   * Constant value for undefined strings.
   */
  public static String UNDEFINED = "undefined";
  private static Application INSTANCE_HOLDER;
  private Configuration mConfiguration;

  /**
   * A singleton instance of the Application subclass.
   *
   * @return The singleton instance of the Application subclass.
   */
  public static Application getInstance() {
    return INSTANCE_HOLDER;
  }

  /**
   * Current Configuration of the application as set by the system and the user.
   *
   * @return The current Application's Configuration instance.
   */
  public static Configuration getApplicationConfiguration() {
    return getInstance().getConfiguration();
  }

  /**
   * Additional meta-data associated with the application. The meta-data can be defined in your
   * AndroidManifest.xml file.
   *
   * @return The application's associated meta-data.
   */
  public static Bundle getApplicationMetaData() {
    return getApplicationConfiguration().getMetaData();
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
  public static SharedPreferences getApplicationPreferences() {
    return getApplicationConfiguration().getSharedPreferences();
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
  public static SharedPreferences.Editor getApplicationPreferencesEditor() {
    return getApplicationConfiguration().getSharedPreferencesEditor();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate() {
    super.onCreate();

    if (INSTANCE_HOLDER == null) {
      INSTANCE_HOLDER = this;
    }

    INSTANCE_HOLDER.initializeConfiguration();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onTerminate() {
    INSTANCE_HOLDER.terminateConfiguration();

    INSTANCE_HOLDER = null;

    super.onTerminate();
  }

  /**
   * Initialize the Application's configuration cache.
   */
  private void initializeConfiguration() {
    mConfiguration = new Configuration(this);
  }

  /**
   * Terminates the Application's configuration in order for it to close properly.
   */
  private void terminateConfiguration() {
    mConfiguration = mConfiguration.terminate();
  }

  /**
   * Current Configuration of the application as set by the system and the user.
   *
   * @return The current Application's Configuration instance.
   */
  private Configuration getConfiguration() {
    return mConfiguration;
  }
}
