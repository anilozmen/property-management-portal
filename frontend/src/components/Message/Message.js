import './Message.css';
import {getUserType} from '../../services/token';
import {useRef} from "react";

export default function Message({isOwner = false, message, sendMessage}) {

    const inputRef = useRef();

    return (<div>
        <div className='question'><b>You:</b> {message.message}</div>
        {message.reply && <div className='reply'><b>Owner:</b> {message.reply}</div>}
        {!message.reply && isOwner && <form onSubmit={(event) => {
            event.preventDefault();
            if(inputRef.current.value !== '') {
            sendMessage(inputRef.current.value, message);
            }
            inputRef.current.value = "";
        }}><input name={'reply'} ref={inputRef}/>
            <button>submit</button>
        </form>}
    </div>);
}


