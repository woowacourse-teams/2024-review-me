package reviewme.template.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reviewme.cache.TemplateCacheRepository;
import reviewme.cache.exception.TemplateNotFoundException;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateJpaRepository;

@Repository
@RequiredArgsConstructor
public class TemplateRepository {

    private final TemplateJpaRepository jpaRepository;
    private final TemplateCacheRepository cacheRepository;

    public Template findById(long id) {
        return cacheRepository.findTemplateById(id)
                .or(() -> jpaRepository.findById(id))
                .orElseThrow(() -> new TemplateNotFoundException(id));
    }
}
