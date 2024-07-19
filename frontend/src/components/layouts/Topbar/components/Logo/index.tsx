import LogoIcon from '../../../../../assets/logo.svg';

import * as S from './styles';

const Logo = () => {
  return (
    <S.Logo>
      <img src={LogoIcon} alt="로고 아이콘" />
      <S.LogoText>
        <span>review</span>
        <span>me</span>
      </S.LogoText>
    </S.Logo>
  );
};

export default Logo;
