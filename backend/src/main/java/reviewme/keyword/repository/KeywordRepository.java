package reviewme.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.domain.exception.KeywordNotFoundException;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    default Keyword getKeywordById(long id) {
        return findById(id).orElseThrow(KeywordNotFoundException::new);
    }
}
