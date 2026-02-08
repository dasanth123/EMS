import React, { useEffect, useState } from "react";
import "./EmployeePage.css";
import TopDashboard from "../dashboard/TopDashboard";
import axios from "axios";

interface EmployeePageProps {
  darkMode: boolean;
  toggleTheme: () => void;
}

interface Department 
{
  id: number;
  departmentName: string;
  description: string;
  createdAt: string;
  updatedAt:string;
}

interface Employeer{
  id:number;
  name:string;
      email:string;
      phoneNumber:string;
      department:string;
      salary:number;
      createdAt:string;
      updatedAt:string;

}

export default function EmployeePage({ darkMode, toggleTheme }: EmployeePageProps) {
  const [employees, setEmployees] = useState<Employeer[]>([]);
   const [departments, setDepartments] = useState<Department[]>([]);


  const [showPopup, setShowPopup] = useState(false);
  const [editingId, setEditingId] = useState<number | null>(null);

  const [form, setForm] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    department: "",
    salary: "",
  });


  
    const handlegetDep=async()=> {
      try {
      const respose= await axios.get("http://localhost:8089/api/v1/setting/department");
         setDepartments(respose.data.data.content);
       

    } catch(error){
    console.error("failed ",error);
    }
}

useEffect(()=>{
  handlegetDep();
},[])



    const handleget=async()=> {
      try {
      const respose= await axios.get("http://localhost:8089/api/v1/employee");
        setEmployees(respose.data.data.content);
        
       

    } catch(error){
    console.error("failed ",error);
    }
}

useEffect(()=>{
  handleget();
},[])







  const handleChange = (e: any) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const addEmployee = async () => {
    if (!form.name || !form.email) return;

 
    try{
    if (editingId) {
       const response=await axios.put(
        `http://localhost:8089/api/v1/employee/${editingId}`,
        form
        
      );
      alert("employee updated successfully");
      
    } else {
      const response=await axios.post('http://localhost:8089/api/v1/employee/added',
        form
      )
      alert('employee created successfully')
      
    }
    await handleget();


    setForm({ name: "", email: "", phoneNumber: "", department: "", salary: "" });
    setShowPopup(false);
    setEditingId(null);
  }catch(error){
    console.error("failed",error);
    alert("email alredy exist");
  }
  };

  const handleEditEmployee = (emp: any) => {
    setForm({
      name: emp.name,
      email: emp.email,
      phoneNumber: emp.phoneNumber,
      department: emp.department,
      salary: emp.salary,
    });
    setEditingId(emp.id);
    setShowPopup(true);
  };

  const handleDeleteEmployee = async(id: number) => {
    if (window.confirm("Are you sure you want to delete this employee?")) {
      try{
        const response=await axios.delete(`http://localhost:8089/api/v1/employee/${id}`)
        alert("delete successfully");

      }catch(error){
        console.error("failed ",error);
      }
      setEmployees(employees.filter((emp) => emp.id !== id));
    }
  };

  const handleCancel = () => {
    setShowPopup(false);
    setForm({ name: "", email: "", phoneNumber: "", department: "", salary: "" });
    setEditingId(null);
  };

  const formatSalary = (value: number | string) => {
    const num = Number(value) || 0;
    try {
      return `Rs. ${num.toLocaleString("en-IN")}`;
    } catch (e) {
      return `Rs. ${num}`;
    }
  };

  return (
    <>
      <TopDashboard darkMode={darkMode} toggleTheme={toggleTheme} />
      
      <div className={`page ${darkMode ? "dark" : "light"}`}>
        <div className="header">
          <h1>Employee Management</h1>
          <button className="add-btn" onClick={() => setShowPopup(true)}>
            + Add Employee
          </button>
        </div>

      {/* Table */}
      <div className="table-card">
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Department</th>
              <th>Salary</th>
              <th>Created</th>
              <th>Updated</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {employees.map((emp, i) => (
              <tr key={i}>
                <td>{emp.name}</td>
                <td>{emp.email}</td>
                <td>{emp.phoneNumber}</td>
                <td>{emp.department}</td>
                <td>{formatSalary(emp.salary)}</td>
                <td>{new Date(emp.createdAt).toLocaleString()}</td>
                <td>{new Date(emp.updatedAt).toLocaleString()}</td>
                <td className="action-buttons">
                  <button className="edit-btn" onClick={() => handleEditEmployee(emp)}>
                    ✏️ Edit
                  </button>
                  <button className="delete-btn" onClick={() => handleDeleteEmployee(emp.id)}>
                    🗑️ Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Popup Modal */}
      {showPopup && (
        <div className="modal-overlay">
          <div className="modal">
            <h2>{editingId ? "Edit Employee" : "Add Employee"}</h2>

            <input name="name" placeholder="Name" value={form.name} onChange={handleChange} />
            <input name="email" placeholder="Email" value={form.email} onChange={handleChange} />
            <input name="phoneNumber" placeholder="Phone" value={form.phoneNumber} onChange={handleChange} />

            {/* Department Select */}
            <select name="department" value={form.department} onChange={handleChange}>
              <option value="">Select Department</option>
              {departments.map((e)=>(
                <option key={e.id}value={e.departmentName}>
                {e.departmentName}
                </option>
              ))}
              
            </select>

            <input type="number" name="salary" placeholder="Salary" value={form.salary} onChange={handleChange} />

            <div className="modal-actions">
              <button className="cancel" onClick={handleCancel}>
                Cancel
              </button>
              <button className="save" onClick={addEmployee}>
                {editingId ? "Update" : "Save"}
              </button>
            </div>
          </div>
        </div>
      )}
      </div>
    </>
  );
}


