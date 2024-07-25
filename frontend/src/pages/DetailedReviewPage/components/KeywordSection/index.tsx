import ReviewSectionHeader from '../ReviewSectionHeader';

import * as S from './styles';

interface KeywordSectionProps {
  keywords: string[];
  index: number;
}
const KEY_WORD_HEADER = '키워드';

const KeywordSection = ({ keywords, index }: KeywordSectionProps) => {
  return (
    <S.KeywordSection>
      <ReviewSectionHeader number={index + 1} text={KEY_WORD_HEADER} />
      <S.KeywordContainer>
        {keywords.map((keyword) => (
          <S.KeywordBox key={keyword}>{keyword}</S.KeywordBox>
        ))}
      </S.KeywordContainer>
    </S.KeywordSection>
  );
};

export default KeywordSection;
