package seedu.edrecord.model.module;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.edrecord.model.group.GroupSystem;

/**
 * Wraps all data at the module-system level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class ModuleSystem implements ReadOnlyModuleSystem {

    private final UniqueModuleList modules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
    }

    public ModuleSystem() {}

    /**
     * Creates a ModuleSystem using the Modules in the {@code toBeCopied}
     */
    public ModuleSystem(ReadOnlyModuleSystem toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the modules list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Resets the existing data of this {@code ModuleSystem} with {@code newData}.
     */
    public void resetData(ReadOnlyModuleSystem newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the module system.
     */
    public boolean hasModule(Module mod) {
        requireNonNull(mod);
        return modules.contains(mod);
    }

    /**
     * Returns true if a module with the same code as {@code code} exists in the module system.
     */
    public boolean hasModule(String code) {
        requireNonNull(code);
        return hasModule(new Module(code, new GroupSystem()));
    }

    /**
     * Adds a module to the module system.
     * The module must not already exist in the module system.
     */
    public void addModule(Module mod) {
        modules.add(mod);
    }

    /**
     * Removes {@code key} from this {@code ModuleSystem}.
     * {@code key} must exist in the module system.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    /**
     * Returns module with {@code code} from this {@code ModuleSystem}.
     * Module with same code as {@code code} must exist in the module system.
     */
    public Module getModule(Module mod) {
        return modules.getModule(mod);
    }

    /**
     * Returns module with {@code code} from this {@code ModuleSystem}.
     * Module with same code as {@code code} must exist in the module system.
     */
    public Module getModule(String code) {
        return modules.getModule(code);
    }

    //// util methods

    @Override
    public String toString() {
        return modules.toString();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleSystem // instanceof handles nulls
                && modules.equals(((ModuleSystem) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
