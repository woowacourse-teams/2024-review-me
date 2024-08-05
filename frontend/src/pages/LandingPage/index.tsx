import { ErrorSuspenseContainer } from '@/components';

import ReviewAccessForm from './components/ReviewAccessForm';
import URLGeneratorForm from './components/URLGeneratorForm';
import * as S from './styles';

const LandingPage = () => {
  return (
    <S.LandingPage>
      <ErrorSuspenseContainer>
        <URLGeneratorForm />
      </ErrorSuspenseContainer>
      <ReviewAccessForm />
    </S.LandingPage>
  );
};

export default LandingPage;
