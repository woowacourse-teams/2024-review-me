package reviewme.reviewgroup.service;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomCodeGenerator {

    private static final Random random = new Random();
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String CHARACTER = UPPERCASE + LOWERCASE + NUMBERS;

    public String generate(int length) {
        StringBuilder sb = new StringBuilder();
        random.ints(length, 0, CHARACTER.length())
                .mapToObj(CHARACTER::charAt)
                .forEach(sb::append);
        return sb.toString();
    }
}
