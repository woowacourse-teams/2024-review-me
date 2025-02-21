import { useState } from 'react';

import { LoginToggleButton, ErrorSuspenseContainer, URLGeneratorForm } from '@/components';
import { useGetUserProfile } from '@/hooks/oAuth';

import { LoginPrompt, ReviewLinkPrompt, ReviewMeOverview } from './components';
import * as S from './styles';

const HomePage = () => {
  const [showLoginForm, setshowLoginForm] = useState(true);
  const { userProfile, isUserLoggedIn } = useGetUserProfile();

  const renderForm = () => {
    if (isUserLoggedIn && userProfile) {
      return <ReviewLinkPrompt />;
    }

    return (
      <>
        {showLoginForm ? <LoginPrompt /> : <URLGeneratorForm />}
        <LoginToggleButton goToLogin={!showLoginForm} handleClick={() => setshowLoginForm(!showLoginForm)} />
      </>
    );
  };

  return (
    <S.HomePage>
      <ErrorSuspenseContainer>
        <ReviewMeOverview />
        <S.FormSection>{renderForm()}</S.FormSection>
      </ErrorSuspenseContainer>
    </S.HomePage>
  );
};

export default HomePage;
