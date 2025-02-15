import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

interface GetOAuthLoginApiProps {
  gitHubAuthCode: string;
}

export const postOAuthLoginApi = async ({ gitHubAuthCode }: GetOAuthLoginApiProps) => {
  const response = await fetch(endPoint.postingOAuthLogin(gitHubAuthCode), {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  return {};
};
