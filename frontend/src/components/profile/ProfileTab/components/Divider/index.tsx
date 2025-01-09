import * as S from './styles';

interface DividerProps {
  isDisplayedOnlyMobile: boolean;
}

const Divider = ({ isDisplayedOnlyMobile }: DividerProps) => {
  return <S.Divider $isDisplayedOnlyMobile={isDisplayedOnlyMobile} />;
};

export default Divider;
