
<%@page import="com.nrkpj.commetial.hrm.ui.servlets.ArrearFinder"%>
<form action="/hrm/arrearfinder" method="get" class="block-form">
	<div>
		<input type="hidden" name="itemType" value="power" /> <input
			type="hidden" name="circle" value="RRN" />
		<div class="form-row">
			<div class="form-col-label">District :</div>
			<div class="form-col-value">Ranga Reddy</div>
		</div>
		<div class="form-row">
			<div class="form-col-label">ERO :</div>
			<select name="ero" class="form-col-value"
				style="font-size: 15px; font-style: normal;">
				<option value="13">Saroor Nagar</option>
				<option value="14">Champapet</option>
			</select>
		</div>
		<div class="form-row">
			<div class="form-col-label">Service Number:</div>
			<div class="form-col-value" style="float: left;">
				<input type="text" name="scno" class="input-scno" />
			</div>
			<input type="submit" value="Find" />
		</div>
		<%
		    if (request.getSession().getAttribute(ArrearFinder.CURRENT_ARREARS) != null) {
		%>
		<%=request.getSession().getAttribute(ArrearFinder.CURRENT_ARREARS)%>
		<%
		    request.getSession().removeAttribute(ArrearFinder.CURRENT_ARREARS);
		    }
		%>
	</div>
</form>
