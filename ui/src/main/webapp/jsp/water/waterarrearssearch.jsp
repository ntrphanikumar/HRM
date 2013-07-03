
<%@page import="com.nrkpj.commetial.hrm.ui.servlets.ArrearFinder"%>
<form action="/hrm/arrearfinder" method="get" class="block-form">
	<input type="hidden" name="itemType" value="water" />
	<div class="form-row">
		<div class="form-col-label">Can Number:</div>
		<div class="form-col-value" style="float: left;">
			<input type="text" name="can" class="input-scno" />
		</div>
		<input type="submit" value="Find" />
	</div>
	<%
	    if (request.getSession().getAttribute(ArrearFinder.WATER_ARREARS) != null) {
	%>
	<%=request.getSession().getAttribute(ArrearFinder.WATER_ARREARS)%>
	<%
	    request.getSession().removeAttribute(ArrearFinder.WATER_ARREARS);
	    }
	%>
</form>
