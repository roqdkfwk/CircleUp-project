// import { dotenv } from "dotenv";
import Header from "./components/Header";
import Router from "./routers/Routers";
import Course from './components/Card/Course';

const BACKEND_ADDRESS = import.meta.env.VITE_BACKEND_ADDRESS;

function App() {
 
  return (
    <div>
      <Header />
      <Router />
    </div>
  )
}

export default App
