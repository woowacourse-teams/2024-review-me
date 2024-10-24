import { Link } from 'react-router-dom';

import * as S from './styles';

const Logo = () => {
  return (
    <S.Logo>
      <Link to={'/'} aria-label="리뷰미 홈페이지로 이동하기">
        <span aria-hidden="true">REVIEW</span>
        <span aria-hidden="true">ME</span>
      </Link>
    </S.Logo>
  );
};

export default Logo;
