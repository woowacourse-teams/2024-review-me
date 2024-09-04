import { ChangeEvent } from 'react';

import CheckedIcon from '@/assets/checked.svg';
import UncheckedIcon from '@/assets/unchecked.svg';

import * as S from './styles';

// NOTE: 공통 컴포넌트에서 이 스타일 속성을 계속 쓰는 것 같은데 이걸 아예 공통 타입으로 빼버릴지 고민
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
        <img src={isChecked ? CheckedIcon : UncheckedIcon} alt="체크박스" className="prevent-drag" />
      </S.CheckboxLabel>
    </S.CheckboxContainer>
  );
};

export default Checkbox;
