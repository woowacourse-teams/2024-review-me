import NavigateNextIcon from '@/assets/navigateNext.svg';

import * as S from './styles';

interface Step {
  sectionId: number;
  sectionName: string;
  isDone: boolean;
  isMovingAvailable: boolean;
  isCurrentStep: boolean;
  handleClick: () => void;
}

interface ProgressBarProps {
  stepList: Step[];
}

const ProgressBar = ({ stepList }: ProgressBarProps) => {
  return (
    <S.ProgressBar>
      {stepList.map((step, index) => {
        return (
          <>
            <S.StepButton
              key={index}
              $isDone={step.isDone}
              $isMovingAvailable={step.isMovingAvailable}
              $isCurrentStep={step.isCurrentStep}
              onClick={step.handleClick}
            >
              {step.sectionName}
            </S.StepButton>
            {index < stepList.length - 1 && <img src={NavigateNextIcon} alt="다음 화살표" />}
          </>
        );
      })}
    </S.ProgressBar>
  );
};

export default ProgressBar;
