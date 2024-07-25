import styled from '@emotion/styled';

interface ImgProps {
  $size: string;
}

export const Img = styled.img<ImgProps>`
  width: ${(props) => props.$size};
  height: ${(props) => props.$size};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;
