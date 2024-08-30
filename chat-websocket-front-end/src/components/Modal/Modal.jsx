import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

export default function MyVerticallyCenteredModal(props) {
  return (
    <Modal
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {props.title}
        </Modal.Title>
      </Modal.Header>

      <Modal.Body>
        <h4>{props.header}</h4>
        <p>{props.content}</p>
      </Modal.Body>
      <Modal.Footer>
        <Button variant='danger' onClick={props.onHide}>Fechar</Button>
      </Modal.Footer>
    </Modal>
  );
}