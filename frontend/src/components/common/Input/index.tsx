import * as S from './styles';

export interface InputStyleProps {
  $style?: React.CSSProperties;
}
interface InputProps extends InputStyleProps {
  value: string;
  onChange: (value: string) => void;
  type: string;
  id?: string;
  placeholder?: string;
}

const Input = ({ id, value, onChange, type, placeholder, $style, ...rest }: InputProps) => {
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    onChange(event.target.value);
  };

  return (
    <S.Input
      id={id}
      value={value}
      type={type}
      onChange={handleChange}
      placeholder={placeholder}
      $style={$style}
      {...rest}
    />
  );
};

export default Input;
