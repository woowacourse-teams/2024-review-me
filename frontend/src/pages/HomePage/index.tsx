import { useState } from 'react';

import { LoginToggleButton, ErrorSuspenseContainer, URLGeneratorForm } from '@/components';

import { OAuthLoginForm, ReviewMeOverview } from './components';
import * as S from './styles';

const HomePage = () => {
  const [showLoginForm, setshowLoginForm] = useState(true);

  return (
    <S.HomePage>
      <ErrorSuspenseContainer>
        <ReviewMeOverview />
        <S.FormSection>
          {showLoginForm ? <OAuthLoginForm /> : <URLGeneratorForm />}
          <LoginToggleButton goToLogin={!showLoginForm} handleClick={() => setshowLoginForm(!showLoginForm)} />
        </S.FormSection>
      </ErrorSuspenseContainer>
    </S.HomePage>
  );
};

export default HomePage;
