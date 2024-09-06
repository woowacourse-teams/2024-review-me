import { Button } from '@/components';
import { ButtonStyleType } from '@/types';

interface PrevButtonProps extends React.HTMLAttributes<HTMLButtonElement> {
  currentCardIndex: number;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const PrevButton = ({ currentCardIndex, handleCurrentCardIndex, ...rest }: PrevButtonProps) => {
  const styledType: ButtonStyleType = currentCardIndex ? 'secondary' : 'disabled';

  return (
    <Button
      disabled={!currentCardIndex}
      styleType={styledType}
      type={'button'}
      onClick={() => handleCurrentCardIndex('prev')}
      {...rest}
    >
      이전
    </Button>
  );
};

interface NextButtonProps extends React.HTMLAttributes<HTMLButtonElement> {
  isAbleNextStep: boolean;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const NextButton = ({ isAbleNextStep, handleCurrentCardIndex, ...rest }: NextButtonProps) => {
  const styledType: ButtonStyleType = isAbleNextStep ? 'primary' : 'disabled';

  return (
    <Button
      disabled={!isAbleNextStep}
      styleType={styledType}
      type={'button'}
      onClick={() => handleCurrentCardIndex('next')}
      {...rest}
    >
      다음
    </Button>
  );
};

interface ConfirmModalOpenButtonProps extends React.HTMLAttributes<HTMLButtonElement> {
  isAbleNextStep: boolean;
  handleSubmitConfirmModalOpenButtonClick: () => void;
}
const ConfirmModalOpenButton = ({
  isAbleNextStep,
  handleSubmitConfirmModalOpenButtonClick,
  ...rest
}: ConfirmModalOpenButtonProps) => {
  const styleType: ButtonStyleType = isAbleNextStep ? 'primary' : 'disabled';
  return (
    <Button
      disabled={!isAbleNextStep}
      styleType={styleType}
      type={'button'}
      onClick={handleSubmitConfirmModalOpenButtonClick}
      {...rest}
    >
      제출
    </Button>
  );
};
interface RecheckButton extends React.HTMLAttributes<HTMLButtonElement> {
  isAbleNextStep: boolean;
  handleRecheckButtonClick: () => void;
}

const RecheckButton = ({ isAbleNextStep, handleRecheckButtonClick, ...rest }: RecheckButton) => {
  const styledType: ButtonStyleType = isAbleNextStep ? 'secondary' : 'disabled';
  return (
    <Button
      disabled={!isAbleNextStep}
      styleType={styledType}
      type={'button'}
      onClick={handleRecheckButtonClick}
      {...rest}
    >
      작성 내용 확인
    </Button>
  );
};

export interface CardSliderControllerProps
  extends PrevButtonProps,
    NextButtonProps,
    ConfirmModalOpenButtonProps,
    RecheckButton {}

const CardSliderController = () => {
  return <></>;
};

CardSliderController.PrevButton = PrevButton;
CardSliderController.NextButton = NextButton;
CardSliderController.ConfirmModalOpenButton = ConfirmModalOpenButton;
CardSliderController.RecheckButton = RecheckButton;

export default CardSliderController;
