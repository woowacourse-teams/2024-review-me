import { createContext } from 'react';

import { useReviewInfoData } from './hooks';

interface ReviewInfoData {
  revieweeName: string;
  projectName: string;
  totalReviewCount: number;
}

export const ReviewInfoDataContext = createContext<ReviewInfoData>({
  revieweeName: '',
  projectName: '',
  totalReviewCount: 0,
});

export const ReviewInfoDataProvider = ({ children }: { children: React.ReactNode }) => {
  const reviewInfoData = useReviewInfoData();

  return <ReviewInfoDataContext.Provider value={reviewInfoData}>{children}</ReviewInfoDataContext.Provider>;
};
