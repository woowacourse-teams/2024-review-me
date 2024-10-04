import * as S from './styles';

interface SwitchProps {
  leftLabel: string;
  rightLabel: string;
  isLeftChecked: boolean;
  handleSwitchClick: () => void;
}

const Switch = ({ leftLabel, rightLabel, isLeftChecked, handleSwitchClick }: SwitchProps) => {
  return (
    <S.SwitchContainer onClick={handleSwitchClick}>
      <S.CheckboxWrapper isChecked={isLeftChecked}>
        <S.CheckboxLabel isChecked={isLeftChecked}>{leftLabel}</S.CheckboxLabel>
      </S.CheckboxWrapper>

      <S.CheckboxWrapper isChecked={!isLeftChecked}>
        <S.CheckboxLabel isChecked={!isLeftChecked}>{rightLabel}</S.CheckboxLabel>
      </S.CheckboxWrapper>
    </S.SwitchContainer>
  );
};

export default Switch;
