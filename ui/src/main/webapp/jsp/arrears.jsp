<html>
<head>
<title>Arrear finder</title>
<link href="../css/hrm.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<div class="heading" align='center'>Arrear Finder</div>
	<div class="block" style="width: 45%; float: left;">
		<div class="sub-heading" style="margin-left: 120px">Electricity
			bill details</div>
		<div style="height: 100px;">
			<%@ include file="electricity/electricityarrearssearch.jsp"%>
		</div>
		<div
			style="clear: both; height: 300px; overflow: auto; margin-top: 20px">
			<%@ include file="electricity/electricityarrearslist.jsp"%>
		</div>
	</div>

	<div class="block" style="width: 45%; float: left;">
		<div class="sub-heading" style="margin-left: 120px">Water bill
			details</div>
		<div style="height: 100px">
			<%@ include file="water/waterarrearssearch.jsp"%>
		</div>
		<div
			style="clear: both; height: 300px; overflow: auto; margin-top: 20px">
			<%@ include file="water/waterarrearslist.jsp"%>
		</div>
	</div>
</body>
</html>