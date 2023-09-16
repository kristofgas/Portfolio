import React, { state, useState } from "react";
import "./Button_Counter.css";
import { Container, Row, Col, Grid, Card, Button } from "react-bootstrap";

const Button_Counter = () => {
  const [count, setCount] = useState(0);

  const handleIncrement = (event) => {
    setCount(count + 1);
  };

  const handleDecrement = (event) => {
    setCount(count - 1);
  };

  return (
    <div>
      <Button onClick={handleDecrement}>-</Button>
      {count}
      <Button onClick={handleIncrement}>+</Button>
    </div>
  );
};

export default Button_Counter;
