import { Navbar, NavDropdown, Nav, Container, Button } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

export default function NavbarComponent() {
	return (
		<>
			<div>
				<Navbar bg="dark" expand="md" variant="dark">
					<Container fluid>
						<LinkContainer to="/home">
							<Navbar.Brand>ellö</Navbar.Brand>
						</LinkContainer>
						<Navbar.Toggle aria-controls="navbarScroll" />
						<Navbar.Collapse id="navbarScroll">
							<Nav
								className="me-auto my-2 my-lg-0"
								style={{ maxHeight: "300px" }}
								navbarScroll
							>
								<LinkContainer to="/man">
									<Nav.Link>Man</Nav.Link>
								</LinkContainer>
								<LinkContainer to="/woman">
									<Nav.Link>Woman</Nav.Link>
								</LinkContainer>

								<LinkContainer to="/sale">
									<Nav.Link className="text-warning">Sale</Nav.Link>
								</LinkContainer>
							</Nav>
							<Nav>
								<LinkContainer to="/login">
									<Nav.Link>Login</Nav.Link>
								</LinkContainer>
							</Nav>
							<Nav>
								<LinkContainer to="/cart">
									<Nav.Link>
										<Button variant="outline-warning">Cart</Button>
									</Nav.Link>
								</LinkContainer>
							</Nav>
						</Navbar.Collapse>
					</Container>
				</Navbar>
			</div>
		</>
	);
}
