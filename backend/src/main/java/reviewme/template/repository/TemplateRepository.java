package reviewme.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

}
