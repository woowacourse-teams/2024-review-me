import { ChangeEvent } from 'react';

import CheckedIcon from '@/assets/checked.svg';
import UncheckedIcon from '@/assets/unchecked.svg';

import * as S from './styles';

export interface CheckboxStyleProps {
  $isReadonly?: boolean;
  $style?: React.CSSProperties;
}

export interface CheckboxProps extends CheckboxStyleProps {
  id: string;
  isChecked: boolean;
  handleChange?: (event: ChangeEvent<HTMLInputElement>, label?: string) => void;
  name?: string;
  isDisabled?: boolean;
}

const Checkbox = ({ id, isChecked, handleChange, isDisabled, $style, $isReadonly = false, ...rest }: CheckboxProps) => {
  return (
    <S.CheckboxContainer $style={$style} $isReadonly={$isReadonly}>
      <S.CheckboxLabel>
        <input
          id={id}
          data-testid={`checkbox-${id}`}
          checked={isChecked}
          disabled={isDisabled}
          type="checkbox"
          onChange={handleChange}
          {...rest}
        />
        <img src={isChecked ? CheckedIcon : UncheckedIcon} alt="체크박스" />
      </S.CheckboxLabel>
    </S.CheckboxContainer>
  );
};

export default Checkbox;
