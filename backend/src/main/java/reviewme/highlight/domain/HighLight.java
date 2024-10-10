package reviewme.highlight.domain;

import jakarta.persistence.Column;
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

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @Column(name = "review_group_id", nullable = false)
    private long reviewGroupId;

    @Column(name = "answer_id", nullable = false)
    private long answerId;

    @Column(name = "line_index", nullable = false)
    private long lineIndex;

    @Column(name = "start_index", nullable = false)
    private long startIndex;

    @Column(name = "end_index", nullable = false)
    private long endIndex;

    public HighLight(long questionId, long reviewGroupId, long answerId,
                     long lineIndex, long startIndex, long endIndex) {
        this.questionId = questionId;
        this.reviewGroupId = reviewGroupId;
        this.answerId = answerId;
        this.lineIndex = lineIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
}
