import * as S from './styles';

export interface InputStyleProps {
  $style?: React.CSSProperties;
}

interface InputProps extends InputStyleProps {
  type: React.InputHTMLAttributes<HTMLInputElement>['type'];
  value: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onBlur?: () => void;
  id?: string;
  name?: string;
  placeholder?: string;
}

const Input = ({ id, value, name, onChange, onBlur, type, placeholder, $style }: InputProps) => {
  return (
    <S.Input
      id={id}
      value={value}
      type={type}
      name={name}
      onChange={onChange}
      onBlur={onBlur}
      placeholder={placeholder}
      style={$style}
    />
  );
};

export default Input;
