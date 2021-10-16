package seedu.edrecord.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.edrecord.commons.core.GuiSettings;
import seedu.edrecord.model.assignment.Assignment;
import seedu.edrecord.model.module.Module;
import seedu.edrecord.model.module.ReadOnlyModuleSystem;
import seedu.edrecord.model.person.PartOfModulePredicate;
import seedu.edrecord.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' edrecord file path.
     */
    Path getEdRecordFilePath();

    /**
     * Sets the user prefs' edrecord file path.
     */
    void setEdRecordFilePath(Path edRecordFilePath);

    /**
     * Replaces edrecord data with the data in {@code edRecord}.
     */
    void setEdRecord(ReadOnlyEdRecord edRecord);

    /** Returns EdRecord */
    ReadOnlyEdRecord getEdRecord();

    /**
     * Returns true if a person with the same identity as {@code person} exists in edrecord.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in edrecord.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in edrecord.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in edrecord.
     * The person identity of {@code editedPerson} must not be the same as another existing person in edrecord.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns the user prefs' module system file path.
     */
    Path getModuleSystemFilePath();

    /**
     * Sets the user prefs' module system file path.
     */
    void setModuleSystemFilePath(Path moduleSystemFilePath);

    /**
     * Replaces module system data with the data in {@code moduleSystem}.
     */
    void setModuleSystem(ReadOnlyModuleSystem moduleSystem);

    /**
     * Returns the ModuleSystem
     */
    ReadOnlyModuleSystem getModuleSystem();

    /**
     * Returns true if a module with the same code as {@code mod} exists in the module system.
     */
    boolean hasModule(Module mod);

    /**
     * Deletes the given module.
     * The module must exist in the module system.
     */
    void deleteModule(Module target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the module system.
     */
    void addModule(Module mod);

    /**
     * Returns the saved module equivalent of given {@code mod}.
     * {@code mod} must already exist in the module system.
     */
    Module getModule(Module mod);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the module filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void setModuleFilter(PartOfModulePredicate predicate);

    /**
     * Returns the current selected module.
     */
    Module getSelectedModule();

    /**
     * Returns true if there is a currently selected module.
     */
    boolean hasSelectedModule();

    /**
     * Returns true if the currently selected module contains the given assignment.
     */
    boolean hasAssignmentInCurrentModule(Assignment assignment);

    /**
     * Adds the given assignment to the currently selected module.
     * {@code assignment} must not already exist under the currently selected module.
     */
    void addAssignment(Assignment assignment);

    /**
     * Updates the search filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void setSearchFilter(Predicate<Person> predicate);
}
