import GithubLogoIcon from '@/assets/githubLogo.svg';

import * as S from './styles';

export interface ProjectImgProps {
  projectImgSrc?: string;
  projectName: string;
  $size: string;
}
const ProjectImg = ({ projectImgSrc, projectName, $size }: ProjectImgProps) => {
  const src = projectImgSrc ?? GithubLogoIcon;
  const alt = projectImgSrc ? `${projectName} 저장소 이미지` : '깃허브 로고';
  return <S.Img src={src} alt={alt} $size={$size} />;
};

export default ProjectImg;
