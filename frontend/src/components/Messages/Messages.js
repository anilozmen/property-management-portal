import './Messages.css';
import {useEffect, useState} from "react";
import axios from "axios";
import Message from "../Message/Message";

export default function Messages() {

    const [messages, setMessages] = useState([]);

    const fetchMessages = () => {
        axios.get('/messages', {
            params: {
                "property_id": 1
            }
        }).then(response => {
            setMessages(response.data);
        })
            .catch(error => {
                console.log('what just happened');
                alert('something went wrong...');
            })
    };

    useEffect(() => {
        fetchMessages();
    }, []);
    

    const messagesView = messages.map(message => {
        return <Message message={message}/>;
    });

    return (<div>
        {messagesView}
    </div>);
}


