import React from 'react'
import HeaderMain from '../layout/HeaderMain'
import HotelService from '../common/HotelService'
import Parallax from '../common/Parallax'

const Home = () => {
  return (
    <section>
      <HeaderMain/>
      <section className='container'>
        <Parallax/>
        <HotelService/>
        <Parallax/>

      </section>

    </section>
  )
}

export default Home
