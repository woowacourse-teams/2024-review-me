package reviewme.support;

import java.util.Objects;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class CacheCleaner {

    private final CacheManager cacheManager;

    public CacheCleaner(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void execute() {
        cacheManager.getCacheNames()
                .stream()
                .map(cacheManager::getCache)
                .filter(Objects::nonNull)
                .forEach(Cache::clear);
    }
}
