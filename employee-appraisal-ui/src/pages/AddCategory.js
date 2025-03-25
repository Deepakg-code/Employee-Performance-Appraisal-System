import { useState, useEffect } from "react";
import axios from "axios";

const AddCategory = () => {
  const [rating, setRating] = useState("");
  const [standardPercentage, setStandardPercentage] = useState("");
  const [categories, setCategories] = useState([]); // Stores existing categories
  const [message, setMessage] = useState(null); // Success/Error messages

  // List of predefined rating options
  const ratingOptions = ["A", "B", "C", "D", "E"];

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = () => {
    axios.get("http://localhost:8081/category")
      .then(response => {
        console.log("Categories Fetched:", response.data);
        setCategories(response.data.data || []); // Store available categories
      })
      .catch(error => {
        console.error("Error fetching categories:", error);
        setCategories([]); // Ensure it's always an array
      });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Check if the selected rating already exists
    if (categories.some(category => category.rating === rating)) {
      setMessage({ type: "error", text: "❌ Category with this rating already exists!" });
      return;
    }

    axios.post("http://localhost:8081/category", { rating, standardPercentage })
      .then(response => {
        setMessage({ type: "success", text: "✅ Category added successfully!" });
        fetchCategories(); // Refresh categories list
        setRating(""); // Reset fields
        setStandardPercentage("");
      })
      .catch(error => {
        setMessage({ type: "error", text: "❌ Error adding category. Try again!" });
        console.error("Error adding category:", error);
      });
  };

  return (
    <div className="p-5">
      <h2 className="text-2xl font-bold mb-4">📚 Add Category</h2>

      {/* Message Display */}
      {message && (
        <div className={`p-3 mb-3 text-white rounded ${message.type === "success" ? "bg-green-500" : "bg-red-500"}`}>
          {message.text}
        </div>
      )}

      {/* Add Category Form */}
      <form onSubmit={handleSubmit} className="space-y-4 bg-white p-4 rounded-lg shadow-md">
        <div>
          <label className="block font-semibold">Rating:</label>
          <select 
            value={rating} 
            onChange={(e) => setRating(e.target.value)} 
            className="border p-2 w-full"
            required
          >
            <option value="" disabled>Select Rating</option>
            {ratingOptions.map((option) => (
              <option key={option} value={option}>{option}</option>
            ))}
          </select>
        </div>

        <div>
          <label className="block font-semibold">Standard Percentage:</label>
          <input 
            type="number" 
            value={standardPercentage} 
            onChange={(e) => setStandardPercentage(e.target.value)} 
            className="border p-2 w-full" 
            required 
          />
        </div>

        <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded w-full">
          ➕ Add Category
        </button>
      </form>

    </div>
  );
};

export default AddCategory;
