package reviewme.template.domain;

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

    @Column(name = "position", nullable = false)
    private int position;

    // TODO: 카테고리/키워드 여부는 도메인 로직이 아니라 서비스단에 있는 것이 자연스럽다고 생각함
    @Column(name = "option_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OptionType optionType;

    public OptionItem(String content, int position, OptionType optionType) {
        this.content = content;
        this.position = position;
        this.optionType = optionType;
    }
}
