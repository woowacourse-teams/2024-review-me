import { useEffect } from 'react';
import { useNavigate } from 'react-router';
import { useRecoilState } from 'recoil';

import ReviewZoneIcon from '@/assets/reviewZone.svg';
import { Button } from '@/components';
// TODO: ROUTE 상수명을 단수로 고치기
import { ROUTE } from '@/constants/route';
import { useGetReviewGroupData, useSearchParamAndQuery } from '@/hooks';
import useModals from '@/hooks/useModals';
import { reviewRequestCodeAtom } from '@/recoil';

import PasswordModal from './components/PasswordModal';
import * as S from './styles';

const MODAL_KEYS = {
  content: 'CONTENT_MODAL',
};

const ReviewZonePage = () => {
  const { isOpen, openModal, closeModal } = useModals();
  const [storedReviewRequestCode, setStoredReviewRequestCode] = useRecoilState(reviewRequestCodeAtom);

  const navigate = useNavigate();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  if (!reviewRequestCode) throw new Error('유효하지 않은 리뷰 요청 코드예요');

  useEffect(() => {
    if (!storedReviewRequestCode && reviewRequestCode) {
      setStoredReviewRequestCode(reviewRequestCode);
    }
  }, []);

  const { data: reviewGroupData } = useGetReviewGroupData({ reviewRequestCode });

  const handleReviewWritingButtonClick = () => {
    navigate(`/${ROUTE.reviewWriting}/${reviewRequestCode}`);
  };

  const handleReviewListButtonClick = () => {
    openModal(MODAL_KEYS.content);
  };

  return (
    <S.ReviewZonePage>
      <S.ReviewZoneMainImg src={ReviewZoneIcon} alt="" />
      <S.ReviewGuideContainer>
        {/* NOTE: 추후 API 연동되면 서버에서 받아온 이름들을 출력하도록 수정해야 함 */}
        <S.ReviewGuide>{`${reviewGroupData.projectName}를 함께한`}</S.ReviewGuide>
        <S.ReviewGuide>{`${reviewGroupData.revieweeName}의 리뷰 공간이에요`}</S.ReviewGuide>
      </S.ReviewGuideContainer>
      <S.ButtonContainer>
        <Button
          styleType="primary"
          type="button"
          onClick={handleReviewWritingButtonClick}
          style={{ width: '28rem', height: '8.5rem' }}
        >
          <S.ButtonTextContainer>
            <S.ButtonText>리뷰 쓰기</S.ButtonText>
            <S.ButtonDescription>작성한 리뷰는 익명으로 제출돼요</S.ButtonDescription>
          </S.ButtonTextContainer>
        </Button>
        <Button
          styleType="secondary"
          type="button"
          onClick={handleReviewListButtonClick}
          style={{ width: '28rem', height: '8.5rem' }}
        >
          <S.ButtonTextContainer>
            <S.ButtonText>리뷰 확인하기</S.ButtonText>
            <S.ButtonDescription>리뷰 링크가 있다면 비밀번호로 확인할 수 있어요</S.ButtonDescription>
          </S.ButtonTextContainer>
        </Button>
      </S.ButtonContainer>
      {isOpen(MODAL_KEYS.content) && (
        <PasswordModal reviewRequestCode={reviewRequestCode} closeModal={() => closeModal(MODAL_KEYS.content)} />
      )}
    </S.ReviewZonePage>
  );
};

export default ReviewZonePage;
