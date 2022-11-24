import './Messages.css';
import {useEffect, useState} from "react";
import axios from "axios";
import Message from "../Message/Message";

export default function Messages({isOwner, propertyId}) {

    const [messages, setMessages] = useState([]);

    const fetchMessages = () => {
        axios.get('/messages', {
            params: {
                "property_id": propertyId
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
        return <div key={message.id}><Message message={message} isOwner={isOwner}/></div>;
    });

    function sendMessage() {

    }

    return (<div className={'messages'}>
        {messagesView}

    </div>);
}


