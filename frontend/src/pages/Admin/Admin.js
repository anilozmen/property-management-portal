import './Admin.css';
import Properties from "../Properties/Properties";
import {useEffect, useState} from "react";
import axios from "axios";

export default function Admin() {
    const [adminResponse, setAdminResponse] = useState({properties: []});

    useEffect(() => {
        axios.get('/admin').then(response => {
            console.log(response.data);
            setAdminResponse(response.data);
        }).catch(error => {
            console.log(error.message);
            alert('Something went wrong fetching admin information.');
        })
    }, []);
    
    
    return (<div>
        <Properties fetched_properties={adminResponse.properties} noProductMessage={''}/>

    </div>);
}


