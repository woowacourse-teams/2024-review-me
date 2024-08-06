import { ErrorSuspenseContainer } from '@/components';

import ReviewAccessForm from './components/ReviewAccessForm';
import URLGeneratorForm from './components/URLGeneratorForm';
import * as S from './styles';

const LandingPage = () => {
  // NOTE: ReviewAccessForm에서는 Errorboundary를 사용하지 않지만 감싸지 않으면 
  // Error fallback에서 ReviewAccessForm 컴포넌트가 노출되므로 둘 다 감쌌음
  return (
    <S.LandingPage>
      <ErrorSuspenseContainer>
        <URLGeneratorForm />
        <ReviewAccessForm />
      </ErrorSuspenseContainer>
    </S.LandingPage>
  );
};

export default LandingPage;
