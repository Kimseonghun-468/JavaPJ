import React from 'react';
import './TickerTable.css'; // 스타일 분리할 경우



const TickerTable = ({ data }) => {
  return (
    <div className="ticker-table-wrapper">
      <table className="ticker-table">
        <thead>
          <tr>
            <th>한글명</th>
            <th>현재가</th>
            <th>전일대비</th>
          </tr>
        </thead>
        <tbody>
          {data.map((coin, idx) => (
            <tr key={idx}>
              <td>
                <strong>{coin.name}</strong>
                <div className="symbol">{coin.symbol}</div>
              </td>
              <td>{coin.price.toLocaleString()}</td>
              <td className={coin.changeRate >= 0 ? 'up' : 'down'}>
                {coin.changeRate >= 0 ? '+' : ''}
                {coin.changeRate}%<br />
                <span>{coin.changeAmount >= 0 ? '+' : ''}{coin.changeAmount}</span>
              </td>
              <td>{coin.volume}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TickerTable;
