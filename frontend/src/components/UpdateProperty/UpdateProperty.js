import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import PropertyForm from "../AddNewProperty/PropertyForm"
import ContentContainer from "../Layout/Layout";

const UpdateProperty = props => {
  const navigate = useNavigate();
  const location = useLocation();
  const property = location.state.property;

  const handleSubmit = formData => {
    axios.put(`/properties/${property.id}`, formData)
      .then(response => {
        alert("Property updated successfully!");
        navigate(-1);
      })
      .catch(error => {
        alert("Failed to add new prperty!");
      });
  }

  const handleCancelButtonClick = event => {
    event.preventDefault();
    navigate(-1);
  }

  return (
    <ContentContainer>
      <h1 className="mb-4">
        Update Property
        &ensp;&ensp;<button onClick={handleCancelButtonClick} className="btn btn-a">Cancel</button>
      </h1>
      <PropertyForm onSubmit={handleSubmit} data={property || null} isUpdate />
    </ContentContainer>
  );

}

export default UpdateProperty;
