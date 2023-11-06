import { css } from '@emotion/react/macro';
import styled from '@emotion/styled/macro';
import React from 'react';

export interface ChipProps {
  /**
   * Trạng thái của chip
   * @default "default"
   * */
  status?: 'success' | 'warning' | 'critical' | 'highlight' | 'new' | 'default';
  /** Trạng thái complete của chip, show nút tròn thể hiện điều đó */
  progress?: 'incomplete' | 'partiallyComplete' | 'complete';
  /** Kích thước của chip */
  size?: 'small' | 'medium';
  /** Nội dung để hiển thị bên trong chip */
  children?: string;
}

/**
 * Thường được dùng để hiện thị trạng thái của đối tượng
 */
export function Chip({ status = 'default', children, progress, size = 'medium' }: ChipProps) {
  const pipMarkup = progress ? (
    <StyledPipContainer>
      <StyledPip progress={progress} />
    </StyledPipContainer>
  ) : null;

  return (
    <StyledChip status={status} $size={size}>
      {pipMarkup}
      {children}
    </StyledChip>
  );
}

const StyledPip = styled.span<{
  progress: ChipProps['progress'];
}>`
  display: inline-block;
  color: #a3a8af;
  height: 8px;
  width: 8px;
  border: 1px solid #a3a8af;
  border-radius: 9999px;
  flex-shrink: 0;
  ${(p) => {
    switch (p.progress) {
      case 'complete':
        return css`
          background: currentColor;
        `;
      case 'partiallyComplete':
        return css`
          background: linear-gradient(to top, currentColor, currentColor 50%, transparent 50%, transparent);
        `;
      default:
        return css`
          background: transparent;
        `;
    }
  }}
`;

const StyledChip = styled.span<{
  status: ChipProps['status'];
  $size: ChipProps['size'];
}>`
  display: inline-flex;
  align-items: center;
  padding: 4px 16px;
  background-color: #eeefef;
  outline: 1px solid #eeefef;
  outline-offset: -1px;
  border-radius: 5px;
  color: #0f1824;
  ${(p) => {
    let bgColor = null;
    let textColor = null;
    let borderColor = null;
    switch (p.status) {
      case 'success':
        bgColor = '#F3FCF9';
        textColor = '#747C87';
        borderColor = '#9FEDCF';
        break;
      case 'warning':
        bgColor = '#FFFBF2';
        textColor = '#E49C06';
        borderColor = '#FFDF9B';
        break;
      case 'critical':
        bgColor = '#FFF6F6';
        textColor = '#FFB8B8';
        borderColor = '#EE4747';
        break;
      case 'highlight':
        bgColor = '#F2F9FF';
        textColor = '#007CE8';
        borderColor = '#99CFFF';
        break;
      case 'new':
        break;
      default:
        bgColor = '#F3F4F5';
        textColor = '#747C87';
        borderColor = '#D3D5D7';
        break;
    }
    return bgColor
      ? css`
          background-color: ${bgColor};
          color: ${textColor};
          outline-color: ${borderColor};
          ${StyledPip} {
            color: ${textColor};
            border-color: ${textColor};
          }
        `
      : css``;
  }}
  ${(p) =>
    p.$size === 'small' &&
    css`
      padding: 2px 8px;
    `}
`;

const StyledPipContainer = styled.span`
  display: grid;
  align-items: center;
  margin-left: 1px;
  margin-right: 4px;
`;
