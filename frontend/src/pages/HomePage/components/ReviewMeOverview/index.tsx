import WritingIcon from '@/assets/overviewTitle.svg';
import UsageCarousel1Icon from '@/assets/usageCarousel1.svg';
import UsageCarousel2Icon from '@/assets/usageCarousel2.svg';
import UsageCarousel3Icon from '@/assets/usageCarousel3.svg';
import UsageCarousel4Icon from '@/assets/usageCarousel4.svg';

import InfinityCarousel, { Slide } from '../InfinityCarousel';

import * as S from './styles';

const OVERVIEW_TITLE = '리뷰미, 이렇게 사용해요';

const OVERVIEW_SLIDES_LIST: Slide[] = [
  {
    imageSrc: UsageCarousel1Icon,
    alt: '리뷰이가 리뷰를 요청하는 모습',
  },
  {
    imageSrc: UsageCarousel2Icon,
    alt: '리뷰어가 리뷰를 작성하는 모습',
  },
  {
    imageSrc: UsageCarousel3Icon,
    alt: '리뷰이가 리뷰 목록을 확인하는 모습',
  },
  {
    imageSrc: UsageCarousel4Icon,
    alt: '리뷰이가 리뷰를 모아보는 모습',
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
        <InfinityCarousel slideList={OVERVIEW_SLIDES_LIST} />
      </S.ColumnSectionContainer>
    </S.ReviewMeOverview>
  );
};

export default ReviewMeOverview;
