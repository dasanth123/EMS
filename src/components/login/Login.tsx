import React, { useState } from "react";
import "./Login.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [darkMode, setDarkMode] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const navigate=useNavigate();

  const payload={
    email:email,
    password:password
  }

  const handleSubmit =async(e: React.FormEvent<HTMLFormElement>)=>{
     e.preventDefault();
    try{
         const response=await axios.post("http://localhost:8089/api/v1/users",payload)
         if(response.status==200){
            alert("login successfully")
            navigate('/EmployeePage')
            
         }

    }catch(error){
        console.error("failed",error);
        alert("invalid username or password")
    }
   
  }

  const toggleTheme = () => {
    setDarkMode(!darkMode);
  };

  const togglePassword = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div className={`login-container ${darkMode ? "dark" : "light"}`}>
      {/* Top Header */}
      <div className="login-header">
        <div className="header-logo">🏢 EMS</div>
        <button className="theme-toggle" onClick={toggleTheme}>
          {darkMode ? "Light Mode ☀️" : "Dark Mode 🌙"}
        </button>
      </div>

      <div className="login-card">
        <h2 className="login-title">Welcome Back 👋</h2>
        <p className="login-subtitle">Login to your account</p>

        <form onSubmit={handleSubmit} className="login-form">

          <input
            type="email"
            placeholder="Email"
            className="login-input"
            value={email}
            onChange={(e)=>setEmail(e.target.value)}
            required
            autoComplete="off"
          />

         
          <div className="password-wrapper">
            <input
              type={showPassword ? "text" : "password"}
              placeholder="Password"
              className="login-input"
              value={password}
              onChange={(e)=>setPassword(e.target.value)}
              required
              autoComplete="new-password"
            />

            <span className="toggle-icon" onClick={togglePassword}>
              {showPassword ? "🙈" : "👁️"}
            </span>
          </div>

          <button type="submit" className="login-button">
            Login
          </button>

        </form>
      </div>
    </div>
  );
};

export default Login;
