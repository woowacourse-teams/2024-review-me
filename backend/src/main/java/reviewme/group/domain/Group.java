package reviewme.group.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.review.domain.exception.InvalidProjectNameLengthException;
import reviewme.review.domain.exception.InvalidRevieweeNameLengthException;

@Entity
@Table(name = "group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Group {

    private static final int MAX_REVIEWEE_LENGTH = 50;
    private static final int MAX_PROJECT_NAME_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviewee")
    private String reviewee;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "review_request_code")
    private String reviewRequestCode;

    @Column(name = "group_access_code")
    private String groupAccessCode;

    public Group(String reviewee, String projectName, String reviewRequestCode, String groupAccessCode) {
        if (reviewee.length() > MAX_REVIEWEE_LENGTH || reviewee.isBlank()) {
            throw new InvalidRevieweeNameLengthException(MAX_REVIEWEE_LENGTH);
        }
        if (projectName.length() > MAX_PROJECT_NAME_LENGTH || projectName.isBlank()) {
            throw new InvalidProjectNameLengthException(MAX_PROJECT_NAME_LENGTH);
        }
        this.reviewee = reviewee;
        this.projectName = projectName;
        this.reviewRequestCode = reviewRequestCode;
        this.groupAccessCode = groupAccessCode;
    }
}
