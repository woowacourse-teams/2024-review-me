import { useMemo } from 'react';

import { ReviewWritingCardSection } from '@/types';
import { substituteString } from '@/utils';

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

  // 리뷰 작성 폼에서 사용할 질문의 문자열 치환
  const parsedDataSection: ReviewWritingCardSection[] = useMemo(() => {
    return data.sections.map((section) => {
      const parsedHeader = substituteString({
        content: section.header,
        variables: { revieweeName, projectName },
      });

      const parsedQuestions = section.questions.map((question) => {
        const parsedContent = substituteString({
          content: question.content,
          variables: { revieweeName, projectName },
        });

        const parsedGuideline = question.guideline
          ? substituteString({
              content: question.guideline,
              variables: { revieweeName, projectName },
            })
          : null;

        return {
          ...question,
          content: parsedContent,
          guideline: parsedGuideline,
        };
      });

      return {
        ...section,
        header: parsedHeader,
        questions: parsedQuestions,
      };
    });
  }, [data.sections, revieweeName, projectName]);

  const { cardSectionList } = useCardSectionList({ cardSectionListData: parsedDataSection });

  return {
    revieweeName,
    projectName,
    cardSectionList,
  };
};

export default useLoadAndPrepareReview;
