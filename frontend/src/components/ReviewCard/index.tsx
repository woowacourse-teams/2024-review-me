import { Category } from '@/types';

import * as S from './styles';

interface ReviewCardProps {
  createdAt: string;
  contentPreview: string;
  categories: Category[];
  handleClick: () => void;
}

const ReviewCard = ({ createdAt, contentPreview, categories, handleClick }: ReviewCardProps) => {
  return (
    <S.Layout onClick={handleClick}>
      <S.Header>
        <S.Date>{createdAt}</S.Date>
      </S.Header>
      <S.Main>
        <S.ContentPreview>{contentPreview}</S.ContentPreview>
        <S.Footer>
          <S.Keyword>
            {categories.map((category) => (
              <div key={category.optionId}>{category.content}</div>
            ))}
          </S.Keyword>
        </S.Footer>
      </S.Main>
    </S.Layout>
  );
};

export default ReviewCard;
