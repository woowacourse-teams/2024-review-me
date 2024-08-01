import * as S from './styles';

interface InputProps {
  value: string;
  onChange: (value: string) => void;
  type: string;
  id?: string;
  placeholder?: string;
  $width?: string;
  $height?: string;
}

const Input = ({ id, value, onChange, type, placeholder, $width, $height, ...rest }: InputProps) => {
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
      $width={$width}
      $height={$height}
      {...rest}
    />
  );
};

export default Input;
