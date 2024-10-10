import * as S from './styles';

export interface OptionSwitchStyleProps {
  $isChecked: boolean;
}

export interface OptionSwitchOption {
  label: string;
  isChecked: boolean;
  handleOptionClick: () => void;
}

interface OptionSwitchProps {
  options: OptionSwitchOption[];
}

const OptionSwitch = ({ options }: OptionSwitchProps) => {
  const handleSwitchClick = (index: number) => {
    const clickedOption = options[index];
    if (clickedOption) clickedOption.handleOptionClick();
  };

  return (
    <S.OptionSwitchContainer>
      {options.map((option, index) => (
        <S.CheckboxWrapper key={option.label} $isChecked={option.isChecked} onClick={() => handleSwitchClick(index)}>
          <S.CheckboxButton type="button" $isChecked={option.isChecked}>
            {option.label}
          </S.CheckboxButton>
        </S.CheckboxWrapper>
      ))}
    </S.OptionSwitchContainer>
  );
};

export default OptionSwitch;
