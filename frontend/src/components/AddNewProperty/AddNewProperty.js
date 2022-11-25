import axios from "axios";
import { useNavigate } from "react-router-dom";
import ContentContainer from "../Layout/Layout";
import PropertyForm from "./PropertyForm";

const AddNewProperty = (props) => {
  const navigate = useNavigate();

  const handleSubmit = formData => {

    axios.post('/properties', formData)
      .then(response => {
        alert("New property added successfully!");
        navigate("/properties");
      })
      .catch(error => {
        alert("Failed to add new prperty!");
      });
  }

  return (
    <ContentContainer>
      <h1 className="mb-4">Add New Property</h1>
      <PropertyForm onSubmit={handleSubmit} />
    </ContentContainer>
  );
}

export default AddNewProperty;
