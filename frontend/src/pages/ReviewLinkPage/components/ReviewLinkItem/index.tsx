import { CopyTextButton, ReviewDate } from '@/components';
import { ROUTE } from '@/constants';
import { ReviewGroup } from '@/types';

import RevieweeName from '../RevieweeName';

import * as S from './styles';

interface ReviewLinkCardProps extends ReviewGroup {
  handleClick: () => void;
}

const ReviewLinkItem = ({
  revieweeName,
  projectName,
  createdAt,
  reviewRequestCode,
  reviewCount,
  handleClick,
}: ReviewLinkCardProps) => {
  const date = new Date(createdAt);
  const url = `${window.origin}/${ROUTE.reviewZone}/${reviewRequestCode}`;

  return (
    <S.Layout onClick={handleClick}>
      <ReviewDate date={date} dateTitle="생성일" />
      <S.ContentContainer>
        <S.ProjectDetails>
          <S.ProjectName>{projectName}</S.ProjectName>
          <S.ReviewCount>{reviewCount}</S.ReviewCount>
          <CopyTextButton targetText={url} alt="회원이 생성한 리뷰 링크 복사하기" />
        </S.ProjectDetails>
        <S.ReviewLink>{url}</S.ReviewLink>
      </S.ContentContainer>
      <S.Divider />
      <RevieweeName revieweeTitle="🧑‍💻 리뷰이" revieweeName={revieweeName} />
    </S.Layout>
  );
};

export default ReviewLinkItem;
