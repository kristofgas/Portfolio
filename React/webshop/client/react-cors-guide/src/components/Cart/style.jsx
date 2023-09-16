import styled from 'styled-components';

export const Container = styled.div` //div

margin: 0px 10px;


`;

export const SectionHeader =styled.h2` //section header
font-family: 'Poppins', sans-serif ;
font-weight: normal;
color: #333;
text-align: center;
font-size: 2.5em;
margin: 0px 10px;
margin-left: 50%;

}
 
`;

export const TopButton = styled.button`
  padding: 10px;
  font-weight: 500;
  cursor: pointer;
  border-radius: 5px;
  `;


export const Top = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
`;

export const TitleCart =styled.h1` //cart header
font-weight: 300;
text-align: center;
`;


export const Card = styled.div` //.div
display: flex;
align-items: center;
justify-content: space-between;
padding: 20px;
    
`;


export const ProductDetail = styled.div`
  flex: 2;
  display: flex;
`;


export const Details = styled.div`
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
`;

export const ProductName = styled.span``;

export const ProductId = styled.span``;

export const ProductColor = styled.div`
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: ${(props) => props.color};
`;

export const ProductSize = styled.span``;

export const PriceDetail = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export const ProductAmountContainer = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 20px;
`;

export const ProductAmount = styled.div`
  font-size: 24px;
  margin: 5px;
 
`;

export const ProductPrice = styled.div`
  font-size: 30px;
  font-weight: 200;
  
`;


export const Summary = styled.div`
  flex: 1;
  border: 0.5px solid lightgray;
  border-radius: 10px;
  padding: 20px;
  height: 50vh;
`;

export const SummaryTitle = styled.h1`
  font-weight: 200;
`;

export const SummaryItem = styled.div`
  margin: 30px 0px;
  display: flex;
  justify-content: space-between;
  font-weight: ${(props) => props.type === "total" && "500"};
  font-size: ${(props) => props.type === "total" && "24px"};
`;

export const SummaryItemText = styled.span``;

export const SummaryItemPrice = styled.span``;

export const Button = styled.button`
  width: wrap-around;
  padding: 10px;
  background-color: black;
  color: white;
  font-weight: 600;
  radius: 20px;
`;
    

export const CartRow = styled.div`
    display: flex;
}
`;

export const Text =styled.span`
margin-top: 2%;
padding-left: 30px;
 
`;


export const CartColumn = styled.div`
    display: flex;
    align-items: center;
    border-bottom: 1px solid black;
  
    padding-bottom: 10px;
    margin-top: 10px;`;


export const CartItem = styled.div`
width: 50%;
height: 50%;
padding-top: 2%;
padding-bottom: 2%;

`;


;

export const Total = styled.div`
margin-top: 2%;
`
;

