package reviewme.fixture;

import java.util.List;
import java.util.stream.IntStream;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.OptionType;
import reviewme.template.domain.Question;
import reviewme.template.domain.QuestionType;

public class QuestionFixture {

    public static Question 선택형_질문(boolean required, int optionCount, int position) {
        List<OptionItem> optionItems = IntStream.rangeClosed(1, optionCount)
                .mapToObj(i -> new OptionItem("선택지 본문", i, OptionType.CATEGORY))
                .toList();
        OptionGroup optionGroup = new OptionGroup(optionItems, 1, optionItems.size());
        return new Question(required, QuestionType.CHECKBOX, optionGroup, "본문", null, position);
    }

    public static Question 선택형_필수_질문() {
        return 선택형_필수_질문(1);
    }

    public static Question 선택형_필수_질문(int position) {
        return new Question(true, QuestionType.CHECKBOX, "본문", null, position);
    }

    public static Question 서술형_필수_질문() {
        return 서술형_필수_질문(1);
    }

    public static Question 서술형_필수_질문(int position) {
        return new Question(true, QuestionType.TEXT, "본문", null, position);
    }

    public static Question 서술형_옵션_질문() {
        return 서술형_옵션_질문(1);
    }

    public static Question 서술형_옵션_질문(int position) {
        return new Question(false, QuestionType.TEXT, "본문", null, position);
    }
}
