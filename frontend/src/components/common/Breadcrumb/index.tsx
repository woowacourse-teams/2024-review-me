import React from 'react';
import { useNavigate } from 'react-router';

import * as S from './styles';

type PathType = string | number;

export interface Path {
  pageName: string;
  path: PathType;
}

interface BreadcrumbProps {
  paths: Path[];
}

const Breadcrumb = ({ paths }: BreadcrumbProps) => {
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
      {paths.map(({ pageName, path }, index) => (
        <S.BreadcrumbItem key={index} onClick={() => handleNavigation(path)}>
          {pageName}
        </S.BreadcrumbItem>
      ))}
    </S.BreadcrumbList>
  );
};

export default Breadcrumb;
