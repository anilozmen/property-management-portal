import { Route, Routes, Navigate, Outlet } from "react-router-dom";
import Login from "../../components/Login/Login";
import ResetPassword from '../../components/ResetPassword/ResetPassword';
import ChangePassword from '../../components/ChangePassword/ChangePassword';

import Register from "../../components/Register/Register";
import VerifyEmail from "../../components/VerifyEmail/VerifyEmail";
import Properties from "../Properties/Properties";
import PropertyDetail from "../../components/PropertyDetail/PropertyDetail";
import ProtectedComponent from "../../components/ProtectedComponent/ProtectedComponent";
import { ADMIN, CUSTOMER, OWNER } from "../../constants/roles";
import AddNewProperty from "../../components/AddNewProperty/AddNewProperty";
import SavedProperties from "../SavedProperties/SavedProperties";
import Admin from "../Admin/Admin";
import HomeNavigator from "../../components/HomeNavigator/HomeNavigator";
import Offers from "./Offers";
import User from "../../components/User/User";
import OwnerProperties from "../../components/OwnerProperties/OwnerProperties";
import UpdateProperty from "../../components/UpdateProperty/UpdateProperty";
import React from "react";


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
            <Route path="properties/:id" element={
                <React.Fragment>
                    <Outlet />
                </React.Fragment>
            }>
                <Route path="" element={<PropertyDetail />} />
                <Route
                    path="update"
                    element={<ProtectedComponent requiredRole={OWNER} component={<UpdateProperty />} />}
                />
            </Route>
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
                path="offers"
                element={<ProtectedComponent requiredRole={CUSTOMER} component={<Offers />} />}
            />

            <Route
                path="admin"
                element={<ProtectedComponent requiredRole={ADMIN} component={<Admin />} />}
            />

            <Route
                path="admin/users"
                element={<ProtectedComponent requiredRole={ADMIN} component={<User />} />}
            />

            <Route
                path="admin/users/:userId/properties"
                element={<ProtectedComponent requiredRole={ADMIN} component={<OwnerProperties />} />}
            />

        </Routes>

    );

}

export default PageRoutes;