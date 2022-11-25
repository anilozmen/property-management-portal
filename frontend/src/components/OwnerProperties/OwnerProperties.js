import ContentContainer from "../Layout/Layout";
import axios from 'axios';
import React, { useEffect, useRef, useState } from 'react';
import { useParams } from "react-router";
import Properties from "../../pages/Properties/Properties";


const OwnerProperties = () => {

    const [propertyState, setPropertyState] = useState([]);
    const [hasLoaded, setHasLoaded] = useState(false);
    const params = useParams();

    useEffect(() => {
        if (params.userId) {
            fetchProperties();
        }
    
    }, []);

    const fetchProperties = () => {
        axios.get(`/admin/users/${params.userId}/properties`).then(res => {
            setPropertyState(res.data);
            setHasLoaded(true);
        }).catch(err => {
            console.log(err);
        })
    }

    return (
        <div>
            {hasLoaded && <Properties isOwnerProperty={false} fetched_properties={propertyState} />}
        </div>
    )

}

export default OwnerProperties;