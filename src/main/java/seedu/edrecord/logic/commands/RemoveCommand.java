package seedu.edrecord.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edrecord.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.edrecord.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.edrecord.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.edrecord.commons.core.Messages;
import seedu.edrecord.commons.core.index.Index;
import seedu.edrecord.logic.commands.exceptions.CommandException;
import seedu.edrecord.model.Model;
import seedu.edrecord.model.group.Group;
import seedu.edrecord.model.module.Module;
import seedu.edrecord.model.module.ModuleSet;
import seedu.edrecord.model.person.Person;

/**
 * Removes an existing person in edrecord from an existing mod's class.
 */
public class RemoveCommand extends Command {

    public static final String COMMAND_WORD = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the person identified by the index number used"
            + "in the displayed person list from the specified module and class.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_GROUP + "GROUP\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_GROUP + "T01";

    public static final String MESSAGE_MODULE_DOES_NOT_EXIST_IN_PERSON = "%1$s is not under module %2$s!";
    public static final String MESSAGE_GROUP_DOES_NOT_EXIST_IN_PERSON = "%1$s's module %2$s does not have class %3$s!";
    public static final String MESSAGE_MOVE_PERSON_SUCCESS = "Removed %1$s from %2$s/%3$s";
    public static final String MESSAGE_MOVE_PERSON_FAILURE = "Removing %1$s from %2$s/%3$s was unsuccessful!";

    private final Index index;
    private final Module module;
    private final Group group;

    /**
     * @param index of the person in the filtered person list to remove from their mod and class.
     * @param mod to remove the person from.
     * @param grp to remove the person from.
     */
    public RemoveCommand(Index index, Module mod, Group grp) {
        requireNonNull(index);
        requireNonNull(mod);
        requireNonNull(grp);

        this.index = index;
        this.module = mod;
        this.group = grp;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!model.hasModule(module)) {
            throw new CommandException(String.format(Module.MESSAGE_DOES_NOT_EXIST, module));
        }
        Module savedMod = model.getModule(module);
        if (!savedMod.hasGroup(group)) {
            throw new CommandException(Group.MESSAGE_DOES_NOT_EXIST);
        }

        Person personToMove = lastShownList.get(index.getZeroBased());
        ModuleSet moduleSet = personToMove.getModules();
        if (!moduleSet.containsModule(savedMod)) {
            throw new CommandException(
                    String.format(MESSAGE_MODULE_DOES_NOT_EXIST_IN_PERSON, personToMove.getName(), savedMod));
        }
        if (!moduleSet.containsGroupInModule(savedMod, group)) {
            throw new CommandException(
                    String.format(MESSAGE_GROUP_DOES_NOT_EXIST_IN_PERSON, personToMove.getName(), savedMod, group));
        }

        moduleSet.removeGroup(savedMod, group);

        model.setSearchFilter(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MOVE_PERSON_SUCCESS, personToMove.getName(), savedMod, group));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveCommand)) {
            return false;
        }

        // state check
        RemoveCommand e = (RemoveCommand) other;
        return index.equals(e.index)
                && module.isSameModule(e.module)
                && group.isSameGroup(e.group);
    }

}
