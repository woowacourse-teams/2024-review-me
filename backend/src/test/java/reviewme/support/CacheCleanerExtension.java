package reviewme.support;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class CacheCleanerExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        SpringExtension.getApplicationContext(extensionContext)
                .getBean(CacheCleaner.class)
                .execute();
    }
}
