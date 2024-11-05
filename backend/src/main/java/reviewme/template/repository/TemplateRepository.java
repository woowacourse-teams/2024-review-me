package reviewme.template.repository;

import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    @Cacheable(value = "templateCache", key = "#templateId")
    Optional<Template> findById(long id);
}
