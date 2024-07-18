package reviewme.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.member.domain.exception.DescriptionLengthExceedException;
import reviewme.member.domain.exception.InvalidGroupNameLengthException;

@Entity
@Table(name = "reviewer_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewerGroup {

    private static final Duration DEADLINE_DURATION = Duration.ofDays(7);
    private static final int MAX_DESCRIPTION_LENGTH = 50;
    private static final int MAX_GROUP_NAME_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewee_id", nullable = false)
    private Member reviewee;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    public ReviewerGroup(Member reviewee, String groupName, String description, LocalDateTime createdAt) {
        if (groupName.isBlank() || groupName.length() > MAX_GROUP_NAME_LENGTH) {
            throw new InvalidGroupNameLengthException(MAX_GROUP_NAME_LENGTH);
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new DescriptionLengthExceedException(MAX_DESCRIPTION_LENGTH);
        }
        this.reviewee = reviewee;
        this.groupName = groupName;
        this.description = description;
        this.createdAt = createdAt;
    }

    public boolean isDeadlineExceeded(LocalDateTime now) {
        return now.isAfter(getDeadline());
    }

    public LocalDateTime getDeadline() {
        return createdAt.plus(DEADLINE_DURATION);
    }
}
