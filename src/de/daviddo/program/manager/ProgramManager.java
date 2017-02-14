package de.daviddo.program.manager;


import com.google.common.io.Files;
import de.daviddo.knn.network.NetworkFactory;
import de.daviddo.knn.network.NetworkTrainer;
import de.daviddo.program.gui.MainGUI;
import de.daviddo.utils.Messages;
import de.daviddo.utils.OSType;
import de.daviddo.utils.Utils;
import de.daviddo.utils.io.YAMLFile;
import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;
import de.daviddo.utils.settings.Settings;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static javafx.application.Application.launch;

/**
 *
 * @author  Daviddo3399
 */
public class ProgramManager {

	public static String 		PATHSEPARATOR;
    public static String 		APPLICATION_PATH;

	private static MainGUI			mainGUI;
	private static Logger			logger;
	private static DataManager 		dataManager;
	private static NetworkTrainer	networkTrainer;

	private static File 			folder;
	private static File				logFolder;
	private static File				charactersFolder;
	private static File				sF;
	private static File 			dF;
	private static YAMLFile 		settingsFile;
	private static YAMLFile 		dataFile;

	public static boolean			firstStart;

	public void boot() throws IOException {
		String os = System.getProperty("os.name");

		if (OSType.getOSType().equals(OSType.Windows)) {
			PATHSEPARATOR = "\\";
			System.out.println("OS: win");
		} else if (OSType.getOSType().equals(OSType.MacOS)) {
			PATHSEPARATOR = "/";
			System.out.println("OS: mac");
		} else if (OSType.getOSType().equals(OSType.Linux)) {
			PATHSEPARATOR = "/";
			System.out.println("OS: nux");
		}

		APPLICATION_PATH 	= System.getProperty("user.home") + PATHSEPARATOR + "HWR";

		firstStart 			= new File(APPLICATION_PATH).exists();
		mainGUI 			= new MainGUI();

		sF 					= new File(APPLICATION_PATH + PATHSEPARATOR + "settings.yml");
		dF					= new File(APPLICATION_PATH + PATHSEPARATOR + "data.yml");
		folder 				= new File(APPLICATION_PATH);
		logFolder 			= new File(APPLICATION_PATH + PATHSEPARATOR + "log");
		charactersFolder 	= new File(APPLICATION_PATH + PATHSEPARATOR + "characters");

		if (!firstStart) {
			createProgramFiles();
		}

		settingsFile 		= new YAMLFile(sF);
		dataFile 			= new YAMLFile(dF);

		logger 				= new Logger(APPLICATION_PATH + PATHSEPARATOR + "log");
		logger.setupLogger();
		Logger.log(Messages.MANAGER_LOGGER_LOADED, LoggerLevel.INFO);

		SettingsManager.loadSettings(!firstStart);

		dataManager 		= new DataManager(!firstStart);
		networkTrainer		= new NetworkTrainer();

		launch(MainGUI.class, (java.lang.String[]) null);
	}

	private void createProgramFiles() {
		folder.mkdir();
		logFolder.mkdir();
		charactersFolder.mkdir();

        createFiles();
        copyCharacterFiles();
	}

	private void createFiles() {
		try {
            new File(ProgramManager.APPLICATION_PATH + PATHSEPARATOR + "network.yml").createNewFile();
			sF.createNewFile();
			dF.createNewFile();
		} catch (IOException e) {
		}
	}

	private void copyCharacterFiles() {
		for (String s : Utils.ALPHABET) {
            System.out.println("Try to copy " + s + ".yml..");

			try {
				File source = new File(getClass().getResource("/characters/" + s.toUpperCase() + ".yml").toURI());
				File dest 	= new File(APPLICATION_PATH + PATHSEPARATOR + "characters" + PATHSEPARATOR + s.toUpperCase() + ".yml");
				Files.copy(source, dest);
				System.out.println("Try to copy " + s + ".yml: done!");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
    }

    public static void exit() {
		saveSettingsFile();

		if (Settings.automaticSaveALoad) {
			new NetworkFactory(new File(APPLICATION_PATH + PATHSEPARATOR + "network.yml")).save(ProgramManager.getNetworkTrainer().getNetwork());
		}

		Logger.log(Messages.MANAGER_PROGRAM_CLOSED.replace("%time", Utils.getTime()), LoggerLevel.INFO);
		logger.close();
		System.exit(0);
	}

	public static DataManager getDataManager() {
		return dataManager;
	}

	public static NetworkTrainer getNetworkTrainer() {
		return networkTrainer;
	}

	public static Configuration	getDataFile() {
		return dataFile.get();
	}

	public static void saveDataFile() {
		dataFile.save();
	}

	public static Configuration getSettingsFile() {
		return settingsFile.get();
	}

	public static void saveSettingsFile() {
		settingsFile.save();
	}
}
