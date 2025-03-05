import { useState } from "react";
import axios from "axios";

const AddCategory = () => {
  const [category, setCategory] = useState({ rating: "A", standardPercentage: "" });

  const handleChange = (e) => {
    setCategory({ ...category, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8081/category", category)
      .then(() => alert("Category Added Successfully!"))
      .catch((err) => console.error("Error adding category:", err));
  };

  return (
    <div className="p-5">
      <h2 className="text-2xl font-bold mb-4">➕ Add Category</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <label className="block">
          <span className="text-gray-700">Rating:</span>
          <select name="rating" className="border p-2 w-full" onChange={handleChange}>
            {["A", "B", "C", "D", "E"].map(rating => <option key={rating} value={rating}>{rating}</option>)}
          </select>
        </label>

        <label className="block">
          <span className="text-gray-700">Standard Percentage:</span>
          <input 
            type="number" 
            name="standardPercentage" 
            placeholder="Enter Percentage" 
            className="border p-2 w-full" 
            onChange={handleChange} 
            required 
          />
        </label>

        <button type="submit" className="bg-green-500 text-white px-4 py-2 rounded">
          Add Category
        </button>
      </form>
    </div>
  );
};

export default AddCategory;
