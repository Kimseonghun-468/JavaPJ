import React from "react";
import { PieChart, Pie, Cell, Tooltip } from "recharts";

const AccountChart = ({ pieData }) => {
  return (
    <div className="portfolio-chart">
      <PieChart width={330} height={330}>
        <Pie
          data={pieData}
          cx="50%"
          cy="50%"
          innerRadius={60}
          outerRadius={80}
          dataKey="value"
          nameKey="name"
          label={({ percent }) => `${(percent * 100).toFixed(1)}`}
        >
          {pieData.map((entry, index) => (
            <Cell key={`cell-${index}`} fill={entry.color} />
          ))}
        </Pie>
        <Tooltip />
      </PieChart>

      <div
        className="donut-center-text"
        style={{
          position: "absolute",
          top: "50%",
          left: "35%",
          transform: "translate(-45%, -50%)",
          textAlign: "center",
          fontWeight: "bold",
          pointerEvents: "none",
        }}
      >
        보유 비중
        <br />
        (%)
      </div>

      <div className="legend" style={{ marginTop: 10 }}>
        {pieData.map((entry, idx) => (
          <div
            key={idx}
            className="legend-item"
            style={{ display: "flex", alignItems: "center", marginBottom: 4 }}
          >
            <span
              className="legend-color"
              style={{
                display: "inline-block",
                width: 12,
                height: 12,
                backgroundColor: entry.color,
                marginRight: 8,
              }}
            />
            <span className="legend-name" style={{ flex: 1 }}>
              {entry.name}
            </span>
            <span className="legend-percent">{entry.value.toFixed(1)}%</span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AccountChart;
