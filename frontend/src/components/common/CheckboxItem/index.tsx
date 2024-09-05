import Checkbox, { CheckboxProps } from '../Checkbox';
import UndraggableWrapper from '../UndraggableWrapper';

import * as S from './styles';

interface CheckboxItemProps extends CheckboxProps {
  label: string;
}

const CheckboxItem = ({ label, ...rest }: CheckboxItemProps) => {
  return (
    <S.CheckboxItem>
      <S.CheckboxLabel>
        <UndraggableWrapper>
          <Checkbox {...rest} />
        </UndraggableWrapper>
        {label}
      </S.CheckboxLabel>
    </S.CheckboxItem>
  );
};

export default CheckboxItem;
