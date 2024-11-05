package reviewme.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

@Component("cacheKeyGenerator")
public class CacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getSimpleName() + "_" +
               method.getName() + "_" +
               Arrays.toString(params);
    }
}
