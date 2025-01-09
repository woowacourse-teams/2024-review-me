import { useLocation, useNavigate } from 'react-router-dom';

import { ErrorSuspenseContainer, AuthAndServerErrorFallback } from '@/components';
import { useSearchParamAndQuery } from '@/hooks';

import DetailedWrittenReview from './components/DetailedWrittenReview';
import WrittenReviewList from './components/WrittenReviewList';
import { useCurrentMediaType } from './hooks';
import * as S from './styles';

const WrittenReviewPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { currentDeviceType } = useCurrentMediaType();

  const { queryString: reviewIdString } = useSearchParamAndQuery({
    paramKey: '',
    queryStringKey: 'reviewId',
  });

  const selectedReviewId = reviewIdString ? Number(reviewIdString) : null;

  const handleReviewItemClick = (reviewId: number) => {
    const params = new URLSearchParams();
    params.set('reviewId', reviewId.toString());

    navigate(`${location.pathname}?${params.toString()}`);
  };

  const renderContent = () => {
    if (currentDeviceType.isMobile) {
      // 모바일: queryString 없으면 목록, 있으면 상세보기
      return selectedReviewId ? (
        <DetailedWrittenReview $isMobile={true} selectedReviewId={selectedReviewId} />
      ) : (
        <WrittenReviewList handleClick={handleReviewItemClick} />
      );
    }

    // 태블릿 ~ : 목록 + 상세보기
    return (
      <S.PageContainer>
        <WrittenReviewList handleClick={handleReviewItemClick} />
        <DetailedWrittenReview $isMobile={false} selectedReviewId={selectedReviewId} />
      </S.PageContainer>
    );
  };

  return <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>{renderContent()}</ErrorSuspenseContainer>;
};

export default WrittenReviewPage;
