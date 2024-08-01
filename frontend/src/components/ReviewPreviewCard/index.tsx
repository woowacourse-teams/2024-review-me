import GithubLogoIcon from '@/assets/githubLogo.svg';
import { ReviewPreview } from '@/types';

import * as S from './styles';

interface ReviewPreviewCardProps extends ReviewPreview {
  projectName: string;
}

const ReviewPreviewCard = ({ id, projectName, createdAt, contentPreview, keywords }: ReviewPreviewCardProps) => {
  return (
    <S.Layout data-id={id}>
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
          {keywords.map((keyword) => (
            <div key={keyword.id}>{keyword.content}</div>
          ))}
        </S.Keyword>
      </S.Main>
    </S.Layout>
  );
};

export default ReviewPreviewCard;
