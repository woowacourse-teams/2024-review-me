import React from 'react';
import { useNavigate } from 'react-router';

import rightArrow from '../../../assets/rightArrow.svg';

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
        <React.Fragment key={index}>
          <S.BreadcrumbItem onClick={() => navigate(path)}>{pageName}</S.BreadcrumbItem>
          {index < paths.length - 1 && <img src={rightArrow} alt="오른쪽 화살표" />}
        </React.Fragment>
      ))}
    </S.BreadcrumbList>
  );
};

export default Breadcrumb;
