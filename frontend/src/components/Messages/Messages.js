import './Messages.css';
import {useEffect, useRef, useState} from "react";
import axios from "axios";
import Message from "../Message/Message";

export default function Messages({isOwner, propertyId}) {

    const [messages, setMessages] = useState([]);
    const inputRef = useRef();

    const fetchMessages = () => {
        axios.get('/messages', {
            params: {
                "property_id": propertyId
            }
        }).then(response => {
            setMessages(response.data);
        })
            .catch(error => {
                console.log(error.message);
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
                propertyId: propertyId
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

    function sendReplyOwner(event) {
        event.preventDefault();
        sendMessage(inputRef.current.value);
        inputRef.current.value = "";
    }

    return (<div>
        <div className={'messages'}>
            {messagesView}
        </div>
        {!isOwner && <form onSubmit={sendReplyOwner}><input ref={inputRef}/>
            <button onClick={sendReplyOwner}>submit
            </button>
        </form>}
    </div>);
}


