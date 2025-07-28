import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import NotFound from './pages/NotFound'; // 404 페이지 임포트
import CoinPrice from "./pages/CoinPrice.jsx";
import ChartSample from "./pages/ChartSample";
import CoinList from "./pages/CoinList.jsx";
import Account from "./pages/Account.jsx";
import AccountSample from "./pages/AccountSample.jsx";
import TickerTable from "./pages/TickerTable.jsx";

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="*" element={<NotFound />} /> {/* 404 페이지 처리 */}
            <Route path="/coin/:id" element={<CoinPrice />} />
            <Route path="/chart" element={<ChartSample />} />
            <Route path="/CoinList" element={<CoinList />} />
            <Route path="/Account" element={<Account />} />
            <Route path="/AccountSample" element={<AccountSample />} />
            <Route path="/TickerTable" element={<TickerTable />} />

        </Routes>
      </div>
    </Router>
  );
}

export default App;
