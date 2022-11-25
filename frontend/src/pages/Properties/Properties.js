import Layout from "../../components/Layout/Layout";
import axios from 'axios';
import React, {useEffect, useState} from 'react';
import Property from "../../components/Property/Property";
import PropertyDetail from "../../components/PropertyDetail/PropertyDetail";


const Properties = ({fetched_properties, noProductMessage: noProductsMessage = "No properties added yet!!"}) => {

    const [propertyState, setPropertyState] = useState(fetched_properties);

    const fetchProperties = () => {
        axios.get("/properties").then(res => {
            console.log('fetching properties list ----');
            setPropertyState(res.data);
        }).catch(err => {
            console.log(err);
        })
    }


    useEffect(() => {
        if (!fetched_properties) {
            fetchProperties();
        }
    }, []);


    const properties = propertyState.map(property => {
        return (

            <Property
                id={property.id}
                key={property.id}
                name={property.name}
                price={property.price}
            />

        )
    });

    return (
        <div>
            <section className="intro-single">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12 col-lg-8">
                            <div className="title-single-box">
                                <h1 className="title-single">Properties</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section className="property-grid grid">
                <div className="container">
                    <div className="row">
                        {properties && properties.length !== 0 ? properties : <div>{noProductsMessage}</div>}
                        <PropertyDetail/>
                    </div>
                </div>
            </section>
        </div>
    )
}


export default Properties;