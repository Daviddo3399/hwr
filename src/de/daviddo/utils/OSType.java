package de.daviddo.utils;

/**
 *
 * @author	Daviddo3399
 */
public enum OSType {

    MacOS("mac", "darwin"),
    Windows("win"),
    Linux("nux"),
    Other("generic");

    private static OSType detectedOS;

    private final String[] keys;

    private OSType(String... keys) {
        this.keys = keys;
    }

    private boolean match(String osKey) {
        for (int i = 0; i < keys.length; i++) {
            if (osKey.indexOf(keys[i]) != -1)
                return true;
        }
        return false;
    }

    public static OSType getOSType() {
        if (detectedOS == null)
            detectedOS = getOperatingSystemType(System.getProperty("os.name", Other.keys[0]).toLowerCase());
        return detectedOS;
    }

    private static OSType getOperatingSystemType(String osKey) {
        for (OSType osType : values()) {
            if (osType.match(osKey))
                return osType;
        }
        return Other;
    }
}