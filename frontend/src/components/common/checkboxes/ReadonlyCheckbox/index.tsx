import { CheckboxProps } from '../Checkbox';
import { CheckboxBase } from '../index';

interface ReadonlyCheckboxProps extends Omit<CheckboxProps, 'isDisabled' | 'handleChange'> {}

const ReadonlyCheckbox = ({ isChecked, ...rest }: ReadonlyCheckboxProps) => {
  return (
    <CheckboxBase
      isDisabled={true}
      isChecked={isChecked}
      tabIndex={-1}
      aria-readonly={true}
      $isReadonly={true}
      {...rest}
    />
  );
};

export default ReadonlyCheckbox;
