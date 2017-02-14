package de.daviddo.utils.io;

import de.daviddo.utils.DialogUtils;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author  Daviddo3399
 */
public class YAMLFile {

    private File            file;
    private Configuration   configuration;

    public YAMLFile(File file) {
        this.file       = file;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            configuration   = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            DialogUtils.showExceptionDialog("", e);
        }
    }

    public Configuration get() {
        return configuration;
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            DialogUtils.showExceptionDialog("Failed to save YAML-File(" + file.getName() + ")..", e);
        }
    }

    public void createNewFile() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            DialogUtils.showExceptionDialog("Creating the new file failed!", e);
        }
    }

    public Boolean exists() {
        return file.exists();
    }
}
