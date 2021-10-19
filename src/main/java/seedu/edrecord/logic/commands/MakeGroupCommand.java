package seedu.edrecord.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edrecord.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.edrecord.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.edrecord.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.edrecord.logic.commands.exceptions.CommandException;
import seedu.edrecord.model.Model;
import seedu.edrecord.model.group.Group;
import seedu.edrecord.model.module.Module;

/**
 * Creates a class group.
 */
public class MakeGroupCommand extends Command {

    public static final String COMMAND_WORD = "mkclass";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new class for module "
            + "with the class and module code specified.\n"
            + PREFIX_MODULE + "MODULE "
            + PREFIX_GROUP + "GROUP "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_GROUP + "T01 ";

    public static final String MESSAGE_DUPLICATE_GROUP = "Class already exists in %1$s";
    public static final String MESSAGE_SUCCESS = "Class %1$s created in %2$s!";

    private final Group group;
    private final Module module;

    /**
     * @param group to be created.
     */
    public MakeGroupCommand(Group group, Module mod) {
        requireAllNonNull(group);
        requireAllNonNull(mod);

        this.group = group;
        this.module = mod;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(module)) {
            throw new CommandException(Module.MESSAGE_DOES_NOT_EXIST);
        }

        Module mod = model.getModule(module);
        if (mod.hasGroup(group)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_GROUP, mod));
        }

        mod.addGroup(group);
        return new CommandResult(String.format(MESSAGE_SUCCESS, group, mod));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MakeGroupCommand)) {
            return false;
        }

        // state check
        MakeGroupCommand e = (MakeGroupCommand) other;
        return group.equals(e.group) && module.equals(e.module);
    }
}
