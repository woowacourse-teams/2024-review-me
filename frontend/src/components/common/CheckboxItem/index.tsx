import Checkbox, { CheckboxProps } from '../Checkbox';

import * as S from './styles';

interface CheckboxItemProps extends CheckboxProps {
  label: string;
}

const CheckboxItem = ({ label, ...rest }: CheckboxItemProps) => {
  return (
    <S.CheckboxItem>
      <S.CheckboxLabel>
        <Checkbox {...rest} />
        {label}
      </S.CheckboxLabel>
    </S.CheckboxItem>
  );
};

export default CheckboxItem;
