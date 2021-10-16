package seedu.edrecord.model.module;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.edrecord.model.assignment.Assignment;
import seedu.edrecord.model.assignment.UniqueAssignmentList;
import seedu.edrecord.model.group.Group;
import seedu.edrecord.model.group.GroupSystem;
import seedu.edrecord.model.group.ReadOnlyGroupSystem;

/**
 * Represents a module in EdRecord.
 * Guarantees: immutable; is always valid
 */
public class Module {

    public static final String MESSAGE_CONSTRAINTS = "Module code cannot have whitespaces.";
    public static final String MESSAGE_INVALID_JSON = "Module code cannot be saved with lower case or white spaces.";
    public static final String MESSAGE_DOES_NOT_EXIST = "Module with that code has yet to be created.";
    public static final String MESSAGE_DUPLICATE = "Module with that code has already been created.";

    /* The module code must not have any whitespace characters. */
    public static final String VALIDATION_REGEX = "[^\\s]+";

    public static final ModuleSystem MODULE_SYSTEM = new ModuleSystem();

    private final String code;
    private final GroupSystem groupSystem;
    private final UniqueAssignmentList assignmentList;

    /**
     * Constructs a {@code Module}.
     *
     * @param code A valid module code.
     * @param groupSystem A valid group system.
     */
    public Module(String code, GroupSystem groupSystem) {
        requireNonNull(code);
        this.code = code;

        requireNonNull(groupSystem);
        this.groupSystem = groupSystem;
        this.assignmentList = new UniqueAssignmentList();
    }

    /**
     * Constructs a {@code Module} with a Group in it's Group System.
     *
     * @param code A valid module code.
     */
    public Module(String moduleCode, String groupCode) {
        requireNonNull(moduleCode, groupCode);
        this.code = moduleCode;
        this.groupSystem = new GroupSystem();
        Group group = new Group(groupCode);
        groupSystem.addGroup(group);
        this.assignmentList = new UniqueAssignmentList();
    }

    /**
     * Constructs a {@code Module} containing an empty list of assignments.
     *
     * @param code A valid module code.
     */
    public Module(String code) {
        requireNonNull(code);
        this.code = code.toUpperCase();
        this.groupSystem = new GroupSystem();
        this.assignmentList = new UniqueAssignmentList();
    }

    public String getCode() {
        return code;
    }

    public GroupSystem getGroupSystem() {
        return groupSystem;
    }

    /**
     * Returns a view of this module's assignment list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Assignment> getAssignmentList() {
        return assignmentList.asUnmodifiableObservableList();
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid parsed module code.
     */
    public static boolean isValidSavedModuleCode(String test) {
        return isValidModuleCode(test) && test.equals(test.toUpperCase().trim());
    }

    /**
     * Returns true if the module given has the same module code.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }
        if (otherModule == null) {
            return false;
        }
        return code.equalsIgnoreCase(otherModule.getCode());
    }

    public void setGroupSystem(ReadOnlyGroupSystem groupSystem) {
        this.groupSystem.resetData(groupSystem);
    }

    /**
     * Returns true if groupSystem has a group with the same group code.
     */
    public boolean hasGroup(Group grp) {
        requireNonNull(grp);
        return groupSystem.hasGroup(grp);
    }

    public void deleteGroup(Group target) {
        groupSystem.removeGroup(target);
    }

    public void addGroup(Group toAdd) {
        groupSystem.addGroup(toAdd);
    }

    public Group getGroup(String groupCode) {
        return groupSystem.getGroup(groupCode);
    }

    /**
     * Returns true if the module contains an assignment that is equivalent to the given assignment.
     */
    public boolean hasAssignment(Assignment a) {
        return assignmentList.contains(a);
    }

    /**
     * Adds an assignment to the assignment list under this module.
     * The assignment to be added must not already exist in the assignment list.
     */
    public void addAssignment(Assignment a) {
        assignmentList.add(a);
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Module)) {
            return false;
        }
        Module otherModule = (Module) other;
        return code.equals(otherModule.code)
                && groupSystem.equals(((Module) other).groupSystem)
                && assignmentList.equals(otherModule.assignmentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, groupSystem);
    }
}
