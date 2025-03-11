import axios from "axios";
import { useEffect, useState } from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, Legend, ResponsiveContainer } from "recharts";

const Performance = () => {
  const [metrics, setMetrics] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchPerformanceMetrics();
  }, []);

  const fetchPerformanceMetrics = () => {
    axios.get("http://localhost:8081/performance/bell-curve")
      .then((response) => {
        console.log("Performance Metrics:", response.data);
        setMetrics(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching performance metrics:", error);
        setLoading(false);
      });
  };

  if (loading) {
    return <div className="text-center text-lg font-semibold">Loading Performance Metrics...</div>;
  }

  return (
    <div className="p-5">
      <h2 className="text-2xl font-bold mb-4">📊 Performance Metrics</h2>

      {/* Total Employees */}
      <div className="mb-5 p-4 bg-blue-200 text-blue-900 rounded-lg text-lg">
        <strong>Total Employees:</strong> {metrics.totalEmployees}
      </div>

      {/* Performance Chart */}
      <div className="mb-5 bg-white p-4 rounded-lg shadow-md">
        <h3 className="text-xl font-semibold mb-3">Performance Distribution</h3>
        <ResponsiveContainer width="100%" height={300}>
          <BarChart data={formatChartData(metrics)}>
            <XAxis dataKey="category" />
            <YAxis />
            <Tooltip content={<CustomTooltip />} />
            <Legend />
            <Bar dataKey="actual" fill="#4CAF50" name="Actual Percentage" />
            <Bar dataKey="standard" fill="#FF9800" name="Standard Percentage" />
          </BarChart>
        </ResponsiveContainer>
      </div>

      {/* Suggested Adjustments */}
      <div className="bg-gray-100 p-4 rounded-lg shadow-md">
        <h3 className="text-xl font-semibold mb-3">📌 Suggested Adjustments</h3>
        {Object.keys(metrics.suggestedAdjustments).length === 0 ? (
          <p className="text-gray-500">No adjustments needed.</p>
        ) : (
          <ul className="list-disc list-inside">
            {Object.entries(metrics.suggestedAdjustments).map(([employee, newRating]) => (
              <li key={employee} className="text-gray-700">
                {employee} → <strong className="text-blue-600">{newRating}</strong>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

// Helper function for chart data
const formatChartData = (metrics) => {
  return Object.keys(metrics.actualPercentages).map((category) => ({
    category,
    actual: metrics.actualPercentages[category],
    standard: metrics.standardPercentages[category],
    deviation: (metrics.actualPercentages[category] - metrics.standardPercentages[category]).toFixed(2),
  }));
};

// Custom Tooltip Component
const CustomTooltip = ({ active, payload, label }) => {
  if (active && payload && payload.length >= 2) {
    return (
      <div className="bg-white p-3 rounded-lg shadow-lg border border-gray-300">
        <h4 className="font-bold text-gray-700">{label}</h4>
        <p className="text-green-600">Actual: {payload[0]?.value}%</p>
        <p className="text-orange-600">Standard: {payload[1]?.value}%</p>
        <p className={`font-semibold ${payload[0]?.value - payload[1]?.value >= 0 ? 'text-blue-500' : 'text-red-500'}`}>
          Deviation: {payload[0]?.value - payload[1]?.value}%
        </p>
      </div>
    );
  }
  return null;
};


export default Performance
