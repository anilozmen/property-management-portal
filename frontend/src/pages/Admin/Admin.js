import './Admin.css';
import Properties from "../Properties/Properties";
import {useEffect, useState} from "react";
import axios from "axios";
import RecentUsersList from "../../components/RecentUsersList/RecentUsersList";
import User from "../../reducers/user";
import UserList from "../../components/User/UserList";

export default function Admin() {
    
    const [hasLoaded, setHasLoaded] = useState(false);
    const [adminResponse, setAdminResponse] = useState({properties: [], users: []});

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
        {hasLoaded ? <Properties title={'Recent Transactions Properties'} fetched_properties={adminResponse.properties} noProductMessage={''}/> : null}
        {hasLoaded ? <UserList users={adminResponse.users} title={'Recent Users'}/>: null}
    </div>);
}


