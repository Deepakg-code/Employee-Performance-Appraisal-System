import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className="bg-blue-500 text-white p-4 flex justify-between items-center shadow-md">
      <h1 className="text-2xl font-extrabold">Employee Appraisal</h1>
      <div className="space-x-6 text-lg font-semibold">
        <Link to="/" className="hover:underline text-xl">🏠 Dashboard</Link>
        <Link to="/employees" className="hover:underline text-xl">👥 All Employees</Link>
        <Link to="/performance" className="hover:underline text-xl">📊 Performance Appraisal</Link>
      </div>
    </nav>
  );
};

export default Navbar;
