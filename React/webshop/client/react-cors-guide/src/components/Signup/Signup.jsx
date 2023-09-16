import React, { useState } from "react";
import { Form, Button, Container } from "react-bootstrap";
import "./Signup.css";

export default function Signup() {
  const [firstname, setFirstName] = useState("");
  const [lastname, setLastName] = useState("");
  const [email, setEmail] = useState("");

  function Register() {
    console.warn(firstname, lastname, email);
  }

  return (
    <Container className="container_form">
      <Form className="signup_form" div class="signup_form">
        <Form.Group className="firstname" controlId="formBasicName">
          <Form.Label>First Name</Form.Label>
          <Form.Control
            type="name"
            value={firstname}
            onChange={(e) => setFirstName(e.target.value)}
            placeholder="Enter your first name"
          />
        </Form.Group>
        <Form.Group className="lastname" controlId="formBasicName">
          <Form.Label>Last Name</Form.Label>
          <Form.Control
            type="name"
            value={lastname}
            onChange={(e) => setLastName(e.target.value)}
            placeholder="Enter your last name"
          />
        </Form.Group>
        <Form.Group className="email" controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Enter your email"
          />
          <Form.Text className="text">
            We'll never share your email with anyone else.
          </Form.Text>
        </Form.Group>
        <Button
          onClick={Register}
          className="signup_button"
          variant="secondary"
        >
          SIGN UP
        </Button>
      </Form>
    </Container>
  );
}
