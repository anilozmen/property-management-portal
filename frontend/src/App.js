import './App.css';
import {useDispatch, useSelector} from 'react-redux';
import {add} from './reducers/counter';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import Registration from "./Containers/Registration/Registration";
import './services/axios';

function App() {
    // const dispatch = useDispatch();
    // const count = useSelector(state => state.counter.value);

    return (
        <BrowserRouter>

            <div className="App">
                {/*<span>Count: {count}</span>*/}
                {/*<button onClick={() => dispatch(add(8))}>Click</button>*/}
            </div>

            <Routes>
                <Route path='registration' element={<Registration/>}></Route>
            </Routes>
            
        </BrowserRouter>
    );
}

export default App;
