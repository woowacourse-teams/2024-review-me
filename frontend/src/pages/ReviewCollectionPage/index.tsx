import { useEffect } from 'react';

import { AuthAndServerErrorFallback, ErrorSuspenseContainer, TopButton } from '@/components';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';
import { SESSION_STORAGE_KEY } from '@/constants';

import ReviewCollectionPageContents from './components/ReviewCollectionPageContents';

const ReviewCollectionPage = () => {
  const clearEditorAnswerMapStorage = () => {
    for (let i = 0; i < localStorage.length; i++) {
      const key = localStorage.key(i);

      // 키에 특정 문자열이 포함되어 있는지 확인
      if (key?.includes(SESSION_STORAGE_KEY.editorAnswerMap)) {
        localStorage.removeItem(key); // 해당 키 삭제
        i--; // removeItem 후에 인덱스가 변경되므로 i를 감소시켜야 함
      }
    }
  };

  useEffect(() => {
    return () => {
      clearEditorAnswerMapStorage();
    };
  }, []);

  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewDisplayLayout isReviewList={false}>
        <ReviewCollectionPageContents />
        <TopButton />
      </ReviewDisplayLayout>
    </ErrorSuspenseContainer>
  );
};

export default ReviewCollectionPage;
