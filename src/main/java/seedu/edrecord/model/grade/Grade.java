package seedu.edrecord.model.grade;

import static seedu.edrecord.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Grade under a Student in EdRecord.
 * Guarantees: immutable, details are present and not null.
 */
public class Grade {
    public enum GradeStatus { NOT_SUBMITTED, SUBMITTED, GRADED }

    private final Score score;
    private final GradeStatus status;

    /**
     * Constructs an {@code Assignment}. Every field must be present and not null.
     *
     * @param score  Score of the grade
     * @param status Status of the grade.
     */
    public Grade(Score score, GradeStatus status) {
        requireAllNonNull(score);
        this.score = score;
        this.status = status;
    }

    public Score getScore() {
        return score;
    }

    @Override
    public String toString() {
        return String.format("Score: %s, Status: %s", score, status);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grade // instanceof handles nulls
                && score.equals(((Grade) other).score)
                && status.equals(((Grade) other).status)); // state check
    }

    // TODO change to unique ID
    @Override
    public int hashCode() {
        return score.hashCode();
    }
}
