package reviewme.keyword.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "detail", nullable = false)
    private String detail;

    Keyword(Long id, String detail) {
        this.id = id;
        this.detail = detail;
    }

    public Keyword(String detail) {
        this(null, detail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Keyword keyword)) {
            return false;
        }
        return Objects.equals(id, keyword.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
