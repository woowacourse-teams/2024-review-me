import * as S from './styles';

interface NavItemProps {
  label: string;
  $isSelected: boolean;
  onClick: () => void;
}

const NavItem = ({ label, $isSelected, onClick }: NavItemProps) => {
  return (
    <S.NavItem $isSelected={$isSelected}>
      <button onClick={onClick}>{label}</button>
    </S.NavItem>
  );
};

export default NavItem;
