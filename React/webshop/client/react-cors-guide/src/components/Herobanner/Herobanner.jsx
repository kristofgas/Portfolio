import React from "react";
import Image from "react-bootstrap/Image";
import { Button, Col, Row, Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import "./Herobanner.css";
import heroimage from "../../resources/images/Index_hero/shoe_vector.png";
import UserGreetings from "../UserGreetings/UserGreetings";

export default function Herobanner() {
	let navigate = useNavigate();
	const routeChange = () => {
		let path = `/sale`;
		navigate(path);
	};

	return (
		<Container className="container_hero">
			<Row className="hero-banner_row">
				<div style={{ display: "flex" }}>
					<Col lg="7" sm="6">
						<UserGreetings />
						<Button
							style={{
								size: 5,
								marginLeft: 5,
								height: 45,
							}}
							variant="outline-danger"
							size="lg, sm, xl"
							color="black"
							className="hero-banner btn"
							onClick={routeChange}
						>
							Shop Sale Now!
						</Button>
					</Col>
					<Col lg="3" sm="4" xs="5">
						<Row className="image_row">
							<Image
								className="heroimage"
								alt="Profile image"
								img
								src={heroimage}
							/>
						</Row>
					</Col>
				</div>
			</Row>
		</Container>
	);
}
/* <div classname ="herobanner">
      <h1 classname = "htext">End of the month?</h1>
      <Button variant="outline-success" size="lg" color = "black" className = "hero-banner btn" >Shop Sale Now </Button>
      <Image src = "client/react-cors-guide/src/resources/images/MAN-8/8.2_man.jpeg" className = "image"></Image>
  
    </div>*/

/* <section class="hero-banner bg-warning py-5">
        <div class="container">
          <div class="row row align-items-center">
            <div class="col-lg-5 offset-lg-1 order-lg-1">
              <img
                src="/images/Index_hero/shoe_vector.png"
                class="img-fluid"
                width="400"
                height="auto"   
              />
            </div>
            <div class="col-lg-6">
              <h1 class="mt-3" id="messs">End of the month?</h1>
              <!-- <p class="lead text-secondary my-5">
                It is a long established fact that a reader will be distracted by
                the readable content of a page when looking at its layout. Point
                of using Lorem Ipsum is that it has a more-or-less normal
                distribution of letters.
              </p> -->
              <a href="category_sale.html" class="btn btn-outline-dark btn-lg border"
                >Shop sale now</a
              >*/
