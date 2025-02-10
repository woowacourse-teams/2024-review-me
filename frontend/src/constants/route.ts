import { makeRoutePath } from '@/utils';

export const ROUTE = {
  home: '/',
  reviewList: makeRoutePath('review-list'),
  reviewWriting: makeRoutePath('review-writing'),
  reviewWritingComplete: makeRoutePath('review-writing-complete'),
  detailedReview: makeRoutePath('detailed-review'),
  reviewZone: makeRoutePath('review-zone'),
  reviewCollection: makeRoutePath('review-collection'),
  reviewLinks: makeRoutePath('review-links', true),
  writtenReview: makeRoutePath('written-review', true),
};
