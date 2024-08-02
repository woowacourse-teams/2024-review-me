import { Keyword } from '@/types';

import ReviewSectionHeader from '../ReviewSectionHeader';

import * as S from './styles';

interface KeywordSectionProps {
  keywords: Keyword[];
  index: number;
}
const KEY_WORD_HEADER = '키워드';

const KeywordSection = ({ keywords, index }: KeywordSectionProps) => {
  return (
    <S.KeywordSection>
      <ReviewSectionHeader number={index + 1} text={KEY_WORD_HEADER} />
      <S.KeywordContainer>
        {keywords.map(({ id, content }) => (
          <S.KeywordBox key={id}>{content}</S.KeywordBox>
        ))}
      </S.KeywordContainer>
    </S.KeywordSection>
  );
};

export default KeywordSection;
