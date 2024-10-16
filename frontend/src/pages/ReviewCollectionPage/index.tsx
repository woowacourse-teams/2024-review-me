import { useState } from 'react';

import {
  Accordion,
  AuthAndServerErrorFallback,
  Dropdown,
  ErrorSuspenseContainer,
  TopButton,
  HighlightEditorContainer,
} from '@/components';
import { DropdownItem } from '@/components/common/Dropdown';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';

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

  groupedReviews.reviews.forEach((review) => {
    review.votes?.sort((voteA, voteB) => voteB.count - voteA.count);
  });

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
              return (
                <Accordion title={review.question.name} key={index} isInitiallyOpened={index === 0 ? true : false}>
                  {review.question.type === 'CHECKBOX' ? (
                    <DoughnutChart reviewVotes={review.votes!} />
                  ) : (
                    <S.ReviewAnswerContainer>
                      {review.answers && (
                        <HighlightEditorContainer questionId={review.question.id} answerList={review.answers} />
                      )}
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
