
import axios from "axios";

export const api = axios.create(
    {
        baseURL: "http://localhost:9192"
    }
)

/*
this function adds a new room to the database
*/

export async function addRoom(photo,roomType,roomPrice)
{
    const formData = new FormData()
    formData.append("photo",photo)
    formData.append("roomType",roomType)
    formData.append("roomPrice",roomPrice)

    const response = await api.post("/rooms/add/new-room", formData)
    if(response.status === 201){
        return true
    }else{
        return false
    }
} 

/* 
this function gets all room types from the
 Database
*/
 
export async function getRoomTypes(){
    try{
        const response = await api.get("/rooms/room-types")
        return response.data
    }catch(error){
        throw new Error("Error fetching room types")

    }
}

/*
this function gets all rooms from the DB
*/
export async function getAllRooms(){
    try{

        const result  = await api.get("/rooms/all-rooms")
        return result.data
    }catch(error){
        throw new Error("Error Fetching rooms")
    }
}


/*
handle the delete function on id
*/

export async function deleteRoom(roomId){
    try{
        const result = await api.delete(`/rooms/delete/room/${roomId}`)

        return result.data
    }catch(error){
        throw new Error(`Error deleting room : ${error.message}`)
    }
}


/*
this function updates the room
*/

export async function updateRoom(roomId,roomData){

    const formData = new FormData();
    formData.append("roomType", roomData.roomType)
    formData.append("roomPrice", roomData.roomPrice)
    formData.append("photo", roomData.photo)

    const response = await api.put(`/rooms/update/${roomId}`, formData)

    return response

}

/*
This function gets a room by Id
*/
export async function getRoomById(roomId){

    try{
        const result = await api.get(`/rooms/room/${roomId}`)
        return result.data
    }catch(error){
        throw new Error(`Error Fetching room : ${error.message}`)

    }
}

/*
    function to reserve a room
*/
export async function bookRoom(roomId, booking){
    try{
        const response = await api.post(`/bookings/room/${roomId}/booking`,booking)
        
        return response.data
    
    }catch(error){
        if(error.response && error.response.data){
            throw new Error(error.response.data)
        }else{
            throw new Error(`Error Booking Room : ${error.message} ` )

        }
    }
}


/*
get all bookings made
*/
export async function getAllBookings(){
    try{
        const result = await api.get("/bookings/all-bookings")
        return result.data
    }catch(error){
        throw new Error(`Error fetching bookings : ${error.message}`)

    }
}


export async function getBookingByConfirmationCode(confirmationCode){
    try{
        const result = await api.get(`/bookings/confirmation/${confirmationCode}`)
        return result.data
    }catch(error){
        if(error.response && error.response.data){
            throw new Error(error.response.data)
        }else{
            throw new Error(`Error Finding Booking : ${error.message}`)
        }
    }
}

/*
function to cancel the reservation
*/
export async function cancelBooking(bookingId){
    try{
        const result = await api.delete(`/bookings/booking/${bookingId}/delete`)
        return result.data
    }catch(error){
        throw new Error(`Error cancelling booking : ${error.message}`)

    }
}


//get available rooms
export async function getAvailableRooms(checkInDate, checkOutDate, roomType){
    const result = await api.get(`rooms/available-rooms?
    checkInDate=${checkInDate}
    &checkOutDate=${checkOutDate}
    &roomType=${roomType}`)

    return result
}



