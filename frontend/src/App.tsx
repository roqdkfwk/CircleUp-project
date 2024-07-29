// import { dotenv } from "dotenv";
import Header from "./components/Header";
import Router from "./routers/Routers";
// import Course from "./components/Card/Course";
// import OpenViduSession from "openvidu-react";
import Footer from "./components/Footer";

// const BACKEND_ADDRESS = import.meta.env.VITE_BACKEND_ADDRESS;

function App() {
  return (
    <div>
      <Header />
      <Router />
      <Footer />
    </div>
  );
}

export default App;
