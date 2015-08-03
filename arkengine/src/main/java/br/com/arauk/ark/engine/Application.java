package br.com.arauk.ark.engine;

import android.os.Bundle;

import java.lang.reflect.Constructor;
import java.util.Set;

import br.com.arauk.ark.activerecord.ActiveRecord;
import br.com.arauk.ark.activerecord.connectionadapters.AbstractAdapter;
import br.com.arauk.ark.activesupport.annotations.Beta;
import br.com.arauk.ark.activesupport.application.MetaData;
import br.com.arauk.ark.activesupport.configurable.Settings;
import br.com.arauk.ark.activesupport.inflector.DefaultInflections;
import br.com.arauk.ark.activesupport.logging.Logger;

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
     * A singleton instance of the Application subclass.
     *
     * @return The singleton instance of the Application subclass.
     */
    public static Application getInstance() {
        return INSTANCE_HOLDER;
    }

    /**
     * Current MetaData of the application as set by the system and the
     * user.
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
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        super.onCreate();

        if (INSTANCE_HOLDER == null) {
            INSTANCE_HOLDER = this;
        }

        INSTANCE_HOLDER.initializeLogger();
        INSTANCE_HOLDER.initializeMetaData();

        INSTANCE_HOLDER.initializeActiveRecordConnection();
        INSTANCE_HOLDER.initializeActiveSupportInflections();
    }

    private void initializeActiveRecordConnection() {
        String databaseAdapter = getMetaData().getMetaData().getString("database_adapter");

        try {
            Class<?> aClass = Class.forName("br.com.arauk.ark.activerecord.connectionadapters." + databaseAdapter);
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

    private void initializeActiveSupportInflections() {
        DefaultInflections.initializeDefaultInflections();
    }

    private Bundle getActiveRecordConnectionSettings() {
        Bundle activeRecordConnectionSettings = new Bundle();
        Bundle metaData = getMetaData().getMetaData();

        Set<String> keySet = metaData.keySet();
        for (String key : keySet) {
            if (key.startsWith("database_")) {
                activeRecordConnectionSettings.putString(key, metaData.getString(key));
            }
        }

        return activeRecordConnectionSettings;
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
     * Initialize the Application's Logger.
     */
    private void initializeLogger() {
        mLogger = new Logger(LOGGER_TAG);
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
     * Initialize the Application's MetaData cache.
     */
    private void initializeMetaData() {
        mMetaData = new MetaData(this);
    }

    /**
     * Terminates the Application's MetaData in order for it to close properly.
     */
    private void terminateMetaData() {
        mMetaData = mMetaData.terminate();
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
     * Initialize the Application's MetaData cache.
     */
    private void initializeSettings() {
        mSettings = new Settings(this, SETTINGS_NAME);
    }

    /**
     * Retrieves the main settings initialized by the application.
     *
     * @return The Application's main Settings.
     */
    public Settings getSettings() {
        return mSettings;
    }
}
