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
        <img
          src={isChecked ? CheckedIcon : UncheckedIcon}
          role="checkbox"
          aria-checked={isChecked}
          aria-readonly={$isReadonly}
          alt=""
        />
        {$isReadonly && <span className="sr-only">{isChecked ? '선택됨' : '선택 안 됨'}</span>}
      </S.CheckboxLabel>
    </S.CheckboxContainer>
  );
};

export default Checkbox;
