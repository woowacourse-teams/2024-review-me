import { ReadonlyCheckbox } from '../../checkboxes';
import CheckboxItemBase, { CheckboxItemBaseProps } from '../CheckboxItemBase';

export interface ReadonlyCheckboxItemProps
  extends Omit<CheckboxItemBaseProps, 'tabIndex' | 'isDisabled' | 'handleChange' | 'handleKeyDown'> {}

const ReadonlyCheckboxItem = ({ id, label, isChecked, ...rest }: ReadonlyCheckboxItemProps) => {
  return (
    <CheckboxItemBase id={id} isChecked={isChecked} tabIndex={-1} label={label} >
      <ReadonlyCheckbox id={id} isChecked={isChecked} tabIndex={-1} $isReadonly={true} {...rest}/>
    </CheckboxItemBase>
  );
};

export default ReadonlyCheckboxItem;
