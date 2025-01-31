import { useLocation, useNavigate } from 'react-router-dom';

import { ErrorSuspenseContainer, AuthAndServerErrorFallback, EmptyContent, TopButton } from '@/components';
import { useSearchParamAndQuery } from '@/hooks';

import DetailedWrittenReview from './components/DetailedWrittenReview';
import WrittenReviewList from './components/WrittenReviewList';
import { useDeviceBreakpoints, useGetWrittenReviewList } from './hooks';
import * as S from './styles';

const WrittenReviewPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { deviceType } = useDeviceBreakpoints();
  const { reviewList } = useGetWrittenReviewList();

  const { queryString: reviewIdString } = useSearchParamAndQuery({
    queryStringKey: 'reviewId',
  });

  const selectedReviewId = reviewIdString ? Number(reviewIdString) : null;

  const handleReviewItemClick = (reviewId: number) => {
    const params = new URLSearchParams();
    params.set('reviewId', reviewId.toString());

    navigate(`${location.pathname}?${params.toString()}`);
  };

  const renderEmptyContent = () => (
    <EmptyContent
      iconWidth={deviceType.isDesktop ? '30vw' : '60vw'}
      messageFontSize={deviceType.isTablet ? '2rem' : undefined}
      iconHeight="45vh"
    >
      <p>아직 작성한 리뷰가 없어요...</p>
    </EmptyContent>
  );

  // Large 이상: 목록, 상세 모두 렌더링
  const renderDesktopView = () => (
    <S.PageContainer>
      <WrittenReviewList handleClick={handleReviewItemClick} />
      <DetailedWrittenReview $isDisplayable={true} selectedReviewId={selectedReviewId} />
    </S.PageContainer>
  );

  // 이외의 경우: queryString 없으면 목록, 있으면 상세보기 렌더링
  const renderMobileOrTabletView = () =>
    selectedReviewId ? (
      <DetailedWrittenReview $isDisplayable={!!selectedReviewId} selectedReviewId={selectedReviewId} />
    ) : (
      <WrittenReviewList handleClick={handleReviewItemClick} />
    );

  const renderContent = () => {
    if (reviewList.length === 0) return renderEmptyContent();

    return deviceType.isDesktop ? renderDesktopView() : renderMobileOrTabletView();
  };

  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      <TopButton />
      {renderContent()}
    </ErrorSuspenseContainer>
  );
};

export default WrittenReviewPage;
