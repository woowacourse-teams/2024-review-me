import React from 'react';
import { useLocation, useNavigate } from 'react-router';

import { ROUTE } from '@/constants/route';

import UndraggableWrapper from '../UndraggableWrapper';

import * as S from './styles';

export interface Path {
  pageName: string;
  path: string;
}

interface BreadcrumbProps {
  pathList: Path[];
}

const Breadcrumb = ({ pathList }: BreadcrumbProps) => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleNavigation = (path: string) => {
    navigate(path, { replace: location.pathname.includes(`/${ROUTE.reviewWritingComplete}`) });
  };

  return (
    <S.BreadcrumbList>
      {pathList.map(({ pageName, path }, index) => (
        <S.BreadcrumbItem key={index} onClick={() => handleNavigation(path)}>
          <UndraggableWrapper>{pageName}</UndraggableWrapper>
        </S.BreadcrumbItem>
      ))}
    </S.BreadcrumbList>
  );
};

export default Breadcrumb;
