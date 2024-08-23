import React from 'react';
import { useParams } from 'react-router-dom';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import './PortFolio.css';


ChartJS.register(ArcElement, Tooltip, Legend);

function PortfolioChart() {
    const data = {
        labels: ['KRW', 'BTC', 'ETH', 'XRP', 'EOS', 'BCH'],
        datasets: [
            {
                label: '보유 비중 (%)',
                data: [36.2, 20.5, 17.7, 13.9, 9.1, 2.2],
                backgroundColor: ['#8CC152', '#4A90E2', '#9B59B6', '#E08283', '#F5AB35', '#F6BB42'],
                hoverBackgroundColor: ['#8CC152', '#4A90E2', '#9B59B6', '#E08283', '#F5AB35', '#F6BB42'],
            },
        ],
    };
    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'right',
            },
        },
    };

    return (
        <div>
            <div className="Graph-Name">
                <p className="Graph-Text">자산 포트폴리오</p>
            </div>
            <div className="Graph-Box">
                <div className="Graph-Doughnut">
                    <Doughnut data={data} options={options} />
                </div>
            </div>

        </div>
    );
}


function PortFolioContainer(){
    return(
    <div className="PF-Container">
    {/*    display flex*/}
        <div className="Capital-Graph">
            {PortfolioChart()}
        </div>
        <div className="Capital-Retain">
            <div className="Chart">
                120,000 KRW
            </div>
            <div className="Chart">
                3,000,000 KRW
            </div>
        </div>
        <div className="Capital-Chart">
            <div className="Chart-Detail">
                <div className="Chart">
                    3,000,000 KRW
                </div>
                <div className="Chart">
                    4,200,000 KRW
                </div>
            </div>
            <div className="Chart-Detail">
                <div className="Chart">
                    40.00%
                </div>
                <div className="Chart">
                    1,200,000 KRW
                </div>
            </div>
        </div>
    </div>
    );
}

function PortFolio(){
    const { username } = useParams();
    return (
        <div className="Body">
            <div className="Menu">
                <div className="logo"><a>{username}</a></div>
            </div>
            <div className="Main">
                {PortFolioContainer()}
                <h1>{username}'s Portfolio</h1>
            </div>
        </div>
    )
}
export default PortFolio