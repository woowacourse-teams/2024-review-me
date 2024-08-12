// import LogoIcon from '../../../../../assets/logo.svg';

import * as S from './styles';

const Logo = () => {
  return (
    <S.Logo>
      {/* <img src={LogoIcon} alt="로고 아이콘" /> */}
      <S.LogoText>
        <span>REVIEW</span>
        <span>ME</span>
      </S.LogoText>
    </S.Logo>
  );
};

export default Logo;
