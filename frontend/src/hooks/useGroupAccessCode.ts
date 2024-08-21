import { useRecoilState } from 'recoil';

import { groupAccessCodeAtom } from '@/recoil';

const useGroupAccessCode = () => {
  const [groupAccessCode, setGroupAccessCode] = useRecoilState(groupAccessCodeAtom);

  const updateGroupAccessCode = (code: string) => {
    setGroupAccessCode(code);
  };

  return {
    groupAccessCode,
    updateGroupAccessCode,
  };
};

export default useGroupAccessCode;
