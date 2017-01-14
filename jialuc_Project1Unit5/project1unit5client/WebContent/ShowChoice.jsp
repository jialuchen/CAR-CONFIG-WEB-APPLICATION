<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<%	
	String model = ""; 
	String basePrice = ""; 
	String color = "";
	String transmission = "";
	String bracket = "";
	String airbags = "";
	String moonroof = "";
	String colorPrice = "";
	String transmissionPrice = "";
	String absPrice = "";
	String airbagsPrice = "";
	String moonroofPrice = "";
	float totalCost = 0;

	//store optionset+price
	String[] rawParameter = null; 
	//get baseprice and model
	basePrice = (String) session.getAttribute("basePrice");
	model = (String) session.getAttribute("modelName");

	if(model!=null ){
		totalCost = Float.parseFloat(basePrice);
	} else if(model == null){
		model = "";
	}else if (basePrice == null){
		basePrice = "";
		totalCost = Float.parseFloat(basePrice);
	}

/*String modelandbaseprice = (String) session.getAttribute("modelAndBaseprice");
	if (modelandbaseprice != null) {
		rawParameter = modelandbaseprice.split("=");
		model = rawParameter[0];
		basePrice = rawParameter[1];

		totalCost = Float.parseFloat(basePrice);
	} else {
		model = "";
		basePrice = "";
		totalCost = Float.parseFloat(basePrice);
	}*/
		
	//split pairs into optionset and price
	String colorAndPrice = request.getParameter("color");
	rawParameter = colorAndPrice.split("=");
	color = rawParameter[0];
	colorPrice = rawParameter[1];
	totalCost += Float.parseFloat(colorPrice);
	
	String transmissionAndPrice = request.getParameter("transmission");
	rawParameter = transmissionAndPrice.split("=");
	transmission = rawParameter[0];
	transmissionPrice = rawParameter[1];
	totalCost += Float.parseFloat(transmissionPrice);

	String bracketAndPrice = request.getParameter("bracket");
	rawParameter = bracketAndPrice.split("=");
	bracket = rawParameter[0];
	absPrice = rawParameter[1];
	totalCost += Float.parseFloat(absPrice);

	String airbagAndPrice = request.getParameter("airbag");
	rawParameter = airbagAndPrice.split("=");
	airbags = rawParameter[0];
	airbagsPrice = rawParameter[1];
	totalCost += Float.parseFloat(airbagsPrice);

	String moonroofAndPrice = request.getParameter("moonroof");
	rawParameter = moonroofAndPrice.split("=");
	moonroof = rawParameter[0];
	moonroofPrice = rawParameter[1];
	totalCost += Float.parseFloat(moonroofPrice);
%>

<!-- draw html below -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Car Configuration Proof of Conception</title>
</head>
<body>
		<div>Here is what you selected</div><br>
		<table border="1">
			<tr>
				<td><%=model%></td>
				<td>base price</td>
				<td><%=basePrice%></td>
			</tr>
			<tr>
				<td>Color</td>
				<td><%=color%></td>
				<td><%=colorPrice%></td>
			</tr>
			<tr>
				<td>Transmission</td>
				<td><%=transmission%></td>
				<td><%=transmissionPrice%></td>
			</tr>
			<tr>
				<td>ABS/Traction Control</td>
				<td><%=bracket%></td>
				<td><%=absPrice%></td>
			</tr>
			<tr>
				<td>Side Impact Air Bags</td>
				<td><%=airbags%></td>
				<td><%=airbagsPrice%></td>
			</tr>
			<tr>
				<td>Power Moonroof</td>
				<td><%=moonroof%></td>
				<td><%=moonroofPrice%></td>
			</tr>
			<tr>
				<td><b>Total Cost</b></td>
				<td></td>
				<td><%=totalCost%></td>
			</tr>
		</table>
</body>
</html>