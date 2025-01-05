import { NoSelectedReviewGuide } from '../index';
import { PageContentLayout } from '../layouts';

import * as S from './styles';

interface DetailedWrittenReviewProps {
  $isMobile: boolean;
  selectedReviewId: number | null;
}

// 라우팅으로 들어오는 경우 queryParam으로 reviewId를 가져올 수 있음
// -> 그렇다면 라우터에서 이 컴포넌트를 별도의 props 없이 호출 가능, selectedReviewId는 optional 처리
// but 일단은 props로 id를 무조건 받도록 구현해둔 상태
const DetailedWrittenReview = ({ $isMobile, selectedReviewId }: DetailedWrittenReviewProps) => {
  // 추후 이곳에서 직접 상세 리뷰 데이터 호출

  // 라우팅으로 넘어온 경우 무조건 isMobile은 true
  return (
    <S.DetailedWrittenReview $isMobile={$isMobile}>
      <PageContentLayout title="작성한 리뷰 상세보기">
        <S.Outline>
          {selectedReviewId ? <div style={{ height: '120vh' }}>있다</div> : <NoSelectedReviewGuide />}
        </S.Outline>
      </PageContentLayout>
    </S.DetailedWrittenReview>
  );
};

export default DetailedWrittenReview;
