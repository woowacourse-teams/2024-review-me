import React from 'react';
import { useNavigate } from 'react-router';

import PreventDrag from '../PreventDrag';

import * as S from './styles';

type PathType = string | number;

export interface Path {
  pageName: string;
  path: PathType;
}

interface BreadcrumbProps {
  pathList: Path[];
}

const Breadcrumb = ({ pathList }: BreadcrumbProps) => {
  const navigate = useNavigate();

  const handleNavigation = (path: PathType) => {
    if (typeof path === 'number') {
      navigate(path);
    } else {
      navigate(path);
    }
  };

  return (
    <S.BreadcrumbList>
      {pathList.map(({ pageName, path }, index) => (
        <S.BreadcrumbItem key={index} onClick={() => handleNavigation(path)}>
          <PreventDrag>{pageName}</PreventDrag>
        </S.BreadcrumbItem>
      ))}
    </S.BreadcrumbList>
  );
};

export default Breadcrumb;
