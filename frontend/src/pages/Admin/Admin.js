import './Admin.css';
import Properties from "../Properties/Properties";
import {useEffect, useState} from "react";
import axios from "axios";

export default function Admin() {
    
    const [hasLoaded, setHasLoaded] = useState(false);
    const [adminResponse, setAdminResponse] = useState({properties: []});

    useEffect(() => {
        axios.get('/admin').then(response => {
            console.log(response.data);
            setAdminResponse(response.data);
            setHasLoaded(true);
        }).catch(error => {
            console.log(error.message);
            alert('Something went wrong fetching admin information.');
        })
    }, []);
    

    
    return (<div>
        {hasLoaded ? <Properties fetched_properties={adminResponse.properties} noProductMessage={''}/> : null}

    </div>);
}


