import axios from "axios";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import IntroTitle from "../../components/IntroTitle/IntroTitle";
import Property from "../../components/Property/Property";
import { fetchSavedPropertyIds } from "../../reducers/savedPropertyIdsForCustomer";

import "./SavedProperties.css";

const SavedProperties = props => {
  const [properties, setProperties] = useState([]);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchSavedPropertyIds());
    fetchProperties();
  }, []);

  const fetchProperties = () => {
    axios.get("/properties/saved").then(res => {
      setProperties(res.data);
    }).catch(err => {
      console.log(err);
    })
  }

  const handleRemoveFromSavedList = index => {
    const idx = +index;

    if (!isNaN(idx) && idx >= 0 && idx < properties.length) {
      setProperties(properties => [...properties.slice(0, idx), ...properties.slice(idx + 1)]);
    }
  }

  return (
    <>
      <IntroTitle>Saved Properties</IntroTitle>
      <section className="property-grid grid">
        <div className="container">
          <div className="row">
            {properties &&
              properties.length !== 0
              ? properties.map((property, idx) => (
                <Property
                  id={property.id}
                  key={property.id}
                  name={property.name}
                  price={property.price}
                  propertyAttributes={property.propertyAttributesBasicDto}
                  onRemoveFromSavedList={() => handleRemoveFromSavedList(idx)}
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
