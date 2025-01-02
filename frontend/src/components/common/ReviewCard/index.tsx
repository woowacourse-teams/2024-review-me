import { Category } from '@/types';

import StrengthKeywordList from './StrengthKeywordList';
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
        <S.Date>{`작성일 | ${createdAt}`}</S.Date>
      </S.Header>
      <S.Main>
        <S.ContentPreview>{contentPreview}</S.ContentPreview>
        <S.Footer>
          <StrengthKeywordList categories={categories} />
        </S.Footer>
      </S.Main>
    </S.Layout>
  );
};

export default ReviewCard;
