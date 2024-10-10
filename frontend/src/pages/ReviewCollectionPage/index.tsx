import { useState } from 'react';

import { Accordion, AuthAndServerErrorFallback, Dropdown, ErrorSuspenseContainer, TopButton } from '@/components';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';
import { useGetReviewList } from '@/hooks';
import { GROUPED_REVIEWS_MOCK_DATA, GROUPED_SECTION_MOCK_DATA } from '@/mocks/mockData/reviewCollection';

import DoughnutChart from './components/DoughnutChart';
import * as S from './styles';

const ReviewCollectionPage = () => {
  // TODO: 추후 리뷰 그룹 정보를 받아오는 API로 대체
  const { data } = useGetReviewList();
  const { revieweeName, projectName } = data.pages[0];

  // TODO: react-query 적용 및 드롭다운 아이템 선택 시 요청
  const reviewSectionList = GROUPED_SECTION_MOCK_DATA.sections.map((section) => {
    return { text: section.name, value: section.name };
  });
  const [reviewSection, setReviewSection] = useState(reviewSectionList[0].value);

  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewDisplayLayout projectName={projectName} revieweeName={revieweeName} isReviewList={false}>
        <S.ReviewCollectionContainer>
          <S.ReviewSectionDropdown>
            <Dropdown
              items={reviewSectionList}
              selectedItem={reviewSection}
              handleSelect={(item) => setReviewSection(item)}
            />
          </S.ReviewSectionDropdown>
          <S.ReviewCollection>
            {GROUPED_REVIEWS_MOCK_DATA.reviews.map((review, index) => {
              return (
                <Accordion title={review.question.name} key={index} isInitiallyOpened={index === 0 ? true : false}>
                  {review.question.type === 'CHECKBOX' ? (
                    <DoughnutChart reviewVotes={review.votes!} />
                  ) : (
                    <S.ReviewAnswerContainer>
                      {review.answers?.map((answer, index) => {
                        return <S.ReviewAnswer key={index}>{answer.content}</S.ReviewAnswer>;
                      })}
                    </S.ReviewAnswerContainer>
                  )}
                </Accordion>
              );
            })}
          </S.ReviewCollection>
        </S.ReviewCollectionContainer>
      </ReviewDisplayLayout>
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default ReviewCollectionPage;
