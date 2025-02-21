import { ReviewLinks } from '@/types';

// 변수명이 카멜 케이스인 이유는 리뷰 링크를 생성한 뒤, 새로 생성된 리뷰 링크를 확인하기 위함
export const reviewLinks: ReviewLinks = {
  lastReviewGroupId: 6,
  isLastPage: true,
  reviewGroups: [
    {
      revieweeName: '에프이',
      projectName: '리뷰미1',
      createdAt: '2025-02-05',
      reviewRequestCode: 'MEMBER1234',
      reviewCount: 3,
    },
    {
      revieweeName: '올리',
      projectName: '리뷰미2',
      createdAt: '2025-01-05',
      reviewRequestCode: 'MEMBER1234',
      reviewCount: 7,
    },
    {
      revieweeName: '바다',
      projectName: '리뷰미3',
      createdAt: '2024-11-05',
      reviewRequestCode: 'MEMBER1234',
      reviewCount: 5,
    },
    {
      revieweeName: '쑤쑤',
      projectName: '리뷰미4',
      createdAt: '2024-05-05',
      reviewRequestCode: 'MEMBER1234',
      reviewCount: 10,
    },
  ],
};
