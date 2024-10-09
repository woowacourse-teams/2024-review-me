import React from 'react';

import * as S from './style';

interface EditSwitchButtonProps {
  isEditAble: boolean;
  handleEditToggleButton: () => void;
}

const EditSwitchButton: React.FC<EditSwitchButtonProps> = ({ isEditAble, handleEditToggleButton }) => {
  return (
    <S.EditSwitchButton $isEditAble={isEditAble} onClick={handleEditToggleButton}>
      <S.Circle $isEditAble={isEditAble} />
    </S.EditSwitchButton>
  );
};

export default EditSwitchButton;
