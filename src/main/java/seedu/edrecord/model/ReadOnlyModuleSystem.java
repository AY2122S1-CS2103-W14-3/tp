package seedu.edrecord.model;

import java.util.List;

import seedu.edrecord.model.module.Module;

/**
 * Unmodifiable view of a module system
 */
public interface ReadOnlyModuleSystem {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     * @return
     */
    List<Module> getModuleList();

}
