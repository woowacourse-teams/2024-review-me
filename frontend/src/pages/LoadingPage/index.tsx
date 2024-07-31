import LoadingBar from './components/LoadingBar';
import * as S from './styles';

const LoadingPage = () => {
  return (
    <S.Container>
      <S.Text>Loading ....</S.Text>
      <LoadingBar />
    </S.Container>
  );
};

export default LoadingPage;
