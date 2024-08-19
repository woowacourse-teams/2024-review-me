import OverviewImg1 from '@/assets/overview1.svg';
import OverviewImg2 from '@/assets/overview2.svg';
import OverviewImg3 from '@/assets/overview3.svg';
import WritingIcon from '@/assets/overviewTitle.svg';

import Carousel from '../Carousel';

import * as S from './styles';

const OVERVIEW_TITLE = '리뷰미 사용법';

const OVERVIEW_SLIDES = [
  {
    imageSrc: OverviewImg1,
    title: '리뷰 받는 사람 (You)',
    description: ['나의 리뷰 링크를 만들어요', '리뷰어에게 리뷰 링크를 보내요'],
  },
  {
    imageSrc: OverviewImg2,
    title: '리뷰 쓰는 사람',
    description: ['리뷰 링크에 접속해요', '리뷰 작성하기를 통해 리뷰를 작성해요'],
  },
  {
    imageSrc: OverviewImg3,
    title: '리뷰 받는 사람 (You)',
    description: ['리뷰 링크에 접속해요', '리뷰 확인을 위한 비밀번호를 입력해요', '받은 리뷰를 확인해요'],
  },
];

const ReviewMeOverview = () => {
  return (
    <S.ReviewMeOverview>
      <S.ColumnSectionContainer>
        <S.OverviewTitleContainer>
          <img src={WritingIcon} alt={OVERVIEW_TITLE} />
          <S.OverviewTitle>{OVERVIEW_TITLE}</S.OverviewTitle>
        </S.OverviewTitleContainer>
        <Carousel slides={OVERVIEW_SLIDES} pageWidth={32} pageHeight={40} gap={5} offset={3.6} />
      </S.ColumnSectionContainer>
    </S.ReviewMeOverview>
  );
};

export default ReviewMeOverview;
