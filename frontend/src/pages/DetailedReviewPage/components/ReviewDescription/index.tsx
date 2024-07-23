import { ProjectImg, ReviewDate } from '@/components';
import { ProjectImgProps } from '@/components/common/ProjectImg';
import { ReviewDateProps } from '@/components/common/ReviewDate';

import LockToggle from '../LockToggle';

import * as S from './styles';

const PROJECT_IMAGE_SIZE = '6rem';

interface ReviewDescriptionProps extends Omit<ProjectImgProps, '$size'>, Omit<ReviewDateProps, 'dateTitle'> {
  isLock: boolean;
  handleClickToggleButton: () => void;
}

const ReviewDescription = ({
  projectImgSrc,
  projectName,
  isLock,
  date,
  handleClickToggleButton,
}: ReviewDescriptionProps) => {
  return (
    <S.Description>
      <S.DescriptionSide>
        <ProjectImg projectImgSrc={projectImgSrc} projectName={projectName} $size={PROJECT_IMAGE_SIZE} />
        <S.ProjectNameAndDateContainer>
          <S.ProjectName>{projectName}</S.ProjectName>
          <ReviewDate date={date} dateTitle="리뷰 작성일" />
        </S.ProjectNameAndDateContainer>
      </S.DescriptionSide>
      <S.DescriptionSide>
        <LockToggle $isLock={isLock} handleClickToggleButton={handleClickToggleButton} />
      </S.DescriptionSide>
    </S.Description>
  );
};

export default ReviewDescription;
