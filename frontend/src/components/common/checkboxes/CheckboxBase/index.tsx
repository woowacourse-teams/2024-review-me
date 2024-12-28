import CheckedIcon from '@/assets/checked.svg';
import UncheckedIcon from '@/assets/unchecked.svg';
import UndraggableWrapper from '@/components/common/UndraggableWrapper';

import { CheckboxProps } from '../Checkbox';

import * as S from './styles';

export interface CheckboxStyleProps {
  $isReadonly?: boolean;
  $style?: React.CSSProperties;
}

export interface CheckboxA11yProps {
  isTabAccessible?: boolean; // CheckboxItem을 사용할 때 Checkbox 중복 포커싱 방지용
  tabIndex?: number;
}
export interface CheckboxBaseProps extends CheckboxStyleProps, CheckboxA11yProps {
  id: string;
  isChecked: boolean;
  isDisabled?: boolean;
  name?: string;
}

interface FinalCheckboxBaseProps extends CheckboxBaseProps, CheckboxProps {}

const CheckboxBase = ({
  id,
  isChecked,
  isDisabled,
  tabIndex,
  handleChange,
  $isReadonly = false,
  $style,
  ...rest
}: FinalCheckboxBaseProps) => {
  return (
    <UndraggableWrapper>
      <S.CheckboxContainer $style={$style} $isReadonly={$isReadonly}>
        <S.CheckboxLabel>
          <input
            id={id}
            data-testid={`checkbox-${id}`}
            checked={isChecked}
            disabled={isDisabled}
            onChange={handleChange}
            type="checkbox"
            tabIndex={-1}
            {...rest}
          />
          <img
            src={isChecked ? CheckedIcon : UncheckedIcon}
            tabIndex={tabIndex}
            role="checkbox"
            aria-checked={isChecked}
            alt=""
          />
          {$isReadonly && <span className="sr-only">{isChecked ? '선택됨' : '선택 안 됨'}</span>}
        </S.CheckboxLabel>
      </S.CheckboxContainer>
    </UndraggableWrapper>
  );
};

export default CheckboxBase;
