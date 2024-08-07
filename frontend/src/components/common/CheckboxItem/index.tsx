import { ChangeEvent } from 'react';

import Checkbox, { CheckboxStyleProps } from '../Checkbox';

import * as S from './styles';

interface CheckboxItemProps extends CheckboxStyleProps {
  id: string;
  label: string;
  isChecked: boolean;
  onChange: (event: ChangeEvent<HTMLInputElement>) => void;
  name?: string;
  isDisabled?: boolean;
}
const CheckboxItem = ({ id, name, label, isChecked, onChange, isDisabled = false, $style }: CheckboxItemProps) => {
  return (
    <S.CheckboxItem>
      <S.CheckboxLabel>
        <Checkbox
          id={id}
          name={name}
          isChecked={isChecked}
          onChange={onChange}
          isDisabled={isDisabled}
          $style={$style}
        />
        {label}
      </S.CheckboxLabel>
    </S.CheckboxItem>
  );
};

export default CheckboxItem;
