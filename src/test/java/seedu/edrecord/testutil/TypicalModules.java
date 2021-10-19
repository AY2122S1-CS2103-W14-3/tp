package seedu.edrecord.testutil;

import static seedu.edrecord.testutil.TypicalGroups.getTypicalGroupSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edrecord.model.module.Module;
import seedu.edrecord.model.module.ModuleSystem;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final Module CS2103 = new Module("CS2103", getTypicalGroupSystem());
    public static final Module CS2103T = new Module("CS2103T", getTypicalGroupSystem());
    public static final Module CS3230 = new Module("CS3230", getTypicalGroupSystem());
    public static final Module CS1111 = new Module("CS1111");

    private TypicalModules() {} // prevents instantiation

    /**
     * Returns an {@code ModuleSystem} with all the typical modules.
     */
    public static ModuleSystem getTypicalModuleSystem() {
        ModuleSystem ms = new ModuleSystem();
        ms.setModules(getTypicalModules());
        return ms;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2103, CS2103T, CS3230));
    }

    public static void setTypicalModuleSystem() {
        Module.MODULE_SYSTEM.setModules(getTypicalModules());
    }
}
