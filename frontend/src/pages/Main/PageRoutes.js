import { Route, Routes, Navigate } from "react-router";
import Login from "../../components/Login/Login";
import ResetPassword from '../../components/ResetPassword/ResetPassword';
import ChangePassword from '../../components/ChangePassword/ChangePassword';

import Register from "../../components/Register/Register";
import VerifyEmail from "../../components/VerifyEmail/VerifyEmail";
import Properties from "../Properties/Properties";
import PropertyDetail from "../../components/PropertyDetail/PropertyDetail";
import ProtectedComponent from "../../components/ProtectedComponent/ProtectedComponent";
import { CUSTOMER, OWNER, ADMIN } from "../../constants/roles";
import AddNewProperty from "../../components/AddNewProperty/AddNewProperty";
import SavedProperties from "../SavedProperties/SavedProperties";
import Admin from "../Admin/Admin";
import HomeNavigator from "../../components/HomeNavigator/HomeNavigator";


const PageRoutes = (props) => {

    return (
        <Routes>
            <Route path='/' element={<HomeNavigator />} />
            <Route path='register' element={<Register />} />
            <Route path='login' element={<Login />} />
            <Route path='reset-password' element={<ResetPassword />} />
            <Route path='change-password' element={<ChangePassword />} />
            <Route path='verify-email' element={<VerifyEmail />} />
            <Route path='properties' element={<Properties />} />
            <Route path="properties/:id" element={<PropertyDetail />} />
            <Route
                path="properties/new"
                element={<ProtectedComponent requiredRole={OWNER} component={<AddNewProperty />} />}
            />
            <Route
                path="properties/saved"
                element={<ProtectedComponent requiredRole={CUSTOMER} component={<SavedProperties />} />}
            />
            <Route
                path="properties/new"
                element={<ProtectedComponent requiredRole={OWNER} component={<AddNewProperty />} />}
            />

            <Route
                path="admin"
                element={<ProtectedComponent requiredRole={ADMIN} component={<Admin />} />}
            />

        </Routes>

    );

}

export default PageRoutes;