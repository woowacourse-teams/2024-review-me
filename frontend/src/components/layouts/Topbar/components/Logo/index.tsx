import { Link } from 'react-router-dom';

import * as S from './styles';

const Logo = () => {
  return (
    <S.Logo>
      <Link to={'/'}>
        <span>REVIEW</span>
        <span>ME</span>
      </Link>
    </S.Logo>
  );
};

export default Logo;
