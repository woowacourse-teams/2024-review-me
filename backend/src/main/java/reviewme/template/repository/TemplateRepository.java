package reviewme.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.Template;
import reviewme.template.domain.exception.TemplateNotFoundException;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    default Template getTemplateById(long id) {
        return findById(id).orElseThrow(() -> new TemplateNotFoundException(id));
    }
}
