import React from 'react';

import * as S from './style';

interface EditSwitchButtonProps {
  isEditable: boolean;
  handleEditToggleButton: () => void;
}

const EditSwitchButton: React.FC<EditSwitchButtonProps> = ({ isEditable, handleEditToggleButton }) => {
  return (
    <S.EditSwitchButton $isEditable={isEditable} onClick={handleEditToggleButton}>
      <S.Circle $isEditable={isEditable} />
    </S.EditSwitchButton>
  );
};

export default EditSwitchButton;
