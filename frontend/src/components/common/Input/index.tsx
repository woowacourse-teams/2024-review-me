import * as S from './styles';

export interface InputStyleProps {
  $style?: React.CSSProperties;
}
interface InputProps extends InputStyleProps {
  value: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  type: string;
  id?: string;
  placeholder?: string;
}

const Input = ({ id, value, onChange, type, placeholder, $style }: InputProps) => {
  return <S.Input id={id} value={value} type={type} onChange={onChange} placeholder={placeholder} style={$style} />;
};

export default Input;
