import { ErrorSuspenseContainer, URLGeneratorForm } from '@/components';

import { ReviewMeOverview } from './components';
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
