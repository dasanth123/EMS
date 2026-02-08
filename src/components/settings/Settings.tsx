import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Settings.css";
import TopDashboard from "../dashboard/TopDashboard";
import axios from "axios";

interface SettingsProps {
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

const Settings: React.FC<SettingsProps> = ({ darkMode, toggleTheme }) => {
  const navigate = useNavigate();
  const [departments, setDepartments] = useState<Department[]>([]);

  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    departmentName:"",
    description: "",
  });

  const [editingId, setEditingId] = useState<number | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };


    const handleget=async()=> {
      try {
      const respose= await axios.get("http://localhost:8089/api/v1/setting/department");
         setDepartments(respose.data.data.content);
       

    } catch(error){
    console.error("failed ",error);
    }
}

useEffect(()=>{
  handleget();
},[])

 

  const handleAddDepartment = async() => {
    if (!formData.departmentName || !formData.description) {
      alert("Please fill all fields");
      return;
    }

    try{
    if (editingId !==null) {
     
        
        const response=await axios.put(
        `http://localhost:8089/api/v1/setting/department/${editingId}`,
        formData
        
      );
      alert("Department updated successfully");
      
    
       
    } else {
     
      const response=await axios.post('http://localhost:8089/api/v1/setting/department/added',
        formData
      )
      alert('department created successfully')
    }

    await handleget();

  

    setFormData({ departmentName: "", description: "" });
    setShowForm(false);
    setEditingId(null);
  } catch(error:any){
    alert("department alredy exist")

  }
}

  

  const handleEditDepartment = async(dept:Department) => {
    setFormData({ departmentName: dept.departmentName, description: dept.description });
    setEditingId(dept.id);
    setShowForm(true);
    

 
  };

  const handleDeleteDepartment = async(id: number) => {
    if (window.confirm("Are you sure you want to delete this department?")) {
      try{
        const response=await axios.delete(`http://localhost:8089/api/v1/setting/department/${id}`)
        alert("delete successfully");


      }catch(error){
        console.error("failed ",error);
      }
       
      setDepartments(departments.filter((dept) => dept.id !== id));
    }
    
    
  };

  const handleCancel = () => {
    setShowForm(false);
    setFormData({ departmentName: "", description: "" });
    setEditingId(null);
  };

  return (
    <>
      <TopDashboard darkMode={darkMode} toggleTheme={toggleTheme} />
      
      <div className={`settings-container ${darkMode ? "dark" : "light"}`}>
        <div className="settings-wrapper">
        <div className="settings-header">
          <div>
            <h1>⚙️ Settings</h1>
            <p>Manage your application settings</p>
          </div>
          <button 
            className="settings-close-btn" 
            onClick={() => navigate("/EmployeePage")}
            title="Close Settings"
          >
            ✕
          </button>
        </div>

       
        <div className="settings-section">
          <div className="section-header">
            <h2>Department Management</h2>
            <button
              className="add-dept-btn"
              onClick={() => setShowForm(true)}
            >
              + Add Department
            </button>
          </div>

        
          <div className="department-list">
            {departments.length === 0 ? (
              <p className="empty-message">No departments found. Add one to get started.</p>
            ) : (
              <table className="dept-table">
                <thead>
                  <tr>
                    <th>Department Name</th>
                    <th>Description</th>
                    <th>Created Date</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {departments.map((dept) => (
                    <tr key={dept.id}>
                      <td>{dept.departmentName}</td>
                      <td>{dept.description}</td>
                      <td>{new Date(dept.createdAt).toLocaleString()}</td>
                      <td className="action-buttons">
                        <button
                          className="edit-btn"
                          onClick={() => handleEditDepartment(dept)}
                        >
                          ✏️ Edit
                        </button>
                        <button
                          className="delete-btn"
                          onClick={() => handleDeleteDepartment(dept.id)}
                        >
                          🗑️ Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        </div>

       
        {showForm && (
          <div className="form-overlay" onClick={handleCancel}>
            <div className="form-modal" onClick={(e) => e.stopPropagation()}>
              <div className="form-header">
                <h2>{editingId ? "Edit Department" : "Add New Department"}</h2>
                <button className="close-btn" onClick={handleCancel}>
                  ✕
                </button>
              </div>

              <div className="form-group">
                <label>Department Name *</label>
                <input
                  type="text"
                  name="departmentName"
                  placeholder="e.g., Marketing"
                  value={formData.departmentName}
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>Description *</label>
                <textarea
                  name="description"
                  placeholder="Enter department description"
                  rows={4}
                  value={formData.description}
                  onChange={handleChange}
                />
              </div>

              <div className="form-actions">
                <button className="cancel-btn" onClick={handleCancel}>
                  Cancel
                </button>
                <button className="save-btn" onClick={handleAddDepartment}>
                  {editingId ? "Update" : "Add"} Department
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
      </div>
    </>
  );
};

export default Settings;
