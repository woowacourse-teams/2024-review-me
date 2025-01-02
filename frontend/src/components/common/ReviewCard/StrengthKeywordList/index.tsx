import { Category } from '@/types';

import * as S from './styles';

interface StrengthKeywordProps {
  categories: Category[];
}

const StrengthKeywordList = ({ categories }: StrengthKeywordProps) => {
  const formattedCategories = categories.map((category) => {
    // 카테고리 내용에서 '(예: ' 앞부분만 추출
    const contentWithoutExample = Array.from(category.content.split(' (예: ')[0]);

    const emoji = contentWithoutExample.shift();
    const keyword = contentWithoutExample.join('');

    return {
      optionId: category.optionId,
      content: `${emoji} ${keyword}`,
    };
  });

  return (
    <S.KeywordList>
      {formattedCategories.map((category) => (
        <S.KeywordItem key={category.optionId}>{category.content}</S.KeywordItem>
      ))}
    </S.KeywordList>
  );
};

export default StrengthKeywordList;
