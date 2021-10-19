package seedu.edrecord.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.edrecord.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edrecord.model.group.exceptions.DuplicateGroupException;
import seedu.edrecord.model.group.exceptions.GroupNotFoundException;

/**
 * A list of class groups that enforces uniqueness between its elements and does not allow nulls.
 * A group is considered unique by comparing using {@code Group#isSameGroup(Group)}. As such, adding and updating of
 * groups uses Group#isSameGroup(Group) for equality so as to ensure that the group being added or updated is
 * unique in terms of identity in the UniqueGroupList. However, the removal of a group uses Group#equals(Group) so
 * as to ensure that the group with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Group#isSameGroup(Group)
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();
    private final ObservableList<Group> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent group as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGroup);
    }

    /**
     * Adds a group to the list.
     * The group must not already exist in the list.
     */
    public void add(Group toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent group from the list.
     * The group must exist in the list.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Gets the group with {@code code} from the list.
     * The group must exist in the list.
     */
    public Group getGroup(String code) {
        requireNonNull(code);
        for (Group grp : internalList) {
            if (grp.getCode().equals(code)) {
                return grp;
            }
        }
        throw new GroupNotFoundException();
    }

    /**
     * Replaces the contents of this list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> groups) {
        requireAllNonNull(groups);
        if (!areGroupsUnique(groups)) {
            throw new DuplicateGroupException();
        }

        internalList.setAll(groups);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && internalList.equals(((UniqueGroupList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code groups} contains only unique groups.
     */
    private boolean areGroupsUnique(List<Group> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).isSameGroup(groups.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        List<Group> groups = new ArrayList<>();
        for (Group group : internalList) {
            groups.add(group);
        }
        return groups.toString();
    }
}
