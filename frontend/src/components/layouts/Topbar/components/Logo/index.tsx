// import LogoIcon from '../../../../../assets/logo.svg';

import { useNavigate } from 'react-router';

import * as S from './styles';

const Logo = () => {
  const navigate = useNavigate();

  const handleLogoClick = () => {
    navigate('/');
  };

  return (
    <S.Logo>
      {/* <img src={LogoIcon} alt="로고 아이콘" /> */}
      <S.LogoText onClick={handleLogoClick}>
        <span>REVIEW</span>
        <span>ME</span>
      </S.LogoText>
    </S.Logo>
  );
};

export default Logo;
