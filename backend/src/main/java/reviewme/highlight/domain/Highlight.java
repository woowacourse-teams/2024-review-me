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
public class Highlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_id", nullable = false)
    private long answerId;

    @Column(name = "line_index", nullable = false)
    private int lineIndex;

    @Embedded
    private HighlightRange highlightRange;

    public Highlight(long answerId, int lineIndex, HighlightRange range) {
        this.answerId = answerId;
        this.lineIndex = lineIndex;
        this.highlightRange = range;
    }
}
