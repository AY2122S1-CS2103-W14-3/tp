package seedu.edrecord.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edrecord.commons.core.Messages.MESSAGE_NO_MODULE_SELECTED;
import static seedu.edrecord.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.edrecord.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.edrecord.commons.core.Messages;
import seedu.edrecord.commons.core.index.Index;
import seedu.edrecord.logic.commands.exceptions.CommandException;
import seedu.edrecord.model.Model;
import seedu.edrecord.model.assignment.Assignment;
import seedu.edrecord.model.assignment.Grade;
import seedu.edrecord.model.module.ModuleSet;
import seedu.edrecord.model.name.Name;
import seedu.edrecord.model.person.AssignmentGradeMap;
import seedu.edrecord.model.person.Email;
import seedu.edrecord.model.person.Info;
import seedu.edrecord.model.person.Person;
import seedu.edrecord.model.person.Phone;
import seedu.edrecord.model.tag.Tag;

/**
 * Deletes a student's grade for an assignment.
 */
public class DeleteGradeCommand extends Command {

    public static final String COMMAND_WORD = "dlgrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a grade from the student "
            + "identified by the index number used in the displayed student list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ID + "ASSIGNMENT ID \n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ID + "4";

    public static final String MESSAGE_SUCCESS = "Deleted grade %s \nfor assignment: %s \nfor student: %s";
    public static final String MESSAGE_NO_SUCH_GRADE = "There is no grade for this assignment.";

    private final Index index;
    private final Index id;

    /**
     * @param index Index of the student to grade.
     * @param id    ID of the assignment to grade.
     */
    public DeleteGradeCommand(Index index, Index id) {
        requireNonNull(index);
        requireNonNull(id);

        this.index = index;
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Check for valid index
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Check whether a module has been selected
        if (!model.hasSelectedModule()) {
            throw new CommandException(MESSAGE_NO_MODULE_SELECTED);
        }

        List<Assignment> assignmentList = model.getAssignmentList();

        if (id.getOneBased() >= model.getAssignmentCounter()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignment = assignmentList.stream()
                .filter(asg -> asg.getId() == id.getOneBased())
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX));

        Person personToEdit = lastShownList.get(index.getZeroBased());
        AssignmentGradeMap grades = personToEdit.getGrades();
        Grade toRemove = grades.findGrade(assignment);
        if (toRemove == null) {
            throw new CommandException(MESSAGE_NO_SUCH_GRADE);
        }

        Person editedPerson = createEditedPerson(personToEdit, assignment);

        model.setPerson(personToEdit, editedPerson);
        model.setSearchFilter(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove, assignment, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with the given {@code Assignment} and corresponding {@code Grade}.
     */
    public static Person createEditedPerson(Person personToEdit, Assignment assignment) {
        requireNonNull(personToEdit);

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Info updatedInfo = personToEdit.getInfo();
        ModuleSet updatedModule = personToEdit.getModules();
        Set<Tag> updatedTags = personToEdit.getTags();
        AssignmentGradeMap updatedGrades = personToEdit.getGrades();
        updatedGrades.removeGrade(assignment);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedInfo, updatedModule, updatedTags,
                updatedGrades);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGradeCommand)) {
            return false;
        }

        // state check
        DeleteGradeCommand e = (DeleteGradeCommand) other;
        return index.equals(e.index) && id.equals(id);
    }
}
