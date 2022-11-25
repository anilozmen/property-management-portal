import Layout from "../../components/Layout/Layout";
import axios from 'axios';
import React, { useEffect, useRef, useState } from 'react';
import Property from "../../components/Property/Property";
import PropertyDetail from "../../components/PropertyDetail/PropertyDetail";



const Properties = () => {

    const [propertyState, setPropertyState] = useState([]);
    const filterRef = useRef(null);

    const fetchProperties = (filter = {}) => {
        axios.get("/properties", filter).then(res => {
            setPropertyState(res.data);
        }).catch(err => {
            console.log(err);
        })
    }


    useEffect(() => {
        fetchProperties();
    }, []);

    const filterSubmit = (event) => {
        event.preventDefault();

        const form = filterRef.current;

        if (!filterRef.current)
            return;

        const filterData = {};

        if (['RENT', 'SALE'].includes(form['listingType'].value))
            filterData.listing_type = form['listingType'].value;

        if (['HOUSE', 'APARTMENT', 'CONDO'].includes(form['propertyType'].value))
            filterData.property_type = form['propertyType'].value;

        let minPriceVal = parseFloat(form['minPrice'].value);

        if (!isNaN(minPriceVal) && minPriceVal > 0)
            filterData.price_gt = minPriceVal;

        let maxPriceVal = parseFloat(form['maxPrice'].value)
        if (!isNaN(maxPriceVal) && maxPriceVal > 0)
            filterData.price_lt = maxPriceVal;

        if (filterData.price_gt && filterData.price_lt) {
            if (filterData.price_gt > filterData.price_lt || filterData.price_lt == filterData.price_gt) {
                delete filterData.price_lt;
                delete filterData.price_gt;
            }
        }

        fetchProperties({params: filterData});        
    };

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

            <section className="container m-auto">
                <form onSubmit={filterSubmit} ref={filterRef}>
                    <div className="form-row p-2 justify-content-center">
                        <div className="col-2 mb-2">
                            <select className="form-control" name="listingType">
                                <option value="">Listing Type</option>
                                <option value="SALE">SALE</option>
                                <option value="RENT">RENT</option>
                            </select>
                        </div>
                        <div className="col-2 mb-2">
                            <select className="form-control" name="propertyType">
                                <option value="">Property Type</option>
                                <option value="HOUSE">HOUSE</option>
                                <option value="APARTMENT">APARTMENT</option>
                                <option value="CONDO">CONDO</option>
                            </select>
                        </div>
                        <div className="col-2">
                            <input type="text" name="minPrice" className="form-control" id="minPrice" placeholder="Min Price" />
                        </div>
                        <div className="col-2">
                            <input type="text" name="maxPrice" className="form-control" id="maxPrice" placeholder="Max Price" />
                        </div>
                        <div className="col-1">
                            <button type="submit" className="btn btn-sm btn-warning p7">
                                <i className="fa fa-solid fa-filter" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </section>

            <section className="property-grid grid">
                <div className="container">
                    <div className="row">
                        {properties && properties.length !== 0 ? properties : <div>No properties added yet !!!</div>}
                        <PropertyDetail />
                    </div>
                </div>
            </section>
        </div>
    )
}


export default Properties;