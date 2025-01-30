import { ReviewDate, ReviewKeyword, RevieweeInfo } from '@/components/common';
import { Category } from '@/types';

import * as S from './styles';

interface ReviewCardProps {
  createdAt: string;
  projectName?: string;
  contentPreview: string;
  categories: Category[];
  handleClick: () => void;
  revieweeName?: string;
}

const ReviewCard = ({
  projectName,
  createdAt,
  contentPreview,
  categories,
  handleClick,
  revieweeName,
}: ReviewCardProps) => {
  const date = new Date(createdAt);

  return (
    <S.Layout onClick={handleClick}>
      <S.Header>
        <ReviewDate date={date} dateTitle="작성일" />
      </S.Header>

      <S.Main>
        <S.Title>{projectName}</S.Title>
        <S.ContentPreview>{contentPreview}</S.ContentPreview>
      </S.Main>

      <S.Footer>
        <S.ReviewKeywordList>
          {categories.map(({ optionId, content }) => (
            <ReviewKeyword key={optionId} content={content} />
          ))}
        </S.ReviewKeywordList>
        {revieweeName && (
          <>
            <S.Divider />
            <RevieweeInfo revieweeName={revieweeName} />
          </>
        )}
      </S.Footer>
    </S.Layout>
  );
};

export default ReviewCard;
