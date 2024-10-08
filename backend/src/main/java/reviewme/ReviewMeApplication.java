package reviewme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ReviewMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewMeApplication.class, args);
    }
}
