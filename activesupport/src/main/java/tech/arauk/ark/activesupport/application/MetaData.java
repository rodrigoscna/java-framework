package tech.arauk.ark.activesupport.application;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import tech.arauk.ark.activesupport.annotations.Beta;

/**
 * Provides the framework's base configuration.
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class MetaData {
    private Context mContext;
    private Bundle mMetaData;

    public MetaData(Context context) {
        mContext = context;

        loadMetaData();
    }

    /**
     * Initialize the Application's metadata cache.
     */
    private void loadMetaData() {
        try {
            mMetaData = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA).metaData;
        } catch (NameNotFoundException nnfe) {
            throw new RuntimeException("Application info not found. Have you configured your AndroidManifest.xml's <application> tag properly?");
        }
    }

    /**
     * Terminates the current configuration instance.
     *
     * @return A null value if the configuration was terminated, or the
     * configuration itself if not.
     */
    public MetaData terminate() {
        mMetaData = null;

        return null;
    }

    /**
     * Additional meta-data associated with the application. The meta-data can
     * be defined in your AndroidManifest.xml file.
     *
     * @return The application's associated meta-data.
     */
    public Bundle getMetaData() {
        return mMetaData;
    }
}
