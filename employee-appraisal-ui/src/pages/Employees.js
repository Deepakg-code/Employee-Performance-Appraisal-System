import axios from "axios";
import { useEffect, useState } from "react";

const Employees = () => {
  const [employees, setEmployees] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [newRating, setNewRating] = useState("");

  useEffect(() => {
    fetchEmployees();
  }, []);

  const fetchEmployees = () => {
    axios.get("http://localhost:8081/employees")
      .then((response) => {
        console.log("API Response:", response.data);
        setEmployees(response.data.data);
      })
      .catch((error) => console.error("Error fetching employees:", error));
  };

  const handleEdit = (employeeId, currentRating) => {
    setEditingId(employeeId);
    setNewRating(currentRating);
  };

  const handleSave = (employeeId) => {
    axios.put(`http://localhost:8081/employee/${employeeId}/rating`, { rating: newRating })
      .then(() => {
        console.log(`Updated Employee ${employeeId} to ${newRating}`);
        setEditingId(null);
        fetchEmployees(); // Refresh list after update
      })
      .catch((error) => console.error("Error updating employee:", error));
  };

  const handleDelete = (employeeId) => {
    if (window.confirm("Are you sure you want to delete this employee?")) {
      axios.delete(`http://localhost:8081/employee/${employeeId}`)
        .then(() => {
          console.log(`Deleted Employee ${employeeId}`);
          fetchEmployees(); // Refresh list after deletion
        })
        .catch((error) => console.error("Error deleting employee:", error));
    }
  };

  return (
    <div className="p-5">
      <h2 className="text-xl font-bold mb-4">Employees List</h2>

      {employees.length === 0 ? (
        <p className="text-gray-500">No employees found.</p>
      ) : (
        <table className="w-full border-collapse border border-gray-300">
          <thead className="bg-blue-500 text-white">
            <tr>
              <th className="border border-gray-300 p-2">ID</th>
              <th className="border border-gray-300 p-2">Name</th>
              <th className="border border-gray-300 p-2">Rating</th>
              <th className="border border-gray-300 p-2">Actions</th>
            </tr>
          </thead>
          <tbody>
            {employees.map((emp) => (
              <tr key={emp.employeeId} className="text-center">
                <td className="border border-gray-300 p-2">{emp.employeeId}</td>
                <td className="border border-gray-300 p-2">{emp.employeeName}</td>
                <td className="border border-gray-300 p-2">
                  {editingId === emp.employeeId ? (
                    <select
                      className="border p-1"
                      value={newRating}
                      onChange={(e) => setNewRating(e.target.value)}
                    >
                      {["A", "B", "C", "D", "E"].map((rating) => (
                        <option key={rating} value={rating}>{rating}</option>
                      ))}
                    </select>
                  ) : (
                    emp.rating
                  )}
                </td>
                <td className="border border-gray-300 p-2">
                  {editingId === emp.employeeId ? (
                    <button 
                      className="bg-green-500 text-white px-3 py-1 rounded mr-2"
                      onClick={() => handleSave(emp.employeeId)}
                    >
                      Save
                    </button>
                  ) : (
                    <button 
                      className="bg-blue-500 text-white px-3 py-1 rounded mr-2"
                      onClick={() => handleEdit(emp.employeeId, emp.rating)}
                    >
                      Edit
                    </button>
                  )}
                  <button 
                    className="bg-red-500 text-white px-3 py-1 rounded"
                    onClick={() => handleDelete(emp.employeeId)}
                  >
                    Delete 🗑️
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default Employees;
