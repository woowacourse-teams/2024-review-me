import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

interface DataForURL {
  revieweeName: string;
  projectName: string;
}

export const postCreatedGroupDataApi = async (dataForURL: DataForURL) => {
  const response = await fetch(endPoint.postingCreatedGroupData, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(dataForURL),
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data;
};
