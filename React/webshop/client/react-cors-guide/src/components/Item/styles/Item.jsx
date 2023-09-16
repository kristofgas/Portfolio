import styled from "styled-components";

//.product-wrapper
export const Container = styled.div`
	margin-bottom: 20px;
	height: 270px;
	width: 300px;
	position: relative; ;
`;
//.product
export const Card = styled.div`
	box-shadow: 12px 24px 61%;
	margin-left: 25px;
	margin-top: 25px;
	height: 250px;
	width: 250px;
	position: relative;
	border-radius: 0.5rem;

	&:hover {
		box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2),
			0 6px 20px 0 rgba(0, 0, 0, 0.19);
	}
`;
//with a link we want to change the default blue color, and seperation of space
export const Link = styled.a`
font-weight: 350;
font-size: medium;
font-family:'Lato', sans-serif 
position: absolute;

}

`;

//btn-default in CSS/ prototype miniproject-1
export const Label = styled.div`
	border-radius: 0;
	background-color: red;
	border-radius: 2px;
	box-shadow: 0px 10px 10px #e0e0e0;
	z-index: 1;
	margin-left: 10px;
	margin-top: 10px;
	color: white;
	width: 100px;
	font-size: 14px;
	font-weight: 900;
	position: absolute;
	width: 25%;
	&:hover {
		transform: scale(1.1);
	}
`;

//.header deleted
export const Image = styled.img`
      height: 100%;
      width: 100%;
      position: relative;
      border-radius: 0.5rem;
      index: '1'
      object-fit: cover;
      
    }`;

//.footer changed into Info
export const Info = styled.div`
	bottom: 10px;
	position: absolute;
	width: 100%;
	justify-content: space-between;
	index: "2";
`;

//.brand-name in CSS/ prototype miniproject-1
export const Title = styled.span`
	width: 33%;
	float: left;
	padding: 20px 15px;
	font-weight: 600;
	font-size: medium;
	font-family: "Lato", sans-serif;
	padding-bottom: 20px;
`;

export const Price = styled.span`
	width: 33%;
	float: left;
	padding: 20px 15px;
	font-weight: 350;
	font-size: medium;
	font-family: "Lato", sans-serif;
`;

export const IconContainer = styled.span`
	align-self: center;
	padding: 18px 15px;
	width: 33%;
	float: left;
	&:hover {
		transform: scale(1.25);
	}
`;
