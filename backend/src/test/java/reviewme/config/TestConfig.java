package reviewme.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reviewme.fixture.OptionGroupFixture;
import reviewme.fixture.OptionItemFixture;
import reviewme.fixture.QuestionFixture;
import reviewme.fixture.ReviewGroupFixture;
import reviewme.fixture.SectionFixture;
import reviewme.fixture.TemplateFixture;
import reviewme.support.DatabaseCleaner;

@TestConfiguration
public class TestConfig {

    @Bean
    public DatabaseCleaner databaseCleaner() {
        return new DatabaseCleaner();
    }

    @Bean
    public OptionGroupFixture optionGroupFixture() {
        return new OptionGroupFixture();
    }

    @Bean
    public OptionItemFixture optionItemFixture() {
        return new OptionItemFixture();
    }

    @Bean
    public QuestionFixture questionFixture() {
        return new QuestionFixture();
    }

    @Bean
    public ReviewGroupFixture reviewGroupFixture() {
        return new ReviewGroupFixture();
    }

    @Bean
    public SectionFixture sectionFixture() {
        return new SectionFixture();
    }

    @Bean
    public TemplateFixture templateFixture() {
        return new TemplateFixture();
    }
}
