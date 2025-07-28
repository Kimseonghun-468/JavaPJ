import React, { useState, useEffect } from 'react';
import SockJS from "sockjs-client";
import Stomp from "stompjs";


function Account() {
  const [accounts, setAccounts] = useState([]);
  const [coinPrices, setCoinPrices] = useState({}); // BTC, ETH, XRP 가격 저장
  const [error, setError] = useState(null);
  const formatKRW = (price) =>
      price ? price.toLocaleString("ko-KR") + ' 원' : 'Loading...';
  useEffect(() => {
    // 1. 계정 정보 조회
    fetch('http://192.168.0.117:8080/api/selectAccount', {
      method: 'POST',
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('서버 응답 실패');
        }
        return response.json();
      })
      .then((data) => {
        setAccounts(data);
      })
      .catch((err) => {
        console.error('계정 조회 실패:', err);
        setError('계정 정보를 불러오는 데 실패했습니다.');
      });

    // 2. WebSocket 연결
    const socket = new SockJS('http://192.168.0.117:8080/ws'); // 서버에 맞게 경로 변경
    const client = Stomp.over(socket);

    client.connect({}, () => {
      console.log('WebSocket 연결 성공');

      // 서버가 보내는 CoinDTO 리스트를 받는 구독 경로를 /topic/AllCoin 으로 변경
      client.subscribe('/topic/AllCoin', (message) => {
        const coinList = JSON.parse(message.body);
        console.log(coinList)
        // { KRW-BTC: 162164000, KRW-ETH: 5271000, KRW-XRP: 4437 }
        const prices = {};
        coinList.forEach((coin) => {
          prices[coin.market] = coin.trade_price;
        });

        setCoinPrices(prices);
        console.log(prices)
      });
    });

    return () => {
      if (client.connected) {
        client.disconnect(() => {
          console.log('WebSocket 연결 종료');
        });
      }
    };
  }, []);

  return (
    <div>
      <h2>업비트 계정 정보</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}

      <h3>실시간 코인 시세</h3>
      <ul>
        <li>BTC: {formatKRW(coinPrices['KRW-BTC']) || '-'}</li>
        <li>ETH: {formatKRW(coinPrices['KRW-ETH']) || '-'}</li>
        <li>XRP: {formatKRW(coinPrices['KRW-XRP']) || '-'}</li>
      </ul>

      <h3>계정 보유 자산</h3>
      <ul>
        {/* KRW 먼저 출력 */}
          {accounts
            .filter(account => account.currency === 'KRW')
            .map((account, idx) => {
              const balance = Number(account.balance).toFixed(0);
              return (
                <li key={`krw-${idx}`}>
                  {account.currency} - 잔고: {formatKRW(balance)}
                </li>
              );
            })}

        {/* 그 외 코인들 출력 */}
        {accounts
          .filter(account => account.currency !== 'KRW')
          .map((account, idx) => {
            const currency = account.currency;
            const balance = Number(account.balance);
            const priceKey = `KRW-${currency}`;
            const price = coinPrices[priceKey] || 0;
            const balanceWon = (balance * price).toFixed(0);

            return (
              <li key={`coin-${idx}`}>
                {currency} - 잔고: {balance}
                {price > 0 ? ` (≈ ${formatKRW(balanceWon)} KRW)` : null}
              </li>
            );
          })}
      </ul>


    </div>
  );
}

export default Account;