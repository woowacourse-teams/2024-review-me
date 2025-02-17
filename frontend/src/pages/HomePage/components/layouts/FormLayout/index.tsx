import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface FormLayoutProps {
  title: string;
  subTitleList: string[];
}

const FormLayout = ({ title, subTitleList, children }: EssentialPropsWithChildren<FormLayoutProps>) => {
  return (
    <S.LoginForm>
      <S.Title>{title}</S.Title>
      <S.SubTitleWrapper>
        {subTitleList.map((subTitle) => (
          <S.SubTitle key={subTitle}>{subTitle}</S.SubTitle>
        ))}
      </S.SubTitleWrapper>
      {children}
    </S.LoginForm>
  );
};

export default FormLayout;
