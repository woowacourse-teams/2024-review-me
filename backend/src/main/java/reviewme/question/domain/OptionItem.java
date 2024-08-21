package reviewme.question.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "option_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
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

    @Column(name = "option_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OptionType optionType;

    public OptionItem(String content, long optionGroupId, int position, OptionType optionType) {
        this.content = content;
        this.optionGroupId = optionGroupId;
        this.position = position;
        this.optionType = optionType;
    }
}
