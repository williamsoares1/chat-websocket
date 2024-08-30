import { useEffect, useState } from 'react'
import './App.css'
import Chat from './components/Chat/Chat'
import Join from './components/Join/Join'
import { UserProvider } from './context/UserContext'

function App() {
  const [chatVisibility, setChatVisibility] = useState(false)
  const [client, setClient] = useState()
  const [socket, setSocket] = useState()

  return (
    <UserProvider>
      <main>
        {
          chatVisibility
          ? <Chat client={client} socket={socket}/>
          : <Join setSocket={setSocket} setClient={setClient} setChatVisibility={setChatVisibility}/>
        }
      </main>
    </UserProvider>
  )
}

export default App
