import * as S from './styles';

interface DropDownProps {
  onChange: (value: string) => void;
  options: string[];
}

const DropDown = ({ onChange, options }: DropDownProps) => {
  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    onChange(e.target.value);
  };

  return (
    <S.Container onChange={handleChange}>
      {options.map((option) => (
        <option key={option} value={option}>
          {option}
        </option>
      ))}
    </S.Container>
  );
};

export default DropDown;
