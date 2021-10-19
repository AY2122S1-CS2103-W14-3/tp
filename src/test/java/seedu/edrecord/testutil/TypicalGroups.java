package seedu.edrecord.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edrecord.model.group.Group;
import seedu.edrecord.model.group.GroupSystem;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group T03 = new Group("T03");
    public static final Group T07 = new Group("T07");
    public static final Group T10 = new Group("T10");
    public static final Group T12 = new Group("T12");

    private TypicalGroups() {} // prevents instantiation

    /**
     * Returns an {@code GroupSystem} with all the typical groups.
     */
    public static GroupSystem getTypicalGroupSystem() {
        GroupSystem gs = new GroupSystem();
        gs.setGroups(getTypicalGroups());
        return gs;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(T03, T07));
    }
}
