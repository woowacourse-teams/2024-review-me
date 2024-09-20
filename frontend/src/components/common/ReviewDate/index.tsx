import ClockIcon from '@/assets/clock.svg';
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
      <S.ReviewDateText>
        <S.ClockImg src={ClockIcon} alt="시계 아이콘" />
        <span>{dateTitle}</span>
        <S.Colon>:</S.Colon>
      </S.ReviewDateText>
      <span>
        {year}-{month}-{day}
      </span>
    </S.ReviewDate>
  );
};

export default ReviewDate;
