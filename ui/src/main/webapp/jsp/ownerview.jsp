<%@page import="java.sql.Connection"%>
<%@page import="java.util.Date"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.ComputedPortion"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.google.common.collect.Sets"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.WaterMeter"%>
<%@page import="java.util.Set"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.AbstractMeter"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.CurrentMeter"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.PortionMeter"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.House"%>
<%@page import="com.nrkpj.commetial.hrm.core.cache.PortionMeterCache"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.nrkpj.commetial.hrm.utils.ServiceFactory"%>
<%@page import="com.nrkpj.commetial.hrm.core.cache.HouseCache"%>
<%@page import="java.util.Collection"%>
<%@page import="com.google.common.base.Function"%>
<%@page import="com.google.common.collect.Multimaps"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.Portion"%>
<%@page import="com.google.common.collect.Multimap"%>
<%@page import="com.nrkpj.commetial.hrm.core.services.AmountService"%>

<%
    HouseCache houseCache = ServiceFactory.getService(HouseCache.class);
    String houseId = request.getParameter("houseId");
    if (StringUtils.isBlank(houseId)) {
        response.sendRedirect("ownerview.jsp?houseId=" + houseCache.getEntities().iterator().next().getHouseId());
    } else {
%>

<html>
<head>
<title>Portion wise charges</title>
<link href="../css/hrm.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	function calculateNewTotal(totalElementId,fixedtotal,miscTextField){
		if(miscTextField.value.length==0){
			miscTextField.value=0;
		}
		if(isNaN(miscTextField.value)){
			miscTextField.value='';
		}else{
			document.getElementById(totalElementId).innerHTML=(parseFloat(fixedtotal)+parseFloat(miscTextField.value)).toFixed(1);
		}
	}
</script>
</head>
<body>
	<div class="contianer">
		<form id="houseid" method="get" class="form" action="ownerview.jsp">
			<div class="controllerWrapper">
				<div class="controller">Charges for house:</div>
				<select name="houseId"
					onchange="document.getElementById('houseid').submit()">
					<%
					    for (House house : houseCache.getEntities()) {
					%>
					<option value="<%=house.getHouseId()%>"
						<%=houseId.equals(house.getHouseId() + "") ? "selected='selected'" : ""%>><%=house.getName()%></option>
					<%
					    }
					%>
				</select>
				<div class="controller">
					as on
					<%=new Date()%></div>
				<div class="toggle">
					<%
					    House house = houseCache.getById(Integer.parseInt(houseId));
					        AmountService electricityAmountService = ServiceFactory.getService(AmountService.class);
					        boolean showOwner = Boolean.parseBoolean(request.getParameter("showowner"));
					        if (!showOwner) {
					%>
					<a href="ownerview.jsp?houseId=<%=houseId%>&showowner=true">Show
						Owner</a>
					<%
					    } else {
					%>
					<a href="ownerview.jsp?houseId=<%=houseId%>&showowner=false">Hide
						Owner</a>
					<%
					    }
					%>
				</div>
			</div>
		</form>
		<table border="0" cellpadding="0" cellspacing="0"
			class="owner-view-table">
			<thead style="background-color: AppWorkspace">
				<tr>
					<th rowspan="2" class="owner-view-th header-top-row" align="left">Portion</th>
					<th rowspan="2" class="owner-view-th header-top-row" align="right">Rent</th>
					<th rowspan="2" class="owner-view-th header-top-row" align="right">Current
						Bill</th>
					<th colspan="2" class="owner-view-th header-top-row" align="center">Water
						Bill</th>
					<th rowspan="2" class="owner-view-th header-top-row" align="center">Misc</th>
					<th rowspan="2" class="owner-view-th header-top-row" align="right">Total</th>
				</tr>
				<tr>
					<th class="owner-view-th" align="center">Motor</th>
					<th class="owner-view-th" align="center">Supply</th>
				</tr>
			</thead>
			<tbody>
				<%
				    for (Portion portionBasic : house.getPortions()) {
				            ComputedPortion portion = new ComputedPortion(portionBasic, electricityAmountService);
				            if (!portionBasic.isOwner() || showOwner) {
				%>
				<tr class='owner-view-tr <%=portion.getOwnerClass()%>'>
					<td class="owner-view-td" align="left"><%=portion.getName()%></td>
					<td class="owner-view-td" align="right"><%=portion.getRent()%></td>
					<td class="owner-view-td" align="right"><%=portion.getCurrentBill()%></td>
					<td class="owner-view-td" align="center"><%=portion.getWaterMotorBill()%></td>
					<td class="owner-view-td" align="center"><%=portion.getWaterBill()%></td>
					<td class="owner-view-td" align="center"><input type="text"
						value="0" style="width: 50px; text-align: right;"
						onchange="calculateNewTotal('portion-<%=portionBasic.getPortionId()%>-total',<%=portion.getTotal()%>,this)" /></td>
					<td class="owner-view-td" align="right"><span
						id='portion-<%=portionBasic.getPortionId()%>-total'><%=portion.getTotal()%></td>
				</tr>
				<%
				    }
				        }
				%>
			</tbody>
		</table>
	</div>
</body>
</html>
<%
    }
%>