<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Collection"%>
<%@page import="com.google.common.collect.Multimap"%>
<%@page import="com.nrkpj.commetial.hrm.core.dos.House"%>
<%@page import="com.google.common.base.Function"%>
<%@page import="com.google.common.collect.Multimaps"%>
<%@page import="com.google.common.collect.Maps"%>
<%@page import="java.util.Set"%>
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
			<th width="150px;">Service Number</th>
			<th width="100px;">Arrears</th>
		</tr>
	</thead>
	<tbody>
		<%
		    MeterCache<CurrentMeter> currentMeterCache = ServiceFactory.getService("currentMeterCache");
		    Multimap<House, CurrentMeter> map = Multimaps.index(currentMeterCache.getEntities(), new Function<CurrentMeter, House>() {
		        public House apply(CurrentMeter meter) {
		            return meter.getHouse();
		        }
		    });
		    for (House house : map.keySet()) {
		        boolean houseShown = false;
		        Collection<CurrentMeter> entries = map.get(house);
		        for (CurrentMeter currentMeter : entries) {
		%>
		<tr>
			<%
			    if (!houseShown) {
			%>
			<td rowspan="<%=entries.size()%>" align="center"><%=currentMeter.getHouse().getName()%></td>
			<%
			    houseShown = true;
			            }
			%>
			<td align="center">
				<%
				    if (currentMeter.isSubMeter()) {
				%> Submeter (<%=currentMeter.getParentMeter().getScno()%>) <%
				    } else {
				%> <%=currentMeter.getScno()%> <%
     }
 %>
			</td>
			<td align="center"><%=currentMeter.getArrears()%></td>
		</tr>
		<%
		    }
		    }
		%>
	</tbody>
</table>