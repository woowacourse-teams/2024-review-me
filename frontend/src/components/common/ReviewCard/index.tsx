import { Category } from '@/types';

import ReviewDate from '../ReviewDate';
import ReviewKeyword from '../ReviewKeyword';

import * as S from './styles';

interface ReviewCardProps {
  createdAt: string;
  contentPreview: string;
  categories: Category[];
  handleClick: () => void;
}

const ReviewCard = ({ createdAt, contentPreview, categories, handleClick }: ReviewCardProps) => {
  const date = new Date(createdAt);

  return (
    <S.Layout onClick={handleClick}>
      <S.Header>
        <ReviewDate date={date} dateTitle={`작성일`} />
      </S.Header>
      <S.Main>
        <S.ContentPreview>{contentPreview}</S.ContentPreview>
        <S.Footer>
          <S.ReviewKeywordList>
            {categories.map(({ optionId, content }) => (
              <ReviewKeyword key={optionId} content={content} />
            ))}
          </S.ReviewKeywordList>
        </S.Footer>
      </S.Main>
    </S.Layout>
  );
};

export default ReviewCard;
