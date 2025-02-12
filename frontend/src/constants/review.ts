export const REVIEW = {
  answerMaxLength: 1000,
  answerMinLength: 20,
  keywordMaxCount: 5,
  keywordMinCount: 1,
};

export const REVIEW_MESSAGE = {
  answerMaxLength: `최대 ${REVIEW.answerMaxLength}자까지 입력 가능해요`,
};

export const REVIEW_EMPTY = {
  noReviewInTotal: '아직 받은 리뷰가 없어요...',
  noReviewLink: '생성한 리뷰 링크가 없어요...',
  noReviewInQuestion: '이 질문은 아직 받은 답변이 없어요...',
};

export const REVIEW_URL_GENERATOR_FORM_VALIDATION = {
  groupData: { min: 1, max: 50 },
  password: { min: 4, max: 20 },
};
