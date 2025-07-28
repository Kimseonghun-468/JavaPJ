import React from "react";

const AccountTable = ({ assets }) => {
    const formatNumber = (num) =>
      num.toLocaleString().replace(/(\.\d+)?$/, '');
  return (
    <table className="portfolio-table" style={{ width: "100%", borderCollapse: "collapse" }}>
      <thead>
        <tr>
          <th>보유자산</th>
          <th>보유수량</th>
          <th>매수평균가</th>
          <th>매수금액</th>
          <th>평가금액</th>
          <th>평가손익(%)</th>
          <th>평가손익</th>
        </tr>
      </thead>
      <tbody>
        {assets.map((asset) => (
          <tr key={asset.currency} style={{ borderBottom: "1px solid #eee" }}>
            <td>{asset.currency}</td>
            <td>
              {asset.balance.toFixed(8)} {asset.currency}
            </td>
            <td>{formatNumber(asset.avgBuyPrice.toLocaleString())} KRW</td>
            <td>{formatNumber(asset.buyAmount.toLocaleString())} KRW</td>
            <td>{formatNumber(asset.evalAmount.toLocaleString())} KRW</td>
            <td style={{ color: asset.profitRate >= 0 ? "red" : "blue" }}>
              {asset.profitRate >= 0 ? "+" : ""}
              {asset.profitRate.toFixed(2)} %
            </td>
            <td style={{ color: asset.profit >= 0 ? "red" : "blue" }}>
              {asset.profit >= 0 ? "+" : ""}
              {formatNumber(asset.profit.toLocaleString())} KRW
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default AccountTable;
