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
            title="리뷰이"
            description={['리뷰 요청 URL을 만들어요', '리뷰어에게 리뷰 요청 URL을 보내요']}
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
          title="리뷰어"
          description={['리뷰 요청 URL에 접속해요', '리뷰 작성하기 버튼을 클릭해서 리뷰를 작성해요']}
        />

        <OverviewItem
          direction="column"
          imageSrc={OverviewImg3}
          title="리뷰이"
          description={[
            '리뷰 요청 URL에 접속해요',
            '리뷰 확인하기 버튼을 클릭하고 비밀번호를 입력해요',
            '받은 리뷰를 확인해요',
          ]}
        />
      </S.RowSectionContainer>
    </S.ReviewMeOverview>
  );
};

export default ReviewMeOverview;
