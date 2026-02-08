import { useState } from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Login from './components/login/Login'
import EmployeePage from './components/employee/EmployeePage'
import Settings from './components/settings/Settings'

function App() {
  const [darkMode, setDarkMode] = useState(false)

  const toggleTheme = () => {
    setDarkMode(!darkMode)
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Login />} />
        <Route path='EmployeePage' element={<EmployeePage darkMode={darkMode} toggleTheme={toggleTheme} />} />
        <Route path='Settings' element={<Settings darkMode={darkMode} toggleTheme={toggleTheme} />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
