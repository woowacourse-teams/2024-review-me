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
  const newDataSection: ReviewWritingCardSection[] = useMemo(() => {
    return data.sections.map((section) => {
      const newHeader = substituteString({
        content: section.header,
        variables: { revieweeName, projectName },
      });

      const newQuestions = section.questions.map((question) => {
        const newContent = substituteString({
          content: question.content,
          variables: { revieweeName, projectName },
        });

        const newGuideline = question.guideline
          ? substituteString({
              content: question.guideline,
              variables: { revieweeName, projectName },
            })
          : null;

        return {
          ...question,
          content: newContent,
          guideline: newGuideline,
        };
      });

      return {
        ...section,
        header: newHeader,
        questions: newQuestions,
      };
    });
  }, [data.sections, revieweeName, projectName]);

  const { cardSectionList } = useCardSectionList({ cardSectionListData: newDataSection });

  return {
    revieweeName,
    projectName,
    cardSectionList,
  };
};

export default useLoadAndPrepareReview;
