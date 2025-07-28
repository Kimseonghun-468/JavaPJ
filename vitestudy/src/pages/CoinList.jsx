import React, { useState, useEffect } from 'react';
import SockJS from "sockjs-client";
import Stomp from "stompjs";

function CoinList() {
    const [prices, setPrices] = useState({
        ETH: null,
        BTC: null,
        XRP: null
    });

    useEffect(() => {
        const socket = new SockJS('http://192.168.0.117:8080/ws');
        const stompClient = Stomp.over(socket);

        const coins = ['ETH', 'BTC', 'XRP'];

        stompClient.connect({}, () => {
            coins.forEach((coin) => {
                stompClient.subscribe(`/topic/${coin}`, (message) => {
                    const newPrice = parseFloat(message.body); // ← 여기!
                    setPrices(prev => ({
                        ...prev,
                        [coin]: newPrice
                    }));
                });
            });
        }, (error) => {
            console.error('WebSocket 연결 실패:', error);
        });

        return () => {
            if (stompClient && stompClient.connected) {
                stompClient.disconnect();
            }
        };
    }, []);
    const formatKRW = (price) =>
        price ? price.toLocaleString("ko-KR") + ' 원' : 'Loading...';

    return (
        <div>
            <h1 className="text-3xl font-bold mb-6">📈 실시간 코인 시세</h1>
            <p>ETH Price: {formatKRW(prices.ETH)}</p>
            <p>BTC Price: {formatKRW(prices.BTC)}</p>
            <p>XRP Price: {formatKRW(prices.XRP)}</p>
        </div>

    );
}

export default CoinList;
