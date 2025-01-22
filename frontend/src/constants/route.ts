import { gettingPath } from '@/utils';

export const ROUTE = {
  home: '/',
  reviewList: gettingPath('review-list'),
  reviewWriting: gettingPath('review-writing'),
  reviewWritingComplete: gettingPath('review-writing-complete'),
  detailedReview: gettingPath('detailed-review'),
  reviewZone: gettingPath('review-zone'),
  reviewCollection: gettingPath('review-collection'),
  reviewLinks: gettingPath('review-links', true),
  writtenReview: gettingPath('written-review', true),
};
