package reviewme.util;

import lombok.Getter;

@Getter
public class PageSize {

    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 50;

    private final int size;

    public PageSize(Integer size) {
        if (size == null || size < 1 || size > MAX_SIZE) {
            this.size = DEFAULT_SIZE;
            return;
        }
        this.size = size;
    }
}
