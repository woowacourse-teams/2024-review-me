import { formatDate } from '@/utils';

import * as S from './styles';

export interface ReviewDateProps {
  date: Date;
  dateTitle: string;
}

const ReviewDate = ({ date, dateTitle }: ReviewDateProps) => {
  const { year, month, day } = formatDate(date);

  return (
    <S.ReviewDate>
      <span>
        {dateTitle} | {year}.{month}.{day}
      </span>
    </S.ReviewDate>
  );
};

export default ReviewDate;
