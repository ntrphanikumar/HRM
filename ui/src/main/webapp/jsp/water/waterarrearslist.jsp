<%@page import="java.util.Collection"%>
<%@page import="com.google.common.base.Function"%>
<%@page import="com.google.common.collect.Multimaps"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.House"%>
<%@page import="com.google.common.collect.Multimap"%>
<%@page import="java.util.Set"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.WaterMeter"%>
<%@page
	import="com.nrkpj.commetial.hrm.core.services.APCPDCLArrearService"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.CurrentMeter"%>
<%@page import="com.nrkpj.commetial.hrm.core.cache.MeterCache"%>
<%@page import="com.nrkpj.commetial.hrm.core.cache.HouseCache"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.nrkpj.commetial.hrm.utils.ServiceFactory"%>
<table border="1" style="width: 100%">
	<thead>
		<tr>
			<th width="150px;">House</th>
			<th width="150px;">Can Number</th>
			<th width="100px;">Arrears</th>
		</tr>
	</thead>
	<tbody>
		<%
		    MeterCache<WaterMeter> waterMeterCache = ServiceFactory.getService("waterMeterCache");
		    Multimap<House, WaterMeter> watersmap = Multimaps.index(waterMeterCache.getEntities(), new Function<WaterMeter, House>() {
		        public House apply(WaterMeter waterMeter) {
		            return waterMeter.getHouse();
		        }
		    });
		    for (House house : watersmap.keySet()) {
		        boolean houseShown = false;
		        Collection<WaterMeter> waterEntries = watersmap.get(house);
		        for (WaterMeter waterMeter : waterEntries) {
		%>
		<tr>
			<%
			    if (!houseShown) {
			%>
			<td rowspan="<%=waterEntries.size()%>" align="center"><%=house.getName()%></td>
			<%
			    houseShown = true;
			            }
			%>
			<td align="center"><%=waterMeter.getCanNumber()%></td>
			<td align="center"><%=waterMeter.getArrears()%></td>
		</tr>
		<%
		    }
		    }
		%>
	</tbody>
</table>