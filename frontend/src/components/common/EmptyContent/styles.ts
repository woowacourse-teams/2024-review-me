import styled from '@emotion/styled';

export const EmptyContent = styled.div<{ $iconMessageGap?: string }>`
  display: flex;
  flex-direction: column;
  gap: ${(props) => props.$iconMessageGap ?? '3.2rem'};
  align-items: center;
`;
interface ImgProps {
  $height?: string;
  $width?: string;
}
export const Img = styled.img<ImgProps>`
  aspect-ratio: 39/25;
  width: ${(props) => props.$width || 'auto'};
  height: ${(props) => props.$height || (props.$width ? 'auto' : '19.7rem')};
`;

export const MessageContainer = styled.div<{ $messageFontSize?: string }>`
  font-size: ${(props) => props.$messageFontSize ?? props.theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.emptyContentText};
`;
