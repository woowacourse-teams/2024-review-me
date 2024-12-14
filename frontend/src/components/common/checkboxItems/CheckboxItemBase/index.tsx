import { EssentialPropsWithChildren } from '@/types';

import { CheckboxItemProps } from '../CheckboxItem';

import * as S from './styles';

export interface CheckboxItemBaseProps {
  id: string;
  label: string;
  isChecked: boolean;
  name?: string;
  tabIndex?: number;
}

interface FinalCheckboxItemBaseProps extends CheckboxItemBaseProps, CheckboxItemProps{};

const CheckboxItemBase = ({
  label,
  isChecked,
  tabIndex = 0,
  handleKeyDown,
  children,
  ...rest
}: EssentialPropsWithChildren<FinalCheckboxItemBaseProps>) => {
  const isCheckedLabel = `${label}, ${isChecked ? '선택됨' : '선택 안 됨'}`;

  return (
    <S.CheckboxItem
      className="checkbox-item"
      tabIndex={tabIndex}
      aria-label={isCheckedLabel}
      onKeyDown={handleKeyDown}
      {...rest}
    >
      <S.CheckboxLabel>
        {children}
        {label}
      </S.CheckboxLabel>
    </S.CheckboxItem>
  );
};

export default CheckboxItemBase;
