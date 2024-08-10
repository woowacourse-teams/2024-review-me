package reviewme.question.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "option_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "option_group_id", nullable = false)
    private long optionGroupId;

    @Column(name = "position", nullable = false)
    private int position;

    public OptionItem(String content, long optionGroupId, int position) {
        this.content = content;
        this.optionGroupId = optionGroupId;
        this.position = position;
    }
}
