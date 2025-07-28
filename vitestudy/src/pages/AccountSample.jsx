import React, {useState, useEffect} from 'react';
import {PieChart, Pie, Cell, Tooltip, Legend} from 'recharts';
import './Account.css'
import './Dashboard.css'
import TickerTable from './TickerTable';
import AccountSummary from "./AccountSummary.jsx";
import AccountChart from "./AccountChart.jsx";
import AccountTable from "./AccountTable.jsx";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

const socket = new SockJS('http://192.168.0.117:8080/ws'); // 서버에 맞게 경로 변경
const client = Stomp.over(socket);

const summary = {
    krwBalance: 0,
    totalAsset: 0,
    totalBuy: 0,
    totalEval: 0,
    totalProfit: 0,
    totalProfitRate: 0,
    orderPossible: 0,
};

const formatKRW = (price) =>
    price ? price.toLocaleString("ko-KR") + ' 원' : 'Loading...';

const dummyAssets = [
    {
        currency: 'BTC',
        balance: 0.00004648,
        avgBuyPrice: 162372000,
        buyAmount: 7548,
        evalAmount: 7543,
        profitRate: -0.05,
        profit: -4,
    },
    {
        currency: 'ETH',
        balance: 0.00094607,
        avgBuyPrice: 5286000,
        buyAmount: 5001,
        evalAmount: 5018,
        profitRate: 0.36,
        profit: 18,
    },
];

let assets = [];

const pieData = [
    {name: 'BTC', value: 37.5, color: '#9b4dca'},
    {name: 'KRW', value: 37.5, color: '#5f8ae8'},
    {name: 'ETH', value: 25, color: '#afdbfb'},
];

function convertSymbol(symbol) {
    const data = symbol.split('-');
    return data[1] + '/' + data[0];
}

function convertToKrName(symbol) {
    const data = symbol.split('-')[1];
    let krName;
    if (data === 'BTC') {
        krName = "비트코인";
    } else if (data === 'XRP') {
        krName = "엑스알피(리플)";
    } else if (data === "ETH") {
        krName = "이더리움";
    }
    return krName
}


function AccountSample() {
    const [accounts, setAccounts] = useState({});
    const [coinPrices, setCoinPrices] = useState([]);
    const [chartPies, setChartPies] = useState([]);
    const [error, setError] = useState(null);
    // 1. 계정 정보 조회
    useEffect(() => {
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
                summary.krwBalance = data.filter(account => account.currency === 'KRW')[0]?.balance;
                summary.totalBuy = data
                  .filter(account => account.currency !== 'KRW')
                  .reduce((acc, account) => {
                    const balance = Number(account.balance);
                    const avgBuyPrice = Number(account.avgBuyPrice);
                    return acc + balance * avgBuyPrice
                  }, 0)
                summary.orderPossible = summary.krwBalance - data.filter(account => account.currency === 'KRW')[0]?.locked;

                assets = []
                data.forEach(item => {
                  if (item.currency !== 'KRW') {
                    assets.push({
                      currency: item.currency,
                      balance: item.balance,
                      avgBuyPrice: item.avgBuyPrice,
                      buyAmount: item.balance * item.avgBuyPrice,
                      evalAmount: 0,
                      profitRate: 0,
                      profit: 0,
                    });
                  }
                });
            })
            .catch((err) => {
                console.error('계정 조회 실패:', err);
                setError('계정 정보를 불러오는 데 실패했습니다.');
            });
    }, []);

    // 2. 코인 가격 조회
    client.connect({}, () => {
        client.subscribe('/topic/AllCoin', (message) => {
            const coinList = JSON.parse(message.body);
            console.log(coinList)
            const prices = {};
            const tickerData = [];
            coinList.forEach((coin) => {
                prices[coin.market] = coin.trade_price;
                tickerData.push({
                    name: convertToKrName(coin.market),
                    symbol: convertSymbol(coin.market),
                    price: coin.trade_price, changeRate: (coin.signed_change_rate * 100).toFixed(2),
                    changeAmount: coin.signed_change_price
                })

                assets.forEach(asset => {
                    if (coin.market.split('-')[1] === asset.currency) {
                      asset.evalAmount = (asset.balance * coin.trade_price);
                      asset.profit = asset.evalAmount - asset.buyAmount;
                      asset.profitRate = (asset.profit / asset.buyAmount) * 100;
                    }
                })
            });

            summary.totalEval = assets.reduce((sum, asset) => sum + asset.evalAmount, 0);
            summary.totalProfit = summary.totalEval - summary.totalBuy;
            summary.totalProfitRate = (summary.totalProfit / summary.totalBuy) * 100;
            summary.totalAsset = summary.totalEval + summary.krwBalance;


            console.log(assets)

            setCoinPrices(tickerData);
        });
    });
    return (
        <div style={{display: 'flex', paddingTop: 50, minWidth: 1400}}>
            <div style={{margin: 20, fontFamily: 'Noto Sans, sans-serif', maxWidth: 1500}}>
                {/* 요약 영역 */}
                <div style={{display: 'flex', justifyContent: 'space-between', marginBottom: 40}}>
                    <AccountSummary summary={summary}/>
                    <AccountChart pieData={pieData}/>;
                </div>

                {/* 보유자산 목록 */}
                <AccountTable assets={assets}/>;
            </div>
            <TickerTable data={coinPrices}/>
        </div>
    );
}

export default AccountSample;