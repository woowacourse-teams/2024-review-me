import URLGeneratorForm from './components/URLGeneratorFormBody';
import ReviewAccessForm from './ReviewAccessForm';
import * as S from './styles';

const LandingPage = () => {
  return (
    <S.LandingPage>
      <URLGeneratorForm />
      <ReviewAccessForm />
    </S.LandingPage>
  );
};

export default LandingPage;
