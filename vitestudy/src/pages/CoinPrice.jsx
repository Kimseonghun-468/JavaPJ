import React, { useState, useEffect } from 'react';
import { useParams } from "react-router-dom";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

function CoinPrice() {
    const { id } = useParams();
    const [price, setPrice] = useState(null);

    let content;
    if(id.toUpperCase() === 'BTC'){
        content = '비트코인 가격 정보';
    }
    else if (id.toUpperCase() === 'XRP')
        content = '리플 가격 정보';
    else
        return (
            <div>
                <h1>지원하지 않는 코인입니다.</h1>
            </div>
        );

    // eslint-disable-next-line react-hooks/rules-of-hooks
    useEffect(() => {
        const socket = new SockJS('http://192.168.0.117:8080/ws');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            stompClient.subscribe(`/topic/` + id.toUpperCase(), (message) => {
                setPrice(message.body);
            });
        }, (error) => {
            console.error('WebSocket 연결 실패:', error);
        });

        return () => {
          if (stompClient && stompClient.connected) {
            stompClient.disconnect();
          }
        };
    }, [id]);
    return (
        <div>
            <h1>{content}</h1>
            <p>Price: {price ? price : "Loading..."}</p>
        </div>
    );
}

export default CoinPrice;
