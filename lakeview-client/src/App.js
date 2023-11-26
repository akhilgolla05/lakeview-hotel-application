import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import AddRoom from "./components/room/AddRoom";
import ExistingRooms from "./components/room/ExistingRooms";
import Home from "./components/home/Home";
import EditRoom from "./components/room/EditRoom";
import NavBar from "./components/layout/NavBar";
import Footer from "./components/layout/Footer";
import RoomListing from "./components/room/RoomListing";



function App() {
  return (
    <>
      <main>
        <BrowserRouter>
        <NavBar/>
          <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/edit-room/:roomId" element={<EditRoom/>}/>
            <Route path="/existing-rooms" element={<ExistingRooms/>}/>
            <Route path="/add-room" element={<AddRoom/>}/>
            {/* <Route path="/browse-all-rooms" element={<RoomListing/>}/> */}
            <Route path="/browse-all-rooms" element={<RoomListing />} />
          </Routes>
          
        </BrowserRouter>
        <Footer/>
      </main>
    
      {/* /add-room */}
      {/* <AddRoom/>
      <ExistingRooms/> */}
    </>
  );
}

export default App;
