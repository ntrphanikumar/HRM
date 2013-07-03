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
<%@page
	import="com.nrkpj.commetial.hrm.core.services.AmountService"%>

<%
    HouseCache houseCache = ServiceFactory.getService(HouseCache.class);
    String houseId = request.getParameter("houseId");
    if (StringUtils.isBlank(houseId)) {
        response.sendRedirect("portionbills.jsp?houseId=" + houseCache.getEntities().iterator().next().getHouseId());
    }
%>

<html>
<head>
<title>Portion Charges</title>
<link href="../css/hrm.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<div class="heading" align='center'>Charges</div>
	<div class="block" style="width: 50%; float: left; margin-left: 25%">
		<form id="houseid" method="get" action="portionbills.jsp">
			House: 
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
		</form>
		<table border="1" style="width: 100%">
			<tbody>
				<%
				    PortionMeterCache<CurrentMeter> portionCurrentMeterCache = ServiceFactory.getService("portionCurrentMeterCache");
								    PortionMeterCache<WaterMeter> portionWaterMeterCache = ServiceFactory.getService("portionWaterMeterCache");

								    AmountService electricityAmountService = ServiceFactory.getService(AmountService.class);

								    Set<PortionMeter<? extends AbstractMeter>> portionMeters = new HashSet<PortionMeter<? extends AbstractMeter>>(
								            portionCurrentMeterCache.getPortionMetersByHouseId(Integer.parseInt(houseId)));
								    portionMeters.addAll(portionWaterMeterCache.getPortionMetersByHouseId(Integer.parseInt(houseId)));

								    Multimap<Portion, PortionMeter<? extends AbstractMeter>> map = Multimaps.index(portionMeters,
								            new Function<PortionMeter<? extends AbstractMeter>, Portion>() {
								                public Portion apply(PortionMeter<? extends AbstractMeter> portionCurrentMeter) {
								                    return portionCurrentMeter.getPortion();
								                }
								            });

								    for (Portion portion : map.keySet()) {
								        Collection<PortionMeter<? extends AbstractMeter>> portionCurrentMeters = map.get(portion);
								        Multimap<String, PortionMeter<? extends AbstractMeter>> metersByDisplay = Multimaps.index(portionCurrentMeters,
								                new Function<PortionMeter<? extends AbstractMeter>, String>() {
								                    public String apply(PortionMeter<? extends AbstractMeter> portionMeter) {
								                        return portionMeter.getDisplay();
								                    }
								                });
								        boolean isPortionShown = false;
								        for (String display : metersByDisplay.keySet()) {
								            boolean isCommentShown = false;
								            Collection<PortionMeter<? extends AbstractMeter>> metersForComment = metersByDisplay.get(display);
								            for (PortionMeter<? extends AbstractMeter> portionCurrentMeter : metersForComment) {
				%>
				<tr>
					<%
					    if (!isPortionShown) {
					%>
					<td align="center" rowspan="<%=portionCurrentMeters.size()%>"><%=portionCurrentMeter.getPortion().getName()%></td>
					<%
					    isPortionShown = true;
					                }
					                if (!isCommentShown) {
					%>
					<td align="center" rowspan="<%=metersForComment.size()%>"><%=portionCurrentMeter.getDisplay()%></td>
					<%
					    isCommentShown = true;
					                }
					%>
					<td align="center"><%=electricityAmountService.getAmount(portionCurrentMeter)%></td>
				</tr>
				<%
				    }
				        }
				    }
				%>
			</tbody>
		</table>
	</div>
</body>
</html>