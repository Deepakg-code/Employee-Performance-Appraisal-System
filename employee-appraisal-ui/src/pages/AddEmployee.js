import { useState } from "react";
import axios from "axios";

const AddEmployee = () => {
  const [employeeName, setEmployeeName] = useState("");
  const [rating, setRating] = useState("A");

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8081/employee", { employeeName, rating })
      .then(() => {
        alert("Employee added successfully!");
        setEmployeeName("");
        setRating("A");
      })
      .catch((error) => console.error("Error adding employee:", error));
  };

  return (
    <div className="p-5">
      <h2 className="text-2xl font-bold mb-4">➕ Add Employee</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block font-semibold">Employee Name</label>
          <input
            type="text"
            className="border p-2 w-full"
            value={employeeName}
            onChange={(e) => setEmployeeName(e.target.value)}
            required
          />
        </div>
        <div>
          <label className="block font-semibold">Rating</label>
          <select className="border p-2 w-full" value={rating} onChange={(e) => setRating(e.target.value)}>
            {["A", "B", "C", "D", "E"].map((r) => (
              <option key={r} value={r}>{r}</option>
            ))}
          </select>
        </div>
        <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">Add Employee</button>
      </form>
    </div>
  );
};

export default AddEmployee;
