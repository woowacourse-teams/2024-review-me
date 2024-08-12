import GithubLogoIcon from '@/assets/githubLogo.svg';
import { Category } from '@/types';

import * as S from './styles';

interface ReviewCardProps {
  projectName: string;
  createdAt: string;
  contentPreview: string;
  categories: Category[];
}

const ReviewCard = ({ projectName, createdAt, contentPreview, categories }: ReviewCardProps) => {
  return (
    <S.Layout>
      <S.Header>
        <S.HeaderContent>
          <div>
            <img src={GithubLogoIcon} alt="깃허브 로고" />
          </div>
          <div>
            <S.Title>{projectName}</S.Title>
            <S.SubTitle>{createdAt}</S.SubTitle>
          </div>
        </S.HeaderContent>
        {/* 추후에 사용될 수 있어서 일단 주석 처리 <S.Visibility>
          <img src={isPublic ? UnLock : Lock} alt="자물쇠 아이콘" />
          <span>{isPublic ? '공개' : '비공개'}</span>
        </S.Visibility> */}
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
