import { Button } from '@/components';
import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

const BUTTON_SIZE = {
  width: '30rem',
  height: '8.5rem',
};

interface ReviewWriteButtonProps {
  handleClick: () => void;
}

interface ReviewCheckButtonProps {
  handleClick: () => void;
  isGroupLoggedIn: boolean;
}

/**
 * 1. 리뷰어 로그인 + 리뷰 링크 로그인 = 쓰기 버튼(회원용), 확인 버튼(회원용)
 * 2. 리뷰어 로그인 + 리뷰 링크 비로그인 = 쓰기 버튼(회원용), 확인 버튼(비회원용)
 * 3. 리뷰어 비로그인 + 리뷰 링크 로그인 = 쓰기 버튼(회원용, 비회원용), 확인 버튼(회원용)
 * 4. 리뷰어 비로그인 + 리뷰 링크 비로그인 = 쓰기 버튼(회원용, 비회원용), 확인 버튼(비회원용)
 */
const ReviewButton = ({ children }: EssentialPropsWithChildren) => {
  return <S.ButtonContainer>{children}</S.ButtonContainer>;
};

const Write = ({ handleClick }: ReviewWriteButtonProps) => {
  return (
    <Button styleType="primary" type="button" onClick={handleClick} style={BUTTON_SIZE}>
      <S.ButtonTextContainer>
        <S.ButtonText>회원으로 리뷰 쓰기</S.ButtonText>
        <S.ButtonDescription>작성한 리뷰는 익명으로 제출돼요</S.ButtonDescription>
      </S.ButtonTextContainer>
    </Button>
  );
};

const WriteGuest = ({ handleClick }: ReviewWriteButtonProps) => {
  return (
    <Button styleType="primary" type="button" onClick={handleClick} style={BUTTON_SIZE}>
      <S.ButtonTextContainer>
        <S.ButtonText>비회원으로 리뷰 쓰기</S.ButtonText>
        <S.ButtonDescription>작성한 리뷰는 익명으로 제출돼요</S.ButtonDescription>
      </S.ButtonTextContainer>
    </Button>
  );
};

const Check = ({ handleClick, isGroupLoggedIn }: ReviewCheckButtonProps) => {
  const DESCRIPTION = isGroupLoggedIn
    ? '회원이신가요? 로그인하고 바로 확인해 보세요!'
    : '비밀번호로 내가 받은 리뷰를 확인할 수 있어요';

  return (
    <Button styleType="secondary" type="button" onClick={handleClick} style={BUTTON_SIZE}>
      <S.ButtonTextContainer>
        <S.ButtonText>리뷰 확인하기</S.ButtonText>
        <S.ButtonDescription>{DESCRIPTION}</S.ButtonDescription>
      </S.ButtonTextContainer>
    </Button>
  );
};

ReviewButton.Write = Write;
ReviewButton.WriteGuest = WriteGuest;
ReviewButton.Check = Check;

export default ReviewButton;
