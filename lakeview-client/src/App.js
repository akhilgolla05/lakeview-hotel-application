import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import AddRoom from "./components/room/AddRoom";
import ExistingRooms from "./components/room/ExistingRooms";
import Home from "./components/home/Home";
import EditRoom from "./components/room/EditRoom";



function App() {
  return (
    <>
      <main>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/edit-room/:roomId" element={<EditRoom/>}/>
            <Route path="/existing-rooms" element={<ExistingRooms/>}/>
            <Route path="/add-room" element={<AddRoom/>}/>
          </Routes>
          
        </BrowserRouter>
      </main>
    
      {/* /add-room */}
      {/* <AddRoom/>
      <ExistingRooms/> */}
    </>
  );
}

export default App;
