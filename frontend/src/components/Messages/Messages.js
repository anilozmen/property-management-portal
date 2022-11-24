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

    const sendMessageHandler = (sendingMessage, message) => {
        sendMessage(sendingMessage, message);
    }


    const messagesView = messages.map(message => {
        return <div key={message.id}><Message message={message} isOwner={isOwner} sendMessage={sendMessageHandler}/>
        </div>;
    });

    function sendMessage(myMessage, message) {
        if (!isOwner) {
            axios.post('/messages', {
                message: myMessage,
                propertyId: message.propertyId
            }).then(response => {
                fetchMessages();
            }).catch(error => {
                console.log(error.message);
                alert('something went wrong while posting message !!!');
            })
        } else {
            axios.put('/messages/' + message.id, {
                reply: myMessage
            }).then(response => {
                fetchMessages();
            }).catch(error => {
                console.log(error.message);
                alert('something went wrong while putting message !!!');
            })
        }
    }

    return (<div className={'messages'}>
        {messagesView}

    </div>);
}


