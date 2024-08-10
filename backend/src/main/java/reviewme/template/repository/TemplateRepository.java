package reviewme.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.template.domain.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
