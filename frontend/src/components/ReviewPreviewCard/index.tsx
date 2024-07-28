import GithubLogo from '@/assets/githubLogo.svg';
import Lock from '@/assets/lock.svg';
import UnLock from '@/assets/unLock.svg';
import { ReviewPreview } from '@/types';

import * as S from './styles';

const ReviewPreviewCard = ({ id, reviewerGroup, createdAt, contentPreview, keywords, isPublic }: ReviewPreview) => {
  return (
    <S.Layout data-id={id}>
      <S.Header>
        <S.HeaderContent>
          <div>
            <img src={GithubLogo} alt="깃허브 로고" />
          </div>
          <div>
            <S.Title>{reviewerGroup.name}</S.Title>
            <S.SubTitle>{createdAt}</S.SubTitle>
          </div>
        </S.HeaderContent>
        <S.Visibility>
          <img src={isPublic ? UnLock : Lock} alt="자물쇠 아이콘" />
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
