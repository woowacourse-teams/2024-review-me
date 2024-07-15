package reviewme.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviewer_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewerGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewee_id", nullable = false)
    private Member reviewee;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    public ReviewerGroup(Member reviewee, String groupName, LocalDateTime deadline) {
        this.reviewee = reviewee;
        this.groupName = groupName;
        this.deadline = deadline;
    }
}
