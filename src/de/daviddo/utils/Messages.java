package de.daviddo.utils;

/**
 *
 * @author	Daviddo3399
 */
public class Messages {

    public static final String PANE_CLEARED = "Pane cleared";
    public static final String PANE_PIXEL_PAINTED = "Pixel painted (%x | %y)";
    public static final String PANE_PIXEL_REMOVED = "Pixel removed (%x | %y)";

    public static final String MANAGER_CREATING_FILES = "Creating program files..";
    public static final String MANAGER_LOGGER_LOADED = "Logger loaded!";
    public static final String MANAGER_PROGRAM_LOADING = "====================================Loading program====================================";
    public static final String MANAGER_PROGRAM_LOADING_FINISHED = "Loading finished!";
    public static final String MANAGER_PROGRAM_LOADING_FINISHED2 = "=======================================================================================";
    public static final String MANAGER_PROGRAM_CLOSED = "Program closed at %time..";

    public static final String MANAGER_WRITING_SETTINGS = "Writing settings to the settings.yml..";
    public static final String MANAGER_WRITING_SETTINGS_FINISHED = "Writing settings to the settings.yml: finished!";
    public static final String MANAGER_LOADING_SETTINGS = "Loading settings..";
    public static final String MANAGER_LOADING_SETTINGS_FINSIHED = "Loading settings: finished!";
    public static final String MANAGER_LOADING_SETTINGS_FAILED = "Failed to load an setting instance(%setting) from the settings.yml";
    public static final String MANAGER_LOADING_SETTINGS_FAILED2 = "Failed to load the complete settings from the settings.yml!";

    public static final String MANAGER_WRITING_DATA = "Writing data to the data.yml..";
    public static final String MANAGER_LOADING_DATA = "Loading data..";
    public static final String MANAGER_WRITING_DATA_FINISHED = "Writing data to the data.yml: finished!";
    public static final String MANAGER_LOADING_DATA_FINISHED = "Loading data: finished!";

    public static final String GUI_DATAGUI_TITEL_CHARACTER_TAB = "Fehlerrate (%character)";
    public static final String GUI_DATAGUI_CHARACTER_TAB_NO_DATAFOUND = "Es wurden keine Daten für %character gefunden..";
    public static final String GUI_DATAGUI_NO_DATAFOUND = "Es wurden keine Daten für gefunden..";
    public static final String GUI_DATAGUI_TOOLTIP = "%pievalue_int %name (%pievalue%)";

    public static final String GUI_PANE_EMPTY_SHORT = "Das Zeichenfeld ist leer!";
    public static final String GUI_PANE_EMPTY_LONG = "Achtung! Das Zeichenfeld ist leer. Das neuronale Netz kann keine Berechnungen dürchführen..";
    public static final String GUI_NOT_NUMERIC_SHORT = "Es wurde keine Nummer angegeben!";
    public static final String GUI_NOT_NUMERIC_LONG = "Achtung! Es wurde kein Zahl angegeben oder es handelt sich um keine..";
    public static final String GUI_NO_CHARACTER_SELECTED_SHORT = "Kein Buchstabe ausgewählt!";
    public static final String GUI_NO_CHARACTER_SELECTED_LONG = "Achtung! Es wurde kein Buchstabe ausgewählt, welcher erlernt werden soll..";

    public static final String KNN_IMPROVE_SOLUTION = "Attempting to improve the solution..";
    public static final String KNN_IMPROVE_SOLUTION_FINISHED = "Attempting to improve the solution: finished!";
    public static final String KNN_SOLUTION_SIMILAR = "The 'correct' solution is similar to the recognized solution..";

    public static final String KNN_NEURON_MODIFYWEIHGTS = "Modifying the weights of the neuron(%id)..";
    public static final String KNN_NEURON_INITWEIGHTS = "Initialised weight for the connection %connection: %weight";
    public static final String KNN_NEURON_COMPUTING_NETINPUT = "Computing netinput for the neuron(%id)";
    public static final String KNN_NEURON_COMPUTING_NETINPUT_RESULT = "     Computed netinput: %netinput";

