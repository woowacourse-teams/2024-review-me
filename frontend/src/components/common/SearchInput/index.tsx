import * as S from './styles';

interface SearchInputProps {
  placeholder: string;
  $width: string;
  $height: string;
}

const SearchInput = ({ placeholder, $width, $height }: SearchInputProps) => {
  return <S.Input type="text" placeholder={placeholder} $width={$width} $height={$height} />;
};

export default SearchInput;
