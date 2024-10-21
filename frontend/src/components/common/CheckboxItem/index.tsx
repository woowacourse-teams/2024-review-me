import { ChangeEvent } from 'react';

import Checkbox, { CheckboxProps } from '../Checkbox';
import UndraggableWrapper from '../UndraggableWrapper';

import * as S from './styles';

interface CheckboxItemProps extends CheckboxProps {
  label: string;
}

const CheckboxItem = ({
  id,
  label,
  isChecked,
  handleChange,
  $isReadonly,
  isTabAccessible = false,
  ...rest
}: CheckboxItemProps) => {
  const handleKeyDown = (event: React.KeyboardEvent<HTMLDivElement>) => {
    if (event.key === 'Enter' && handleChange) {
      handleChange({
        currentTarget: {
          id: id,
          checked: !isChecked,
        },
      } as unknown as ChangeEvent<HTMLInputElement>);
    }
  };

  return (
    <S.CheckboxItem tabIndex={$isReadonly ? -1 : 0} onKeyDown={handleKeyDown}>
      <S.CheckboxLabel>
        <UndraggableWrapper>
          <Checkbox id={id} isChecked={isChecked} isTabAccessible={isTabAccessible} handleChange={handleChange} {...rest} />
        </UndraggableWrapper>
        {label}
      </S.CheckboxLabel>
    </S.CheckboxItem>
  );
};

export default CheckboxItem;
