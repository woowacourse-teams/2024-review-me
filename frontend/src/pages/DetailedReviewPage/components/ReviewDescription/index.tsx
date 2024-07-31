import { ProjectImg, ReviewDate } from '@/components';
import { ProjectImgProps } from '@/components/common/ProjectImg';
import { ReviewDateProps } from '@/components/common/ReviewDate';

import LockToggle from '../LockToggle';

import * as S from './styles';

const PROJECT_IMAGE_SIZE = '6rem';
const DATE_TITLE = '리뷰 작성일';

interface ReviewDescriptionProps extends Omit<ProjectImgProps, '$size'>, Omit<ReviewDateProps, 'dateTitle'> {
  isPublic: boolean;
  handleClickToggleButton: () => void;
}

const ReviewDescription = ({
  thumbnailUrl,
  projectName,
  isPublic,
  date,
  handleClickToggleButton,
}: ReviewDescriptionProps) => {
  return (
    <S.Description>
      <S.DescriptionSide>
        <ProjectImg thumbnailUrl={thumbnailUrl} projectName={projectName} $size={PROJECT_IMAGE_SIZE} />
        <S.ProjectNameAndDateContainer>
          <S.ProjectName>{projectName}</S.ProjectName>
          <ReviewDate date={date} dateTitle={DATE_TITLE} />
        </S.ProjectNameAndDateContainer>
      </S.DescriptionSide>
      {/* 시현 때 숨김 <S.DescriptionSide>
        <LockToggle $isPublic={isPublic} handleClickToggleButton={handleClickToggleButton} />
      </S.DescriptionSide> */}
    </S.Description>
  );
};

export default ReviewDescription;
