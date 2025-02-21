import * as S from './styles';

interface NavItemProps {
  label: string;
  $isActiveTab: boolean;
  onClick: () => void;
}

const NavItem = ({ label, $isActiveTab, onClick }: NavItemProps) => {
  return (
    <S.NavItem $isActiveTab={$isActiveTab}>
      <button onClick={onClick}>{label}</button>
    </S.NavItem>
  );
};

export default NavItem;
