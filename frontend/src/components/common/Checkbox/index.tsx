import { ChangeEvent } from 'react';

import CheckedIcon from '@/assets/checked.svg';
import UncheckedIcon from '@/assets/unchecked.svg';

import * as S from './styles';

// NOTE: 공통 컴포넌트에서 이 스타일 속성을 계속 쓰는 것 같은데 이걸 아예 공통 타입으로 빼버릴지 고민
export interface CheckboxStyleProps {
  $style?: React.CSSProperties;
}

interface CheckboxProps extends CheckboxStyleProps {
  id: string;
  isChecked: boolean;
  onChange: (event: ChangeEvent<HTMLInputElement>) => void;
  name?: string;
  isDisabled?: boolean;
}

const Checkbox = ({ id, name, isChecked, isDisabled, onChange, $style }: CheckboxProps) => {
  return (
    <S.CheckboxContainer style={$style}>
      <S.CheckboxLabel>
        <input
          id={id}
          name={name}
          checked={isChecked}
          onChange={onChange}
          disabled={isDisabled}
          aria-hidden={false}
          type="checkbox"
        />
        <img src={isChecked ? CheckedIcon : UncheckedIcon} alt="체크박스" />
      </S.CheckboxLabel>
    </S.CheckboxContainer>
  );
};

export default Checkbox;
