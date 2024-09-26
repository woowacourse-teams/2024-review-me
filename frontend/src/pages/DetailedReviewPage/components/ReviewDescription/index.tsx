import ReviewDate, { ReviewDateProps } from '@/components/common/ReviewDate';

import * as S from './styles';

const DATE_TITLE = '리뷰 작성일';

interface ReviewDescriptionProps extends Omit<ReviewDateProps, 'dateTitle'> {
  projectName: string;
  isPublic: boolean;
  revieweeName: string;
  handleClickToggleButton: () => void;
}

const ReviewDescription = ({ projectName, revieweeName, date }: ReviewDescriptionProps) => {
  return (
    <S.Description>
      <S.DescriptionSide>
        <S.ProjectInfoContainer>
          <S.ProjectName>{projectName}</S.ProjectName>
          <S.RevieweeNameAndDateContainer>
            <S.RevieweeNameWrapper>
              <S.RevieweeName>{revieweeName}</S.RevieweeName>에 대한 리뷰예요
            </S.RevieweeNameWrapper>
            <ReviewDate date={date} dateTitle={DATE_TITLE} />
          </S.RevieweeNameAndDateContainer>
        </S.ProjectInfoContainer>
      </S.DescriptionSide>
    </S.Description>
  );
};

export default ReviewDescription;
