import * as S from './styles';

interface ReviewSectionHeaderProps {
  number: number;
  text: string;
}

const ReviewSectionHeader = ({ number, text }: ReviewSectionHeaderProps) => {
  return (
    <S.ReviewSectionHeader>
      {number}. {text}
    </S.ReviewSectionHeader>
  );
};

export default ReviewSectionHeader;
