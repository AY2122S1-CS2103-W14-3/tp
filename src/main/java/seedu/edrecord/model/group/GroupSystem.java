package seedu.edrecord.model.group;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the group-system level
 * Duplicates are not allowed (by .isSameGroup comparison)
 */
public class GroupSystem implements ReadOnlyGroupSystem {

    private final UniqueGroupList groups;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        groups = new UniqueGroupList();
    }

    public GroupSystem() {}

    /**
     * Creates a GroupSystem using the Groups in the {@code toBeCopied}
     */
    public GroupSystem(ReadOnlyGroupSystem toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the groups list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> modules) {
        this.groups.setGroups(modules);
    }

    /**
     * Resets the existing data of this {@code GroupSystem} with {@code newData}.
     */
    public void resetData(ReadOnlyGroupSystem newData) {
        requireNonNull(newData);

        setGroups(newData.getGroupList());
    }

    //// group-level operations

    /**
     * Returns true if a group with the same identity as {@code group} exists in the group system.
     */
    public boolean hasGroup(Group grp) {
        requireNonNull(grp);
        return groups.contains(grp);
    }

    /**
     * Returns true if a group with the same code as {@code code} exists in the group system.
     */
    public boolean hasGroup(String code) {
        requireNonNull(code);
        return hasGroup(new Group(code));
    }

    /**
     * Adds a group to the group system.
     * The group must not already exist in the module system.
     */
    public void addGroup(Group grp) {
        groups.add(grp);
    }

    /**
     * Removes {@code key} from this {@code GroupSystem}.
     * {@code key} must exist in the group system.
     */
    public void removeGroup(Group key) {
        groups.remove(key);
    }

    /**
     * Returns group with {@code code} from this {@code GroupSystem}.
     * Group with same code as {@code code} must exist in the group system.
     */
    public Group getGroup(String code) {
        return groups.getGroup(code);
    }

    //// util methods

    @Override
    public String toString() {
        return groups.toString();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupSystem // instanceof handles nulls
                && groups.equals(((GroupSystem) other).groups));
    }

    @Override
    public int hashCode() {
        return groups.hashCode();
    }
}
