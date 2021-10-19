package seedu.edrecord.logic.commands;

import static seedu.edrecord.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edrecord.testutil.TypicalModules.getTypicalModuleSystem;
import static seedu.edrecord.testutil.TypicalModules.getTypicalModules;
import static seedu.edrecord.testutil.TypicalPersons.getTypicalEdRecord;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edrecord.model.Model;
import seedu.edrecord.model.ModelManager;
import seedu.edrecord.model.UserPrefs;
import seedu.edrecord.model.module.Module;

public class ListModulesCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEdRecord(), getTypicalModuleSystem(), new UserPrefs());
        expectedModel = new ModelManager(model.getEdRecord(), getTypicalModuleSystem(), new UserPrefs());
    }

    @Test
    public void execute_listModules_success() {
        List<String> moduleStringList = new ArrayList<>();
        for (Module m : getTypicalModules()) {
            moduleStringList.add(m.toString());
        }

        String moduleList = String.join(ListModulesCommand.MODULE_LIST_DELIM, moduleStringList);
        String expectedOutput = String.format(ListModulesCommand.MESSAGE_SUCCESS, moduleList);

        CommandResult expectedCommandResult = new CommandResult(expectedOutput);
        assertCommandSuccess(new ListModulesCommand(), model, expectedCommandResult, expectedModel);
    }
}
