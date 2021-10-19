package seedu.edrecord.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.edrecord.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edrecord.model.module.exceptions.DuplicateModuleException;
import seedu.edrecord.model.module.exceptions.ModuleNotFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code Module#isSameModule(Module)}. As such, adding and updating of
 * modules uses Module#isSameModule(Module) for equality so as to ensure that the module being added or updated is
 * unique in terms of identity in the UniqueModuleList. However, the removal of a module uses Module#equals(Object) so
 * as to ensure that the group with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Module#isSameModule(Module)
 */
public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    private final ObservableList<Module> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Adds a module to the list.
     * The module must not already exist in the list.
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent module from the list.
     * The module must exist in the list.
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Gets the module with {@code code} from the list.
     * The module must exist in the list.
     */
    public Module getModule(Module mod) {
        requireNonNull(mod);
        for (Module m : internalList) {
            if (m.isSameModule(mod)) {
                return m;
            }
        }
        throw new ModuleNotFoundException();
    }

    /**
     * Gets the module with {@code code} from the list.
     * The module must exist in the list.
     */
    public Module getModule(String code) {
        requireNonNull(code);
        for (Module mod : internalList) {
            if (mod.getCode().equals(code)) {
                return mod;
            }
        }
        throw new ModuleNotFoundException();
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!areModulesUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList // instanceof handles nulls
                && internalList.equals(((UniqueModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     */
    private boolean areModulesUnique(List<Module> modules) {
        for (int i = 0; i < modules.size() - 1; i++) {
            for (int j = i + 1; j < modules.size(); j++) {
                if (modules.get(i).isSameModule(modules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        List<Module> modules = new ArrayList<>();
        for (Module module : internalList) {
            modules.add(module);
        }
        return modules.toString();
    }
}
