import { Options } from '@/types';

import * as S from './styles';

interface KeywordSectionProps {
  options: Options[];
}

const KeywordSection = ({ options }: KeywordSectionProps) => {
  return (
    <S.KeywordSection>
      <S.KeywordList>
        {options.map(({ optionId, content }) => (
          <S.KeywordItem key={optionId}>{content}</S.KeywordItem>
        ))}
      </S.KeywordList>
    </S.KeywordSection>
  );
};

export default KeywordSection;
