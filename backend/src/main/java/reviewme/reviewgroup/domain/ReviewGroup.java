package reviewme.reviewgroup.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.reviewgroup.domain.exception.InvalidProjectNameLengthException;
import reviewme.reviewgroup.domain.exception.InvalidRevieweeNameLengthException;

@Entity
@Table(name = "review_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewGroup {

    private static final int MIN_REVIEWEE_LENGTH = 1;
    private static final int MAX_REVIEWEE_LENGTH = 50;
    private static final int MIN_PROJECT_NAME_LENGTH = 1;
    private static final int MAX_PROJECT_NAME_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviewee", nullable = false)
    private String reviewee;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "review_request_code", nullable = false)
    private String reviewRequestCode;

    @Column(name = "group_access_code", nullable = false)
    private String groupAccessCode;

    @Column(name = "template_id", nullable = false)
    private long templateId = 1L;

    public ReviewGroup(String reviewee, String projectName, String reviewRequestCode, String groupAccessCode) {
        validateRevieweeLength(reviewee);
        validateProjectNameLength(projectName);
        this.reviewee = reviewee;
        this.projectName = projectName;
        this.reviewRequestCode = reviewRequestCode;
        this.groupAccessCode = groupAccessCode;
    }

    private void validateRevieweeLength(String reviewee) {
        if (reviewee.length() < MIN_REVIEWEE_LENGTH || reviewee.length() > MAX_REVIEWEE_LENGTH) {
            throw new InvalidRevieweeNameLengthException(reviewee.length(), MIN_REVIEWEE_LENGTH, MAX_REVIEWEE_LENGTH);
        }
    }

    private void validateProjectNameLength(String projectName) {
        if (projectName.length() < MIN_PROJECT_NAME_LENGTH || projectName.length() > MAX_PROJECT_NAME_LENGTH) {
            throw new InvalidProjectNameLengthException(
                    projectName.length(), MIN_PROJECT_NAME_LENGTH, MAX_PROJECT_NAME_LENGTH
            );
        }
    }

    public boolean hasAccessCodeOf(String groupAccessCode) {
        return groupAccessCode.equals(groupAccessCode);
    }
}
