package tech.arauk.ark.android;

import android.os.Bundle;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Set;

import tech.arauk.ark.activerecord.ActiveRecord;
import tech.arauk.ark.activerecord.connectionadapters.AbstractAdapter;
import tech.arauk.ark.activesupport.annotations.Beta;
import tech.arauk.ark.activesupport.inflector.DefaultInflections;
import tech.arauk.ark.activesupport.logging.Logger;
import tech.arauk.ark.android.application.MetaData;
import tech.arauk.ark.android.configurable.Settings;
import tech.arauk.ark.android.logging.AndroidLog;

/**
 * inAndroid framework's base class. You must provide this class for your
 * application by specifying its name in your AndroidManifest.xml's
 * <application> tag which will cause this class to be instantiated for you when
 * the process for your application/package is created.
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class Application extends android.app.Application {
    private static final String LOGGER_TAG = "Application";
    private static final String SETTINGS_NAME = "d244e961183808f36991c4907ff5a81a";
    private static Application INSTANCE_HOLDER;
    private Logger mLogger;
    private MetaData mMetaData;
    private Settings mSettings;

    /**
     * Current MetaData of the application as set by the system and the user.
     *
     * @return The current Application's MetaData instance.
     */
    public static MetaData getApplicationConfiguration() {
        return getInstance().getMetaData();
    }

    /**
     * Additional meta-data associated with the application. The meta-data can
     * be defined in your AndroidManifest.xml file.
     *
     * @return The application's associated meta-data.
     */
    public static Bundle getApplicationMetaData() {
        return getApplicationConfiguration().getMetaData();
    }

    /**
     * Retrieves the main settings initialized by the application.
     *
     * @return The Application's main Settings.
     */
    public static Settings getApplicationSettings() {
        return getInstance().getSettings();
    }

    /**
     * A singleton instance of the Application subclass.
     *
     * @return The singleton instance of the Application subclass.
     */
    public static Application getInstance() {
        return INSTANCE_HOLDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        super.onCreate();

        this.initializeInstanceHolder();

        this.initializeLogger();
        this.initializeMetaData();
        this.initializeSettings();

        this.initializeActiveRecordConnection();
        this.initializeActiveSupportInflections();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTerminate() {
        INSTANCE_HOLDER.terminateMetaData();

        INSTANCE_HOLDER = null;

        super.onTerminate();
    }

    /**
     * Retrieves the main logger initialized by the application.
     *
     * @return The Application's main Logger.
     */
    public Logger getLogger() {
        return mLogger;
    }

    /**
     * Retrieves the current MetaData of the application as set by the system
     * and the user.
     *
     * @return The current Application's MetaData instance.
     */
    public MetaData getMetaData() {
        return mMetaData;
    }

    /**
     * Retrieves the main settings initialized by the application.
     *
     * @return The Application's main Settings.
     */
    public Settings getSettings() {
        return mSettings;
    }

    private HashMap<String, String> getActiveRecordConnectionSettings() {
        HashMap<String, String> activeRecordConnectionSettings = new HashMap<String, String>();
        Bundle metaData = getMetaData().getMetaData();

        Set<String> keySet = metaData.keySet();
        for (String key : keySet) {
            if (key.startsWith("database_")) {
                activeRecordConnectionSettings.put(key, metaData.getString(key));
            }
        }

        return activeRecordConnectionSettings;
    }

    private void initializeActiveRecordConnection() {
        if (getMetaData().getMetaData().containsKey("database_adapter")) {
            String databaseAdapter = getMetaData().getMetaData().getString("database_adapter");

            try {
                Class<?> aClass = Class.forName(databaseAdapter);
                Constructor<?> aClassConstructor = aClass.getConstructor();
                AbstractAdapter abstractAdapter = (AbstractAdapter) aClassConstructor.newInstance();
                abstractAdapter.setConnectionSettings(getActiveRecordConnectionSettings());

                ActiveRecord.establishConnection(abstractAdapter);
            } catch (ClassNotFoundException cnfe) {
                throw new UnsupportedOperationException("Database adapter not found.", cnfe);
            } catch (Exception e) {
                throw new UnsupportedOperationException("Invalid database adapter.");
            }
        }
    }

    private void initializeActiveSupportInflections() {
        DefaultInflections.initializeDefaultInflections();
    }

    private void initializeInstanceHolder() {
        if (INSTANCE_HOLDER == null) {
            INSTANCE_HOLDER = this;
        }
    }

    private void initializeLogger() {
        mLogger = new Logger(new AndroidLog(), LOGGER_TAG);
    }

    private void initializeMetaData() {
        mMetaData = new MetaData(this);
    }

    private void initializeSettings() {
        mSettings = new Settings(this, SETTINGS_NAME);
    }

    private void terminateMetaData() {
        mMetaData = mMetaData.terminate();
    }
}
