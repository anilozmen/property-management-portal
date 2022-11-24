import './Message.css';
import {getUserType} from '../../services/token';
import {useRef} from "react";

export default function Message({isOwner = false, message, sendMessage}) {

    const inputRef = useRef();

    return (<div>
        <div className='question'>{message.message}</div>
        {message.reply && <div className='reply'>{message.reply}</div>}
        {!message.reply && isOwner && <div><input name={'reply'} ref={inputRef}/>
            <button onClick={() => {
                sendMessage(inputRef.current.value, message);
            }
            }>submit
            </button>
        </div>}
    </div>);
}


