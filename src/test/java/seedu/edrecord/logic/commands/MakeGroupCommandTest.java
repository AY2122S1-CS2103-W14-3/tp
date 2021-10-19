package seedu.edrecord.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edrecord.logic.commands.exceptions.CommandException;
import seedu.edrecord.model.EdRecord;
import seedu.edrecord.model.ModelManager;
import seedu.edrecord.model.UserPrefs;
import seedu.edrecord.model.group.Group;
import seedu.edrecord.model.module.Module;
import seedu.edrecord.testutil.TypicalGroups;
import seedu.edrecord.testutil.TypicalModules;

public class MakeGroupCommandTest {
    @Test
    public void constructor_nullGroupAndModule_throwsNullPointerException() {
        Group validGroup = TypicalGroups.T03;
        Module validModule = TypicalModules.CS2103;
        assertThrows(NullPointerException.class, () -> new MakeGroupCommand(validGroup, null));
        assertThrows(NullPointerException.class, () -> new MakeGroupCommand(null, validModule));
    }

    @Test
    public void execute_moduleDontExist_unsuccessful() {
        ModelManager expectedModel = new ModelManager(new EdRecord(),
            TypicalModules.getTypicalModuleSystem(), new UserPrefs());
        Group validGroup = TypicalGroups.T03;
        Module moduleNotInModuleSystem = TypicalModules.CS1111;
        assertThrows(CommandException.class, () -> new MakeGroupCommand(validGroup, moduleNotInModuleSystem)
            .execute(expectedModel));

    }

    @Test
    public void execute_groupAlreadyExists_unsuccessful() {
        ModelManager expectedModel = new ModelManager(new EdRecord(), TypicalModules.getTypicalModuleSystem(),
            new UserPrefs());
        Module validModule = TypicalModules.CS2103;
        Group groupThatAlreadyExistsInModule = TypicalGroups.T03;
        assertThrows(CommandException.class, () -> new MakeGroupCommand(groupThatAlreadyExistsInModule, validModule)
            .execute(expectedModel));
    }

    @Test
    public void execute_groupAddedToModule_makeGroupSuccessful() throws Exception {
        ModelManager expectedModel = new ModelManager(new EdRecord(), TypicalModules.getTypicalModuleSystem(),
            new UserPrefs());
        Module validModule = TypicalModules.CS2103;
        Group groupThatDoesNotExist = TypicalGroups.T10;
        assertEquals(String.format(MakeGroupCommand.MESSAGE_SUCCESS, groupThatDoesNotExist, validModule),
            new MakeGroupCommand(groupThatDoesNotExist, validModule).execute(expectedModel).getFeedbackToUser());
    }

    @Test
    public void execute_groupsAddedToDifferentModule_makeDifferentGroupsSuccessful() throws Exception {
        ModelManager expectedModel = new ModelManager(new EdRecord(), TypicalModules.getTypicalModuleSystem(),
            new UserPrefs());
        Module validModule1 = TypicalModules.CS2103;
        Module validModule2 = TypicalModules.CS2103T;
        Group groupThatDoesNotExist = TypicalGroups.T12;
        assertEquals(String.format(MakeGroupCommand.MESSAGE_SUCCESS, groupThatDoesNotExist, validModule1),
            new MakeGroupCommand(groupThatDoesNotExist, validModule1).execute(expectedModel).getFeedbackToUser());
        assertEquals(String.format(MakeGroupCommand.MESSAGE_SUCCESS, groupThatDoesNotExist, validModule2),
            new MakeGroupCommand(groupThatDoesNotExist, validModule2).execute(expectedModel).getFeedbackToUser());
    }
}
