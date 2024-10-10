package reviewme.highlight.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "highlight")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class HighLight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_group_id", nullable = false)
    private long reviewGroupId;

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @Column(name = "answer_id", nullable = false)
    private long answerId;

    @Embedded
    private HighlightPosition highlightPosition;

    public HighLight(long reviewGroupId, long questionId, long answerId,
                     long lineIndex, long startIndex, long endIndex) {
        this.reviewGroupId = reviewGroupId;
        this.questionId = questionId;
        this.answerId = answerId;
        this.highlightPosition = new HighlightPosition(lineIndex, startIndex, endIndex);
    }
}
