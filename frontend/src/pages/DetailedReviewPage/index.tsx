import { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { DetailReviewData } from '@/types';

import { getDetailedReviewApi } from '../../apis/review';

import ReviewDescription from './components/ReviewDescription';
import ReviewViewSection from './components/ReviewViewSection';

const ANSWER =
  '림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. \n 바람은 여전히 그를 감싸며, 그의 마음 속 깊은 곳에 있는 꿈과 희망을 불러일으켰습니다.\n 림순은 미소 지으며 앞으로 나아갔습니다.림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. ';


const COMMENT = 'VITE 쓰고 싶다.';

const DetailedReviewPage = ({}) => {
  const { id } = useParams<{ id: string }>();

  const [detailReview, setDetailReview] = useState<DetailReviewData>(MOCK_DATA);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string>('');

  useEffect(() => {
    const fetchReview = async () => {
      try {
        const result = await getDetailedReviewApi({ reviewId: Number(id), memberId: 4 });

        setDetailReview(result);
        setErrorMessage('');
      } catch (error) {
        setErrorMessage('리뷰를 불러오는 데 실패했습니다.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchReview();
  }, [id]);

  if (isLoading) return <div>Loading...</div>;

  if (errorMessage) return <div>Error: {errorMessage}</div>;
  if (!detailedReview) return <div>Error: 상세보기 리뷰 데이터를 가져올 수 없어요.</div>;

  return (
    <S.DetailedReviewPage>
      <ReviewDescription
        projectName={detailedReview.reviewerGroup.name}
        date={new Date(detailedReview.reviewerGroup.deadline)}
        isPublic={true}
        handleClickToggleButton={() => console.log('click toggle ')}
      />
      <ReviewComment comment={COMMENT} />
      {detailedReview.contents.map((item, index) => (
        <ReviewSection question={item.question} answer={item.answer} key={index} index={index} />
      ))}
      <KeywordSection keywords={detailedReview.keywords} index={detailedReview.contents.length} />
    </S.DetailedReviewPage>
  );
};

export default DetailedReviewPage;
