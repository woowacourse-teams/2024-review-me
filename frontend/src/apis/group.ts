import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

interface DataForURL {
  revieweeName: string;
  projectName: string;
}

export const getCreatedGroupDataApi = async (dataForURL: DataForURL) => {
  const response = await fetch(endPoint.gettingCreatedGroupData, {
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
