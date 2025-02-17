import React from 'react';

import { ReviewDate, ReviewKeyword, RevieweeInfo, UndraggableWrapper } from '@/components';
import { Category } from '@/types';

import * as S from './styles';

interface ReviewPreviewProps {
  id: number;
  createdAt: string;
  projectName?: string;
  contentPreview: string;
  categories: Category[];
  handleClick: (reviewId: number) => void;
  revieweeName?: string;
}

const ReviewPreview = ({
  id,
  projectName,
  createdAt,
  contentPreview,
  categories,
  handleClick,
  revieweeName,
}: ReviewPreviewProps) => {
  const date = new Date(createdAt);

  return (
    <S.Layout onClick={() => handleClick(id)}>
      <UndraggableWrapper>
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
      </UndraggableWrapper>
    </S.Layout>
  );
};

export default React.memo(ReviewPreview);
