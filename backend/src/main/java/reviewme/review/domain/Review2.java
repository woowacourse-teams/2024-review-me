package reviewme.review.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review2")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_id", nullable = false)
    private long templateId; // todo: templateId 를 여기가 아니라 reviewGroup 안에 둬야 함

    @Column(name = "review_group_id", nullable = false)
    private long reviewGroupId;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private List<TextAnswer> textAnswers;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private List<CheckboxAnswer> checkboxAnswers;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Review2(long templateId, long reviewGroupId,
                   List<TextAnswer> textAnswers, List<CheckboxAnswer> checkboxAnswers,
                   LocalDateTime createdAt) {
        this.templateId = templateId;
        this.reviewGroupId = reviewGroupId;
        this.textAnswers = textAnswers;
        this.checkboxAnswers = checkboxAnswers;
        this.createdAt = createdAt;
    }
}
