import './HomeNavigator.css';
import {useSelector} from "react-redux";
import Admin from "../../pages/Admin/Admin";
import {Navigate} from "react-router";

export default function HomeNavigator() {

    const userRole = useSelector((state) => state.user.role);

    if(userRole === "ADMIN") {
        return <Navigate to={'/admin'}/>;
    }
    
    return <Navigate to={'/properties'}/>;
    
}


