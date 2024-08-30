import React, { useContext, useRef, useState } from 'react';
import { UserContext } from '../../context/UserContext';
import SockJS, { load } from "sockjs-client/dist/sockjs";
import Stomp from 'stompjs';
import {Button} from "react-bootstrap";
import './style.css';
import MyVerticallyCenteredModal from '../Modal/Modal';
import Spinner from 'react-bootstrap/Spinner';

export default function Join({setChatVisibility, setClient, setSocket}) {
    const usernameRef = useRef();
    const [modalShow, setModalShow] = useState(false);
    const [modalTitle, setModalTitle] = useState();
    const [modalContent, setModalContent] = useState();
    const [modalHeader, setModalHeader] = useState();
    const [loading, setLoading] = useState(false);

    const {setUser} = useContext(UserContext)

    const modalErroInput = () => {
        setModalShow(true);
        setModalTitle("Erro ao acessar");
        setModalHeader("Informe seu nickname");
        setModalContent("O nickname deve conter no minimo 3 caracteres.");
        setLoading(false);
    }

    const modalErroConexao = () => {
        setModalShow(true);
        setModalTitle("Erro ao acessar");
        setModalHeader("Não foi possivel se conectar ao chat");
        setModalContent("O nickname deve conter no minimo 3 caracteres.");
        setLoading(false);
    }

    const handleSubmit = async () => {
        const username = usernameRef.current.value
        setLoading(true);

        if(!username.trim() || username.length < 3){
            modalErroInput();
            return
        }

        setUser(username);

        await fetch("http://localhost:8080/ws")
            .then(response => {
                const socket = SockJS('http://localhost:8080/ws');
                const client = Stomp.over(socket);
                
                setSocket(socket);
                setClient(client);

                setLoading(false);
                setChatVisibility(true);
            })
            .catch(response => modalErroConexao());
    }
        
    return (
        <div className='container'>
            <h2>Bem vindo ao chatbox com Web Socket!!</h2>
                <input type="text" ref={usernameRef} placeholder='Your Username'/>
                <Button onClick={() => handleSubmit()} disabled={loading}>
                    {loading ? <Spinner animation="border" /> : "Acessar"}
                </Button>

                <MyVerticallyCenteredModal
                    show={modalShow}
                    onHide={() => setModalShow(false)}
                    title={modalTitle}
                    content={modalContent}
                    header={modalHeader}
                />
        </div>
    )
}
