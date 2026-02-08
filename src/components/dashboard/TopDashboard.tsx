import React from "react";
import { useNavigate } from "react-router-dom";
import "./TopDashboard.css";

interface TopDashboardProps {
  darkMode: boolean;
  toggleTheme: () => void;
}

const TopDashboard: React.FC<TopDashboardProps> = ({ darkMode, toggleTheme }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    navigate("/");
  };

  return (
    <div className={`top-dashboard ${darkMode ? "dark" : "light"}`}>
      <div className="dashboard-content">
        {/* Logo */}
        <div className="logo">
          <h2>🏢 EMS</h2>
        </div>

        {/* Welcome */}
        <div className="welcome">
          <div className="welcome-badge">✨</div>
          <div className="welcome-text">Welcome to</div>
          <div className="welcome-brand">Employee Management System</div>
        </div>

        {/* Navigation */}
        <nav className="nav-menu">
          <button
            className="nav-link"
            onClick={() => navigate("/EmployeePage")}
          >
            👥 Employees
          </button>
        </nav>

        {/* Right Actions */}
        <div className="dashboard-actions">
          <button
            className="settings-btn"
            onClick={() => navigate("/Settings")}
            title="Settings"
          >
            ⚙️
          </button>
          <button className="theme-toggle-top" onClick={toggleTheme}>
            {darkMode ? "☀️" : "🌙"}
          </button>
          <button className="logout-btn" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};

export default TopDashboard;
