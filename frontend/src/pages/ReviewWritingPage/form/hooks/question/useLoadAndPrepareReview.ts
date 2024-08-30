import useCardSectionList from './useCardSectionList';
import useGetDataToWrite from './useGetDataToWrite';

interface UseLoadAndPrepareReviewProps {
  reviewRequestCode: string | undefined;
}
/**
 * 서버에서 리뷰에 필요한 데이터를 받고 질문지와 리뷰 작성에 필요한 데이터를 반환하는 함수
 */
const useLoadAndPrepareReview = ({ reviewRequestCode }: UseLoadAndPrepareReviewProps) => {
  const { data } = useGetDataToWrite({ reviewRequestCode });
  const { revieweeName, projectName } = data;
  const { cardSectionList } = useCardSectionList({ cardSectionListData: data.sections });

  return {
    revieweeName,
    projectName,
    cardSectionList,
  };
};

export default useLoadAndPrepareReview;
