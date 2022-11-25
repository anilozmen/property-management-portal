import Layout from "../../components/Layout/Layout";
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Property from "../../components/Property/Property";
import { useDispatch, useSelector } from "react-redux";
import { CUSTOMER } from "../../constants/roles";
import { fetchSavedPropertyIds } from "../../reducers/savedPropertyIdsForCustomer";


const Properties = () => {
    const userRole = useSelector(state => state.user.role);
    const [propertyState, setPropertyState] = useState([]);
    const dispatch = useDispatch();

    const fetchProperties = () => {
        axios.get("/properties").then(res => {
            setPropertyState(res.data);
        }).catch(err => {
            console.log(err);
        })
    }

    useEffect(() => {
        if (userRole === CUSTOMER)
            dispatch(fetchSavedPropertyIds());

        fetchProperties();
    }, [userRole]);


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
                        {properties && properties.length !== 0 ? properties : <div>No properties added yet !!!</div>}
                    </div>
                </div>
            </section>
        </div>
    )
}


export default Properties;