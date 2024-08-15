import { ErrorSuspenseContainer } from '@/components';

import URLGeneratorForm from './components/URLGeneratorForm';
import * as S from './styles';

const HomePage = () => {
  return (
    <S.HomePage>
      <ErrorSuspenseContainer>
        <S.ReviewMeOverview></S.ReviewMeOverview>
        <URLGeneratorForm />
      </ErrorSuspenseContainer>
    </S.HomePage>
  );
};

export default HomePage;
