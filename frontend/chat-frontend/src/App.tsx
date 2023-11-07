import styled from '@emotion/styled/macro';
import React, { useEffect, useRef, useState } from 'react';
import io, { Socket } from 'socket.io-client';

import { Message } from './components/Message';
import { messageConst } from './mock';
import { MessageType } from './types/Message.type';

function App() {
  const [messages, setMessages] = useState<MessageType[]>(messageConst);
  const [temp, setTemp] = useState<string>('');
  const socketRef = useRef<Socket>();
  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const socketIo = io('http://localhost:8082?', {
      transports: ['websocket'],
      autoConnect: true,
    });
    socketIo.on('connect', () => {
      console.log('connected');
    });
    socketRef.current = socketIo;
    socketIo.on('allMessages', (message) => {
      setMessages((prevMess) => [...prevMess, message]);
      scrollToBottom();
    });
    return () => {
      socketIo.disconnect();
      console.log('disconnect');
    };
  }, []);

  const sendMessage = () => {
    if (temp !== null && temp !== '' && socketRef.current !== undefined) {
      const msg: MessageType = {
        from: 'me',
        text: temp,
      };
      socketRef.current.emit('chat', msg);
      setTemp('');
    }
  };

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const messagesMarkup = messages.map((mes, index) => <Message key={index} user={mes.from} content={mes.text} />);
  return (
    <BoxChat>
      <BoxChatMessage>
        {messagesMarkup}
        <div ref={messagesEndRef} />
      </BoxChatMessage>
      <BoxChatSendMessage>
        <InputMessage value={temp} onChange={(e) => setTemp(e.target.value)} placeholder='Nhập tin nhắn ...' />
        <ButtonSend onClick={sendMessage}>Gửi</ButtonSend>
      </BoxChatSendMessage>
    </BoxChat>
  );
}

const BoxChat = styled.div`
  width: 500px;
  height: 600px;
  margin: 0 auto;
  border: 1px solid #282c34;
  display: flex;
  flex-direction: column;
  padding: 16px;
`;
const BoxChatMessage = styled.div`
  height: calc(600px - 7%);
  display: flex;
  flex-flow: column nowrap;
  overflow-y: auto;
`;
const BoxChatSendMessage = styled.div`
  display: flex;
`;
const InputMessage = styled.textarea`
  flex: 1;
  border-radius: 6px;
  padding: 4px;
  overflow: hidden;
`;
const ButtonSend = styled.button`
  width: 56px;
  margin-left: 16px;
  background-color: cornflowerblue;
  border: none;
  color: white;
  border-radius: 8px;
`;

export default App;
