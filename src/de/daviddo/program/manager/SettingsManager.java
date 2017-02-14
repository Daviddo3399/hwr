package de.daviddo.program.manager;

import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;
import de.daviddo.utils.settings.Settings;

import java.awt.*;

import static de.daviddo.program.manager.ProgramManager.getSettingsFile;
import static de.daviddo.utils.Messages.*;
import static de.daviddo.utils.settings.Settings.neuron_count;

/**
 *
 * @author  Daviddo3399
 */
public class SettingsManager {

	public static void loadSettings(boolean firstTime) {

		if (firstTime) {
			Logger.log(MANAGER_WRITING_SETTINGS, LoggerLevel.INFO);
			getSettingsFile().set("knn.neuron.count", 26);
			getSettingsFile().set("program.settings.automaticSaveALoad", false);
			getSettingsFile().set("gui.drawingPanel.resolution", 20);
			getSettingsFile().set("gui.drawingPanel.color.gridLine", String.valueOf(Color.GRAY.getRGB()));
			getSettingsFile().set("gui.drawingPanel.color.pixel", String.valueOf(Color.BLACK.getRGB()));
			ProgramManager.saveSettingsFile();
			Logger.log(MANAGER_WRITING_SETTINGS_FINISHED, LoggerLevel.FINISHED);
		}

		Logger.log(MANAGER_LOADING_SETTINGS, LoggerLevel.INFO);
		neuron_count			 	= getSettingsFile().getInteger("knn.neuron.count");
		Settings.automaticSaveALoad			= ProgramManager.getSettingsFile().getBoolean("program.settings.automaticSaveALoad");
		Settings.gui_dpanel_res 			= ProgramManager.getSettingsFile().getInteger("gui.drawingPanel.resolution");
		Settings.gui_color_gridline 		= new Color(ProgramManager.getSettingsFile().getInteger("gui.drawingPanel.color.gridLine"));
		Settings.gui_color_pixel 			= new Color(ProgramManager.getSettingsFile().getInteger("gui.drawingPanel.color.pixel"));

		boolean complete;

		if (neuron_count == null) {
			Logger.log(MANAGER_LOADING_SETTINGS_FAILED.replace("%setting", "neuron_count"), LoggerLevel.WARNING);
			complete = false;
		} else {
			complete = true;
		}

		if (Settings.automaticSaveALoad == null) {
			Logger.log(MANAGER_LOADING_SETTINGS_FAILED.replace("%setting", "automaticSaveALoad"), LoggerLevel.WARNING);
			complete = false;
		} else {
			complete = true;
		}

		if (Settings.gui_dpanel_res == null) {
			Logger.log(MANAGER_LOADING_SETTINGS_FAILED.replace("%setting", "gui_dpanel_res"), LoggerLevel.WARNING);
			complete = false;
		} else {
			complete = true;
		}

		if (Settings.gui_color_gridline == null) {
			Logger.log(MANAGER_LOADING_SETTINGS_FAILED.replace("%setting", "gui_color_gridline"), LoggerLevel.WARNING);
			complete = false;
		} else {
			complete = true;
		}

		if (Settings.gui_color_pixel == null) {
			Logger.log(MANAGER_LOADING_SETTINGS_FAILED.replace("%setting", "gui_color_pixel"), LoggerLevel.WARNING);
			complete = false;
		} else {
			complete = true;
		}

		if (complete) {
			Logger.log("settings.yml: knn.neuron.count = " + neuron_count);
			Logger.log("settings.yml: program.settings.automaticSaveALoad = " + Settings.automaticSaveALoad);
			Logger.log("settings.yml: gui.drawingPanel.resolution = " + Settings.gui_dpanel_res);
			Logger.log("settings.yml: gui.drawingPanel.color.gridLine = " + Settings.gui_color_gridline);
			Logger.log("settings.yml: gui.drawingPanel.color.pixel = " + Settings.gui_color_pixel);
			Logger.log(MANAGER_LOADING_SETTINGS_FINSIHED, LoggerLevel.LOADED);
		} else {
			Logger.log(MANAGER_LOADING_SETTINGS_FAILED2, LoggerLevel.ERROR);
			System.exit(404);
		}
	}

	public static void updateSettings(String token, Object object) {
		getSettingsFile().set(token, object);
		ProgramManager.saveSettingsFile();
	}
}
