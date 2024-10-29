import React from 'react';
import './OrderBook.css';
function OrderBook(){
    return(
        <div className="OB-Container">
            <div className="OB-Chart-Box">
                <div className="OB-Item">
                    삼성전자 (PEA10203)
                </div>
                <div className="OB-PAmount">
                    2,000,000
                </div>
                <div className="OB-CAmount">
                    1,800,000
                </div>
                <div className="OB-Profit">
                    - 200,000
                </div>
                <div className="OB-Profit-Percentage">
                    - 10.00 %
                </div>
            </div>

            <div className="OB-Chart-Box">
                <div className="OB-Item">
                    LG전자 (PEA22049)
                </div>
                <div className="OB-PAmount">
                    3,000,000
                </div>
                <div className="OB-CAmount">
                    3,600,000
                </div>
                <div className="OB-Profit">
                    + 600,000
                </div>
                <div className="OB-Profit-Percentage">
                    + 20.00 %
                </div>
            </div>

            <div className="OB-Chart-Box">
                <div className="OB-Item">
                    삼성전자 (PEA10203)
                </div>
                <div className="OB-PAmount">
                    2,000,000
                </div>
                <div className="OB-CAmount">
                    1,800,000
                </div>
                <div className="OB-Profit">
                    - 200,000
                </div>
                <div className="OB-Profit-Percentage">
                    - 10.00 %
                </div>
            </div>

            <div className="OB-Chart-Box">
                <div className="OB-Item">
                    삼성전자 (PEA10203)
                </div>
                <div className="OB-PAmount">
                    2,000,000
                </div>
                <div className="OB-CAmount">
                    1,800,000
                </div>
                <div className="OB-Profit">
                    - 200,000
                </div>
                <div className="OB-Profit-Percentage">
                    - 10.00 %
                </div>
            </div>

            <div className="OB-Chart-Box">
                <div className="OB-Item">
                    삼성전자 (PEA10203)
                </div>
                <div className="OB-PAmount">
                    2,000,000
                </div>
                <div className="OB-CAmount">
                    1,800,000
                </div>
                <div className="OB-Profit">
                    - 200,000
                </div>
                <div className="OB-Profit-Percentage">
                    - 10.00 %
                </div>
            </div>
        </div>
    );
}

export default OrderBook;