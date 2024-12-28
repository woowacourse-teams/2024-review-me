import { ChangeEvent } from 'react';

import { CheckboxBaseProps } from '../CheckboxBase';
import { CheckboxBase } from '../index';

export interface CheckboxProps extends CheckboxBaseProps {
  handleChange?: (event: ChangeEvent<HTMLInputElement>, label?: string) => void;
}

export const Checkbox = ({ isDisabled, isChecked, isTabAccessible = true, handleChange, ...rest }: CheckboxProps) => {
  return (
    <CheckboxBase
      isDisabled={isDisabled}
      isChecked={isChecked}
      tabIndex={isDisabled || isTabAccessible ? 0 : -1}
      handleChange={handleChange}
      {...rest}
    />
  );
};

export default Checkbox;
