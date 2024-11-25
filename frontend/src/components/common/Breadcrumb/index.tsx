import { Link } from 'react-router-dom';

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
  return (
    <nav>
      <S.BreadcrumbList>
        {pathList.map(({ pageName, path }, index) => (
          <S.BreadcrumbItem key={index}>
            <UndraggableWrapper>
              <Link to={{ pathname: path }}>{pageName}</Link>
            </UndraggableWrapper>
          </S.BreadcrumbItem>
        ))}
      </S.BreadcrumbList>
    </nav>
  );
};

export default Breadcrumb;
