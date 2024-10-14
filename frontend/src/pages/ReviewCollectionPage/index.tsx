import { useState } from 'react';

import { Accordion, AuthAndServerErrorFallback, Dropdown, ErrorSuspenseContainer, TopButton } from '@/components';
import { DropdownItem } from '@/components/common/Dropdown';
import HighlightEditor from '@/components/highlight/HighlightEditor';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';
import { useReviewInfoData } from '@/components/layouts/ReviewDisplayLayout/hooks';
import { REVIEW_EMPTY } from '@/constants';
import { GroupedReview } from '@/types';
import { substituteString } from '@/utils';

import ReviewEmptySection from '../../components/common/ReviewEmptySection';

import DoughnutChart from './components/DoughnutChart';
import useGetGroupedReviews from './hooks/useGetGroupedReviews';
import useGetSectionList from './hooks/useGetSectionList';
import * as S from './styles';

const ReviewCollectionPage = () => {
  const { data: reviewSectionList } = useGetSectionList();
  const dropdownSectionList = reviewSectionList.sections.map((section) => {
    return { text: section.name, value: section.id };
  });

  const [selectedSection, setSelectedSection] = useState<DropdownItem>(dropdownSectionList[0]);
  const { data: groupedReviews } = useGetGroupedReviews({ sectionId: selectedSection.value as number });

  const { revieweeName, projectName, totalReviewCount } = useReviewInfoData();

  const renderContent = (review: GroupedReview) => {
    if (review.question.type === 'CHECKBOX') {
      const hasNoCheckboxAnswer = review.votes?.every((vote) => vote.count === 0);

      return hasNoCheckboxAnswer ? (
        <ReviewEmptySection content={REVIEW_EMPTY.noReviewInQuestion} />
      ) : (
        <DoughnutChart reviewVotes={review.votes!} />
      );
    }

    if (review.answers?.length === 0) {
      return <ReviewEmptySection content={REVIEW_EMPTY.noReviewInQuestion} />;
    }

    return <HighlightEditor questionId={review.question.id} answerList={review.answers!} />;
  };

  // 전체 받은 리뷰가 없는 경우
  if (totalReviewCount === 0) {
    return (
      <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
        <ReviewDisplayLayout isReviewList={false}>
          <ReviewEmptySection content={REVIEW_EMPTY.noReviewInTotal} />
        </ReviewDisplayLayout>
      </ErrorSuspenseContainer>
    );
  }

  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewDisplayLayout isReviewList={false}>
        <S.ReviewCollectionContainer>
          <S.ReviewSectionDropdown>
            <Dropdown
              items={dropdownSectionList}
              selectedItem={dropdownSectionList.find((section) => section.value === selectedSection.value)!}
              handleSelect={(item) => setSelectedSection(item)}
            />
          </S.ReviewSectionDropdown>
          <S.ReviewCollection>
            {groupedReviews.reviews.map((review, index) => {
              const parsedQuestionName = substituteString({
                content: review.question.name,
                variables: { revieweeName, projectName },
              });

              return (
                <Accordion title={parsedQuestionName} key={index} isInitiallyOpened={index === 0 ? true : false}>
                  {renderContent(review)}
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
