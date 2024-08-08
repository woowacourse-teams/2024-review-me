import * as S from './styles';

export interface InputStyleProps {
  $style?: React.CSSProperties;
}
interface InputProps extends InputStyleProps {
  value: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  type: string;
  id?: string;
  name?: string;
  placeholder?: string;
}

const Input = ({ id, value, name, onChange, type, placeholder, $style }: InputProps) => {
  return (
    <S.Input
      id={id}
      value={value}
      type={type}
      name={name}
      onChange={onChange}
      placeholder={placeholder}
      style={$style}
    />
  );
};

export default Input;
