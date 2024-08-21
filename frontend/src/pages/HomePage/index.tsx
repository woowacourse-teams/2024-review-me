import { ErrorSuspenseContainer } from '@/components';

import { ReviewMeOverview, URLGeneratorForm } from './components';
import * as S from './styles';

const HomePage = () => {
  return (
    <S.HomePage>
      <ErrorSuspenseContainer>
        <ReviewMeOverview />
        <URLGeneratorForm />
      </ErrorSuspenseContainer>
    </S.HomePage>
  );
};

export default HomePage;
