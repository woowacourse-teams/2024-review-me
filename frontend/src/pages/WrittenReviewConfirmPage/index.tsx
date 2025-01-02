import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { ErrorSuspenseContainer, AuthAndServerErrorFallback } from '@/components';
import { useSearchParamAndQuery } from '@/hooks';

import DetailedWrittenReview from './components/DetailedWrittenReview';
import WrittenReviewList from './components/WrittenReviewList';
import { useCurrentMediaType } from './hooks';
import * as S from './styles'; // TODO: 마지막에 import 경로들, 시맨틱 확인하기!

// refactor(선택): 레이아웃 도입 등으로 이 페이지에서는 에러바운더리 + 탭 + 이하 페이지 Content 요소만 쓰도록 분리

const WrittenReviewConfirmPage = () => {
  const [selectedReviewId, setSelectedReviewId] = useState<number | null>(null);

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });
  const navigate = useNavigate();
  const { currentDeviceType } = useCurrentMediaType();

  const handleClick = (reviewId: number) => {
    if (currentDeviceType.isMobile) {
      navigate(`/user/written-review-confirm/${reviewRequestCode}/${reviewId}`);
    } else {
      setSelectedReviewId(reviewId);
    }
  };

  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <S.PageContainer>
        <WrittenReviewList handleClick={handleClick} />
        {/* TODO: 모바일에서 DetailedWrittenReview를 화살표 레이아웃으로 감싸기 */}
        {!currentDeviceType.isMobile && (
          <DetailedWrittenReview $isMobile={currentDeviceType.isMobile} selectedReviewId={selectedReviewId} />
        )}
      </S.PageContainer>
    </ErrorSuspenseContainer>
  );
};

export default WrittenReviewConfirmPage;
