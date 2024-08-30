import { useRecoilValue } from 'recoil';

import { Carousel } from '@/components';
import { Direction } from '@/hooks/review/writingCardForm/useCurrentCardIndex';
import { cardSectionListSelector } from '@/recoil';
import { ReviewWritingCardSection } from '@/types';

import { CARD_FORM_MODAL_KEY } from '../CardForm';
import CardSliderController from '../CardSliderController';
import ReviewWritingCard from '../ReviewWritingCard';

import { useMovingStepAvailability, useSlideWidthAndHeight } from './hooks';
import * as S from './style';

interface CardSliderProps {
  currentCardIndex: number;
  cardSectionList: ReviewWritingCardSection[];
  handleCurrentCardIndex: (direction: Direction) => void;
  handleOpenModal: (key: keyof typeof CARD_FORM_MODAL_KEY) => void;
}

const CardSlider = ({ currentCardIndex, handleCurrentCardIndex, handleOpenModal }: CardSliderProps) => {
  const cardSectionList = useRecoilValue(cardSectionListSelector);

  const { wrapperRef, slideHeight, slideWidth, makeId } = useSlideWidthAndHeight({ currentCardIndex });

  const { isAblePrevStep, isAbleNextStep, isLastCard } = useMovingStepAvailability({ currentCardIndex });

  const handleNextClick = () => {
    const nextIndex = currentCardIndex + 1;
    if (nextIndex < cardSectionList.length) {
      handleCurrentCardIndex('next');
    }
  };

  const handleRecheckButtonClick = () => {
    handleOpenModal('recheck');
  };

  const handleSubmitConfirmModalOpenButtonClick = () => {
    handleOpenModal('submitConfirm');
  };

  return (
    <Carousel ref={wrapperRef} translateX={currentCardIndex * slideWidth} height={slideHeight}>
      {cardSectionList?.map((section, index) => (
        <S.Slide id={makeId(index)} key={section.sectionId}>
          <ReviewWritingCard cardSection={section} />
          <S.ButtonContainer>
            {isAblePrevStep(index) && (
              <CardSliderController.PrevButton
                currentCardIndex={currentCardIndex}
                handleCurrentCardIndex={handleCurrentCardIndex}
              />
            )}
            {isLastCard() ? (
              <>
                <CardSliderController.RecheckButton
                  isAbleNextStep={isAbleNextStep}
                  handleRecheckButtonClick={handleRecheckButtonClick}
                />
                <CardSliderController.ConfirmModalOpenButton
                  isAbleNextStep={isAbleNextStep}
                  handleSubmitConfirmModalOpenButtonClick={handleSubmitConfirmModalOpenButtonClick}
                />
              </>
            ) : (
              <CardSliderController.NextButton
                isAbleNextStep={isAbleNextStep}
                handleCurrentCardIndex={handleNextClick}
              />
            )}
          </S.ButtonContainer>
        </S.Slide>
      ))}
    </Carousel>
  );
};

export default CardSlider;
