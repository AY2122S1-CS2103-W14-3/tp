package seedu.edrecord.model.assignment;

import static seedu.edrecord.commons.util.CollectionUtil.requireAllNonNull;

import seedu.edrecord.model.name.Name;

/**
 * Represents an Assignment under a module in edrecord.
 * Guarantees: immutable, details are present and not null.
 */
public class Assignment {
    private final Name name;
    private final Weightage weightage;
    private final MaxScore maxScore;

    /**
     * Constructs an {@code Assignment}. Every field must be present and not null.
     * @param name The assignment name, must be unique across the module.
     * @param weightage The assignment weightage in percentage. It is <em>not</em> guaranteed that
     *                  the total weightage of all assignments in the module sum to 100%.
     * @param maxScore The maximum score for the assignment.
     */
    public Assignment(Name name, Weightage weightage, MaxScore maxScore) {
        requireAllNonNull(name, weightage, maxScore);
        this.name = name;
        this.weightage = weightage;
        this.maxScore = maxScore;
    }

    public Name getName() {
        return name;
    }

    public Weightage getWeightage() {
        return weightage;
    }

    public MaxScore getMaxScore() {
        return maxScore;
    }

    /**
     * Returns true if both assignments have the same name, i.e. they are considered to have the same identity.
     * This defines a weaker notion of equality between two assignments.
     * @param otherAssignment The other assignment to compare to.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }
        return otherAssignment != null
                && otherAssignment.getName().equals(getName());
    }

    @Override
    public String toString() {
        return String.format("%s - Weightage: %s, maximum score: %s", name, weightage, maxScore);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Assignment // instanceof handles nulls
                && name.equals(((Assignment) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
