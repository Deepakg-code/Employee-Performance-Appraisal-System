import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const Dashboard = () => {
  const [categories, setCategories] = useState([]);
  const [showCategories, setShowCategories] = useState(false);

  useEffect(() => {
    if (showCategories) {
      fetchCategories();
    }
  }, [showCategories]);

  const fetchCategories = () => {
    axios.get("http://localhost:8081/category")
      .then((response) => {
        console.log("Categories Response:", response.data);
        setCategories(response.data.data || []);
      })
      .catch((error) => {
        console.error("Error fetching categories:", error);
        setCategories([]);
      });
  };

  return (
    <div className="p-5">
      <h2 className="text-3xl font-bold mb-6 text-center">🏠 Welcome to Dashboard</h2>

      {/* Buttons Section - Now stacked */}
      <div className="flex flex-col gap-6 items-center">
        <Link 
          to="/add-employee" 
          className="bg-blue-600 text-white w-96 py-5 text-xl font-semibold text-center rounded-lg shadow-md transition duration-300 hover:bg-blue-700"
        >
          ➕ Add Employee
        </Link>

        <Link 
          to="/add-category" 
          className="bg-green-600 text-white w-96 py-5 text-xl font-semibold text-center rounded-lg shadow-md transition duration-300 hover:bg-green-700"
        >
          📚 Add Category
        </Link>

        <button
          className="bg-purple-600 text-white w-96 py-5 text-xl font-semibold rounded-lg shadow-md transition duration-300 hover:bg-purple-700"
          onClick={() => setShowCategories(!showCategories)}
        >
          📋 Available Categories
        </button>
      </div>

      {/* Available Categories Table */}
      {showCategories && (
        <div className="bg-white p-6 rounded-lg shadow-lg mt-6">
          <h3 className="text-2xl font-semibold mb-4 text-center">📋 Available Categories</h3>
          {categories.length === 0 ? (
            <p className="text-gray-500 text-center">No categories available.</p>
          ) : (
            <table className="w-full border-collapse border border-gray-300">
              <thead className="bg-blue-500 text-white text-lg">
                <tr>
                  <th className="border border-gray-300 p-3">Rating</th>
                  <th className="border border-gray-300 p-3">Standard Percentage</th>
                </tr>
              </thead>
              <tbody>
                {categories.map((category) => (
                  <tr key={category.id} className="text-center text-lg">
                    <td className="border border-gray-300 p-3">{category.rating}</td>
                    <td className="border border-gray-300 p-3">{category.standardPercentage}%</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
    </div>
  );
};

export default Dashboard;
