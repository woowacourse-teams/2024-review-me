import { PropsWithChildren } from 'react';
import * as S from './styles';

const Main = ({ children }: PropsWithChildren) => {
  return (
    <S.MainContainer>
      <S.Contents>{children}</S.Contents>
    </S.MainContainer>
  );
};

export default Main;
