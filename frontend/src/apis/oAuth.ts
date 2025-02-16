import { UserProfile } from '@/types/profile';

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

export const getUserProfileApi = async () => {
  try {
    const response = await fetch(endPoint.gettingUserProfile, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    });

    if (!response.ok) return null;

    const data = await response.json();
    return data as UserProfile;
  } catch (error) {
    return null;
  }
};

export const postOAuthLogoutApi = async () => {
  const response = await fetch(endPoint.postingOAuthLogout, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  return {};
};
