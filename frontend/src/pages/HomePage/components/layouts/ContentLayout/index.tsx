import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface ContentLayoutProps {
  title: string;
  subTitleList: string[];
}

const ContentLayout = ({ title, subTitleList, children }: EssentialPropsWithChildren<ContentLayoutProps>) => {
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

export default ContentLayout;
