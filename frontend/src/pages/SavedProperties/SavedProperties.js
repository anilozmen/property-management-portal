import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from "axios";
import { useEffect, useState } from "react";
import IntroTitle from "../../components/IntroTitle/IntroTitle";
import Property from "../../components/Property/Property";

import "./SavedProperties.css";

const SavedProperties = props => {
  const [properties, setProperties] = useState([]);

  useEffect(() => {
    fetchProperties();
  }, []);

  const fetchProperties = () => {
    axios.get("/properties/saved").then(res => {
      setProperties(res.data);
    }).catch(err => {
      console.log(err);
    })
  }

  const removePropertyFromList = id => {
    axios.delete(`/properties/saved/${id}`)
      .then(response => {
        fetchProperties();
      })
      .catch(error => {
        alert("Failed to remove the property!");
      });
  }

  return (
    <>
      <IntroTitle>Saved Properties</IntroTitle>
      <section className="property-grid grid">
        <div className="container">
          <div className="row">
            {properties &&
              properties.length !== 0
              ? properties.map(property => (
                <Property
                  id={property.id}
                  key={property.id}
                  name={property.name}
                  price={property.price}
                  propertyAttributes={property.propertyAttributesBasicDto}
                  propertyHead={
                    <div className="saved-property-remove" onClick={() => removePropertyFromList(property.id)} title="Remove from list">
                      <FontAwesomeIcon icon={faXmark} size="2x" />
                    </div>
                  }
                />
              ))
              : <div className="col-md-4">No properties saved yet!</div>}
          </div>
        </div>
      </section>
    </>
  );
}

export default SavedProperties;
