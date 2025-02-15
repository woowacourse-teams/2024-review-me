import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

interface GetOAuthLoginApiProps {
  gitHubAuthCode: string;
}

export const postOAuthLoginApi = async ({ gitHubAuthCode }: GetOAuthLoginApiProps) => {
  const response = await fetch(endPoint.postingOAuthLogin(gitHubAuthCode), {
    // TODO: 아직 api 구현이 완료되지 않은 관계로 일단 get으로 하고, 추후 post로 변경
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  return {};
};
