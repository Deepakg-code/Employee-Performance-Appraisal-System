import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Dashboard from "./pages/Dashboard";
import Employees from "./pages/Employees";
import Performance from "./pages/Performance";
import AddEmployee from "./pages/AddEmployee";
import AddCategory from "./pages/AddCategory"; // ✅ Import AddCategory

const App = () => (
  <Router>
    <Navbar />
    <Routes>
      <Route path="/" element={<Dashboard />} />
      <Route path="/employees" element={<Employees />} />
      <Route path="/performance" element={<Performance />} />
      <Route path="/add-employee" element={<AddEmployee />} />
      <Route path="/add-category" element={<AddCategory />} /> {/* ✅ AddCategory route */}
    </Routes>
  </Router>
);

export default App;
