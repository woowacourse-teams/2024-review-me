import React, { useContext, useState } from 'react';

import { Accordion, Dropdown, HighlightEditorContainer } from '@/components';
import { DropdownItem } from '@/components/common/Dropdown';
import ReviewEmptySection from '@/components/common/ReviewEmptySection';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';
import { ReviewInfoDataContext } from '@/components/layouts/ReviewDisplayLayout/ReviewInfoDataProvider';
import { REVIEW_EMPTY } from '@/constants';
import { GroupedReview } from '@/types';
import { substituteString } from '@/utils';

import useGetGroupedReviews from '../../hooks/useGetGroupedReviews';
import useGetSectionList from '../../hooks/useGetSectionList';
import DoughnutChart from '../DoughnutChart';

import * as S from './styles';

const ReviewCollectionPageContents = () => {
  const { revieweeName, projectName, totalReviewCount } = useContext(ReviewInfoDataContext);

  const { data: reviewSectionList } = useGetSectionList();
  const dropdownSectionList = reviewSectionList.sections.map((section) => {
    return { text: section.name, value: section.id };
  });

  const [selectedSection, setSelectedSection] = useState<DropdownItem>(dropdownSectionList[0]);
  const { data: groupedReviews } = useGetGroupedReviews({ sectionId: selectedSection.value as number });

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

    return <HighlightEditorContainer questionId={review.question.id} answerList={review.answers!} />;
  };

  if (totalReviewCount === 0) {
    <ReviewDisplayLayout isReviewList={false}>
      <ReviewEmptySection content={REVIEW_EMPTY.noReviewInTotal} />
    </ReviewDisplayLayout>;
  }

  return (
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
              <Accordion
                title={parsedQuestionName}
                key={`${selectedSection.value}-${index}`}
                isInitiallyOpened={index === 0}
              >
                {renderContent(review)}
              </Accordion>
            );
          })}
        </S.ReviewCollection>
      </S.ReviewCollectionContainer>
    </ReviewDisplayLayout>
  );
};

export default ReviewCollectionPageContents;
