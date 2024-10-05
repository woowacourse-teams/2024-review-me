import { Category } from '@/types';

import * as S from './styles';

interface ReviewCardProps {
  projectName: string;
  createdAt: string;
  contentPreview: string;
  categories: Category[];
  handleClick: () => void;
}

const ReviewCard = ({ createdAt, contentPreview, categories, handleClick }: ReviewCardProps) => {
  return (
    <S.Layout onClick={handleClick}>
      <S.LeftLineBorder />

      <S.Main>
        <span>{contentPreview}</span>
        <S.Footer>
          <S.Keyword>
            {categories.map((category) => (
              <div key={category.optionId}>{category.content}</div>
            ))}
          </S.Keyword>
          <S.Date>{createdAt}</S.Date>
        </S.Footer>
      </S.Main>
    </S.Layout>
  );
};

export default ReviewCard;
