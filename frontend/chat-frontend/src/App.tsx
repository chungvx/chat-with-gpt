import React, {useEffect, useRef, useState} from 'react';
import styled from "@emotion/styled";
import { io } from 'socket.io-client';
import {MessageType} from "./types/Message.type";
import {Chip} from "./components/Chip";

function App() {
  const [messages, setMessages] = useState<MessageType[]>([]);
  const [temp, setTemp] = useState<string>("");

  useEffect(() => {
    const socket = io("http://localhost:8081" , {
      "timeout" : 10000,
      "transports" : ["websocket"],
      withCredentials:true,
      extraHeaders:{
        "my-custom-header": "abcd"
      }
    });
    socket.
  }, []);

  const messagesMarkup = messages.map((mes, index) => (
      <Chip key={index} children={mes.text} status={mes.from !== "me" ? "new" : "success"} />
  ));
  return (
    <BoxChat>
      <BoxChatMessage>{messagesMarkup}</BoxChatMessage>
      <BoxChatSendMessage>
        <InputMessage value={temp} onChange={(e) => setTemp(e.target.value)} placeholder="Nhập tin nhắn ..." />
        <ButtonSend onClick={()=> {}} >Gửi</ButtonSend>
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
`;
const BoxChatMessage = styled.div`
  flex: 1;
`;
const BoxChatSendMessage = styled.div`
  padding: 8px 16px;
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
