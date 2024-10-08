import * as S from './styles';

export interface OptionSwitchStyleProps {
  $isChecked: boolean;
}

interface OptionSwitchProps extends OptionSwitchStyleProps {
  leftLabel: string;
  rightLabel: string;
  isReviewList: boolean;
  handleSwitchClick: () => void;
}

const OptionSwitch = ({ leftLabel, rightLabel, isReviewList, handleSwitchClick }: OptionSwitchProps) => {
  return (
    <S.OptionSwitchContainer onClick={handleSwitchClick}>
      <S.CheckboxWrapper $isChecked={isReviewList}>
        <S.CheckboxLabel $isChecked={isReviewList}>{leftLabel}</S.CheckboxLabel>
      </S.CheckboxWrapper>

      <S.CheckboxWrapper $isChecked={!isReviewList}>
        <S.CheckboxLabel $isChecked={!isReviewList}>{rightLabel}</S.CheckboxLabel>
      </S.CheckboxWrapper>
    </S.OptionSwitchContainer>
  );
};

export default OptionSwitch;
