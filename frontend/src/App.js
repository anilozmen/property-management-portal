import logo from './logo.svg';
import './App.css';
import { useDispatch, useSelector } from 'react-redux';
import { add } from './reducers/counter';

function App() {
  const dispatch = useDispatch();
  const count = useSelector(state => state.counter.value);

  return (
    <div className="App">
      <span>Count: {count}</span>
      <button onClick={() => dispatch(add(8))}>Click</button>
    </div>
  );
}

export default App;
