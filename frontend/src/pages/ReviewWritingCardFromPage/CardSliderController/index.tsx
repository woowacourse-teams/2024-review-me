import { Button } from '@/components';
import { ButtonStyleType } from '@/types';

interface PrevButtonProps {
  currentCardIndex: number;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const PrevButton = ({ currentCardIndex, handleCurrentCardIndex }: PrevButtonProps) => {
  const styledType: ButtonStyleType = currentCardIndex ? 'secondary' : 'disabled';

  return (
    <Button
      disabled={!!currentCardIndex}
      styleType={styledType}
      type={'button'}
      onClick={() => handleCurrentCardIndex('prev')}
    >
      이전
    </Button>
  );
};

interface NextButtonProps {
  isAbleNextStep: boolean;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const NextButton = ({ isAbleNextStep, handleCurrentCardIndex }: NextButtonProps) => {
  const styledType: ButtonStyleType = isAbleNextStep ? 'primary' : 'disabled';

  return (
    <Button
      disabled={!isAbleNextStep}
      styleType={styledType}
      type={'button'}
      onClick={() => handleCurrentCardIndex('next')}
    >
      다음
    </Button>
  );
};

interface SubmitButtonProps {
  handleSubmitButtonClick: () => void;
}
const SubmitButton = ({ handleSubmitButtonClick }: SubmitButtonProps) => {
  return (
    <Button styleType="primary" type={'button'} onClick={handleSubmitButtonClick}>
      제출
    </Button>
  );
};
interface PreviewButton {
  handlePreviewButtonClick: () => void;
}

const PreviewButton = ({ handlePreviewButtonClick }: PreviewButton) => {
  return (
    <Button styleType="secondary" type={'button'} onClick={handlePreviewButtonClick}>
      미리보기
    </Button>
  );
};

export interface CardSliderControllerProps extends PrevButtonProps, NextButtonProps, SubmitButtonProps, PreviewButton {}

const CardSliderController = () => {
  return <></>;
};

CardSliderController.PrevButton = PrevButton;
CardSliderController.NextButton = NextButton;
CardSliderController.SubmitButton = SubmitButton;
CardSliderController.PreviewButton = PreviewButton;

export default CardSliderController;
