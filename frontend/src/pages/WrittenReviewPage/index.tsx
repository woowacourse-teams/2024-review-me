import { useLocation, useNavigate } from 'react-router-dom';

import { ErrorSuspenseContainer, AuthAndServerErrorFallback } from '@/components';
import { useSearchParamAndQuery } from '@/hooks';

import DetailedWrittenReview from './components/DetailedWrittenReview';
import WrittenReviewList from './components/WrittenReviewList';
import { useDeviceBreakpoints } from './hooks';
import * as S from './styles';

const WrittenReviewPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { deviceType } = useDeviceBreakpoints();

  const { queryString: reviewIdString } = useSearchParamAndQuery({
    queryStringKey: 'reviewId',
  });

  const selectedReviewId = reviewIdString ? Number(reviewIdString) : null;

  const handleReviewItemClick = (reviewId: number) => {
    const params = new URLSearchParams();
    params.set('reviewId', reviewId.toString());

    navigate(`${location.pathname}?${params.toString()}`);
  };

  const renderContent = () => {
    // 노트북, 보통 사이즈 이상의 태블릿 가로모드: 목록, 상세 모두 렌더링
    if (deviceType.isDesktop) {
      return (
        <S.PageContainer>
          <WrittenReviewList handleClick={handleReviewItemClick} />
          <DetailedWrittenReview $isDisplayable={true} selectedReviewId={selectedReviewId} />
        </S.PageContainer>
      );
    }

    // 이외의 경우: queryString 없으면 목록, 있으면 상세보기 렌더링
    return selectedReviewId ? (
      <DetailedWrittenReview $isDisplayable={!!selectedReviewId} selectedReviewId={selectedReviewId} />
    ) : (
      <WrittenReviewList handleClick={handleReviewItemClick} />
    );
  };

  return <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>{renderContent()}</ErrorSuspenseContainer>;
};

export default WrittenReviewPage;
