import formatKeyword from '@/utils/formatKeyword';

import * as S from './styles';

const ReviewKeyword = ({ content }: { content: string }) => {
  const formattedKeyword = formatKeyword(content);

  return <S.ReviewKeyword>{formattedKeyword}</S.ReviewKeyword>;
};

export default ReviewKeyword;
