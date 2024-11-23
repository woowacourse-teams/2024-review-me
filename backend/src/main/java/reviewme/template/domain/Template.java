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
import java.util.HashSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.template.domain.exception.DuplicateSectionIdException;
import reviewme.template.domain.exception.SectionIdsNotExistException;

@Entity
@Table(name = "template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "template_id", nullable = false, updatable = false)
    private List<TemplateSection> sectionIds;

    public Template(List<Long> sectionIds) {
        validateSectionIds(sectionIds);
        this.sectionIds = sectionIds.stream()
                .map(TemplateSection::new)
                .toList();
    }

    private void validateSectionIds(List<Long> sectionIds) {
        validateNotEmpty(sectionIds);
        validateNoDuplicates(sectionIds);
    }

    private void validateNotEmpty(List<Long> sectionIds) {
        if (sectionIds == null || sectionIds.isEmpty()) {
            throw new SectionIdsNotExistException();
        }
    }

    private void validateNoDuplicates(List<Long> sectionIds) {
        int originalSize = sectionIds.size();
        int deduplicatedSize = new HashSet<>(sectionIds).size();

        if (originalSize != deduplicatedSize) {
            throw new DuplicateSectionIdException(sectionIds);
        }
    }

    public List<Long> getSectionIds() {
        return sectionIds.stream()
                .map(TemplateSection::getSectionId)
                .toList();
    }
}
