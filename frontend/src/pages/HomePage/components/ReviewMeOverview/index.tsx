import WritingIcon from '@/assets/overviewTitle.svg';
import UsageCarosel1Icon from '@/assets/usageCarosel1.svg';
import UsageCarosel2Icon from '@/assets/usageCarosel2.svg';
import UsageCarosel3Icon from '@/assets/usageCarosel3.svg';

import Carousel, { Slide } from '../Carousel';

import * as S from './styles';

const OVERVIEW_TITLE = '리뷰미, 이렇게 사용해요';

const OVERVIEW_SLIDES_LIST: Slide[] = [
  {
    imageSrc: UsageCarosel1Icon,
    alt: '리뷰 받는 사람이 리뷰 링크를 생성하는 모습과 이에 대한 설명',
  },
  {
    imageSrc: UsageCarosel2Icon,
    alt: '리뷰 쓰는 사람이 리뷰 쓰는 모습과 이에 대한 설명',
  },
  {
    imageSrc: UsageCarosel3Icon,
    alt: '리뷰 받는 사람이 받은 리뷰를 확인하는 모습',
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
        <Carousel slides={OVERVIEW_SLIDES_LIST} />
      </S.ColumnSectionContainer>
    </S.ReviewMeOverview>
  );
};

export default ReviewMeOverview;
