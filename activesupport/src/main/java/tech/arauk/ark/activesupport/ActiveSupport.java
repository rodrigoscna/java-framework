package tech.arauk.ark.activesupport;

import tech.arauk.ark.activesupport.annotations.Beta;
import tech.arauk.ark.activesupport.core_ext.StringUtils;

/**
 * Abstract representation of the ActiveSupport module.
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public abstract class ActiveSupport {
    public static boolean isPreRelease() {
        return !StringUtils.isEmpty(Version.PRE);
    }

    public static String version() {
        return Version.string();
    }

    public static class Version {
        public static int MAJOR = 0;
        public static int MINOR = 0;
        public static int TINY = 1;
        public static String PRE = null;

        public static String string() {
            StringBuilder versionBuilder = new StringBuilder();
            versionBuilder.append(MAJOR);
            versionBuilder.append(".");
            versionBuilder.append(MINOR);
            versionBuilder.append(".");
            versionBuilder.append(TINY);

            if (!StringUtils.isEmpty(PRE)) {
                versionBuilder.append("-");
                versionBuilder.append(PRE);
            }

            return versionBuilder.toString();
        }
    }
}
