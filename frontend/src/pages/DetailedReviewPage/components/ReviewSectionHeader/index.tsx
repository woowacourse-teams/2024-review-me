import * as S from './styles';

interface ReviewSectionHeaderProps {
  text: string;
}

const ReviewSectionHeader = ({ text }: ReviewSectionHeaderProps) => {
  return <S.ReviewSectionHeader>{text}</S.ReviewSectionHeader>;
};

export default ReviewSectionHeader;
