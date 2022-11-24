import './Message.css';
import {getUserType} from '../../services/token';

export default function Message({isOwner = false, message}) {
    
    return (<div>
        {message.reply}<br/>
        {!isOwner && message.message}
        
    </div>);
}


