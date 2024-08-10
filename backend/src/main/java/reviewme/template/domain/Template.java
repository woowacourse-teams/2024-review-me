package reviewme.template.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "section_ids", joinColumns = @JoinColumn(name = "template_id"))
    List<Long> sectionIds;

    public Template(List<Long> sectionIds) {
        this.sectionIds = sectionIds;
    }
}
