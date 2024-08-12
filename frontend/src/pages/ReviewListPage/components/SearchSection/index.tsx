import { Button, DropDown, SearchInput } from '@/components/common';

import * as S from './styles';

interface SearchSectionProps {
  handleChange: (value: string) => void;
  options: string[];
  placeholder: string;
}

const SearchSection = ({ handleChange, options, placeholder }: SearchSectionProps) => {
  return (
    <S.Container>
      <S.SearchBox>
        <SearchInput $width="42rem" $height="100%" placeholder={placeholder} />
        <Button styleType="secondary">검색</Button>
      </S.SearchBox>
      <DropDown onChange={handleChange} options={options} />
    </S.Container>
  );
};

export default SearchSection;
