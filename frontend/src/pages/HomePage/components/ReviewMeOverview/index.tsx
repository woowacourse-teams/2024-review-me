import ArrowIcon from '@/assets/downArrow.svg';
import OverviewImg1 from '@/assets/overview1.svg';
import OverviewImg2 from '@/assets/overview2.svg';
import OverviewImg3 from '@/assets/overview3.svg';
import WritingIcon from '@/assets/overviewTitle.svg';

import { OverviewItem } from '../index';

import * as S from './styles';

const ReviewMeOverview = () => {
  return (
    <S.ReviewMeOverview>
      <S.RowSectionContainer>
        <S.ColumnSectionContainer>
          <S.OverviewTitleContainer>
            <img src={WritingIcon} alt="리뷰미 사용법" />
            <S.OverviewTitle>리뷰미 사용법</S.OverviewTitle>
          </S.OverviewTitleContainer>

          <OverviewItem
            direction="row"
            imageSrc={OverviewImg1}
            title="리뷰 받는 사람 (You)"
            description={['나의 리뷰 링크를 만들어요', '리뷰어에게 리뷰 링크를 보내요']}
          />
        </S.ColumnSectionContainer>
      </S.RowSectionContainer>

      <S.RowSectionContainer>
        <img src={ArrowIcon} alt="화살표 아이콘" />
      </S.RowSectionContainer>

      <S.RowSectionContainer>
        <OverviewItem
          direction="column"
          imageSrc={OverviewImg2}
          title="리뷰 쓰는 사람"
          description={['리뷰 링크에 접속해요', '리뷰 작성하기를 통해 리뷰를 작성해요']}
        />

        <OverviewItem
          direction="column"
          imageSrc={OverviewImg3}
          title="리뷰 받는 사람 (You)"
          description={['리뷰 링크에 접속해요', '리뷰 확인을 위한 비밀번호를 입력해요', '받은 리뷰를 확인해요']}
        />
      </S.RowSectionContainer>
    </S.ReviewMeOverview>
  );
};

export default ReviewMeOverview;
