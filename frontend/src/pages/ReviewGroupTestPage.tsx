import { useGetReviewGroupData, useSearchParamAndQuery } from '@/hooks';
// 아직 develop에 리뷰 연결 페이지가 머지되지 않아서 리뷰 그룹 정보를 잘 가져오는 지 확인하기 위한 테스트 페이지로, 삭제 예정입니다.
const ReviewGroupTestPage = () => {
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });
  if (!reviewRequestCode) throw new Error('유효하지 않은 리뷰 요청 코드입니다.');

  const { data } = useGetReviewGroupData({ reviewRequestCode });

  return (
    <div>
      <div>리뷰이:{data.revieweeName}</div>
      <div>프로젝트:{data.projectName}</div>
    </div>
  );
};

export default ReviewGroupTestPage;
