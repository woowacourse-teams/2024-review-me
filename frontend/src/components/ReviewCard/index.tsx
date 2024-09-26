import { Category } from '@/types';

import * as S from './styles';

interface ReviewCardProps {
  projectName: string;
  createdAt: string;
  contentPreview: string;
  categories: Category[];
  handleClick: () => void;
}

const ReviewCard = ({ projectName, createdAt, contentPreview, categories, handleClick }: ReviewCardProps) => {
  return (
    <S.Layout onClick={handleClick}>
      <S.Header>
        <S.HeaderContent>
          <div>
            <S.Title>{projectName}</S.Title>
            <S.SubTitle>{createdAt}</S.SubTitle>
          </div>
        </S.HeaderContent>
      </S.Header>
      <S.Main>
        <span>{contentPreview}</span>
        <S.Keyword>
          {categories.map((category) => (
            <div key={category.optionId}>{category.content}</div>
          ))}
        </S.Keyword>
      </S.Main>
    </S.Layout>
  );
};

export default ReviewCard;
