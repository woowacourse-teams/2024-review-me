import * as S from './style';

const Footer = () => {
  return (
    <S.Footer>
      <div>
        <S.Link href="https://github.com/woowacourse-teams/2024-review-me" aria-label="리뷰미 깃헙 저장소">
          ⓒ 2024 Review me All rights reserved
        </S.Link>
      </div>
      <div>
        {'Icons By '}
        <S.Link href="https://icons8.com/" aria-label="Icons8 홈페이지">
          Icons8
        </S.Link>
      </div>
    </S.Footer>
  );
};

export default Footer;
