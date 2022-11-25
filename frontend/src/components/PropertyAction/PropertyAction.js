import axios from "axios";

const PropertyAction = ({ propertyId, onSuccess }) => {
  const sendAction = (action) => {
    axios
      .put(`/properties/${propertyId}/action`, action)
      .then((response) => {
        if (response.status === 200) {
          if (response.data.success) onSuccess();
          else alert(response.data.message);
        } else {
          alert("API ERROR");
        }
      })
      .catch((e) => {
        alert("API Error.");
        console.log(e);
      });
  };

  const complete = () => sendAction({ action: "COMPLETE" });
  const cancel = () => sendAction({ action: "COMPLETE" });

  return (
    <div className="float-right">
      <button className="btn btn-success" onClick={complete}>
        {" "}
        Completed{" "}
      </button>
      <button className="btn btn-danger" onClick={cancel}>
        {" "}
        Unsuccessful{" "}
      </button>
    </div>
  );
};

export default PropertyAction;
