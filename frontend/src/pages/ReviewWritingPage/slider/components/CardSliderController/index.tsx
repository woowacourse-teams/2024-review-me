import { Button } from '@/components';
import { ButtonStyleType } from '@/types';

interface PrevButtonProps extends React.HTMLAttributes<HTMLButtonElement> {
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const PrevButton = ({ handleCurrentCardIndex, ...rest }: PrevButtonProps) => {
  return (
    <Button styleType={'secondary'} type={'button'} onClick={() => handleCurrentCardIndex('prev')} {...rest}>
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
      aria-label={isAbleNextStep ? '활성화된 다음' : '비활성화된 다음'}
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
      aria-label="제출 버튼, 클릭 시 작성한 내용을 제출합니다"
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
      aria-label="작성 내용 확인 버튼, 클릭 시 작성한 내용을 한눈에 볼 수 있는 모달이 열립니다"
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
