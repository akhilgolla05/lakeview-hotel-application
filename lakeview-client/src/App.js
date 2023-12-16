import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import AddRoom from "./components/room/AddRoom";
import ExistingRooms from "./components/room/ExistingRooms";
import Home from "./components/home/Home";
import EditRoom from "./components/room/EditRoom";
import NavBar from "./components/layout/NavBar";
import Footer from "./components/layout/Footer";
import RoomListing from "./components/room/RoomListing";
import Admin from "./components/admin/Admin";
import CheckOut from "./components/bookings/CheckOut";
import BookingSuccess from "./components/bookings/BookingSuccess";
import Bookings from "./components/bookings/Bookings";



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
            <Route path="/admin" element={<Admin/>} />
            <Route path="/book-room/:roomId" element={<CheckOut/>}/>
            <Route path="/booking-success" element={<BookingSuccess/>}/>
            <Route path="/existing-bookings" element={<Bookings/>}/>
          
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
