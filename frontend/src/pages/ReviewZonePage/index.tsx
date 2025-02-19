import { useEffect } from 'react';
import { useNavigate } from 'react-router';
import { useRecoilState } from 'recoil';

import ReviewZoneIcon from '@/assets/reviewZone.svg';
import { ImgWithSkeleton, LoginRequestModal } from '@/components';
import { ROUTE } from '@/constants';
import { useGetReviewGroupData, useSearchParamAndQuery, useModals, useToastContext } from '@/hooks';
import { useGetUserProfile } from '@/hooks/oAuth';
import { reviewRequestCodeAtom } from '@/recoil';
import { calculateParticle } from '@/utils';

import PasswordModal from './components/PasswordModal';
import ReviewButton from './components/ReviewButton';
import * as S from './styles';

const MODAL_KEYS = {
  content: 'CONTENT_MODAL',
  writeOnLogin: 'LOGIN_MODAL_TO_WRITE',
  checkOnLogin: 'LOGIN_MODAL_TO_CHECK',
};

const BUTTON_SIZE = {
  width: '30rem',
  height: '8.5rem',
};

const IMG_HEIGHT = '15rem';

const ReviewZonePage = () => {
  const navigate = useNavigate();

  const { isOpen, openModal, closeModal } = useModals();
  const [storedReviewRequestCode, setStoredReviewRequestCode] = useRecoilState(reviewRequestCodeAtom);
  const { userProfile, isUserLoggedIn } = useGetUserProfile();
  const { showToast } = useToastContext();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  if (!reviewRequestCode) throw new Error('유효하지 않은 리뷰 요청 코드예요');

  useEffect(() => {
    if (!storedReviewRequestCode && reviewRequestCode) {
      setStoredReviewRequestCode(reviewRequestCode);
    }
  }, []);

  useEffect(() => {
    // 쿼리 파라미터로 전달된 state 파싱
    const params = new URLSearchParams(location.search);
    const loginActionParam = params.get('login_action');
    if (!loginActionParam) return;

    // 로그인 상태가 아닌 경우 쿼리 파라미터 제거
    if (!isUserLoggedIn) {
      params.delete('login_action');
      window.history.replaceState(null, '', `${location.pathname}`);
      return;
    }

    if (loginActionParam === 'reviewWrite') return handleReviewWrite();
    if (loginActionParam === 'reviewCheck') return handleReviewCheck();
  }, []);

  const { data: reviewGroupData, isMemberLink } = useGetReviewGroupData({ reviewRequestCode });

  const PROJECT_NAME_GUIDE = `${reviewGroupData.projectName}${calculateParticle({ target: reviewGroupData.projectName, particles: { withFinalConsonant: '을', withoutFinalConsonant: '를' } })} 함께한`;
  const REVIEWEE_NAME_GUIDE = `${reviewGroupData.revieweeName}의 리뷰 공간이에요`;

  const handleReviewWrite = () => {
    if (isUserLoggedIn) return navigate(`/${ROUTE.reviewWriting}/${reviewRequestCode}`);
    openModal(MODAL_KEYS.writeOnLogin);
  };

  const handleReviewWriteGuest = () => {
    navigate(`/${ROUTE.reviewWriting}/${reviewRequestCode}`);
  };

  const handleReviewCheck = () => {
    // 비회원이 만든 그룹이면 비밀번호 입력
    if (!isMemberLink) return openModal(MODAL_KEYS.content);
    // 리뷰어가 로그인 안 했으면 로그인 모달 띄우기
    if (!isUserLoggedIn) return openModal(MODAL_KEYS.checkOnLogin);
    // 리뷰어가 링크 주인이면 목록 페이지로 이동
    if (userProfile?.memberId === reviewGroupData.revieweeId) {
      return navigate(`/${ROUTE.reviewList}/${reviewRequestCode}`);
    }
    return showToast({
      type: 'error',
      message: '리뷰는 본인만 확인할 수 있어요',
      durationMS: 3000,
      position: 'bottom',
    });
  };

  return (
    <S.ReviewZonePage>
      <ImgWithSkeleton imgWidth={BUTTON_SIZE.width} imgHeight={IMG_HEIGHT}>
        <S.ReviewZoneMainImg src={ReviewZoneIcon} alt="" $height={IMG_HEIGHT} />
      </ImgWithSkeleton>
      <S.ReviewGuideContainer>
        <S.ReviewGuide>{PROJECT_NAME_GUIDE}</S.ReviewGuide>
        <S.ReviewGuide>{REVIEWEE_NAME_GUIDE}</S.ReviewGuide>
      </S.ReviewGuideContainer>
      <ReviewButton>
        <ReviewButton.Write handleClick={handleReviewWrite} />
        {!isUserLoggedIn && <ReviewButton.WriteGuest handleClick={handleReviewWriteGuest} />}
        <ReviewButton.Check isGroupLoggedIn={isMemberLink} handleClick={handleReviewCheck} />
      </ReviewButton>
      {isOpen(MODAL_KEYS.content) && (
        <PasswordModal reviewRequestCode={reviewRequestCode} closeModal={() => closeModal(MODAL_KEYS.content)} />
      )}
      {isOpen(MODAL_KEYS.writeOnLogin) && (
        <LoginRequestModal
          action="reviewWrite"
          titleType="loginIntent"
          closeModal={() => closeModal(MODAL_KEYS.writeOnLogin)}
        />
      )}
      {isOpen(MODAL_KEYS.checkOnLogin) && (
        <LoginRequestModal
          action="reviewCheck"
          titleType="loginIntent"
          closeModal={() => closeModal(MODAL_KEYS.checkOnLogin)}
        />
      )}
    </S.ReviewZonePage>
  );
};

export default ReviewZonePage;
