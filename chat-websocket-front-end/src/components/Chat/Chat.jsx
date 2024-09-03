import React, { useContext, useEffect, useRef, useState } from 'react';
import SockJS from "sockjs-client/dist/sockjs";
import Stomp from 'stompjs';
import { UserContext } from '../../context/UserContext';

export default function Chat({client, socket}) {
    const messageRef = useRef()
    const [messages, setMessages] = useState([])

    const {user} = useContext(UserContext)

    useEffect(() => {
        console.log("Usaurio:" + user)
        connect()
    }, [])

    const connect = () => {
        client.connect({}, (frame) => {
            console.log(frame)
        
            client.subscribe("/topic/messages", (message) => {
                showMessage(JSON.parse(message.body))
            })
        })
    }

    const showMessage = (message1) => {
        setMessages(prevMessages => [message1, ...prevMessages])
    }

    const sendMessage = (message) => {
        const messageOBJ = {
            user,
            message
        }

        client.send('/app/message', {}, JSON.stringify(messageOBJ))
    }

    const handleSubmit = () => {
        sendMessage(messageRef.current.value)
    }

    return (
        <div style={{backgroundColor: "white"}}>
            <h1>CHAT</h1>
            <input type="text" ref={messageRef} placeholder='Your Message'/>
            <button onClick={() => handleSubmit()}>SEND</button>
            <ul>
                {
                    messages.map((message) => {
                        return (
                            <li>
                                <h1>{message.user}</h1>
                                <p>{message.message}</p>
                            </li>
                        )
                    })
                }
            </ul>
        </div>
    )
}
