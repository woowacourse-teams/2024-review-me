import Checkbox, { CheckboxProps } from '../Checkbox';
import PreventDrag from '../PreventDrag';

import * as S from './styles';

interface CheckboxItemProps extends CheckboxProps {
  label: string;
}

const CheckboxItem = ({ label, ...rest }: CheckboxItemProps) => {
  return (
    <S.CheckboxItem>
      <S.CheckboxLabel>
        <PreventDrag>
          <Checkbox {...rest} />
        </PreventDrag>
        {label}
      </S.CheckboxLabel>
    </S.CheckboxItem>
  );
};

export default CheckboxItem;
