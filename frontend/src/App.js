import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Registration from "./Containers/Registration/Registration";
import Login from "./pages/Login/Login";

function App() {

    return (
        <BrowserRouter>

            <div className="App">
                <Routes>
                    <Route path='/' element={<div>Home Page</div>} />
                    <Route path='registration' element={<Registration />} />
                    <Route path='login' element={<Login />} />
                </Routes>
            </div>

        </BrowserRouter>
    );
}

export default App;
