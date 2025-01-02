import { Category } from '@/types';
import formattedCategories from '@/utils/formattedCategories';

import * as S from './styles';

interface StrengthKeywordProps {
  categories: Category[];
}

const StrengthKeywordList = ({ categories }: StrengthKeywordProps) => {
  const transformedCategories = formattedCategories(categories);

  return (
    <S.KeywordList>
      {transformedCategories.map((category) => (
        <S.KeywordItem key={category.optionId}>{category.content}</S.KeywordItem>
      ))}
    </S.KeywordList>
  );
};

export default StrengthKeywordList;
