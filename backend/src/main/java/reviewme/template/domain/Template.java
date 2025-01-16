package reviewme.template.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Aggregate root
// 템플릿 하나를 만들고 이를 수정/삭제할 수 있다. 이는 하나의 애그리거트에서 일어나는 것이 자연스럽다.
@Entity
@Table(name = "template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Template : Section은 1 : N 관계이다. Section을 여러 곳에서 재사용한다고 생각하니 머리가 아프다. 정말 재사용할 일이 있을까? 싶다.
    // 마찬가지로 Section : Question도 1 : N 관계이다. Question 또한 재사용될 일이 있을까? N:M이 아닌 1:N 관계로 생각해보자
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "template_id", nullable = false, updatable = false)
    private List<Section> sections;

    public Template(List<Section> sections) {
        if (sections.isEmpty()) {
            throw new IllegalArgumentException("섹션은 최소 한 개 이상이어야 합니다.");
        }
        // TODO: Max section count limit?
        this.sections = sections;
    }
}
