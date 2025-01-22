import { useLayoutEffect } from 'react';
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
  const { data } = useGetWrittenReviewList();

  const { queryString: reviewIdString } = useSearchParamAndQuery({
    queryStringKey: 'reviewId',
  });

  const reviewList = data?.pages.flatMap((page) => page.reviews) || [];
  const selectedReviewId = reviewIdString ? Number(reviewIdString) : null;

  const handleReviewItemClick = (reviewId: number) => {
    const params = new URLSearchParams();
    params.set('reviewId', reviewId.toString());

    navigate(`${location.pathname}?${params.toString()}`);
  };

  // NOTE: 임시 로그인 인증 쿠키 설정
  // 추후 로그인 쿠키값/비로그인 쿠키값을 분리하고,
  // (이 페이지에서 쿠키를 설정하지 않고) 이전 페이지(url 관리 페이지)의 값을 가져와야 함
  // useLayoutEffect(() => {
  //   document.cookie = 'mockAuthToken=2024-review-me';
  // }, []);

  const renderEmptyContent = () => (
    <EmptyContent
      iconWidth={deviceType.isDesktop ? '30vw' : '60vw'}
      messageFontSize={deviceType.isTablet ? '2rem' : undefined}
      iconHeight="45vh"
    >
      <p>아직 작성한 리뷰가 없어요...</p>
    </EmptyContent>
  );

  // 노트북, 보통 사이즈 이상의 태블릿 가로모드: 목록, 상세 모두 렌더링
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