    public static final String KNN_SOLUTION_MANAGER_UPDATE_OUTPUT = "Attempting to update the outputs for the SolutionManager..";
    public static final String KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_FINISHED = "Attempting to update the outputs for the SolutionManager: finished!";
    public static final String KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_NEW = "    New outputs: ";
    public static final String KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_OLD = "    Old outputs: ";
    public static final String KNN_SOLUTION_MANAGER_UPDATE_SOLUTION = "    Solution(%id): updated!";
    public static final String KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_WITH_NEW_OUTPUTS = "Attempting to update the solutions with the new outputs..";
    public static final String KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_WITH_NEW_OUTPUTS_FINISHED = "Attempting to update the solutions with the new outputs: finished!";
    public static final String KNN_SOLUTION_MANAGER_TOP3_SOLUTION = "Solution #%id: ";
    public static final String KNN_SOLUTION_MANAGER_TOP3_SOLUTION_CHARACTER = "    Character:  %key (%index)";
    public static final String KNN_SOLUTION_MANAGER_TOP3_SOLUTION_PERCENTAGE = "    Percentage: %percentage%";

    public static final String KNN_NETWORK_RECOGNIZING_PATTERN = "Attempting to recognize the pattern..";
    public static final String KNN_NETWORK_RECOGNIZING_PATTERN_FINISHED = "Attempting to recognize the pattern: finished!";
    public static final String KNN_NETWORK_RECOGNIZING_PATTERN_ANALYSING = "Analysing the outputs..";
    public static final String KNN_NETWORK_RECOGNIZING_PATTERN_AB = "	%a > %b";

    public static final String KNN_NETWORK_RECOGNIZING_PATTERN_INDEX = "	Index: %index";
    public static final String KNN_NETWORK_RECOGNIZING_PATTERN_CHARACTER = "	Character: %character";
    public static final String KNN_NETWORK_RECOGNIZING_PATTERN_NEURON = "	Neuron: %neuron";
    public static final String KNN_NETWORK_RECOGNIZING_PATTERN_OUTPUT = "	Output: %output";

    public static final String KNN_NETWORK_INPUTS_LOAD_UPDATE = "Attempting to load inputs for the neuron(%id)..";
    public static final String KNN_NETWORK_INPUTS_LOAD_UPDATE_FINISHED = "Attempting to load inputs for the neuron(%id): finished!";
    public static final String KNN_NETWORK_INPUTS_LOAD_UPDATE_FINISHED_ARRAYLIST_SIZE = "        ArrayList size: %size";
    public static final String KNN_NETWORK_INPUTS_LOAD_UPDATE_FINISHED_ARRAYLIST_CONTENT = "        ArrayList content: %content";
    public static final String KNN_NETWORK_INPUTS_LOAD_UPDATE_FAILED = "Attempting to load inputs for the neuron(%id): failed!";
    public static final String KNN_NETWORK_INPUTS_SAVE = "Attempting to save inputs for the neuron(%id)..";
    public static final String KNN_NETWORK_INPUTS_SAVE_ARRAYLIST_SIZE = "        ArrayList size: %size";
    public static final String KNN_NETWORK_INPUTS_SAVE_ARRAYLIST_CONTENT = "        ArrayList content: %content";
    public static final String KNN_NETWORK_INPUTS_SAVE_FINISHED = "Attempting to save inputs for the neuron(%id): finished!";

    public static final String KNN_NETWORK_WEIGHTS_LOAD_UPDATE = "Attempting to load weights for the neuron(%id)..";
    public static final String KNN_NETWORK_WEIGHTS_LOAD_UPDATE_FINISHED = "Attempting to load weights for the neuron(%id): finished!";
    public static final String KNN_NETWORK_WEIGHTS_LOAD_UPDATE_FINISHED_ARRAYLIST_SIZE = "        ArrayList size: %size";
    public static final String KNN_NETWORK_WEIGHTS_LOAD_UPDATE_FINISHED_ARRAYLIST_CONTENT = "        ArrayList content: %content";
    public static final String KNN_NETWORK_WEIGHTS_LOAD_UPDATE_FAILED = "Attempting to load weights for the neuron(%id): failed!";
    public static final String KNN_NETWORK_WEIGHTS_SAVE = "Attempting to save weights for the neuron(%id)..";
    public static final String KNN_NETWORK_WEIGHTS_SAVE_ARRAYLIST_SIZE = "        ArrayList size: %size";
    public static final String KNN_NETWORK_WEIGHTS_SAVE_ARRAYLIST_CONTENT = "        ArrayList content: %content";
    public static final String KNN_NETWORK_WEIGHTS_SAVE_FINISHED = "Attempting to save weights for the neuron(%id): finished!";

    public static final String KNN_NETWORK_TRAINING_LOADER_CHARACTER = "Attempting to load training inputs from %character.yml..";
    public static final String KNN_NETWORK_TRAINING_LOADER_CHARACTER_FINISHED = "Attempting to load training inputs from %character.yml: finished!";
    public static final String KNN_NETWORK_TRAINING_LOADER_CHARACTER_FAILED = "Attempting to load training inputs from %character.yml: failed!";
}
