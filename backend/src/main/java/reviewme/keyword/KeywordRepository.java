package reviewme.keyword;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    default Keyword getKeywordById(long id) {
        return findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
