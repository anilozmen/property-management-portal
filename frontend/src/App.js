import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Registration from "./Containers/Registration/Registration";
import Login from "./pages/Login/Login";
import ResetPassword from './pages/ResetPassword/ResetPassword';
import ChangePassword from './pages/ChangePassword/ChangePassword';

function App() {

    return (
        <BrowserRouter>

            <div className="App">
                <Routes>
                    <Route path='/' element={<div>Home Page</div>} />
                    <Route path='registration' element={<Registration />} />
                    <Route path='login' element={<Login />} />
                    <Route path='reset-password' element={<ResetPassword />} />
                    <Route path='change-password' element={<ChangePassword />} />
                </Routes>
            </div>

        </BrowserRouter>
    );
}

export default App;
