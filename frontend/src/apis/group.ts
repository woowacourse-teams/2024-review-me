import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

export interface DataForReviewRequestCode {
  revieweeName: string;
  projectName: string;
  groupAccessCode: string;
}

export const postDataForReviewRequestCodeApi = async (dataForReviewRequestCode: DataForReviewRequestCode) => {
  const response = await fetch(endPoint.postingDataForReviewRequestCode, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(dataForReviewRequestCode),
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data;
};
