import React from 'react';
import { useNavigate } from 'react-router';

import * as S from './styles';

interface Path {
  pageName: string;
  path: string;
}

interface BreadcrumbProps {
  paths: Path[];
}

const Breadcrumb = ({ paths }: BreadcrumbProps) => {
  const navigate = useNavigate();

  return (
    <S.BreadcrumbList>
      {paths.map(({ pageName, path }, index) => (
        <S.BreadcrumbItem key={index} onClick={() => navigate(path)}>
          {pageName}
        </S.BreadcrumbItem>
      ))}
    </S.BreadcrumbList>
  );
};

export default Breadcrumb;
