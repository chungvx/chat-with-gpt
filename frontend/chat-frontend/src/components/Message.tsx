import { css } from '@emotion/react/macro';
import styled from '@emotion/styled/macro';
import React from 'react';

import { Chip } from './Chip';

export interface MessageProps {
  user: string;
  content: string;
}

export function Message({ user, content }: MessageProps) {
  return (
    <MessageContainer user={user}>
      <Chip children={content} status={user !== 'me' ? 'new' : 'highlight'}></Chip>
    </MessageContainer>
  );
}

const MessageContainer = styled.div<{
  user: MessageProps['user'];
}>`
  width: max-content;
  max-width: 66%;
  margin-top: 8px;
  margin-bottom: 8px;
  ${(p) =>
    p.user === 'me' &&
    css`
      margin-left: auto;
      margin-right: 0;
    `}
`;
