
import React, { useEffect, useState } from 'react'
import RoomFilter from '../common/RoomFilter'
import RoomPaginator from '../common/RoomPaginator'
import { Col, Row } from 'react-bootstrap'
import { deleteRoom, getAllRooms } from '../utils/ApiFunctions'
import {FaEdit, FaEye, FaPlus, FaTrashAlt} from 'react-icons/fa'
import { Link } from 'react-router-dom'

const ExistingRooms = () => {

    const [rooms, setRooms] = useState([])

    const [currentPage, setCurrentPage] = useState(1)

    const [roomsPerPage] = useState(8)

    const [isLoading, setLoading] = useState(false)

    const [filteredRooms, setFilteredRooms] = useState([])

    const [selectedRoomType, setSelectedRoomType] = useState("")

    const [successMessage, setSuccessMessage] = useState("")

    const [errorMessage, setErrorMessage] = useState("")


    useEffect(()=>{

        fetchRooms()
    },[])

/*
fetch all rooms from DB
*/
    const fetchRooms = async()=>{
        setLoading(true)
        try{
            const result = await getAllRooms()
            setRooms(result)
            setLoading(false)
        }catch(error){
            setErrorMessage(error.message)

        }

    }

    useEffect(()=>{
        if(selectedRoomType === ""){
            setFilteredRooms(rooms)
        }else{
            const filtered = rooms.filter((room)=>room.roomType === selectedRoomType)
            setFilteredRooms(filtered)
        }

        setCurrentPage(1)
    },[rooms, selectedRoomType])


    /*
    pagination : no of rooms to display on each page
    */
   const calculateTotalPages = (filteredRooms, roomsPerPage, rooms)=>{

    const totalRooms = filteredRooms.length > 0 ? filteredRooms.length : rooms.length
    return Math.ceil(totalRooms / roomsPerPage)
   } 

   const indexOfLastRoom = currentPage * roomsPerPage
   const indexOfFirstRoom = indexOfLastRoom - roomsPerPage

   const currentRooms = filteredRooms.slice(indexOfFirstRoom, indexOfLastRoom)


   const handlePaginationClick = (pageNumber)=>{
    setCurrentPage(pageNumber)
   }

   /*
    handling the delete function
   */

    const handleDelete = async (roomId)=>{

        try{
            const result = await deleteRoom(roomId)
            if(result === ""){
                setSuccessMessage(`Room no : ${roomId} was deleted`)
                fetchRooms()
            }else{
                console.error(`Error deleting room : ${result.message}`)
            }
        }catch(error){
            setErrorMessage(error.message)
        }

        //after 3 seconds ay message that is displayed on the page going to be cleared off
        setTimeout(()=>{
            setSuccessMessage("")
            setErrorMessage("")
        }, 3000)
    }

  return (
    <>
      {isLoading ? (
        <p>Loading existing rooms</p>
      ): (
        <>
        <section className="mt-5 mb-5 container">
            <div className="d-flex justify-content-between mb-3 mt-5">
                <h2>Existing Rooms</h2>

            </div>
            <Row>
                <Col md={6} className="mb-3 mb-md-0">
                    <RoomFilter data={rooms} 
                    setFilteredData={setFilteredRooms}/>
                </Col>

                <Col md={6} className="d-flex justify-content-end">
                    <Link to={"/add-room"}
                    className="">
                        <FaPlus/> Add Room
                    </Link>
                </Col>
            </Row>

            <table className="table table-bordered table-hover">
                <thead>
                    <tr className="text-center">
                        <th>ID</th>
                        <th>Room Type</th>
                        <th>Room Price</th>
                        <th>Actions</th>
                        
                    </tr>
                </thead>

                <tbody>
                    {
                        currentRooms.map((room)=>(
                            <tr key={room.id} className="text-center">
                                <td>{room.id}</td>
                                <td>{room.roomType}</td>
                                <td>{room.roomPrice}</td>
                                <td className="gap-2">
                                    <Link to={`/edit-room/${room.id}`}>
                                        <span className="btn btn-info btn-sm">
                                            <FaEye/>
                                            </span>
                                        <span className="btn btn-warning btn-sm">
                                            <FaEdit/>
                                            </span>
                                        </Link>
                                    
                                    
                                    <button
                                    className="btn btn-danger btn-sm"
                                    onClick={()=>handleDelete(room.id)}>
                                        <FaTrashAlt/>
                                    </button>
                                    
                                </td>
                        
                            </tr>
                        ))
                    }
                </tbody>
            </table>

            <RoomPaginator 
                currentPage={currentPage}
                totalPages={calculateTotalPages(filteredRooms, roomsPerPage, rooms)}
                onPageChange={handlePaginationClick}/>
        </section>
        </>
      )}

    </>
  )
}

export default ExistingRooms

