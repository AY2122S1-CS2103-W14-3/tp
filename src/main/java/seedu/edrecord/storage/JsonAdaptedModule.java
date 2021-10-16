package seedu.edrecord.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edrecord.commons.exceptions.IllegalValueException;
import seedu.edrecord.model.assignment.Assignment;
import seedu.edrecord.model.module.Module;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Assignment list for this module contains duplicates.";

    private final String code;
    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module code and assignments.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("code") String code,
                             @JsonProperty("assignments") List<JsonAdaptedAssignment> assignments) {
        this.code = code;
        if (assignments != null) {
            this.assignments.addAll(assignments);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        code = source.getCode();
        assignments.addAll(source.getAssignmentList().stream()
                .map(JsonAdaptedAssignment::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this {@code JsonAdaptedModule} object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Module toModelType() throws IllegalValueException {
        if (Module.MODULE_SYSTEM.hasModule(code)) {
            throw new IllegalValueException(Module.MESSAGE_DUPLICATE);
        }
        if (!Module.isValidModuleCode(code)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS);
        }
        if (!Module.isValidSavedModuleCode(code)) {
            throw new IllegalValueException(Module.MESSAGE_INVALID_JSON);
        }
        Module module = new Module(code);
        for (JsonAdaptedAssignment jsonAdaptedAssignment : assignments) {
            Assignment asg = jsonAdaptedAssignment.toModelType();
            if (module.hasAssignment(asg)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSIGNMENT);
            }
            module.addAssignment(asg);
        }
        return module;
    }

}
