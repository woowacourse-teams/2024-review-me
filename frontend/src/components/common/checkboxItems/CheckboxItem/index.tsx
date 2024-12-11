import React, { ChangeEvent } from 'react';

import { Checkbox } from '../../checkboxes';
import CheckboxItemBase, { CheckboxItemBaseProps } from '../CheckboxItemBase';

export interface CheckboxItemProps extends CheckboxItemBaseProps {
  isDisabled?: boolean;
  handleChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  handleKeyDown?: (event: React.KeyboardEvent<HTMLDivElement>) => void;
}

const CheckboxItem = ({ id, label, isChecked, isDisabled = false, handleChange, ...rest }: CheckboxItemProps) => {
  const handleKeyDown = (event: React.KeyboardEvent<HTMLDivElement>) => {
    if (event.key === 'Enter' && handleChange) {
      handleChange({
        currentTarget: {
          id: id,
          checked: !isChecked,
        } as Partial<HTMLInputElement>,
      } as ChangeEvent<HTMLInputElement>);
    }
  };

  return (
    <CheckboxItemBase
      id={id}
      isChecked={isChecked}
      tabIndex={isDisabled ? -1 : 0}
      isDisabled={isDisabled}
      label={label}
      handleKeyDown={handleKeyDown}
    >
      <Checkbox
        id={id}
        isChecked={isChecked}
        isDisabled={isDisabled}
        tabIndex={-1}
        handleChange={handleChange}
        {...rest}
      />
    </CheckboxItemBase>
  );
};

export default CheckboxItem;
