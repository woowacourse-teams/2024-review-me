import AlertTriangle from '@/assets/alertTriangle.svg';
import Home from '@/assets/home.svg';
import ReLoad from '@/assets/reload.svg';
import { Button } from '@/components';

import * as S from './styles';

interface ErrorSectionProps {
  errorMessage: string;
  handleReLoad: () => void;
  handleGoHome: () => void;
}

const ErrorSection = ({ errorMessage, handleReLoad, handleGoHome }: ErrorSectionProps) => {
  return (
    <S.Layout>
      <S.ErrorLogoWrapper>
        <img src={AlertTriangle} alt="에러 로고" />
      </S.ErrorLogoWrapper>
      <S.ErrorMessage>{errorMessage}</S.ErrorMessage>
      <S.Container>
        <S.ButtonContainer>
          <Button buttonType="primary" text="새로고침하기" icon={ReLoad} onClick={handleReLoad} />
        </S.ButtonContainer>
        <S.ButtonContainer>
          <Button buttonType="secondary" text="홈으로 이동하기" icon={Home} onClick={handleGoHome} />
        </S.ButtonContainer>
      </S.Container>
    </S.Layout>
  );
};

export default ErrorSection;
