package reviewme.template.domain;

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
@Table(name = "template_section")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TemplateSectionId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_id", nullable = false, insertable = false, updatable = false)
    private long templateId;

    @Column(name = "section_id", nullable = false)
    private long sectionId;

    public TemplateSectionId(long sectionId) {
        this.sectionId = sectionId;
    }
}
