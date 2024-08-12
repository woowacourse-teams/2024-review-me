import { Options } from '@/types';

// import ReviewSectionHeader from '../ReviewSectionHeader';

import * as S from './styles';

interface KeywordSectionProps {
  options: Options[];
}

const KeywordSection = ({ options }: KeywordSectionProps) => {
  return (
    <S.KeywordSection>
      <S.KeywordContainer>
        {options.map(({ optionId, content }) => (
          <S.KeywordBox key={optionId}>{content}</S.KeywordBox>
        ))}
      </S.KeywordContainer>
    </S.KeywordSection>
  );
};

export default KeywordSection;
