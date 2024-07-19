import * as S from './styles';

const defaultText = '안녕하세요! 리뷰 잘 부탁드립니다.';

interface RevieweeCommentProps {
  text: string;
}

const RevieweeComment = ({ text }: RevieweeCommentProps) => {
  return <S.RevieweeComment>{text || defaultText}</S.RevieweeComment>;
};

export default RevieweeComment;
