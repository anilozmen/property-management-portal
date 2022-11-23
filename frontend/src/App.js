import { BrowserRouter } from "react-router-dom";
import Main from './pages/Main/Main';
import './App.css';
function App() {

    return (
        <div className="App">
            <BrowserRouter>
                <Main />
            </BrowserRouter>
        </div>
    );
}

export default App;
