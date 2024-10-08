package reviewme.review.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "new_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_id", nullable = false)
    private long templateId;

    @Column(name = "review_group_id", nullable = false)
    private long reviewGroupId;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private List<Answer> answers;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Review(long templateId, long reviewGroupId, List<Answer> answers) {
        this.templateId = templateId;
        this.reviewGroupId = reviewGroupId;
        this.answers = answers;
        this.createdAt = LocalDateTime.now();
    }

    public Set<Long> getAnsweredQuestionIds() {
        return answers.stream()
                .map(Answer::getQuestionId)
                .collect(Collectors.toSet());
    }

    public boolean hasAnsweredQuestion(long questionId) {
        return getAnsweredQuestionIds().contains(questionId);
    }

    public <T extends Answer> List<T> getAnswersByType(Class<T> clazz) {
        return answers.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }

    public LocalDate getCreatedDate() {
        return createdAt.toLocalDate();
    }
}
