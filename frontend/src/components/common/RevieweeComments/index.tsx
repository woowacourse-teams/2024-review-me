import * as S from './styles';

interface RevieweeCommentProps {
  comment: string;
}

const DEFAULT_COMMENTS = '안녕하세요! 리뷰 잘 부탁드립니다.';

const RevieweeComments = ({ comment }: RevieweeCommentProps) => {
  return <S.RevieweeComments>{comment || DEFAULT_COMMENTS}</S.RevieweeComments>;
};

export default RevieweeComments;
