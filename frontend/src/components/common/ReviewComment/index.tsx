import * as S from './styles';

interface ReviewCommentProps {
  comment: string;
}

const ReviewComments = ({ comment }: ReviewCommentProps) => {
  return <S.ReviewComment>{comment}</S.ReviewComment>;
};

export default ReviewComments;
