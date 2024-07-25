import GithubLogo from '@/assets/githubLogo.svg';
import Lock from '@/assets/lock.svg';
import UnLock from '@/assets/unLock.svg';

import * as S from './styles';

interface ReviewPreviewCardProps {
  reviewerGroup: {
    name: string;
  };
  createdAt: string;
  contentPreview: string;
  keywords: { id: number; content: string }[];
  isPublic: boolean;
}

const ReviewPreviewCard = ({
  reviewerGroup,
  createdAt,
  contentPreview,
  keywords,
  isPublic,
}: ReviewPreviewCardProps) => {
  return (
    <S.Layout>
      <S.Header>
        <S.HeaderContainer>
          <div>
            <img src={GithubLogo} />
          </div>
          <div>
            <S.Title>{reviewerGroup.name}</S.Title>
            <S.SubTitle>{createdAt}</S.SubTitle>
          </div>
        </S.HeaderContainer>
        <S.Visibility>
          <img src={isPublic ? UnLock : Lock} />
          <span>{isPublic ? '공개' : '비공개'}</span>
        </S.Visibility>
      </S.Header>
      <S.Main>
        <span>{contentPreview}</span>
        <S.Keyword>
          {keywords.map((keyword) => (
            <div key={keyword.id}>{keyword.content}</div>
          ))}
        </S.Keyword>
      </S.Main>
    </S.Layout>
  );
};

export default ReviewPreviewCard;
