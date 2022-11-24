import { BrowserRouter } from "react-router-dom";
import Main from './pages/Main/Main';
import './App.css';
import { useDispatch } from "react-redux";
import { useEffect } from "react";
import { setRole } from "./reducers/user";

function App() {
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(setRole());
    }, []);

    return (
        <div className="App">
            <BrowserRouter>
                <Main />
            </BrowserRouter>
        </div>
    );
}

export default App;
