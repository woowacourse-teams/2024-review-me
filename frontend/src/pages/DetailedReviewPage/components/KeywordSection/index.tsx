import { Options } from '@/types';
import formattedCategories from '@/utils/formattedCategories';

import * as S from './styles';

interface KeywordSectionProps {
  options: Options[];
}

const KeywordSection = ({ options }: KeywordSectionProps) => {
  const transformedOptions = formattedCategories(options);

  return (
    <S.KeywordSection>
      <S.KeywordList>
        {transformedOptions.map(({ optionId, content }) => (
          <li key={optionId}>{content}</li>
        ))}
      </S.KeywordList>
    </S.KeywordSection>
  );
};

export default KeywordSection;
