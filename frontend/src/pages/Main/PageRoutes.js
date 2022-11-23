import { Route, Routes } from "react-router";
import Login from "../../components/Login/Login";
import ResetPassword from '../../components/ResetPassword/ResetPassword';
import ChangePassword from '../../components/ChangePassword/ChangePassword';
import Homepage from "../../components/Homepage/Homepage";
import Register from "../../components/Register/Register";


const PageRoutes = (props) => {

    return (
        <Routes>
            <Route path='/' element={<Homepage />} />
            <Route path='register' element={<Register />} />
            <Route path='login' element={<Login />} />
            <Route path='reset-password' element={<ResetPassword />} />
            <Route path='change-password' element={<ChangePassword />} />
        </Routes>

    );

}

export default PageRoutes;