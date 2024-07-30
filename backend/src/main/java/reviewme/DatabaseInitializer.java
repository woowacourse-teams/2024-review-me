package reviewme;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.member.repository.MemberRepository;
import reviewme.member.repository.ReviewerGroupRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.repository.QuestionRepository;
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewRepository;

@Profile("local")
@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewerGroupRepository reviewerGroupRepository;

    private final QuestionRepository questionRepository;
    private final ReviewContentRepository reviewContentRepository;
    private final KeywordRepository keywordRepository;

    @PostConstruct
    @Transactional
    void setup() {
        Question question1 = questionRepository.save(new Question("동료의 개발 역량 향상을 위해 피드백을 남겨 주세요."));
        Question question2 = questionRepository.save(new Question("동료의 소프트 스킬의 성장을 위해 피드백을 남겨 주세요."));

        Keyword keyword1 = keywordRepository.save(new Keyword("회의를 잘 이끌어요"));
        Keyword keyword2 = keywordRepository.save(new Keyword("추진력이 좋아요"));
        Keyword keyword3 = keywordRepository.save(new Keyword("의견을 잘 조율해요"));
        Keyword keyword4 = keywordRepository.save(new Keyword("꼼꼼하게 기록해요"));
        Keyword keyword5 = keywordRepository.save(new Keyword("말투가 상냥해요"));

        Member 아루 = memberRepository.save(new Member("아루", 1L));
        Member 산초 = memberRepository.save(new Member("산초", 2L));
        Member 커비 = memberRepository.save(new Member("커비", 3L));
        Member 테드 = memberRepository.save(new Member("테드", 4L));
        Member 올리 = memberRepository.save(new Member("올리", 5L));
        Member 바다 = memberRepository.save(new Member("바다", 6L));
        Member 쑤쑤 = memberRepository.save(new Member("쑤쑤", 7L));
        Member 에프이 = memberRepository.save(new Member("에프이", 8L));

        ReviewerGroup 산초리뷰그룹 = reviewerGroupRepository.save(
                new ReviewerGroup(
                        산초,
                        Stream.of(아루, 커비, 테드, 올리, 바다, 쑤쑤, 에프이).map(Member::getGithubId).toList(),
                        "2024-review-me",
                        "우테코에서 진행한 상호 리뷰 프로젝트입니다.",
                        LocalDateTime.now().plusDays(7)
                )
        );

        Review review = reviewRepository.save(
                new Review(아루, 산초, 산초리뷰그룹, List.of(keyword1, keyword2), LocalDateTime.now().minusHours(10))
        );
        String answer = "안녕하세요, 산초님. 먼저 리뷰 프로젝트에 대한 헌신과 노력에 감사드립니다. 산초 님의 기여는 팀 전체의 성과에 큰 도움이 되었습니다. 산초 님은 코드의 가독성과 유지보수성을 높이기 위해 항상 깨끗하고 잘 구조화된 코드를 작성하셨습니다. 주석을 통해 코드의 의도를 명확히 설명하는 점도 인상적이었습니다. 또한, 복잡한 문제를 분석하고 효과적으로 해결하는 능력이 뛰어납니다. 특히 리뷰 과정에서 발생한 버그를 신속하게 찾아내고 수정하는 능력은 매우 탁월했습니다. 팀원들과의 원활한 소통을 통해 협업을 촉진하고, 피드백을 적극 수용하는 태도가 매우 좋았습니다. 덕분에 프로젝트 진행이 순조로웠습니다. 개선할 부분으로는 현재 작성하신 테스트가 기본적인 시나리오를 잘 다루고 있으나, 다양한 엣지 케이스와 예외 상황을 다루는 테스트가 부족한 경향이 있습니다. 테스트 커버리지를 넓히면 코드의 안정성을 더욱 높일 수 있습니다. 또한 코드의 효율성 측면에서 개선할 여지가 있습니다. 특히, 리뷰 과정에서 성능이 중요한 부분에서는 알고리즘 최적화나 데이터 처리 방식을 개선하는 방법을 고민해 볼 필요가 있습니다. 마지막으로 코드 자체에 대한 주석은 훌륭하지만, 프로젝트 전반에 대한 문서화가 조금 더 상세했으면 합니다. 이를 통해 새로운 팀원이 프로젝트에 빠르게 적응할 수 있도록 도울 수 있습니다. 최신 기술 동향을 지속적으로 학습하고, 새로운 프레임워크나 도구를 실험해 보는 것도 추천드립니다. 예를 들어, 테스트 자동화 도구나 성능 최적화 기법을 도입하면 더욱 효율적인 개발을 할 수 있을 것입니다. 앞으로도 지속적인 발전을 기대합니다. 감사합니다.";
        String answer2 = "산초는 자바 백엔드 개발자로서, Spring Framework 및 Hibernate를 이용한 RESTful API 개발에 능숙합니다. 또한, 마이크로서비스 아키텍처 설계에 대한 깊은 이해를 바탕으로 Docker 및 Kubernetes를 활용한 컨테이너화에 숙련되어 있습니다. 데이터베이스 최적화와 보안에 관한 전문 지식을 갖추고 있어 복잡한 시스템을 효율적으로 관리합니다. 소프트 스킬 측면에서 산초는 팀 내 커뮤니케이션을 강화하는 데 중점을 두고 있습니다. 그는 명확한 커뮤니케이션과 활발한 피드백을 통해 프로젝트의 투명성과 팀원 간의 신뢰를 높이는 데 기여하고 있습니다. 이러한 능력은 프로젝트 관리와 협업에 있어서 큰 자산이 되며, 팀 내에서 문제 해결자로서의 역할을 탁월하게 수행하고 있습니다.";
        reviewContentRepository.save(new ReviewContent(review, question1, answer));
        reviewContentRepository.save(new ReviewContent(review, question2, answer2));

        Review review2 = reviewRepository.save(
                new Review(커비, 산초, 산초리뷰그룹, List.of(keyword3, keyword4, keyword5), LocalDateTime.now().minusHours(5))
        );
        String answer3 = "산초는 자바 기반의 백엔드 개발에서 뛰어난 실력을 보여줍니다. 특히, 스프링과 하이버네이트를 활용한 복잡한 서버 사이드 애플리케이션을 구축하는데 숙련되어 있으며, 클라우드 인프라와 서비스 운영에도 밝습니다. 그의 기술력은 대규모 데이터 처리와 시스템 통합 프로젝트에서도 두각을 나타내며, 기술적 문제 해결에 강한 역량을 가지고 있습니다.";
        String answer4 = "인간적인 면에서 산초는 팀워크를 중시하는 개발자로, 팀 동료와의 원활한 소통을 위해 끊임없이 노력합니다. 그는 프로젝트의 성공을 위해 동료들과 지속적으로 지식을 공유하며, 협업하는 과정에서 발생하는 문제들을 해결하기 위해 적극적으로 나서는 것을 주저하지 않습니다. 이러한 성향은 프로젝트 진행에 있어 매우 긍정적인 영향을 미치며, 팀 내에서의 그의 역할은 매우 중요합니다.";
        reviewContentRepository.save(new ReviewContent(review2, question1, answer3));
        reviewContentRepository.save(new ReviewContent(review2, question2, answer4));

        Review review3 = reviewRepository.save(
                new Review(테드, 산초, 산초리뷰그룹, List.of(keyword1, keyword2, keyword3, keyword4, keyword5), LocalDateTime.now().minusHours(3))
        );
        String answer5 = "산초는 자바 백엔드 개발 분야에서 두각을 나타내는 개발자이다. 스프링 부트와 JPA를 활용한 효율적인 웹 서비스 구축에 능숙하며, 오픈 소스 도구들을 통합해 개발 프로세스를 최적화하는 데 깊은 지식을 갖고 있다. 또한, 비동기 프로그래밍과 멀티스레딩을 이용하여 고성능 백엔드 시스템을 설계하고 구현하는 능력도 탁월하다.";
        String answer6 = "소프트 스킬 측면에서도 산초는 눈에 띄는 역량을 보여준다. 팀원들과의 의사소통을 원활하게 하며, 갈등 상황에서 중재자 역할을 하여 팀 내 긴장을 완화시키는 데 큰 도움을 준다. 이러한 노력으로 프로젝트의 분위기를 긍정적으로 이끌고, 팀의 동기 부여를 향상시키는 데 중요한 역할을 하고 있다. 그의 리더십과 팀워크는 프로젝트의 성공적인 완수에 결정적인 영향을 미치고 있다.";
        reviewContentRepository.save(new ReviewContent(review3, question1, answer5));
        reviewContentRepository.save(new ReviewContent(review3, question2, answer6));
    }
}
