import { useContext, useEffect, useState } from 'react';

import { Accordion, Dropdown, EmptyContent, HighlightEditorContainer } from '@/components';
import { DropdownItem } from '@/components/common/Dropdown';
import { ReviewInfoDataContext } from '@/components/layouts/ReviewDisplayLayout/ReviewInfoDataProvider';
import { REVIEW_EMPTY, SESSION_STORAGE_KEY } from '@/constants';
import { useReviewRequestCodeParam } from '@/hooks';
import { GroupedReview } from '@/types';
import { substituteString } from '@/utils';

import useGetGroupedReviews from '../../hooks/useGetGroupedReviews';
import useGetSectionList from '../../hooks/useGetSectionList';
import DoughnutChart from '../DoughnutChart';

import * as S from './styles';

const ReviewCollectionPageContents = () => {
  const { reviewRequestCode } = useReviewRequestCodeParam();

  const { revieweeName, projectName, totalReviewCount } = useContext(ReviewInfoDataContext);

  const { data: reviewSectionList } = useGetSectionList();
  const dropdownSectionList = reviewSectionList.sections.map((section) => {
    return { text: section.name, value: section.id };
  });

  const [selectedSection, setSelectedSection] = useState<DropdownItem>(dropdownSectionList[0]);

  const { data: groupedReviews } = useGetGroupedReviews({
    reviewRequestCode: reviewRequestCode,
    sectionId: selectedSection.value as number,
  });

  groupedReviews.reviews.forEach((review) => {
    review.votes?.sort((voteA, voteB) => voteB.count - voteA.count);
  });

  useEffect(() => {
    return () => {
      sessionStorage.removeItem(SESSION_STORAGE_KEY.currentReviewCollectionSectionId);
    };
  }, []);

  const renderContent = (review: GroupedReview) => {
    if (review.question.type === 'CHECKBOX') {
      const hasNoCheckboxAnswer = review.votes?.every((vote) => vote.count === 0);

      return hasNoCheckboxAnswer ? (
        <EmptyContent iconWidth="18rem" messageFontSize="1.8rem" iconMessageGap="2.6rem">
          {REVIEW_EMPTY.noReviewInQuestion}
        </EmptyContent>
      ) : (
        <DoughnutChart reviewVotes={review.votes!} />
      );
    }

    if (review.question.type === 'TEXT') {
      const hasNoTextAnswer = !review.answers || review.answers.length === 0;

      return hasNoTextAnswer ? (
        <EmptyContent iconWidth="18rem" messageFontSize="1.8rem" iconMessageGap="2.6rem">
          {REVIEW_EMPTY.noReviewInQuestion}
        </EmptyContent>
      ) : (
        <HighlightEditorContainer questionId={review.question.id} answerList={review.answers!} />
      );
    }

    return null;
  };

  // 받은 리뷰가 아무것도 없는 경우
  if (totalReviewCount === 0) {
    return (
      <EmptyContent iconWidth="17rem" messageFontSize="2rem" iconMessageGap="2.6rem" isBorder={true}>
        {REVIEW_EMPTY.noReviewInTotal}
      </EmptyContent>
    );
  }

  return (
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
            <Accordion title={parsedQuestionName} key={`${selectedSection.value}-${index}`} isInitiallyOpened={false}>
              {renderContent(review)}
            </Accordion>
          );
        })}
      </S.ReviewCollection>
    </S.ReviewCollectionContainer>
  );
};

export default ReviewCollectionPageContents;
