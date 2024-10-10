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

    @Column(name = "answer_id", nullable = false)
    private long answerId;

    @Column(name = "line_index", nullable = false)
    private long lineIndex;

    @Embedded
    private HighlightRange highlightRange;

    public HighLight(long answerId, long lineIndex, long startIndex, long endIndex) {
        this.answerId = answerId;
        this.lineIndex = lineIndex;
        this.highlightRange = new HighlightRange(startIndex, endIndex);
    }
}
