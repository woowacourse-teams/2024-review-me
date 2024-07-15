import LockButton from '../LockButton';
import { Contents, Clone, ListItem, Title, Description, ProjectAndLockButtonContainer } from './styles';

interface ReviewDescriptionItemProps {
  title: string;
  contents: string;
}
const ReviewDescriptionItem = ({ title, contents }: ReviewDescriptionItemProps) => {
  return (
    <ListItem>
      <Title>{title}</Title>
      <Clone>:</Clone>
      <Contents>{contents}</Contents>
    </ListItem>
  );
};

interface ReviewDescriptionProps {
  projectName: string;
  dateCreated: string;
  isLock: boolean;
}
const ReviewDescription = ({ projectName, dateCreated, isLock }: ReviewDescriptionProps) => {
  return (
    <Description>
      <ProjectAndLockButtonContainer>
        <ReviewDescriptionItem title="프로젝트명" contents={projectName} />
        <LockButton isLock={isLock} onClick={() => console.log('lock')} />
      </ProjectAndLockButtonContainer>

      <ReviewDescriptionItem title="작성일" contents={dateCreated} />
    </Description>
  );
};

export default ReviewDescription;
