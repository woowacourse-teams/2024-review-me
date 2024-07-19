package reviewme.support;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;

public class DatabaseCleaner {

    private static final String TRUNCATE_FORMAT = "TRUNCATE TABLE %s";
    private static final String ALTER_FORMAT = "ALTER TABLE %s ALTER COLUMN ID RESTART WITH 1";

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @PostConstruct
    public void afterPropertiesSet() {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        tableNames = entities.stream()
                .filter(entity -> entity.getJavaType().isAnnotationPresent(Table.class))
                .map(entity -> entity.getJavaType().getAnnotation(Table.class).name())
                .toList();
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        for (String tableName : tableNames) {
            entityManager.createNativeQuery(TRUNCATE_FORMAT.formatted(tableName)).executeUpdate();
            entityManager.createNativeQuery(ALTER_FORMAT.formatted(tableName)).executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
