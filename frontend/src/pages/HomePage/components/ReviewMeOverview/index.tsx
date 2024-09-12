import WritingIcon from '@/assets/overviewTitle.svg';
import UsageCarousel1Icon from '@/assets/usageCarousel1.svg';
import UsageCarousel2Icon from '@/assets/usageCarousel2.svg';
import UsageCarousel3Icon from '@/assets/usageCarousel3.svg';

import InfinityCarousel, { Slide } from '../InfinityCarousel';

import * as S from './styles';

const OVERVIEW_TITLE = '리뷰미, 이렇게 사용해요';

const OVERVIEW_SLIDES_LIST: Slide[] = [
  {
    imageSrc: UsageCarousel1Icon,
    alt: '리뷰 받는 사람이 리뷰 링크를 생성하는 모습과 이에 대한 설명',
  },
  {
    imageSrc: UsageCarousel2Icon,
    alt: '리뷰 쓰는 사람이 리뷰 쓰는 모습과 이에 대한 설명',
  },
  {
    imageSrc: UsageCarousel3Icon,
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
        <InfinityCarousel slideList={OVERVIEW_SLIDES_LIST} />
      </S.ColumnSectionContainer>
    </S.ReviewMeOverview>
  );
};

export default ReviewMeOverview;
