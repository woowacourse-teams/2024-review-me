import * as S from './style';

const Footer = () => {
  return (
    <S.Footer>
      <div>
        <S.Link href="https://github.com/woowacourse-teams/2024-review-me" aria-label="리뷰미 깃헙 저장소">
          © 20204-review-me
        </S.Link>
      </div>
      <div>
        Icon By &nbsp;
        <S.Link href="https://icons8.com/" aria-label="icon8 홈페이지">
          icon8
        </S.Link>
      </div>
    </S.Footer>
  );
};

export default Footer;
