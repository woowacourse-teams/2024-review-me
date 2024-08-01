import { Button, DropDown, SearchInput } from '@/components/common';

import * as S from './styles';

interface SearchSectionProps {
  onChange: (value: string) => void;
  options: string[];
  placeholder: string;
}

const SearchSection = ({ onChange, options, placeholder }: SearchSectionProps) => {
  return (
    <S.Container>
      <S.SearchBox>
        <SearchInput $width="48rem" $height="100%" placeholder={placeholder} />
        <Button styleType="secondary" text="검색" />
      </S.SearchBox>
      <DropDown onChange={onChange} options={options} />
    </S.Container>
  );
};

export default SearchSection;
