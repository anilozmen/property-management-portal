import { Route, Routes, Navigate } from "react-router";
import Login from "../../components/Login/Login";
import ResetPassword from '../../components/ResetPassword/ResetPassword';
import ChangePassword from '../../components/ChangePassword/ChangePassword';

import Register from "../../components/Register/Register";
import Properties from "../Properties/Properties";
import PropertyDetail from "../../components/PropertyDetail/PropertyDetail";
import ProtectedComponent from "../../components/ProtectedComponent/ProtectedComponent";
import { CUSTOMER, OWNER } from "../../constants/roles";
import AddNewProperty from "../../components/AddNewProperty/AddNewProperty";
import SavedProperties from "../SavedProperties/SavedProperties";


const PageRoutes = (props) => {

    return (
        <Routes>
            <Route path='/' element={<Navigate replace to="/properties" />} />
            <Route path='register' element={<Register />} />
            <Route path='login' element={<Login />} />
            <Route path='reset-password' element={<ResetPassword />} />
            <Route path='change-password' element={<ChangePassword />} />
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
        </Routes>

    );

}

export default PageRoutes;