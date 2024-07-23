import LockButton from '../LockButton';

import * as S from './styles';

interface ReviewDescriptionItemProps {
  title: string;
  contents: string;
}
const ReviewDescriptionItem = ({ title, contents }: ReviewDescriptionItemProps) => {
  return (
    <S.ListItem>
      <S.Title>{title}</S.Title>
      <S.Clone>:</S.Clone>
      <S.Contents>{contents}</S.Contents>
    </S.ListItem>
  );
};

interface ReviewDescriptionProps {
  projectName: string;
  createdAt: Date;
  isLock: boolean;
}



const ReviewDescription = ({ projectName, createdAt, isLock }: ReviewDescriptionProps) => {
  return (
    <S.Description>
      <S.ProjectAndLockButtonContainer>
        <ReviewDescriptionItem title="프로젝트명" contents={projectName} />
        <LockButton isLock={isLock} onClick={() => console.log('lock')} />
      </S.ProjectAndLockButtonContainer>

      <ReviewDescriptionItem title="작성일" contents={formatDate(createdAt)} />
    </S.Description>
  );
};

export default ReviewDescription;
