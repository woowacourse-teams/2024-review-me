package reviewme.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.template.domain.TemplateSection;

public interface TemplateSectionRepository extends JpaRepository<TemplateSection, Long> {
}
