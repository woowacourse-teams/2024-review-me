import { ReviewWritingAnswer, ReviewWritingCardSection } from '@/types';

import QnABox from '../QnABox';

import CardSliderController from './../CardSliderController/index';
import * as S from './style';

interface ReviewWritingCardProps {
  currentCardIndex: number;
  isLastCard: boolean;
  cardSection: ReviewWritingCardSection;
  isAbleNextStep: boolean;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
  updateAnswerMap: (answer: ReviewWritingAnswer) => void;
}

const ReviewWritingCard = ({
  currentCardIndex,
  isLastCard,
  cardSection,
  isAbleNextStep,
  handleCurrentCardIndex,
  updateAnswerMap,
}: ReviewWritingCardProps) => {
  return (
    <S.ReviewWritingCard>
      <S.Header>{cardSection.header}</S.Header>
      <S.Main>
        {cardSection.questions.map((question) => (
          <QnABox key={question.questionId} question={question} updateAnswerMap={updateAnswerMap} />
        ))}

        <S.ButtonContainer>
          <CardSliderController.PrevButton
            currentCardIndex={currentCardIndex}
            handleCurrentCardIndex={handleCurrentCardIndex}
          />
          {isLastCard ? (
            <>
              <CardSliderController.PreviewButton handlePreviewClick={() => {}} />
              <CardSliderController.SubmitButton handleSubmitButtonClick={() => {}} />
            </>
          ) : (
            <CardSliderController.NextButton
              disabled={!isAbleNextStep}
              handleCurrentCardIndex={handleCurrentCardIndex}
            />
          )}
        </S.ButtonContainer>
      </S.Main>
    </S.ReviewWritingCard>
  );
};

export default ReviewWritingCard;
