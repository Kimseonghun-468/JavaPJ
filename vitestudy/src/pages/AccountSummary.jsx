import React from 'react';

const AccountSummary = ({ summary }) => {
  // 소수점 이하 제거하는 헬퍼 함수
  const formatNumber = (num) =>
    num.toLocaleString().replace(/(\.\d+)?$/, '');

  return (
    <div className="asset-summary">
      <h3>보유자산</h3>
      <div className="asset-summary-grid">
        <div className="asset-summary-label">보유 KRW</div>
        <div className="asset-summary-value">{formatNumber(summary.krwBalance)} KRW</div>

        <div className="asset-summary-label">총 보유자산</div>
        <div className="asset-summary-value">{formatNumber(summary.totalAsset)} KRW</div>

        <div className="asset-summary-label">총 매수</div>
        <div className="asset-summary-value">{formatNumber(summary.totalBuy)} KRW</div>

        <div className="asset-summary-label">총 평가손익</div>
        <div className={`asset-summary-value ${summary.totalProfit >= 0 ? 'profit' : 'loss'}`}>
          {summary.totalProfit >= 0 ? '+' : ''}
          {formatNumber(summary.totalProfit)} KRW
        </div>

        <div className="asset-summary-label">총 평가</div>
        <div className="asset-summary-value">{formatNumber(summary.totalEval)} KRW</div>

        <div className="asset-summary-label">총 수익률</div>
        <div className="asset-summary-value">{summary.totalProfitRate.toFixed(3)}%</div>

        <div className="asset-summary-label">주문가능</div>
        <div className="asset-summary-value">{formatNumber(summary.orderPossible)} KRW</div>
      </div>
    </div>
  );
};

export default AccountSummary;
